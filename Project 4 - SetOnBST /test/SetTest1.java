import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Sean-Paul Billups and Michael Hu
 *
 */
public abstract class SetTest1 {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /*
     * Test cases for constructors
     */

    /**
     * Tests the NoArgumentConstructor.
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor1() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor2() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello");
        Set<String> sExpected = this.createFromArgsRef("Hello");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor3() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hi", "Hey");
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hi", "Hey");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests constructor.
     */
    @Test
    public final void testConstructor4() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
                "z");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
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
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("Hello");

        s.add("Hello");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests add.
     */
    @Test
    public final void testAdd2() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hi", "Hey");

        s.add("Hello");
        s.add("Hi");
        s.add("Hey");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests add.
     */
    @Test
    public final void testAdd3() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z");

        char letter = 'A';
        final int twentySix = 26;

        for (int i = 0; i < twentySix; i++) {
            s.add(String.valueOf(letter));
            letter++;
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests add.
     */
    @Test
    public final void testAdd4() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hi");
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hi", "Hey");

        s.add("Hey");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests add.
     */
    @Test
    public final void testAdd5() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
                "z");

        char letter = 'a';
        final int twentySix = 26;

        for (int i = 0; i < twentySix; i++) {
            s.add(String.valueOf(letter));
            letter++;
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
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
        Set<String> s = this.createFromArgsTest("Hello");
        Set<String> sExpected = this.createFromArgsRef();

        s.remove("Hello");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove.
     */
    @Test
    public final void testRemove2() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hi", "Hey");
        Set<String> sExpected = this.createFromArgsRef();

        s.remove("Hello");
        s.remove("Hi");
        s.remove("Hey");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove.
     */
    @Test
    public final void testRemove3() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z");
        Set<String> sExpected = this.createFromArgsRef();

        char letter = 'A';
        final int twentySix = 26;

        for (int i = 0; i < twentySix; i++) {
            s.remove(String.valueOf(letter));
            letter++;
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove.
     */
    @Test
    public final void testRemove4() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hi", "Hey");
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hi");

        s.remove("Hey");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove.
     */
    @Test
    public final void testRemove5() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Z", "Y", "X", "W", "V", "U", "T", "S",
                "R", "Q", "P", "O", "N", "M", "L", "K", "J", "I", "H", "G", "F", "E", "D",
                "C", "B", "A");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "T",
                "U", "V", "W", "X", "Y", "Z");

        char letter = 'G';
        final int thirteen = 13;

        for (int i = 0; i < thirteen; i++) {
            s.remove(String.valueOf(letter));
            letter++;
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
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
        Set<String> s = this.createFromArgsTest("Hello");
        Set<String> sExpected = this.createFromArgsRef();

        s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny2() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hey");
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hey");

        String str = s.removeAny();
        sExpected.remove(str);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny3() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hi", "Hey");
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hi", "Hey");

        final int three = 3;
        for (int i = 0; i < three; i++) {
            String str = s.removeAny();
            sExpected.remove(str);
        }
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny4() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z");

        final int fifteen = 15;
        for (int i = 0; i < fifteen; i++) {
            String str = s.removeAny();
            sExpected.remove(str);
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests remove any.
     */
    @Test
    public final void testRemoveAny5() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z");

        final int twentySix = 26;
        for (int i = 0; i < twentySix; i++) {
            String str = s.removeAny();
            sExpected.remove(str);
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /*
     * Test cases for contains.
     */

    /**
     * Tests contains.
     */
    @Test
    public final void testContains1() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        boolean contains = s.contains("Hello");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertTrue(!contains);
    }

    /**
     * Tests contains.
     */
    @Test
    public final void testContains2() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello");
        Set<String> sExpected = this.createFromArgsRef("Hello");

        boolean contains = s.contains("Hello");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertTrue(contains);
    }

    /**
     * Tests contains.
     */
    @Test
    public final void testContains3() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hi", "Hey");
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hi", "Hey");

        boolean contains = s.contains("Hello");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertTrue(contains);
    }

    /**
     * Tests contains.
     */
    @Test
    public final void testContains4() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z");

        char letter = 'A';
        final int twentySix = 26;
        for (int i = 0; i < twentySix; i++) {
            boolean contains = s.contains(String.valueOf(letter));
            assertTrue(contains);

            letter++;
        }

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);

    }

    /**
     * Tests contains.
     */
    @Test
    public final void testContains5() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z");

        boolean contains = s.contains("Hello");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertTrue(!contains);
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
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(0, i);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize2() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello");
        Set<String> sExpected = this.createFromArgsRef("Hello");

        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(1, i);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize3() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("Hello", "Hi", "Hey");
        Set<String> sExpected = this.createFromArgsRef("Hello", "Hi", "Hey");

        final int three = 3;
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(three, i);
    }

    /**
     * Tests size.
     */
    @Test
    public final void testSize4() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z");

        final int twentySix = 26;
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(twentySix, i);
    }

}
