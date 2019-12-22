package ru.vuchobe.util;import java.util.*;import androidx.annotation.Nullable;/** * @param <E> */public class StoreLastArrayList<E> extends AbstractList<E> implements RandomAccess {    private final int capacity;    private final List<E> buff;    private final List<Integer> indexs;    private int size = 0;    private StoreLastArrayList(int capacity){        this.capacity = capacity;        buff = new ArrayList<>(Collections.nCopies(capacity, (E) null));        indexs = new ArrayList<>(Collections.nCopies(capacity, -1));    }    public int capacity() {        return capacity;    }    private int wrapIndex(int i){        int m = i % capacity;        if(m < 0){            m += capacity;        }        return m;    }    @Override    public int size(){        return size;    }    /**     * Может вернуть null     * @param index     * @return     */    @Override    public @Nullable    E get(int index){        if(index < 0 || index >= size()){            throw new IndexOutOfBoundsException(String.format("%s >= %s", index, size()));        }        int pos = wrapIndex(index);        return (indexs.get(pos) == index)? buff.get(pos) : null;    }    @Override    public @Nullable E set(int index, E elem){        if(index < 0 || index >= size()){            throw new IndexOutOfBoundsException();        }        int pos = wrapIndex(index);        int indexOld = indexs.set(pos, index);        E result = buff.set(pos, elem);        return (index == indexOld)? result : null;    }    /**     * Если буфер переполнен будет затирать старые значения     * @param index     * @param elem     */    @Override    public void add(int index, E elem){        if(index < 0 || index > size()){            throw new IndexOutOfBoundsException();        }        int pos = wrapIndex(index);        indexs.set(pos, index);        buff.set(pos, elem);        size++;    }    @Override    public @Nullable E remove(int index){        if(index < 0 || index >= size()){            throw new IndexOutOfBoundsException();        }        int pos = wrapIndex(index);        int indexOld = indexs.set(pos, index);        size--;        return (index == indexOld)? buff.set(pos, null) : null;    }    @Override    public void clear(){        int sizeReal = (size() < capacity())? size() : capacity();        for(int i = 0; i < sizeReal; i++) buff.set(i, null);        size = 0;    }    public static <K> List<K> create(int capacity){        return Collections.synchronizedList(new StoreLastArrayList<K>(capacity));    }}