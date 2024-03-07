package com.hhl.rpc.common.json;


import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * 参考fastjson的JSONObject的API使用gson进行底层实现
 */
public class JSONArray<T> extends JSON implements List<T>, Serializable {

    private final List<T> innerList;

    public JSONArray() {
        this.innerList = new ArrayList<>();
    }

    public JSONArray(List<T> list) {
        this.innerList = list;
    }

    @Override
    public int size() {
        return this.innerList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.innerList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.innerList.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.innerList.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.innerList.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return this.innerList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.innerList.toArray(a);
    }

    @Override
    public boolean add(T o) {
        return this.innerList.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return this.innerList.remove(o);
    }

    @Override
    public boolean containsAll( Collection<?> c) {
        return this.innerList.containsAll(c);
    }

    @Override
    public boolean addAll( Collection<? extends T> c) {
        return this.innerList.addAll(c);
    }

    @Override
    public boolean addAll(int index,  Collection<? extends T> c) {
        return this.innerList.addAll(index, c);
    }

    @Override
    public boolean removeAll( Collection<?> c) {
        return this.innerList.removeAll(c);
    }

    @Override
    public boolean retainAll( Collection<?> c) {
        return this.innerList.retainAll(c);
    }

    @Override
    public void clear() {
        this.innerList.clear();
    }

    @Override
    public T get(int index) {
        return this.innerList.get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.innerList.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.innerList.add(index, element);
    }

    @Override
    public T remove(int index) {
        return this.innerList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.innerList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.innerList.lastIndexOf(o);
    }


    
    @Override
    public ListIterator<T> listIterator() {
        return this.innerList.listIterator();
    }

    
    @Override
    public ListIterator<T> listIterator(int index) {
        return this.innerList.listIterator(index);
    }

    
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.innerList.subList(fromIndex, toIndex);
    }

    public JSONArray fluentAdd(T o) {
        this.innerList.add(o);
        return this;
    }


}
