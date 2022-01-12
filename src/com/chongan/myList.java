package com.chongan;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * <b>只为myCollection中没有注释的方法添加注释</b><br>
 * List，一类有序集合，用户可以精准控制元素的插入位置，通过整数索引访问元素，在集合中搜索元素。
 * 与Set不同，List允许同一个集合中存在多个相同元素。
 * <br><br>
 * <b>ListIterator</b><br>
 * List提供一个特殊的Iterator，可以提供插入、替换和双向访问。
 * <br><br>
 * <b>Unmodifiable Lists</b><br>
 * 不可修改的List，List.of和List.copyOf提供了创建不可修改的List的方法，
 * 因为这两个方法都是直接使用ImmutableCollections的方法，而ImmutableCollections类不为public，
 * 无法在此处调用，故不作实现，只作注释
 * <br><br>
 * @author GuChongan
 * @param <E> List中元素的类型
 */
public interface myList<E> extends myCollection<E> {
    int size();

    boolean isEmpty();

    boolean contains(Object e);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    boolean add(E e);

    boolean remove(Object e);

    boolean containsAll(myCollection<?> c);

    boolean addAll(myCollection<? extends E> c);

    /**
     * 根据index索引插入集合c中的所有元素到对应的位置
     * @param index 插入的位置所有
     * @param c 插入的元素集合
     * @return 如果成功插入则返回true
     */
    boolean addAll(int index, myCollection<? extends E> c);

    boolean removeAll(myCollection<?> c);

    boolean retainAll(myCollection<?> c);

    /**
     * 替换list中的所有元素为operator.apply的结果
     * @param operator 对每个元素执行的操作
     */
    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }

    /**
     * 对List中的元素进行排序，实际是通过Arrays的sort方法实现的
     * @param c 排序的比较方法
     */
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }

    void clear();

    boolean equals(Object o);

    /**
     * hash code的计算规则如下
     * <pre>{@code
     *     int hashCode = 1;
     *     for (E e : list)
     *         hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     * }</pre>
     * @return list的hash code
     */
    int hashCode();

    /**
     * 获取指定索引位置的元素
     * @param index 指定的索引
     * @return index位置的元素
     */
    E get(int index);

    /**
     * 替换指定位置的元素
     * @param index 指定的索引
     * @param element 替换元素
     * @return 之前位于index位置的元素
     */
    E set(int index, E element);

    /**
     * 插入元素到指定位置
     * @param index 指定的索引
     * @param element 插入的元素
     */
    void add(int index, E element);

    /**
     * 删除指定位置的元素
     * @param index 指定的索引
     * @return 删除的元素
     */
    E remove(int index);

    /**
     * 得到list中第一个o的位置的索引，没有o则返回-1
     * @param o 搜索的元素
     * @return o位置的索引或者-1
     */
    int indexOf(Object o);

    /**
     * 得到list中最后一个o的位置的索引，没有o则返回-1
     * @param o 搜索的元素
     * @return o位置的索引或者-1
     */
    int lastIndexOf(Object o);

    /**
     * @return 返回ListIterator
     */
    ListIterator<E> listIterator();

    /**
     * @param index 指定的索引
     * @return 返回从指定位置开始的ListIterator
     */
    ListIterator<E> listIterator(int index);

    /**
     * 返回List的一个子list
     * @param fromIndex 子list的开始索引
     * @param toIndex 子list的结束索引
     * @return 子list
     */
    myList<E> subList(int fromIndex, int toIndex);

    /**
     * 为输入的元素构造一个不变的List
     * @param elements 构造集合的元素
     * @param <E> List中元素的类型
     * @return 构造的不变集合
     */
    static <E> myList<E> of(E... elements) {
        return (myList<E>) elements[0];
        /*
        switch (elements.length) { // implicit null check of elements
            case 0:
                @SuppressWarnings("unchecked")
                var list = (List<E>) ImmutableCollections.EMPTY_LIST;
                return list;
            case 1:
                return new ImmutableCollections.List12<>(elements[0]);
            case 2:
                return new ImmutableCollections.List12<>(elements[0], elements[1]);
            default:
                return ImmutableCollections.listFromArray(elements);
        }
        */
    }

    /**
     * 通过调用ImmutableCollections的listCopy方法，构造不可变的List
     * @param c 输入集合
     * @param <E> List中元素的类型
     * @return 构造的不变集合
     */
    static <E> myList<E> copyOf(myCollection<? extends E> c) {
        return (myList<E>) c;
        //return ImmutableCollections.listCopy(c);
    }
}
