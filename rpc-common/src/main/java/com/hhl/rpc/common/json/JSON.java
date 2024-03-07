package com.hhl.rpc.common.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public abstract class JSON {
    private static final ObjectMapper mapper;

    static {
        mapper = (new ObjectMapper()).registerModule(new JavaTimeModule()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static JSONObject parseObject(String text) {
        try {
            return mapper.readValue(text, JSONObject.class);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parseObject: " + ex.getMessage(), ex);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(text, typeReference);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parseObject: " + ex.getMessage(), ex);
        }
    }

    public static <T> T parseObject(String text, Class<T> cls) {
        try {
            return mapper.readValue(text, cls);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parseObject: " + ex.getMessage(), ex);
        }
    }


    public static String toJSONString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred to execute toJSONString: " + ex.getMessage(), ex);
        }
    }

    public static JSONArray parseArray(String text) {
        try {
            return mapper.readValue(text, JSONArray.class);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parseArray: " + ex.getMessage(), ex);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> cls) {
        try {
            return mapper.readValue(text, mapper.getTypeFactory().constructCollectionType(List.class, cls));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parseArray: " + ex.getMessage(), ex);
        }
    }

    public static boolean isJSON(String text) {
        try {
            JsonNode jsonNode = mapper.readTree(text);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isArray(String text) {
        try {
            JsonNode jsonNode = mapper.readTree(text);
            return jsonNode.isArray();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to check if JSONArray for: " + text, ex);
        }
    }

    public static boolean isObject(String text) {
        try {
            JsonNode jsonNode = mapper.readTree(text);
            return jsonNode.isObject();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to check if JSONObject for: " + text, ex);
        }
    }


    @Override
    public String toString() {
        return this.toJSONString();
    }

    public String toJSONString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred to execute toJSONString: " + ex.getMessage(), ex);
        }

    }


}
