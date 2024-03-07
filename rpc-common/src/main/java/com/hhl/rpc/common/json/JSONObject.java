package com.hhl.rpc.common.json;

import java.io.Serializable;
import java.util.*;

public class JSONObject extends JSON implements Map<String, Object>, Cloneable, Serializable {


    public Map<String, Object> innerMap;

    private final static int DEFAULT_CAPABILITY = 16;

    public JSONObject() {
        this.innerMap = new HashMap<>(DEFAULT_CAPABILITY);
    }

    public JSONObject(Map<String, Object> map) {
        this.innerMap = map;
    }

    public JSONObject(int capability, boolean order) {
        if (order) {
            this.innerMap = new LinkedHashMap<>(capability);
        } else {
            this.innerMap = new HashMap<>(capability);
        }
    }

    @Override
    public int size() {
        return this.innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.innerMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.innerMap.get(key);
    }

    public JSONObject getJSONObject(Object key) {
        if (!this.innerMap.containsKey(key))
            return null;
        Object value = this.innerMap.get(key);

        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }

        if (value instanceof Map) {
            return new JSONObject((Map) value);
        }

        if (value instanceof String) {
            return parseObject((String) value);
        }
        return parseObject(toJSONString(value));
    }

    public JSONArray getJSONArray(Object key) {
        if (!this.innerMap.containsKey(key))
            return null;
        Object value = this.innerMap.get(key);

        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }

        if (value instanceof List) {
            return new JSONArray((List) value);
        }

        if (value instanceof String) {
            return parseArray(value.toString());
        }

        return parseArray(toJSONString(value));
    }

    public Object put(String key, Object value) {
        return this.innerMap.put(key, value);
    }

    public JSONObject fluentPut(String key, Object value) {
        this.innerMap.put(key, value);
        return this;
    }

    @Override
    public Object remove(Object key) {
        return this.innerMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        this.innerMap.putAll(map);
    }

    public JSONObject fluentPutAll(Map<? extends String, ?> map) {
        this.innerMap.putAll(map);
        return this;
    }

    public String getString(String key) {
        return this.innerMap.containsKey(key) && this.innerMap.get(key) !=null ? this.innerMap.get(key).toString() : null;
    }

    public Long getLong(String key) {
        Object value = get(key);
        return TypeUtils.castToLong(value);
    }

    public Float getFloat(String key) {
        if (!this.innerMap.containsKey(key))
            return null;
        return Float.parseFloat(this.innerMap.get(key).toString());
    }

    public Double getDouble(String key) {
        if(!this.innerMap.containsKey(key))
            return null;
        return Double.parseDouble(this.innerMap.get(key).toString());
    }

    public Integer getInteger(String key) {
        Object value = get(key);
        return TypeUtils.castToInteger(value);
    }

    public int getIntValue(String key) {
        Object value = get(key);
        Integer result = TypeUtils.castToInteger(value);
        if (result == null) {
            return 0;
        }
        return result.intValue();
    }

    public boolean getBooleanValue(String key) {
        Object value = get(key);

        Boolean booleanVal = TypeUtils.castToBoolean(value);
        if (booleanVal == null) {
            return false;
        }

        return booleanVal.booleanValue();
    }

    @Override
    public void clear() {
        this.innerMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.innerMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.innerMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.innerMap.entrySet();
    }

    @Override
    public int hashCode() {
        return this.innerMap.hashCode();
    }

    @Override
    public JSONObject clone() {
        return new JSONObject(new HashMap<>(this.innerMap));
    }

}
