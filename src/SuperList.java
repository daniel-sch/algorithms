import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class SuperList<T extends Comparable<T>> extends
		AbstractSequentialList<T> implements List<T>, Queue<T> {
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
	public void clear() {
		mSize = 0;
		mFirst = mLast = null;
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

	@Override
	public int size() {
		return mSize;
	}

	@Override
	public T element() {
		if (mSize == 0)
			throw new NoSuchElementException();
		return mFirst.data;
	}

	@Override
	public boolean offer(T e) {
		return add(e);
	}

	@Override
	public T peek() {
		if (mSize == 0)
			return null;
		return mFirst.data;
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
	public T remove() {
		if (mSize == 0)
			throw new NoSuchElementException();
		return poll();
	}

	private Item getItem(int index) {
		Item cur = mFirst;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}
		return cur;
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

	@Override
	protected Object clone() throws CloneNotSupportedException {
		List<T> l = new SuperList<T>();
		l.addAll(this);
		return l;
	}

	private class Item {
		Item prev;
		Item next;
		T data;

		Item(Item prev, Item next, T data) {
			this.prev = prev;
			this.next = next;
			this.data = data;
		}
	}

	private class SuperListIterator implements ListIterator<T> {
		private Item cur;
		private SuperList<T> list;
		private int index;
		private Item last;

		public SuperListIterator(Item i, int index, SuperList<T> l) {
			cur = i;
			this.index = index;
			list = l;
		}

		public void add(T e) {
			last = null;
			list.addItemBefore(cur, e);
			index++;
		}

		public boolean hasNext() {
			return cur != null;
		}

		public boolean hasPrevious() {
			if (cur == null && list.size() > 0)
				return true;
			return cur.prev != null;
		}

		public T next() {
			if (cur == null)
				throw new NoSuchElementException();

			index++;
			T d = cur.data;
			last = cur;
			cur = cur.next;
			return d;
		}

		public int nextIndex() {
			return index + 1;
		}

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

		public int previousIndex() {
			return index - 1;
		}

		public void remove() {
			if (last == null)
				throw new IllegalStateException();
			list.removeItem(last);
			last = null;
			index -= 2;
		}

		public void set(T e) {
			if (last == null)
				throw new IllegalStateException();
			last.data = e;
		}
	}
}
