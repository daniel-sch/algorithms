import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SuperList<T extends Comparable<T>> implements List<T> {
	private Item mFirst;
	private Item mLast;
	private int mSize;

	public boolean add(T e) {
		if (mSize == 0)
			mLast = mFirst = new Item(null, null, e);
		else
			mLast = mLast.next = new Item(mLast, null, e);

		mSize++;
		return true;
	}

	public void add(int index, T element) {
		if (index < 0 || index > mSize)
			throw new IndexOutOfBoundsException();
		// delegate to add(T e) if possible
		if (mSize == 0 || index == mSize) {
			this.add(element);
			return;
		}

		Item cur = getItem(index);
		addItemBefore(cur, element);

		mSize++;
	}

	public boolean addAll(Collection<? extends T> c) {
		if (c.isEmpty())
			return false;
		for (T o : c)
			add(o);

		return true;
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		if (index < 0 || index > mSize)
			throw new IndexOutOfBoundsException();
		if (c.isEmpty())
			return false;

		Item cur = getItem(index);
		for (T o : c) {
			addItemBefore(cur, o);
			mSize++;
		}
		return true;
	}

	public void clear() {
		mSize = 0;
		mFirst = mLast = null;
	}

	public boolean contains(Object o) {
		return findItem(o) != null;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!this.contains(o))
				return false;
		}
		return true;
	}

	public T get(int index) {
		if (index < 0 || index > mSize - 1)
			throw new IndexOutOfBoundsException();
		return getItem(index).data;
	}

	public int indexOf(Object o) {
		Item cur = mFirst;
		for (int i = 0; i < mSize; i++) {
			if (o == null ? cur.data == null : o.equals(cur.data))
				return i;
			cur = cur.next;
		}
		return -1;
	}

	public boolean isEmpty() {
		return (mSize == 0);
	}

	public Iterator<T> iterator() {
		return listIterator();
	}

	public int lastIndexOf(Object o) {
		Item cur = mLast;
		for (int i = mSize - 1; i >= 0; i--) {
			if (o == null ? cur.data == null : o.equals(cur.data))
				return i;
			cur = cur.prev;
		}
		return -1;
	}

	public ListIterator<T> listIterator() {
		return new SuperListIterator(mFirst, 0, this);
	}

	public ListIterator<T> listIterator(int index) {
		if (index < 0 || index > mSize)
			throw new IndexOutOfBoundsException();
		return new SuperListIterator(getItem(index), index, this);
	}

	public boolean remove(Object o) {
		Item i = findItem(o);
		if (i == null)
			return false;

		removeItem(i);
		mSize--;
		return true;
	}

	public T remove(int index) {
		if (index < 0 || index > mSize - 1)
			throw new IndexOutOfBoundsException();

		Item i = getItem(index);
		removeItem(i);
		mSize--;
		return i.data;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object o : c)
			ret |= remove(o);
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ret = false;
		Item cur = mFirst;
		do {
			if (!c.contains(cur.data)) {
				removeItem(cur);
				mSize--;
				ret = true;
			}
		} while ((cur = cur.next) != null);
		return ret;
	}

	public T set(int index, T element) {
		if (index < 0 || index > mSize - 1)
			throw new IndexOutOfBoundsException();

		Item i = getItem(index);
		T d = i.data;
		i.data = element;
		return d;
	}

	public int size() {
		return mSize;
	}

	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > mSize || fromIndex > toIndex)
			throw new IndexOutOfBoundsException();

		// doesn't implement this method for now, because it's to difficult
		throw new UnsupportedOperationException();
	}

	public Object[] toArray() {
		Object[] a = new Object[mSize];
		Item cur = mFirst;
		for (int i = 0; i < mSize; i++) {
			a[i] = cur.data;
			cur = cur.next;
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	public <E> E[] toArray(E[] a) {
		// dirty dirty hack
		List<E> l = new ArrayList<E>(mSize);
		for (T e : this)
			l.add((E) e);
		return l.toArray(a);
	}

	private Item getItem(int index) {
		Item cur = mFirst;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}
		return cur;
	}

	private Item findItem(Object o) {
		Item cur = mFirst;
		do {
			if (o == null ? cur.data == null : o.equals(cur.data))
				return cur;
		} while ((cur = cur.next) != null);
		return null;
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
	}

	private void addItemBefore(Item o, T element) {
		if (o == null)
			add(element);
		else if (o.prev == null)
			mFirst = mFirst.prev = new Item(null, mFirst, element);
		else {
			Item n = new Item(o.prev, o, element);
			o.prev.next = n;
			o.prev = n;
		}
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
			list.mSize++;
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

	protected Object clone() throws CloneNotSupportedException {
		List<T> l = new SuperList<T>();
		l.addAll(this);
		return l;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		Collection<T> c = (Collection<T>) obj;
		if (c.size() != mSize)
			return false;
		Iterator<T> t1 = this.iterator();
		Iterator<T> t2 = c.iterator();
		while (t1.hasNext()) {
			if (!t1.next().equals(t2.next()))
				return false;
		}
		return true;
	}

	public int hashCode() {
		int hashCode = 1;
		Iterator<T> i = this.iterator();
		while (i.hasNext()) {
			T obj = i.next();
			hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
		}
		return hashCode;
	}

	public String toString() {
		if (mSize == 0)
			return "[]";
		Iterator<T> iter = iterator();
		StringBuilder out = new StringBuilder();
		out.append("[");
		for (int i = 0; i < mSize - 1; i++) {
			out.append(iter.next());
			out.append(", ");
		}
		out.append(iter.next());
		out.append("]");
		return out.toString();
	}
}
