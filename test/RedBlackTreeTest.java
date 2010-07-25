import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class RedBlackTreeTest {

	private RedBlackTree<String> t;

	@Before
	public void setUp() throws Exception {
		t = new RedBlackTree<String>();
	}

	@Test
	public void testRebalacing() {
		t.add("a");
		t.add("b");
		t.add("c");
		t.add("d");
		t.add("e");
		assertEquals("[a, b, c, d, e]", t.toString());
		assertTrue(t.contains("a"));
		assertTrue(t.contains("c"));
		assertTrue(t.contains("e"));
		assertFalse(t.contains("f"));
	}

}
