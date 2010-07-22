import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class SuperListTest {
	private SuperList<String> l;

	@Before
	public void setUp() throws Exception {
		l = new SuperList<String>();
	}

	@Test
	public void testAdd2CanAddMultiple() {
		l.add(0, "foo");
		assertEquals("foo", l.get(0));
		assertEquals(1, l.size());
		l.add(0, "bar");
		assertEquals("bar", l.get(0));
		assertEquals("foo", l.get(1));
		assertEquals(2, l.size());
		l.add(1, "foobar");
		assertEquals("bar", l.get(0));
		assertEquals("foobar", l.get(1));
		assertEquals("foo", l.get(2));
		assertEquals(3, l.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAdd2ThrowsIndexOutOfBoundsException() {
		l.add(1, "foo");
	}

	@Test
	public void testAddAllWithIndex() {
		l.add("a");
		l.add("d");
		List<String> c = new ArrayList<String>();
		c.add("b");
		c.add("c");
		assertTrue(l.addAll(1, c));
		assertEquals(4, l.size());
		assertEquals("a", l.get(0));
		assertEquals("b", l.get(1));
		assertEquals("c", l.get(2));
		assertEquals("d", l.get(3));
		assertEquals(4, l.size());
	}

	@Test
	public void testAddAllWithoutIndex() {
		l.add("a");
		List<String> c = new ArrayList<String>();
		c.add("b");
		c.add("c");
		assertTrue(l.addAll(c));
		assertEquals(3, l.size());
		assertEquals("a", l.get(0));
		assertEquals("b", l.get(1));
		assertEquals("c", l.get(2));
	}

	@Test
	public void testAddFirst() {
		l.add("a");
		l.addFirst("b");
		assertEquals("b", l.get(0));
		assertEquals("a", l.get(1));
		assertEquals(2, l.size());
	}

	@Test
	public void testBubblesort() {
		l.add("d");
		l.add("e");
		l.add("a");
		l.add("c");
		l.add("b");
		l.bubbleSort();
		assertEquals("[a, b, c, d, e]", l.toString());
	}

	@Test
	public void testCanAdd() {
		assertTrue(l.add("foo"));
		assertEquals("foo", l.get(0));
		assertEquals(1, l.size());
	}

	@Test
	public void testCanAddMultiple() {
		assertTrue(l.add("foo"));
		assertTrue(l.add("bar"));
		assertEquals("foo", l.get(0));
		assertEquals("bar", l.get(1));
		assertEquals(2, l.size());
	}

	@Test
	public void testClear() {
		l.add("foo");
		l.clear();
		assertEquals(0, l.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testClone() throws CloneNotSupportedException {
		l.add("a");
		l.add("b");
		SuperList<String> l2 = (SuperList<String>) l.clone();
		l2.add("c");
		assertEquals(2, l.size());
	}

	@Test
	public void testContains() {
		l.add("foo");
		assertFalse(l.contains("bar"));
		assertTrue(l.contains("foo"));
	}

	@Test
	public void testContainsAll() {
		l.add("a");
		l.add("b");
		l.add("c");
		List<String> c = new ArrayList<String>();
		c.add("a");
		c.add("c");
		assertTrue(l.containsAll(c));
		c.add("d");
		assertFalse(l.containsAll(c));
	}

	@Test
	public void testDescendingIterator() {
		l.add("a");
		l.add("b");
		Iterator<String> i = l.descendingIterator();
		assertTrue(i.hasNext());
		assertEquals("b", i.next());
		assertTrue(i.hasNext());
		assertEquals("a", i.next());
		assertFalse(i.hasNext());
		try {
			i.next();
			fail("i.next() should throw Exception!");
		} catch (NoSuchElementException e) {
		}
	}

	@Test
	public void testDescendingIteratorRemove() {
		l.add("a");
		l.add("b");
		Iterator<String> i = l.descendingIterator();
		try {
			i.remove();
			fail("i.remove should throw Exception!");
		} catch (IllegalStateException e) {
		}
		i.next();
		i.remove();
		assertEquals("a", l.get(0));
		assertEquals(1, l.size());
		i.next();
		i.remove();
		assertTrue(l.isEmpty());
	}

	@Test
	public void testElement() {
		try {
			l.element();
			fail("l.element() should throw excepton!");
		} catch (NoSuchElementException e) {
		}
		l.add("a");
		l.add("b");
		assertEquals("a", l.element());
	}

	@Test
	public void testEquals() {
		l.add("a");
		l.add("b");
		l.add("c");
		List<String> c = new ArrayList<String>();
		c.add("a");
		c.add("b");
		assertFalse(l.equals(c));
		assertFalse(c.equals(l));
		c.add("c");
		assertTrue(l.equals(c));
		assertTrue(c.equals(l));
	}

	@Test
	public void testGetLast() {
		try {
			l.getLast();
			fail("l.getLast should throw exception!");
		} catch (NoSuchElementException e) {
		}
		l.add("a");
		l.add("b");
		assertEquals("b", l.getLast());
	}

	@Test
	public void testIndexOf() {
		l.add("foo");
		l.add("bar");
		l.add("foo");
		assertEquals(0, l.indexOf("foo"));
		assertEquals(1, l.indexOf("bar"));
		assertEquals(-1, l.indexOf("foobar"));
	}

	@Test
	public void testInsertionsort() {
		l.add("d");
		l.add("e");
		l.add("a");
		l.add("c");
		l.add("b");
		l.insertionSort();
		assertEquals("[a, b, c, d, e]", l.toString());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(l.isEmpty());
		l.add("foo");
		assertFalse(l.isEmpty());
	}

	@Test
	public void testIteratorAdd() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator();
		i.add("c");
		i.next();
		i.add("d");
		assertEquals(4, i.nextIndex());
		assertEquals("d", i.previous());
		assertEquals("c", l.get(0));
		assertEquals("a", l.get(1));
		assertEquals("d", l.get(2));
		assertEquals("b", l.get(3));
	}

	@Test
	public void testIteratorHasNext() {
		l.add("a");
		l.add("b");
		assertTrue(l.listIterator().hasNext());
		assertTrue(l.listIterator(1).hasNext());
		assertFalse(l.listIterator(2).hasNext());
	}

	@Test
	public void testIteratorHasPrevious() {
		l.add("a");
		l.add("b");
		assertFalse(l.listIterator().hasPrevious());
		assertTrue(l.listIterator(1).hasPrevious());
		assertTrue(l.listIterator(2).hasPrevious());
	}

	@Test
	public void testIteratorNext() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator();
		assertEquals("a", i.next());
		assertTrue(i.hasNext());
		assertEquals("b", i.next());
		assertEquals(3, i.nextIndex());
		assertFalse(i.hasNext());
		try {
			i.next();
			fail("i.next() should throw exception!");
		} catch (NoSuchElementException e) {
		}
	}

	@Test
	public void testIteratorNextIndex() {
		l.add("a");
		l.add("b");
		assertEquals(1, l.listIterator().nextIndex());
		assertEquals(2, l.listIterator(1).nextIndex());
	}

	@Test
	public void testIteratorNextPrevious() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator(2);
		assertEquals("b", i.previous());
		assertTrue(i.hasPrevious());
		assertEquals("a", i.previous());
		assertTrue(i.hasNext());
		assertEquals("a", i.next());
		assertEquals("b", i.next());
	}

	@Test
	public void testIteratorPrevious() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator(2);
		assertEquals("b", i.previous());
		assertTrue(i.hasPrevious());
		assertEquals("a", i.previous());
		assertEquals(-1, i.previousIndex());
		assertFalse(i.hasPrevious());
		try {
			i.previous();
			fail("i.previous() schould throw exception!");
		} catch (NoSuchElementException e) {
		}
	}

	@Test
	public void testIteratorPreviousIndex() {
		l.add("a");
		l.add("b");
		assertEquals(-1, l.listIterator().previousIndex());
		assertEquals(0, l.listIterator(1).previousIndex());
		assertEquals(1, l.listIterator(2).previousIndex());
	}

	@Test
	public void testIteratorRemove() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator();
		i.next();
		i.remove();
		assertEquals(0, i.nextIndex());
		assertEquals("b", l.get(0));
		assertEquals("b", i.next());
		assertEquals(1, i.nextIndex());
	}

	@Test(expected = IllegalStateException.class)
	public void testIteratorRemoveException() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator();
		i.remove();
	}

	@Test
	public void testIteratorSet() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator();
		i.next();
		i.set("c");
		assertEquals("c", l.get(0));
	}

	@Test(expected = IllegalStateException.class)
	public void testIteratorSetException() {
		l.add("a");
		l.add("b");
		ListIterator<String> i = l.listIterator();
		i.set("c");
	}

	@Test
	public void testLastIndexOf() {
		l.add("foo");
		l.add("bar");
		l.add("foo");
		assertEquals(2, l.lastIndexOf("foo"));
		assertEquals(1, l.lastIndexOf("bar"));
		assertEquals(-1, l.lastIndexOf("foobar"));
	}

	@Test
	public void testOffer() {
		assertTrue(l.offer("a"));
		assertEquals("a", l.get(0));
		assertEquals(1, l.size());
	}

	@Test
	public void testPeek() {
		assertNull(l.peek());
		l.add("a");
		l.add("b");
		assertEquals("a", l.peek());
	}

	@Test
	public void testPeekLast() {
		assertNull(l.peekLast());
		l.add("a");
		l.add("b");
		assertEquals("b", l.peekLast());
	}

	@Test
	public void testPoll() {
		l.add("a");
		l.add("b");
		assertEquals("a", l.poll());
		assertEquals("b", l.poll());
		assertNull(l.poll());
	}

	@Test
	public void testPollLast() {
		l.add("a");
		l.add("b");
		assertEquals("b", l.pollLast());
		assertEquals("a", l.pollLast());
		assertNull(l.pollLast());
	}

	@Test
	public void testRemove() {
		l.add("a");
		l.add("b");
		assertEquals("a", l.remove());
		assertEquals("b", l.remove());
		try {
			l.remove();
		} catch (NoSuchElementException e) {
		}
	}

	@Test
	public void testRemoveAll() {
		l.add("a");
		l.add("b");
		l.add("c");
		l.add("d");
		List<String> c = new ArrayList<String>();
		c.add("a");
		c.add("c");
		c.add("e");
		assertTrue(l.removeAll(c));
		c = new ArrayList<String>();
		c.add("f");
		assertFalse(l.removeAll(c));
		assertEquals(2, l.size());
		assertEquals("b", l.get(0));
		assertEquals("d", l.get(1));
	}

	@Test
	public void testRemoveLast() {
		l.add("a");
		l.add("b");
		assertEquals("b", l.removeLast());
		assertEquals("a", l.removeLast());
		try {
			l.removeLast();
			fail("l.removeLast should throw exception!");
		} catch (NoSuchElementException e) {
		}
	}

	@Test
	public void testRemoveLastOccurence() {
		l.add("a");
		l.add("b");
		assertFalse(l.removeLastOccurrence("c"));
		assertEquals(2, l.size());
		assertTrue(l.removeLastOccurrence("a"));
		assertEquals(1, l.size());
		assertEquals("b", l.get(0));
	}

	@Test
	public void testRemoveWithIndex() {
		l.add("a");
		l.add("b");
		l.add("c");
		l.add("d");
		l.add("e");
		assertEquals("a", l.remove(0));
		assertEquals("e", l.remove(3));
		assertEquals("c", l.remove(1));
		assertEquals("b", l.get(0));
		assertEquals("d", l.get(1));
	}

	@Test
	public void testRemoveWithObject() {
		l.add("a");
		l.add("b");
		assertTrue(l.remove("a"));
		assertFalse(l.remove("a"));
		assertEquals("b", l.get(0));
	}

	@Test
	public void testRetainAll() {
		l.add("a");
		l.add("b");
		l.add("c");
		l.add("d");
		List<String> c = new ArrayList<String>();
		c.add("b");
		c.add("c");
		assertTrue(l.retainAll(c));
		assertFalse(l.retainAll(c));
		assertEquals("b", l.get(0));
		assertEquals("c", l.get(1));
		assertEquals(2, l.size());
	}

	@Test
	public void testSelectionsort() {
		l.add("d");
		l.add("e");
		l.add("a");
		l.add("c");
		l.add("b");
		l.selectionSort();
		assertEquals("[a, b, c, d, e]", l.toString());
	}

	@Test
	public void testSet() {
		l.add("a");
		l.add("b");
		assertEquals("a", l.set(0, "c"));
		assertEquals("c", l.get(0));
	}

	@Test
	public void testSizeIsNullAfterCreate() {
		assertEquals(0, l.size());
	}

	@Test
	public void testSublist() {
		l.add("a");
		l.add("b");
		l.add("c");
		List<String> sl = l.subList(0, 2);
		assertEquals(2, sl.size());
		assertEquals("a", sl.get(0));
		ListIterator<String> i = sl.listIterator();
		assertEquals("a", i.next());
		assertFalse(i.hasNext());
		i.remove();
		assertEquals("b", l.get(0));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testThrowsIndexOutOfBoundsExceptionOnGet() {
		l.get(1);
	}

	@Test
	public void testToArrayObject() {
		l.add("a");
		l.add("b");
		Object[] a = l.toArray();
		assertEquals("a", a[0]);
		assertEquals("b", a[1]);
	}

	@Test
	public void testToArrayWithType() {
		l.add("a");
		l.add("b");
		String[] a = l.toArray(new String[0]);
		assertEquals(2, a.length);
		assertEquals("a", a[0]);
		assertEquals("b", a[1]);
	}

	@Test
	public void testToString() {
		l.add("a");
		l.add("b");
		l.add("c");
		assertEquals("[a, b, c]", l.toString());
	}
}
