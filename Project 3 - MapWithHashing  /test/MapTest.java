import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Sean-Paul Billups, Michael Hu
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /*
     * Test cases for constructor.
     */

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor4() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C", "c", "D",
                "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k",
                "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S",
                "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z",
                "z");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j",
                "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R",
                "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y",
                "Z", "z");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for add.
     */

    /**
     * Tests add.
     */
    @Test
    public final void testAdd1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello");

        m.add("Greeting 1", "Hello");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests add.
     */
    @Test
    public final void testAdd2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");

        m.add("Greeting 1", "Hello");
        m.add("Greeting 2", "Hi");
        m.add("Greeting 3", "Hey");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests add.
     */
    @Test
    public final void testAdd3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j",
                "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R",
                "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y",
                "Z", "z");
        char upper = 'A';
        char lower = 'a';
        final int n = 26;

        for (int i = 0; i < n; i++) {

            m.add("" + upper, "" + lower);
            upper++;
            lower++;

        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for remove.
     */

    /**
     * Tests remove.
     */
    @Test
    public final void testRemove1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello");
        Map<String, String> mExpected = this.createFromArgsRef();

        m.remove("Greeting 1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests remove.
     */
    @Test
    public final void testRemove2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 3", "Hey");

        m.remove("Greeting 2");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests remove.
     */
    @Test
    public final void testRemove3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef();

        m.remove("Greeting 1");
        m.remove("Greeting 2");
        m.remove("Greeting 3");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for remove any.
     */

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello");
        Map<String, String> mExpected = this.createFromArgsRef();

        m.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");

        mExpected.remove(m.removeAny().key());
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");

        final int three = 3;
        for (int i = 0; i < three; i++) {
            mExpected.remove(m.removeAny().key());
        }
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny4() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C", "c", "D",
                "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k",
                "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S",
                "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z",
                "z");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j",
                "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R",
                "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y",
                "Z", "z");

        final int fifteen = 15;
        for (int i = 0; i < fifteen; i++) {
            mExpected.remove(m.removeAny().key());
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny5() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C", "c", "D",
                "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k",
                "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S",
                "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z",
                "z");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j",
                "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R",
                "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y",
                "Z", "z");

        final int twentySix = 26;
        for (int i = 0; i < twentySix; i++) {
            mExpected.remove(m.removeAny().key());
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for hasKey.
     */

    /**
     * Tests hasKey.
     */
    @Test
    public final void testHasKey1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello");

        boolean hasKey = m.hasKey("Greeting 1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertTrue(hasKey);
    }

    /**
     * Tests hasKey.
     */
    @Test
    public final void testHasKey2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");

        boolean hasKey = m.hasKey("Greeting 2");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertTrue(hasKey);
    }

    /**
     * Tests hasKey.
     */
    @Test
    public final void testHasKey3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");

        boolean hasKey = m.hasKey("Greeting 4");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertTrue(!hasKey);
    }

    /**
     * Tests hasKey.
     */
    @Test
    public final void testHasKey4() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();

        boolean hasKey = m.hasKey("Greeting 1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertTrue(!hasKey);
    }

    /*
     * Test cases for value.
     */

    /**
     * Tests value.
     */
    @Test
    public final void testValue1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello");

        String s = m.value("Greeting 1");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(s, "Hello");
    }

    /**
     * Tests value.
     */
    @Test
    public final void testValue2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello",
                "Greeting 2", "Hi", "Greeting 3", "Hey");

        String s = m.value("Greeting 2");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(s, "Hi");
    }

    /**
     * Tests value.
     */
    @Test
    public final void testValue3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C", "c", "D",
                "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k",
                "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S",
                "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z",
                "z");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j",
                "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R",
                "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y",
                "Z", "z");

        String s = m.value("Z");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(s, "z");
    }

    /*
     * Test cases for size.
     */

    /**
     * Tests size.
     */
    @Test
    public final void testSize1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();

        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(i, 0);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("Greeting 1", "Hello");
        Map<String, String> mExpected = this.createFromArgsRef("Greeting 1", "Hello");

        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(i, 1);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest("A", "a", "B", "b", "C", "c", "D",
                "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k",
                "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S",
                "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z",
                "z");
        Map<String, String> mExpected = this.createFromArgsRef("A", "a", "B", "b", "C",
                "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j",
                "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R",
                "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y",
                "Z", "z");

        int i = m.size();
        final int n = 26;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(i, n);
    }

}
