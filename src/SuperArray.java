import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

public class SuperArray<T extends Comparable<T>> extends AbstractList<T>
		implements List<T>, RandomAccess {

	private static final int INSERTIONSORT_THRESHOLD = 512;

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

	public void heapSort() {
		for (int k = size / 2; k >= 0; k--)
			sink(k, size);

		for (int n = size - 1; n > 0; n--) {
			swap(0, n);
			sink(0, n);
		}
	}

	public void insertionSort() {
		insertionSort_int(0, size);
	}

	public void mergeSort() {
		if (size <= 1)
			return;

		int i = 0;
		while (i < size) {
			insertionSort_int(i, Math.min(size, i + INSERTIONSORT_THRESHOLD));
			i += INSERTIONSORT_THRESHOLD;
		}

		int diff = INSERTIONSORT_THRESHOLD;
		while (diff < size) {
			i = 0;
			Object[] newData = new Object[size];
			while (true) {
				int newI = i + 2 * diff;
				if (newI > size) {
					if (i + diff < size)
						merge(newData, i, size, diff);
					else
						System.arraycopy(data, i, newData, i, size - i);
					break;
				} else
					merge(newData, i, newI, diff);
				i = newI;
			}
			data = newData;
			diff *= 2;
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

	private void insertionSort_int(int start, int end) {
		for (int i = start; i < end; i++) {
			int j = binarySearch(start, i, data[i]);
			if (j != i) {
				Object o = data[i];
				System.arraycopy(data, j, data, j + 1, i - j);
				data[j] = o;
			}
		}
	}

	private void merge(Object[] dest, int start, int end, int diff) {
		int middle = start + diff;
		if (comp(middle - 1, middle) <= 0)
			System.arraycopy(data, 0, dest, 0, size);
		int i = start;
		int j = middle;

		int x = start;
		while (i < middle && j < end) {
			if (comp(i, j) < 0)
				dest[x++] = data[i++];
			else
				dest[x++] = data[j++];
		}
		if (i < middle)
			System.arraycopy(data, i, dest, x, middle - i);
		else
			System.arraycopy(data, j, dest, x, end - j);
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

	private void sink(int i, int size) {
		while (2 * (i + 1) <= size) {
			int j = 2 * (i + 1) - 1;
			if (j < size - 1 && comp(j, j + 1) < 0)
				j++;
			if (comp(i, j) >= 0)
				break;
			swap(i, j);
			i = j;
		}
	}

	private void swap(int i1, int i2) {
		Object tmp = data[i1];
		data[i1] = data[i2];
		data[i2] = tmp;
	}

	public void shellSort() {
		final int[] gaps = { 2147483647, 1131376761, 410151271, 157840433, 58548857,
				21521774, 8810089, 3501671, 1355339, 543749, 213331, 84801,
				27901, 11969, 4711, 1968, 815, 271, 111, 41, 13, 4, 1 };

		for (int k = 0; k < gaps.length; k++) {
			int gap = gaps[k];
			for (int i = gap; i < size; i++)
				for (int j = i; j >= gap && comp(j, j - gap) < 0; j -= gap)
					swap(j, j - gap);
		}
	}
}
