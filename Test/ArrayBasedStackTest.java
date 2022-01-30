/**
 * 
 */


import java.util.EmptyStackException;

/**
 * 
 * @author Taimoor Qamar
 * @version 2019.07.15
 *
 */
public class ArrayBasedStacktest extends student.TestCase {

    // FIELDS
    private ArrayBasedStack<String> testStack;
    private String[] testItems;


    /**
     * SetUp method to initialize fields for tests.
     */
    public void setUp() {
        testStack = new ArrayBasedStack<String>();
        testItems = new String[100];
        for (int i = 0; i < 100; i++) {
            testItems[i] = String.valueOf(i);
        }
    }


    /**
     * Tests the two constructor methods.
     */
    public void testConstructors() {
        // PRECONDITIONS
        this.testStack = null;
        assertNull(this.testStack);

        // EXECUTION
        this.testStack = new ArrayBasedStack<String>();

        // POSTCONDITIONS
        assertNotNull(this.testStack);
        assertEquals(this.testStack.size(), 0);
    }


    /**
     * Tests the isEmpty method.
     */
    public void testIsEmpty() {
        // CASE 1: STACK IS EMPTY
        // POSTCONDITIONS
        assertTrue(this.testStack.isEmpty());

        // CASE 2: STACK IS NOT EMPTY
        // PRECONDITIONS
        this.testStack.push(this.testItems[0]);
        // POSTCONDITIONS
        assertFalse(this.testStack.isEmpty());
    }


    /**
     * Tests the peek method for all conditions.
     */
    public void testPeek() {
        // CASE 1: STACK IS EMPTY
        // PRECONDITIONS
        Exception thrown = null;
        // EXECUTION
        try {
            this.testStack.peek();
        }
        catch (Exception e) {
            thrown = e;
        }
        // POSTCONDITIONS
        assertNotNull(thrown);
        assertTrue(thrown instanceof EmptyStackException);

        // CASE 2: STACK IS NOT EMPTY
        // PRECONDITIONS
        this.testStack.push(this.testItems[0]);
        this.testStack.push(this.testItems[1]);
        this.testStack.push(this.testItems[2]);
        this.testStack.push(this.testItems[3]);

        String testString;

        // EXEUCTION
        testString = this.testStack.peek();

        // POSTCONDITIONS
        assertEquals(testString, this.testItems[3]);
    }


    /**
     * Tests the pop method for all conditions
     */
    public void testPop() {
        // CASE 1: STACK IS EMPTY
        // PRECONDITIONS
        Exception thrown = null;
        // EXECUTION
        try {
            this.testStack.pop();
        }
        catch (Exception e) {
            thrown = e;
        }
        // POSTCONDITIONS
        assertNotNull(thrown);
        assertTrue(thrown instanceof EmptyStackException);

        // CASE 2: STACK IS NOT EMPTY
        // PRECONDITIONS
        this.testStack.push(this.testItems[0]);
        this.testStack.push(this.testItems[1]);
        this.testStack.push(this.testItems[2]);
        this.testStack.push(this.testItems[3]);
        assertEquals(this.testStack.size(), 4);
        String testString;

        // EXEUCTION
        testString = this.testStack.pop();

        // POSTCONDITIONS
        assertEquals(testString, this.testItems[3]);
        assertEquals(this.testStack.size(), 3);
    }


    /**
     * Tests the push method for all conditions
     */
    public void testPush() {
        // CASE 1: STACK IS NOT AT FULL CAPACITY
        // PRECONDITIONS
        assertEquals(this.testStack.size(), 0);

        // EXECUTION
        this.testStack.push(this.testItems[0]);
        this.testStack.push(this.testItems[1]);
        this.testStack.push(this.testItems[2]);
        this.testStack.push(this.testItems[3]);

        // POSTCONDITIONS
        assertEquals(this.testStack.size(), 4);

        // CASE 2: STACK IS AT FULL CAPACITY
        // PRECONDITIONS
        for (int i = 4; i < testItems.length; i++) {
            this.testStack.push(this.testItems[i]);
        }
        assertEquals(this.testStack.size(), 100);

        // EXECUTION
        this.testStack.push("101");

        // POSTCONDITIONS
        assertEquals(this.testStack.size(), 101);
        assertEquals(this.testStack.peek(), "101");
    }


