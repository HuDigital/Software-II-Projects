import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Sean-Paul Billups and Michael Hu
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens, Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION")
                : "" + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        // Eliminates the "INSTRUCTION" string
        tokens.dequeue();

        // Checks whether the identifier is valid
        String identifier = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(identifier),
                "INVALID TOKEN");

        // Checks whether the next token is "IS"
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"), "INVALID TOKEN");

        /*
         * Iterates through the body of $this by calling parseBlock until the
         * end of tokens is reached.
         */

        body.parseBlock(tokens);

        tokens.dequeue();

        // Checks whether the next token is the same as the identifier
        Reporter.assertElseFatalError(tokens.dequeue().equals(identifier),
                "INVALID TOKEN");

        // Returns the name of the instruction removed from tokens
        return identifier;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // Checks whether the first token is "PROGRAM"
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "INVALID TOKEN");

        /*
         * Checks whether the identifier is valid, then assigns it as the name
         * of the program.
         */
        String identifier = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(identifier),
                "INVALID TOKEN");
        this.setName(identifier);

        // Checks whether the next token is "IS"
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"), "INVALID TOKEN");

        /*
         * Initializes a map for the context of this, then initializes a new
         * statement for the parseInstruction method.
         */
        Map<String, Statement> context = this.newContext();
        this.swapContext(context);

        // Checks whether the next token is "INSTRUCTION" or "BEGIN"
        Reporter.assertElseFatalError(
                tokens.front().equals("INSTRUCTION") || tokens.front().equals("BEGIN"),
                "INVALID TOKEN");

        /*
         * Adds a corresponding Map.Pair to the context for each given
         * instruction in $this.
         */
        while (tokens.front().equals("INSTRUCTION")) {
            Statement s = this.newBody();
            String name = parseInstruction(tokens, s);
            context.add(name, s);

        }

        // Checks whether the next token is "BEGIN"
        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"), "INVALID TOKEN");

        // Assigns the instructions to $this.
        this.swapContext(context);

        // Swaps the body of this
        Statement b = this.newBody();
        this.swapBody(b);

        /*
         * Iterates through the body of $this by calling parseBlock until the
         * end of tokens is reached.
         */
        while (!tokens.front().equals(identifier)) {
            b.parseBlock(tokens);
            tokens.dequeue();

        }

        // Swaps the body then removes the identifier at the end of $tokens
        this.swapBody(b);
        tokens.dequeue();

        // Checks whether the only token is "END OF INPUT"
        Reporter.assertElseFatalError(tokens.length() == 1, "INVALID TOKEN");

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        // out.print("Enter valid BL program file name: "); TEMP
        // String fileName = in.nextLine(); TEMP
        String fileName = "test/program2.bl";
        /*
         * Parse input file
         */
        //out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        // out.println(tokens);
        //out.println();
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        //out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
