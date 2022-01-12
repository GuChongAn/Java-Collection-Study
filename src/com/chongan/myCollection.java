package com.chongan;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * Collection的根接口，所谓collection，表示一组特定元素，
 * JDK不为此接口提供直接实现，具体实现在List或Set这些本接口的子接口中提供。
 * 此接口常用于传递集合。
 * <br><br>
 * <b>构造函数</b><br>
 * 约定所有实现Collection接口的类必须实现两个构造函数，一个是默认的空构造函数，
 * 另一个接收一个Collection类型的参数，这表示复制传入的Collection构造一个新集合
 * <br><br>
 * <b>可选方法</b><br>
 * 某些方法被标记为可选，如果实现Collection接口的类没有实现这些方法，则应标记
 * 这些方法抛出UnsupportedOperationException
 * <br><br>
 * <b>不合格元素</b><br>
 * 某些Collection的实现类可能对包含的元素有要求，当在不符合要求的元素上执行操作（查询，添加等）时，
 * 根据实现的不同，可能会引发NullPointerException或者ClassCastException异常，也可能会成功执行，
 * 但是返回false
 * <br><br>
 * @param <E> 集合存储元素的类型
 * @author GuChongan
 */

public interface myCollection<E> extends Iterable<E> {

    /**
     * @return 集合元素的数量
     */
    int size();

    /**
     * @return 当集合中没有元素时，返回true
     */
    boolean isEmpty();

    /**
     * @param o 需要查询是否在集合中的元素
     * @return 当且仅当集合中包含有o时，返回true
     */
    boolean contains(Object o);

    /**
     * @return 返回一个迭代器
     */
    Iterator<E> iterator();

    /**
     * @return 返回一个包含集合所有元素的数组
     */
    Object[] toArray();

    /**
     * @param a 集合元素传入的数组
     * @param <T> 集合存储元素的类型
     * @return 返回一个包含集合所有元素的数组，将集合元素存在a中（如果存得下，否则分配新数组）
     */
    <T> T[] toArray(T[] a);

    /**
     * @param generator 根据指定类型和长度构造数组的函数
     * @param <T> 集合存储元素的类型
     * @return 包含集合所有元素的数组
     */
    default <T> T[] toArray(IntFunction<T[]> generator) {
        return toArray(generator.apply(0));
    }

    /**
     * 添加元素
     * @param e 要添加的元素
     * @return 如果成功添加则返回true
     */
    boolean add(E e);

    /**
     * 删除元素
     * @param e 要删除的元素
     * @return 如果删除成功则返回true
     */
    boolean remove(Object e);

    /**
     * 判断集合中是否包含有c中的所有元素
     * @param c 需要判断是否包含的集合
     * @return 如果集合c包含在本集合中，则返回true
     */
    boolean containsAll(myCollection<?> c);

    /**
     * 添加集合c中的所有元素
     * @param c 待添加的集合
     * @return 如果添加成功，则返回true
     */
    boolean addAll(myCollection<? extends E> c);

    /**
     * 删除集合c中的所有元素
     * @param c 待删除的集合
     * @return 如果删除成功，则返回true
     */
    boolean removeAll(myCollection<?> c);

    /**
     * 删除filter中包含的元素
     * @param filter 一个predicate，当元素成功删除时返回true
     * @return 如果发生删除，则返回true
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * 保留集合c中包含的元素，也就是删除集合c中不包含的元素
     * @param c 需要留下的元素的集合
     * @return 如果集合发生变化，则返回true
     */
    boolean retainAll(myCollection<?> c);

    /**
     * 删除集合中的所有元素
     */
    void clear();

    /**
     * 判断两个集合是否相等
     * @param o 需要判断相等的集合
     * @return 如果相等则返回true
     */
    boolean equals(Object o);

    /**
     * @return 集合的hash code
     */
    int hashCode();
}