    /**
     * Tests the contains method for all conditions
     */
    public void testContains() {
        // CASE 1: ITEM IS NOT IN STACK
        // PRECONDITIONS
        assertFalse(this.testStack.contains("blah"));

        // CASE 2: ITEM IS IN STACK
        // PRECONDITIONS
        for (int i = 0; i < testItems.length; i++) {
            this.testStack.push(this.testItems[i]);
        }
        assertEquals(this.testStack.size(), 100);

        // POSTCONDITIONS
        assertTrue(this.testStack.contains("55"));
        assertTrue(this.testStack.contains("24"));
        assertTrue(this.testStack.contains("83"));

        // CASE 2: ITEM IS IN STACK ALONG WITH NULL VALUES
        // PRECONDITIONS
        this.testStack.clear();
        for (int i = 0; i < 10; i++) {
            this.testStack.push(null);
        }
        this.testStack.push("10");

        // POSTCONDITIONS
        assertTrue(this.testStack.contains("10"));

    }


    /**
     * Tests the size method
     */
    public void testSize() {
        assertEquals(this.testStack.size(), 0);
        this.testStack.push("blah");
        assertEquals(this.testStack.size(), 1);
    }


    /**
     * Tests the clear method
     */
    public void testClear() {
        // CASE 2: STACK IS AT FULL CAPACITY
        // PRECONDITIONS
        for (int i = 0; i < testItems.length; i++) {
            this.testStack.push(this.testItems[i]);
        }
        assertFalse(this.testStack.isEmpty());
        assertEquals(this.testStack.size(), 100);

        // EXECUTION
        this.testStack.clear();

        // POSTCONDITIONS
        assertTrue(this.testStack.isEmpty());

    }


    /**
     * Tests the toArray method
     */
    public void testToArray() {
        // PRECONDITIONS
        for (int i = 0; i < testItems.length; i++) {
            this.testStack.push(this.testItems[i]);
        }

        Object[] testArray = this.testStack.toArray();

        // EXECUTION

        // POSTCONDITIONS
        assertEquals(100, testArray.length);
        for (int i = 0; i < this.testStack.size(); i++) {
            assertEquals(String.valueOf(i), testArray[i]);
        }
    }


    /**
     * Tests the toString method
     */
    public void testToString() {
        // PRECONDITIONS
        for (int i = 0; i < testItems.length; i++) {
            this.testStack.push(this.testItems[i]);
        }
        this.testStack.clear();
        String testItemsString = "[a, b, c, d]";
        this.testStack.push("a");
        this.testStack.push("b");
        this.testStack.push("c");
        this.testStack.push("d");
        String testString = this.testStack.toString();

        // POSTCONDITIONS
        assertTrue(testItemsString.equals(testString));

    }


    /**
     * Tests the equals method
     */
    public void testEquals() {
        // ALL CASE PRECONDITIONS
        for (int i = 0; i < testItems.length; i++) {
            this.testStack.push(this.testItems[i]);
        }

        // CASE 1: Object PASSED IS NULL
        // PRECONDITIONS
        ArrayBasedStack<String> test1 = null;

        // POSTCONDITIONS
        assertFalse(this.testStack.equals(test1));

        // CASE 2: OBJECT PASSED IS SAME OBJECT
        // PRECONDITIONS
        test1 = this.testStack;

        // EXECUTION
        assertTrue(this.testStack.equals(test1));

        // CASE 3: OBJECT PASSED IS SAME TYPE
        // OF OBJECT WITH DIFFERENT SIZE

        // PRECONDITIONS
        ArrayBasedStack<String> test2 = new ArrayBasedStack<String>();
        // POSTCONDITIONS
        assertFalse(this.testStack.equals(test2));

        // CASE 4: OBJECT PASSED IS A DIFFERENT
        // OBJECT WITH THE SAME CONTENT, AKA EQUAL
        // PRECONDITIONS
        for (int i = 0; i < testItems.length; i++) {
            test2.push(this.testItems[i]);
        }
        // POSTCONDITIONS
        assertTrue(this.testStack.equals(test2));

        // CASE 5: OBJECT PASSED IS A DIFFERENT
        // OBJECT WITH ALL DIFFERENT CONTENT, AKA UNEQUAL
        // PRECONDITIONS
        test2.clear();
        for (int i = 100; i < 200; i++) {
            test2.push(String.valueOf(i));
        }
        // POSTCONDITIONS
        assertFalse(this.testStack.equals(test2));

        // CASE 6: OBJECT PASSED IS A DIFFERENT
        // CLASS

        // PRECONDITIONS
        Object testObj = new Object();

        // POSTCONDITIONS
        assertFalse(this.testStack.equals(testObj));

    }

}
