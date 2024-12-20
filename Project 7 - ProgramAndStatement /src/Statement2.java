import components.sequence.Sequence;
import components.statement.Statement;
import components.statement.StatementSecondary;
import components.tree.Tree;
import components.tree.Tree1;
import components.utilities.Tokenizer;

/**
 * {@code Statement} represented as a {@code Tree<StatementLabel>} with
 * implementations of primary methods.
 *
 * @convention [$this.rep is a valid representation of a Statement]
 * @correspondence this = $this.rep
 *
 * @author Sean-Paul Billups and Michael Hu
 *
 */
public class Statement2 extends StatementSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Label class for the tree representation.
     */
    private static final class StatementLabel {

        /**
         * Statement kind.
         */
        private Kind kind;

        /**
         * IF/IF_ELSE/WHILE statement condition.
         */
        private Condition condition;

        /**
         * CALL instruction name.
         */
        private String instruction;

        /**
         * Constructor for BLOCK.
         *
         * @param k
         *            the kind of statement
         *
         * @requires k = BLOCK
         * @ensures this = (BLOCK, ?, ?)
         */
        private StatementLabel(Kind k) {
            assert k == Kind.BLOCK : "Violation of: k = BLOCK";
            this.kind = k;
        }

        /**
         * Constructor for IF, IF_ELSE, WHILE.
         *
         * @param k
         *            the kind of statement
         * @param c
         *            the statement condition
         *
         * @requires k = IF or k = IF_ELSE or k = WHILE
         * @ensures this = (k, c, ?)
         */
        private StatementLabel(Kind k, Condition c) {
            assert k == Kind.IF || k == Kind.IF_ELSE || k == Kind.WHILE
                    : "" + "Violation of: k = IF or k = IF_ELSE or k = WHILE";
            this.kind = k;
            this.condition = c;
        }

        /**
         * Constructor for CALL.
         *
         * @param k
         *            the kind of statement
         * @param i
         *            the instruction name
         *
         * @requires k = CALL and [i is an IDENTIFIER]
         * @ensures this = (CALL, ?, i)
         */
        private StatementLabel(Kind k, String i) {
            assert k == Kind.CALL : "Violation of: k = CALL";
            assert i != null : "Violation of: i is not null";
            assert Tokenizer.isIdentifier(i) : "Violation of: i is an IDENTIFIER";
            this.kind = k;
            this.instruction = i;
        }

        @Override
        public String toString() {
            String condition = "?", instruction = "?";
            if ((this.kind == Kind.IF) || (this.kind == Kind.IF_ELSE)
                    || (this.kind == Kind.WHILE)) {
                condition = this.condition.toString();
            } else if (this.kind == Kind.CALL) {
                instruction = this.instruction;
            }
            return "(" + this.kind + "," + condition + "," + instruction + ")";
        }

    }

    /**
     * The tree representation field.
     */
    private Tree<StatementLabel> rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.rep = new Tree1<StatementLabel>();

        this.rep.assemble(new StatementLabel(Kind.BLOCK), this.rep.newSequenceOfTree());

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement2() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final Statement2 newInstance() {
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
    public final void transferFrom(Statement source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Statement2
                : "" + "Violation of: source is of dynamic type Statement2";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Statement2.
         */
        Statement2 localSource = (Statement2) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final Kind kind() {

        // Returns the kind of the root node
        return this.rep.root().kind;

    }

    @Override
    public final void addToBlock(int pos, Statement s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";
        assert s instanceof Statement2 : "Violation of: s is a Statement2";
        assert this.kind() == Kind.BLOCK
                : "" + "Violation of: [this is a BLOCK statement]";
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos <= this.lengthOfBlock()
                : "" + "Violation of: pos <= [length of this BLOCK]";
        assert s.kind() != Kind.BLOCK : "Violation of: [s is not a BLOCK statement]";

        // Makes s into a Statement2
        Statement2 localS = (Statement2) s;

        // Adds the tree representation of s into this.rep
        this.rep.addSubtree(pos, localS.rep);

        // Clears s
        localS.createNewRep();

    }

    @Override
    public final Statement removeFromBlock(int pos) {
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos < this.lengthOfBlock()
                : "" + "Violation of: pos < [length of this BLOCK]";
        assert this.kind() == Kind.BLOCK
                : "" + "Violation of: [this is a BLOCK statement]";
        /*
         * The following call to Statement newInstance method is a violation of
         * the kernel purity rule. However, there is no way to avoid it and it
         * is safe because the convention clearly holds at this point in the
         * code.
         */
        Statement2 s = this.newInstance();

        s.rep.transferFrom(this.rep.removeSubtree(pos));

        return s;
    }

    @Override
    public final int lengthOfBlock() {
        assert this.kind() == Kind.BLOCK
                : "" + "Violation of: [this is a BLOCK statement]";

        // Returns the number of subtrees within the tree
        return this.rep.numberOfSubtrees();
    }

    @Override
    public final void assembleIf(Condition c, Statement s) {
        assert c != null : "Violation of: c is not null";
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";
        assert s instanceof Statement2 : "Violation of: s is a Statement2";
        assert s.kind() == Kind.BLOCK : "" + "Violation of: [s is a BLOCK statement]";

        // Changes the dynamic type of s
        Statement2 localS = (Statement2) s;

        // Initializes an IF label with the condition c
        StatementLabel label = new StatementLabel(Kind.IF, c);

        // Initializes a new sequence then adds the representation of s
        Sequence<Tree<StatementLabel>> children = this.rep.newSequenceOfTree();
        children.add(0, localS.rep);

        // Assembles this.rep
        this.rep.assemble(label, children);

        // Clears s
        localS.createNewRep();
    }

    @Override
    public final Condition disassembleIf(Statement s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";
        assert s instanceof Statement2 : "Violation of: s is a Statement2";

        // Changes the dynamic type of s
        Statement2 localS = (Statement2) s;

        // Initializes a new sequence then adds the children of this.rep
        Sequence<Tree<StatementLabel>> children = this.rep.newSequenceOfTree();
        StatementLabel label = this.rep.disassemble(children);

        // Initializes the s.rep
        localS.rep = children.remove(0);

        // Clears this
        this.createNewRep();

        // Returns the condition of the IF statement
        return label.condition;
    }

    @Override
    public final void assembleIfElse(Condition c, Statement s1, Statement s2) {
        assert c != null : "Violation of: c is not null";
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1 != this : "Violation of: s1 is not this";
        assert s2 != this : "Violation of: s2 is not this";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1 instanceof Statement2 : "Violation of: s1 is a Statement2";
        assert s2 instanceof Statement2 : "Violation of: s2 is a Statement2";
        assert s1.kind() == Kind.BLOCK : "Violation of: [s1 is a BLOCK statement]";
        assert s2.kind() == Kind.BLOCK : "Violation of: [s2 is a BLOCK statement]";

        // Changes the type of s1 and s2 to Statment2
        Statement2 localS1 = (Statement2) s1;
        Statement2 localS2 = (Statement2) s2;

        // Initializes a new if-else label with the condition c
        StatementLabel label = new StatementLabel(Kind.IF_ELSE, c);

        // Initializes a new sequence then adds the tree value of s1 and s2
        Sequence<Tree<StatementLabel>> children = this.rep.newSequenceOfTree();
        children.add(0, localS1.rep);
        children.add(1, localS2.rep);

        // Assembles this.rep as an if-else statement with the children s1 and s2
        this.rep.assemble(label, children);

        // Clears s1 and s2
        localS1.createNewRep();
        localS2.createNewRep();

    }

    @Override
    public final Condition disassembleIfElse(Statement s1, Statement s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s1 is not null";
        assert s1 != this : "Violation of: s1 is not this";
        assert s2 != this : "Violation of: s2 is not this";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1 instanceof Statement2 : "Violation of: s1 is a Statement2";
        assert s2 instanceof Statement2 : "Violation of: s2 is a Statement2";
        assert this.kind() == Kind.IF_ELSE
                : "" + "Violation of: [this is an IF_ELSE statement]";

        // Changes the dynamic type of s
        Statement2 localS1 = (Statement2) s1;
        Statement2 localS2 = (Statement2) s2;

        // Initializes a new sequence then adds the children of this.rep
        Sequence<Tree<StatementLabel>> children = this.rep.newSequenceOfTree();
        StatementLabel label = this.rep.disassemble(children);

        // Initializes the s.rep
        localS1.rep = children.remove(0);
        localS2.rep = children.remove(0);

        // Clears this
        this.createNewRep();

        // Returns the condition of the IF statement
        return label.condition;
    }

    @Override
    public final void assembleWhile(Condition c, Statement s) {
        assert c != null : "Violation of: c is not null";
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";
        assert s instanceof Statement2 : "Violation of: s is a Statement2";
        assert s.kind() == Kind.BLOCK : "Violation of: [s is a BLOCK statement]";

        // Changes s's type to Statment2
        Statement2 localS = (Statement2) s;

        // Initializes a new while label with the condition c
        StatementLabel label = new StatementLabel(Kind.WHILE, c);

        // Initializes a new sequence then adds s's tree value to it
        Sequence<Tree<StatementLabel>> children = this.rep.newSequenceOfTree();
        children.add(0, localS.rep);

        // Assembles this.rep as a while statement with the child s
        this.rep.assemble(label, children);

        // Clears s
        localS.createNewRep();

    }

    @Override
    public final Condition disassembleWhile(Statement s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";
        assert s instanceof Statement2 : "Violation of: s is a Statement2";
        assert this.kind() == Kind.WHILE
                : "" + "Violation of: [this is a WHILE statement]";

        // Changes the dynamic type of s
        Statement2 localS = (Statement2) s;

        // Disassembles this.rep
        Sequence<Tree<StatementLabel>> children = this.rep.newSequenceOfTree();
        StatementLabel label = this.rep.disassemble(children);

        // Assigns the child of the while statement to localS
        localS.rep = children.remove(0);

        // Clears this
        this.createNewRep();

        // Returns the condition of the while statement
        return label.condition;
    }

    @Override
    public final void assembleCall(String inst) {
        assert inst != null : "Violation of: inst is not null";
        assert Tokenizer.isIdentifier(inst)
                : "" + "Violation of: inst is a valid IDENTIFIER";

        // Initializes a label with the kind CALL and instruction name of inst
        StatementLabel labelCall = new StatementLabel(Kind.CALL, inst);

        // Assembles the call with its label and no children
        Sequence<Tree<StatementLabel>> subTrees = this.rep.newSequenceOfTree();
        this.rep.assemble(labelCall, subTrees);

    }

    @Override
    public final String disassembleCall() {
        assert this.kind() == Kind.CALL : "" + "Violation of: [this is a CALL statement]";

        // Disassembles this.rep and assigns its children to subTrees
        Sequence<Tree<StatementLabel>> subTrees = this.rep.newSequenceOfTree();
        StatementLabel root = this.rep.disassemble(subTrees);

        // Initializes the instruction then clears this
        String inst = root.instruction;
        this.createNewRep();

        // Returns the instruction within the call
        return inst;
    }

}
