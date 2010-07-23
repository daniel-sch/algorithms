import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

public class SuperArray<T extends Comparable<T>> extends AbstractList<T>
		implements List<T>, RandomAccess {

	private Object[] data;

	private int size;

	public SuperArray() {
		this(10);
	}

	public SuperArray(Collection<? extends T> c) {
		data = c.toArray();
		size = data.length;
	}

	public SuperArray(int initialCapacity) {
		if (initialCapacity <= 0)
			throw new IllegalArgumentException("Illegal Capacity: "
					+ initialCapacity);

		data = new Object[initialCapacity];
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (data.length == size)
			data = Arrays.copyOf(data, data.length * 2);

		if (index == size) {
			data[index] = element;
		} else {
			System.arraycopy(data, index, data, index + 1, size - index);
			data[index] = element;
		}
		size++;
	}

	public void bubbleSort() {
		int end = size - 1;
		while (end > 0) {
			int newEnd = 0;
			for (int i = 0; i < end; i++) {
				if (compSwap(i, i + 1))
					newEnd = i;
			}
			end = newEnd;
		}
	}

	@Override
	public void clear() {
		data = new Object[10];
		size = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return (T) data[index];
	}

	public void insertionSort() {
		for (int i = 0; i < size; i++) {
			int j = binarySearch(0, i, data[i]);
			if (j != i) {
				Object o = data[i];
				System.arraycopy(data, j, data, j + 1, i - j);
				data[j] = o;
			}
		}
	}

	public void quickSort() {
		if (size <= 1)
			return;

		quickSort_rec(0, size - 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();

		Object d = data[index];
		System.arraycopy(data, index + 1, data, index, size - index - 1);
		data[--size] = null;
		return (T) d;
	}

	public void selectionSort() {
		for (int i = 0; i < size; i++) {
			int cur = i;
			for (int j = i + 1; j < size; j++) {
				if (comp(cur, j) > 0)
					cur = j;
			}
			swap(i, cur);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T set(int index, T element) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();

		Object d = data[index];
		data[index] = element;
		return (T) d;
	}

	@Override
	public int size() {
		return size;
	}

	private int binarySearch(int start, int end, Object x) {
		while (start < end) {
			int middle = start + (end - start) / 2;
			if (comp(middle, x) == 0)
				return middle;
			if (comp(middle, x) > 0)
				end = middle;
			else
				start = middle + 1;
		}
		return end;
	}

	@SuppressWarnings("unchecked")
	private int comp(int i1, int i2) {
		return ((Comparable<T>) data[i1]).compareTo((T) data[i2]);
	}

	@SuppressWarnings("unchecked")
	private int comp(int i1, Object i2) {
		return ((Comparable<T>) data[i1]).compareTo((T) i2);
	}

	private boolean compSwap(int i1, int i2) {
		if (comp(i1, i2) > 0) {
			swap(i1, i2);
			return true;
		}
		return false;
	}

	private void quickSort_rec(int start, int end) {
		swap(start + (end - start), end);
		int i = start;
		int j = end - 1;
		Object pivot = data[end];
		while (i < j) {
			while (comp(i, pivot) < 0 && i < end)
				i++;
			while (comp(j, pivot) > 0 && j > start)
				j--;
			if (i < j)
				swap(i, j);
		}
		if (comp(i, pivot) > 0)
			swap(i, end);
		if (start < i - 1)
			quickSort_rec(start, i - 1);
		if (i + 1 < end)
			quickSort_rec(i + 1, end);
	}

	private void swap(int i1, int i2) {
		Object tmp = data[i1];
		data[i1] = data[i2];
		data[i2] = tmp;
	}
}
