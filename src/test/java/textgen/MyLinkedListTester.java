package textgen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");

		emptyList = new MyLinkedList<Integer>();

		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}

		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 *
	 * You should not need to add much to this method.
	 * We provide it as an example of a thorough test.
	 */
	@Test
	public void testGet()
	{
		// test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		try {
			int a = list1.remove(100);
			fail("Check out of bounds too high");
		} catch (IndexOutOfBoundsException e) {}

		try {
			int a = list1.remove(-1);
			fail("Check out of bounds too low");
		} catch (IndexOutOfBoundsException e) {}

		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		// test add null
        try {
        	emptyList.add(null);
        	fail("Check add on null");
		} catch (NullPointerException e) {
        	assertEquals("AddEnd: check after adding null size is not changed ", 0, emptyList.size());
		}

        // test add to empty list
        emptyList.add(11);
        assertEquals("AddEnd: check size is correct ", 1, emptyList.size());
		assertEquals("AddEnd: check last value is correct ", (Integer) 11, emptyList.get(emptyList.size() - 1));

		// test add to non empty list
		emptyList.add(22);
		assertEquals("AddEnd: check size is correct ", 2, emptyList.size());
		assertEquals("AddEnd: check last value is correct ", (Integer) 22, emptyList.get(emptyList.size() - 1));
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		assertEquals("Size: empty list ", 0, emptyList.size());
		assertEquals("Size: non empty list ", 3, list1.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		// test add in middle
		list1.add(1, 11);
		assertEquals("AddAtIndex: check size correct ", 4, list1.size());
		assertEquals("AddAtIndex: check new value at index ", (Integer) 11, list1.get(1));
		assertEquals("AddAtIndex: check old value shifted ", (Integer) 21, list1.get(2));

		// test add to end
		list1.add(4, 44);
		assertEquals("AddAtIndex: check size correct ", 5, list1.size());
		assertEquals("AddAtIndex: check new value at index ", (Integer) 44, list1.get(4));
		try {
			list1.get(5);
			fail("Last index should not exist");
		} catch (IndexOutOfBoundsException e) {}

		// test insert to not reasonable index
		try {
			list1.add(40, 200);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {}

		try {
			list1.add(-1, 200);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {}

		// test add null
		try {
			list1.add(2, null);
			fail("Check add null");
		} catch (NullPointerException e) {}
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		// test standard user case
	    int oldVal = list1.set(1, 11);
	    assertEquals("Set: check returned old value is correct ", 21, oldVal);
		assertEquals("Set: check new value is correct ", (Integer) 11, list1.get(1));

		// test set null
		try {
			list1.set(1, null);
			fail("Check set null");
		} catch (NullPointerException e) {}

		// test out of bound index

		try {
			list1.set(20, 22);
		} catch (IndexOutOfBoundsException e) {}
	}
	
	
	// TODO: Optionally add more test methods.
	
}
