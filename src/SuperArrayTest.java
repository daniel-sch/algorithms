import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class SuperArrayTest {
	private SuperArray<String> a;

	@Before
	public void setUp() throws Exception {
		a = new SuperArray<String>(3);
		for (String s : new String[] { "a", "b", "c" })
			a.add(s);
	}

	@Test
	public void testAdd() {
		a.add(3, "d");
		assertEquals(4, a.size());
		assertEquals("[a, b, c, d]", a.toString());
		a.add(1, "x");
		assertEquals(5, a.size());
		assertEquals("[a, x, b, c, d]", a.toString());
		try {
			a.add(6, "d");
			fail("a.add(6,...) should throw exception!");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testBubbleSort() {
		SuperArray<Integer> x = getListToSort();
		x.bubbleSort();
		isSorted(x);
	}

	@Test
	public void testGet() {
		assertEquals("a", a.get(0));
		assertEquals("b", a.get(1));
		assertEquals("c", a.get(2));
		try {
			a.get(3);
			fail("a.get(3) should throw exception!");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testInsertionSort() {
		SuperArray<Integer> x = getListToSort();
		x.insertionSort();
		isSorted(x);
	}

	@Test
	public void testMergeSort() {
		SuperArray<Integer> x = getListToSort();
		x.mergeSort();
		isSorted(x);
	}

	@Test
	public void testQuickSort() {
		SuperArray<Integer> x = getListToSort();
		x.quickSort();
		isSorted(x);
	}

	@Test
	public void testRemove() {
		assertEquals("b", a.remove(1));
		assertEquals("[a, c]", a.toString());
		assertEquals(2, a.size());
		try {
			a.remove(2);
			fail("a.remove(2) should throw exception!");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testSelectionSort() {
		SuperArray<Integer> x = getListToSort();
		x.selectionSort();
		isSorted(x);
	}

	@Test
	public void testSet() {
		assertEquals("b", a.set(1, "x"));
		assertEquals(3, a.size());
		assertEquals("[a, x, c]", a.toString());
		try {
			a.set(3, "y");
			fail("a.set(3,...) should throw exception!");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testSize() {
		assertEquals(3, a.size());
	}

	@Test
	public void testSuperArrayCollectionConstructor() {
		List<String> n = new ArrayList<String>(a);
		a = new SuperArray<String>(n);
		assertEquals("[a, b, c]", a.toString());
		assertEquals(3, a.size());
	}
	
	@Test
	public void testSuperArrayConstructor() {
		a = new SuperArray<String>();
		a.add("a");
	}

	private SuperArray<Integer> getListToSort() {
		SuperArray<Integer> ret = new SuperArray<Integer>(1000);
		Random r = new Random();
		for (int i = 0; i < 1000; i++)
			ret.add(r.nextInt(1000000));
		return ret;
	}
	
	private void isSorted(SuperArray<Integer> a) {
		Object[] arr1 = a.toArray();
		Object[] arr2 = a.toArray();
		Arrays.sort(arr2);
		assertArrayEquals(arr2, arr1);
	}
}
