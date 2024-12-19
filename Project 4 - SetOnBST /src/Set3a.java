import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Sean-Paul Billups and Michael Hu
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        // Initializes a boolean that tells whether {@code x} is in {@code t}
        boolean in = false;

        // Iterates if {@code t} is not empty
        if (t.size() > 0) {

            // Initializes the left and right branch of {@code t}
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();

            // Disassembles {@code t} and initializes {@code root}
            T root = t.disassemble(left, right);

            // Compares {@code root} to {@code x}
            if (root.equals(x)) {
                in = true;

            } else if (root.compareTo(x) > 0) {
                in = isInTree(left, x);

            } else {
                in = isInTree(right, x);
            }

            // Reassembles {@code t}
            t.assemble(root, left, right);

        }

        // Returns whether {@code x} is in {@code t}
        return in;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        // Initializes the left and right branch of {@code t}
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        // Iterates if {@code t} is not empty
        if (t.size() > 0) {

            // Disassembles {@code t} and initializes {@code root}
            T root = t.disassemble(left, right);

            // Compares {@code root} to {@code x}
            if (x.compareTo(root) < 0) {
                insertInTree(left, x);

            } else if (x.compareTo(root) > 0) {
                insertInTree(right, x);

            }

            // Reassembles {@code t}
            t.assemble(root, left, right);

        } else {

            // Assigns {@code x} as the root of {@code t}
            t.assemble(x, left, right);

        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        // Initializes {@code left} and {@code right}
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        // Disassembles {@code t} and initializes {@code root} and {@code smallest}
        T root = t.disassemble(left, right);
        T smallest = root;

        // Gauges the height of the {@code left}
        if (left.size() > 0) {

            // Iterates through {@code left} then reassembles {@code t}
            smallest = removeSmallest(left);
            t.assemble(root, left, right);

        } else { // Removes {@code root} from {@code t}

            // Re-initializes {@code t} as {@code right}
            t.transferFrom(right);

        }

        // Returns the smallest value
        return smallest;

    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        // Initializes {@code left} and {@code right}
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        // Disassembles {@code t} and initializes {@code root} and {@code smallest}
        T root = t.disassemble(left, right);
        T removal = root;

        // Analyzes whether {@code x} is equal to, greater than, or less than {@code root}
        if (x.compareTo(root) < 0) {

            // If {@code x} is less than {@code root}, it is removed from {@code left}
            removal = removeFromTree(left, x);
            t.assemble(root, left, right);

        } else if (x.compareTo(root) > 0) {

            // If {@code x} is greater than {@code root}, it is removed from {@code right}
            removal = removeFromTree(right, x);
            t.assemble(root, left, right);

        } else {

            // If {@code right} is empty, {@code t} takes the value of {@code left}
            if (right.size() == 0) {
                t.transferFrom(left);

            } else {

                /*
                 * If {@code right} is not empty, then {@code root} is replaced
                 * by its smallest value.
                 */
                root = removeSmallest(right);
                t.assemble(root, left, right);
            }

        }

        // Returns the smallest value
        return removal;

    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.tree = new BinaryTree1<T>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {
        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?>
                : "" + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        // Adds {@code x} into {@code this.tree}
        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        // Removes {@code x} from {@code this.tree}
        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        // Removes an arbitrary element
        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        // Returns whether {@code x} is in {@code this.tree}
        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        // Returns the tree size
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
