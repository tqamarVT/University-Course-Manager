import java.util.Iterator;

/**
 * Fill in later
 * 
 * @author Taimoor Qamar
 * @version 2019.09.24
 *
 */
public class BSTTest extends student.TestCase {
    // FIELDS
    private BST<Integer, String> testBST;
    private BST<Integer, String> nullBST;
    private BST<Integer, Integer> test2BST;


    /**
     * Fill in later
     */
    public void setUp() {
        testBST = new BST<Integer, String>();
        nullBST = new BST<Integer, String>();
        test2BST = new BST<Integer, Integer>();
        testBST.insert(37, "37");
        testBST.insert(24, "24");
        testBST.insert(42, "42");
        testBST.insert(7, "7");
        testBST.insert(32, "32");
        testBST.insert(2, "2");
        testBST.insert(30, "30");
        testBST.insert(41, "41");
        testBST.insert(120, "120");
        testBST.insert(40, "40");

        test2BST.insert(37, 0);
        test2BST.insert(24, 1);
        test2BST.insert(42, 2);
        test2BST.insert(7, 3);
        test2BST.insert(32, 4);
        test2BST.insert(2, 5);
        test2BST.insert(30, 6);
        test2BST.insert(41, 7);
        test2BST.insert(120, 8);
        test2BST.insert(40, 9);
    }


    /**
     * Fill in later
     */
    public void testInOrder() {
        // PRECONDITIONS

        // EXECUTION

        // POSTCONDITIONS
        testBST.inOrderTraversal();
        assertNotNull(testBST);
    }


    /**
     * Tests the insert method for all conditions.
     */
    public void testInsert() {
        // CASE 1: ADD TO ROOT
        // ALREADY DONE IN SET UP
        

        // CASE 2: ADD TO RIGHT
        test2BST.insert(130, 10);
        assertNotNull(test2BST.find(130));

        // CASE 3: ADD TO LEFT
        test2BST.insert(1, 11);
        assertNotNull(test2BST.find(1));

        // CASE 4: DUPLICATE KEY, UNIQUE VALUE
        test2BST.insert(1, 12);
        test2BST.insert(30, 13);
        test2BST.insert(30, 14);
        test2BST.inOrderTraversal();

        // CASE 5: DUPLICATE KEY, DUPLICATE VALUE
        Exception thrown = null;
        assertNull(thrown);
        try {
            test2BST.insert(1, 11);
        }
        catch (Exception e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalArgumentException);

    }


    /**
     * Tests the remove method for all conditions.
     */
    public void testRemove() {
        // PRE MANIPULATION BST DUMP
        test2BST.inOrderTraversal();
        // CASE 0: EMPTY BST
        nullBST.remove(new Integer(4), "4");

        // CASE 1: NODE LEFT OF ROOT WITH 2 CHILDREN
        assertNotNull(test2BST.find(24));
        test2BST.remove(24, 1);
        test2BST.inOrderTraversal();
        assertNull(test2BST.find(24));
        test2BST.insert(24, 1);

        // CASE 2: NODE RIGHT OF ROOT WITH 2 CHILDREN
        assertNotNull(test2BST.find(42));
        test2BST.remove(42, 2);
        assertNull(test2BST.find(42));
        test2BST.insert(42, 2);

        // CASE 3: ONLY ONE CHILD TO LEFT
        assertNotNull(test2BST.find(7));
        test2BST.remove(7, 3);
        assertNull(test2BST.find(7));

        // CASE 4: ONLY ONE CHILD TO THE RIGHT
        test2BST.insert(121, 10);
        assertNotNull(test2BST.find(121));
        test2BST.remove(121, 10);
        assertNull(test2BST.find(121));

    }


    /**
     * Fill in later
     */
    public void testFindMinAndMax() {

        // CASE 1: NULL CASE
        assertNull(nullBST.findMin());
        assertNull(nullBST.findMax());

        // CASE 2: NOT NULL CASE
        assertNotNull(testBST.findMin());
        assertNotNull(testBST.findMax());

    }


    /**
     * 
     */
    public void testFind() {
        // CASE 1: NULL CASE
        assertNull(nullBST.find(new Integer(3)));

        // CASE 2: LEFT OF ROOT
        assertNotNull(testBST.find(2));

        // CASE 3: RIGHT OF ROOT
        assertNotNull(testBST.find(120));
    }


    /**
     * 
     */
    public void testEmpties() {

        assertFalse(testBST.isEmpty());
        testBST.makeEmpty();
        assertTrue(testBST.isEmpty());
    }


    /**
     * Fill in later.
     */
    public void testIterator() {

        // CASE 1: FULL TREE
        Iterator<String> iterator = null;
        assertNull(iterator);
        iterator = testBST.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals("2", iterator.next());
        assertEquals("7", iterator.next());
        assertEquals("24", iterator.next());
        assertEquals("30", iterator.next());
        assertEquals("32", iterator.next());

        // CASE 2: EMPTY TREE
        iterator = nullBST.iterator();
        assertNull(iterator.next());
    }

}
