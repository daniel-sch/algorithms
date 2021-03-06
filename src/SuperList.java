import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class SuperList<T extends Comparable<T>> extends
		AbstractSequentialList<T> implements List<T>, Queue<T>, Deque<T> {
	private class Item implements Comparable<Item> {
		T data;
		Item next;
		Item prev;

		Item(Item prev, Item next, T data) {
			this.prev = prev;
			this.next = next;
			this.data = data;
		}

		@Override
		public int compareTo(Item o) {
			return data.compareTo(o.data);
		}
	}

	private class SuperListDescendingIterator implements Iterator<T> {
		private Item cur;
		private Item last;
		private SuperList<T> list;

		public SuperListDescendingIterator(Item i, SuperList<T> l) {
			cur = i;
			list = l;
		}

		@Override
		public boolean hasNext() {
			return cur != null;
		}

		@Override
		public T next() {
			if (cur == null)
				throw new NoSuchElementException();
			T d = cur.data;
			last = cur;
			cur = cur.prev;
			return d;
		}

		@Override
		public void remove() {
			if (last == null)
				throw new IllegalStateException();
			list.removeItem(last);
			last = null;
		}

	}

	private class SuperListIterator implements ListIterator<T> {
		private Item cur;
		private int index;
		private Item last;
		private SuperList<T> list;

		public SuperListIterator(Item i, int index, SuperList<T> l) {
			cur = i;
			this.index = index;
			list = l;
		}

		@Override
		public void add(T e) {
			last = null;
			list.addItemBefore(cur, e);
			index++;
		}

		@Override
		public boolean hasNext() {
			return cur != null;
		}

		@Override
		public boolean hasPrevious() {
			if (cur == null && list.size() > 0)
				return true;
			return cur.prev != null;
		}

		@Override
		public T next() {
			if (cur == null)
				throw new NoSuchElementException();

			index++;
			T d = cur.data;
			last = cur;
			cur = cur.next;
			return d;
		}

		@Override
		public int nextIndex() {
			return index + 1;
		}

		@Override
		public T previous() {
			if (cur == null)
				cur = list.mLast;
			else
				cur = cur.prev;

			if (cur == null)
				throw new NoSuchElementException();

			index--;
			last = cur;
			return cur.data;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Override
		public void remove() {
			if (last == null)
				throw new IllegalStateException();
			list.removeItem(last);
			last = null;
			index -= 2;
		}

		@Override
		public void set(T e) {
			if (last == null)
				throw new IllegalStateException();
			last.data = e;
		}
	}

	private static final int INSERTIONSORT_THRESHOLD = 6;
	private Item mFirst;
	private Item mLast;

	private int mSize;

	public SuperList() {
	}

	public SuperList(Collection<? extends T> c) {
		this.addAll(c);
	}

	@Override
	public boolean add(T e) {
		addItemBefore(null, e);
		return true;
	}

	@Override
	public void addFirst(T e) {
		addItemBefore(mFirst, e);
	}

	@Override
	public void addLast(T e) {
		add(e);
	}

	public void bubbleSort() {
		if (mSize <= 1)
			return;

		Item end = mLast;
		while (end != mFirst) {
			Item cur = mFirst;
			Item newEnd = mFirst;
			while (cur.next != null) {
				if (cur.compareTo(cur.next) > 0) {
					T tmp = cur.data;
					cur.data = cur.next.data;
					cur.next.data = tmp;
					newEnd = cur;
				} else
					cur = cur.next;
			}
			end = newEnd;
		}
	}

	@Override
	public void clear() {
		mSize = 0;
		mFirst = mLast = null;
	}

	@Override
	public Iterator<T> descendingIterator() {
		return new SuperListDescendingIterator(mLast, this);
	}

	@Override
	public T element() {
		if (mSize == 0)
			throw new NoSuchElementException();
		return mFirst.data;
	}

	@Override
	public T getFirst() {
		return element();
	}

	@Override
	public T getLast() {
		if (mSize == 0)
			throw new NoSuchElementException();
		return mLast.data;
	}

	public void insertionSort() {
		if (mSize <= 1)
			return;

		Item cur = mFirst;
		while (cur.next != null) {
			cur = cur.next;
			if (cur.compareTo(cur.prev) > 0)
				continue;

			T curData = cur.data;
			removeItem(cur);
			Item insertPos = cur.prev.prev;

			while (insertPos != null && curData.compareTo(insertPos.data) < 0)
				insertPos = insertPos.prev;

			if (insertPos == null)
				addItemBefore(mFirst, curData);
			else
				addItemBefore(insertPos.next, curData);
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		Item cur = mLast;
		for (int i = mSize - 1; i >= 0; i--) {
			if (o == null ? cur.data == null : o.equals(cur.data))
				return i;
			cur = cur.prev;
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		return new SuperListIterator(mFirst, 0, this);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		if (index < 0 || index > mSize)
			throw new IndexOutOfBoundsException();
		return new SuperListIterator(getItem(index), index, this);
	}

	public void mergeSort() {
		if (mSize <= 1)
			return;

		ArrayList<SuperList<T>> lists = new ArrayList<SuperList<T>>((mSize
				+ INSERTIONSORT_THRESHOLD - 1)
				/ INSERTIONSORT_THRESHOLD);

		Iterator<T> iter = iterator();

		while (iter.hasNext()) {
			SuperList<T> tmp = new SuperList<T>();
			for (int i = 0; i < INSERTIONSORT_THRESHOLD && iter.hasNext(); i++)
				tmp.add(iter.next());

			tmp.insertionSort();
			lists.add(tmp);
		}

		while (lists.size() > 1) {
			ArrayList<SuperList<T>> tempLists = new ArrayList<SuperList<T>>(
					(lists.size() + 1) / 2);
			for (int i = 0; i < lists.size() / 2; i++)
				tempLists.add(merge(lists.get(i * 2), lists.get(i * 2 + 1)));
			if (lists.size() % 2 == 1)
				tempLists.add(lists.get(lists.size() - 1));
			lists = tempLists;
		}

		SuperList<T> res = lists.get(0);
		this.mFirst = res.mFirst;
		this.mLast = res.mLast;
	}

	@Override
	public boolean offer(T e) {
		return add(e);
	}

	@Override
	public boolean offerFirst(T e) {
		addFirst(e);
		return true;
	}

	@Override
	public boolean offerLast(T e) {
		return offer(e);
	}

	@Override
	public T peek() {
		if (mSize == 0)
			return null;
		return mFirst.data;
	}

	@Override
	public T peekFirst() {
		return peek();
	}

	@Override
	public T peekLast() {
		if (mSize == 0)
			return null;
		return mLast.data;
	}

	@Override
	public T poll() {
		if (mSize == 0)
			return null;
		T d = mFirst.data;
		removeItem(mFirst);
		return d;
	}

	@Override
	public T pollFirst() {
		return poll();
	}

	@Override
	public T pollLast() {
		if (mSize == 0)
			return null;
		T d = mLast.data;
		removeItem(mLast);
		return d;
	}

	@Override
	public T pop() {
		return removeFirst();
	}

	@Override
	public void push(T e) {
		addFirst(e);
	}

	public void quickSort() {
		if (mSize <= 1)
			return;
		quickSort_int(mFirst, mLast);
	}

	@Override
	public T remove() {
		if (mSize == 0)
			throw new NoSuchElementException();
		return poll();
	}

	@Override
	public T removeFirst() {
		return remove();
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}

	@Override
	public T removeLast() {
		if (mSize == 0)
			throw new NoSuchElementException();
		T d = mLast.data;
		removeItem(mLast);
		return d;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		Iterator<T> i = descendingIterator();
		while (i.hasNext()) {
			T e = i.next();
			if (o == null ? e == null : o.equals(e)) {
				i.remove();
				return true;
			}
		}
		return false;
	}

	public void selectionSort() {
		if (mSize <= 1)
			return;

		SuperList<T> newList = new SuperList<T>();
		while (mSize > 0) {
			Item cur = mFirst;
			Item searchPos = cur.next;

			while (searchPos != null) {
				if (cur.compareTo(searchPos) > 0)
					cur = searchPos;
				searchPos = searchPos.next;
			}
			newList.add(cur.data);
			removeItem(cur);
		}
		mFirst = newList.mFirst;
		mLast = newList.mLast;
	}

	@Override
	public int size() {
		return mSize;
	}

	private void addItemBefore(Item o, T element) {
		if (o == null) {
			if (mSize == 0)
				mLast = mFirst = new Item(null, null, element);
			else
				mLast = mLast.next = new Item(mLast, null, element);
		} else if (o.prev == null)
			mFirst = mFirst.prev = new Item(null, mFirst, element);
		else {
			Item n = new Item(o.prev, o, element);
			o.prev.next = n;
			o.prev = n;
		}
		mSize++;
	}

	private void append(SuperList<T> o) {
		mLast.next = o.mFirst;
		o.mFirst.prev = mLast;
		mLast = o.mLast;
		mSize += o.mSize;
	}

	private Item getItem(int index) {
		Item cur = mFirst;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}
		return cur;
	}

	private SuperList<T> merge(SuperList<T> a, SuperList<T> b) {
		// the bigger List (the last element) should always be "a"
		if (a.mLast.compareTo(b.mLast) < 0) {
			SuperList<T> tmp = a;
			a = b;
			b = tmp;
		}
		// if a is completely bigger then b, just concatenate them
		if (a.mFirst.compareTo(b.mLast) > 0) {
			b.append(a);
			return b;
		}

		Item curA = a.mFirst;
		Item curB = b.mFirst;
		while (curA != null && curB != null) {
			if ((curA).compareTo(curB) > 0) {
				T d = curB.data;
				b.removeItem(curB);
				a.addItemBefore(curA, d);
				curB = curB.next;
			} else
				curA = curA.next;
		}

		if (curB != null)
			a.append(b);

		return a;
	}

	private void quickSort_int(Item start, Item end) {
		Item before = start.prev;
		Item after = end.next;
		Item pivot = start;
		Item cur = start.next;
		while (cur != after) {
			if (cur.compareTo(pivot) < 0) {
				T d = cur.data;
				removeItem(cur);
				addItemBefore(pivot, d);
			}
			cur = cur.next;
		}
		if (pivot.prev != before)
			quickSort_int(before == null ? mFirst : before.next, pivot.prev);
		if (pivot.next != after)
			quickSort_int(pivot.next, after == null ? mLast : after.prev);
	}

	private void removeItem(Item o) {
		if (o.prev == null)
			mFirst = o.next;
		else
			o.prev.next = o.next;

		if (o.next == null)
			mLast = o.prev;
		else
			o.next.prev = o.prev;

		mSize--;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		List<T> l = new SuperList<T>();
		l.addAll(this);
		return l;
	}
}
