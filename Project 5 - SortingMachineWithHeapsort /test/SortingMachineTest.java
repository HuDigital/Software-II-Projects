import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Sean-Paul Billups and Michael Hu
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "green");
        m.add("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    /*
     * Test cases for add. -------------------------------- START --------------
     */

    /*
     * Set up variables and call method under test
     */

    /**
     * Tests add.
     */
    @Test
    public final void testAdd1() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "apple",
                "banana");

        m.add("banana");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for add. --------------------------------- END ---------------
     */

    /*
     * Test cases for changeToExtractionMode. ------------- START --------------
     */

    /**
     * Tests changeToExtractionMode.
     */
    @Test
    public final void testExtractionMode1() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        m.changeToExtractionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Tests changeToExtractionMode.
     */
    @Test
    public final void testExtractionMode2() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple",
                "banana");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "apple",
                "banana");

        m.changeToExtractionMode();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for changeToExtractionMode. -------------- END ---------------
     */

    /*
     * Test cases for removeFirst. ------------------------ START --------------
     */
    @Test
    public final void testRemoveFirst1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String root = m.removeFirst();
        assertEquals(root, "apple");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirst2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple",
                "banana", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "banana",
                "orange");
        String root = m.removeFirst();
        assertEquals(root, "apple");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirst3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple",
                "banana", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String root1 = m.removeFirst();
        String root2 = m.removeFirst();
        String root3 = m.removeFirst();
        assertEquals(root1, "apple");
        assertEquals(root2, "banana");
        assertEquals(root3, "orange");
        assertEquals(mExpected, m);
    }
    /*
     * Test cases for removeFirst. ------------------------- END ---------------
     */

    /*
     * Test cases for isInInsertionMode. ------------------ START --------------
     */

    /**
     * Tests isInInsertionMode.
     */
    @Test
    public final void testIsInInsertion1() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Tests isInInsertionMode.
     */
    @Test
    public final void testIsInInsertionMode2() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple", "green",
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "apple",
                "green", "blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Tests isInInsertionMode.
     */
    @Test
    public final void testIsInInsertionMode3() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Tests isInInsertionMode.
     */
    @Test
    public final void testIsInInsertionMode4() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple", "green",
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "apple",
                "green", "blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for isInInsertionMode. ------------------- END ---------------
     */

    /*
     * Test cases for order. ------------------------------ START --------------
     */

    /**
     * Tests order.
     */
    @Test
    public final void testOrderInsertion() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Tests order.
     */
    @Test
    public final void testOrderExtraction() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Tests order.
     */
    @Test
    public final void testOrderInsertion1() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    /**
     * Tests order.
     */
    @Test
    public final void testOrderExtraction1() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }
    /*
     * Test cases for order. ------------------------------- END ---------------
     */

    /*
     * Test cases for size. ------------------------------- START --------------
     */

    /**
     * Tests size.
     */
    @Test
    public final void testSize1() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(1, m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize2() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize3() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "apple",
                "banana", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "apple",
                "banana", "orange");

        final int size = 3;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(size, m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize4() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "apple");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(1, m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize5() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize6() {
        /*
         * Set up variables and call method under test
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple", "banana",
                "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "apple",
                "banana", "orange");

        final int size = 3;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(size, m.size());
        assertEquals(mExpected, m);
    }
    /*
     * Test cases for size. -------------------------------- END ---------------
     */

}
