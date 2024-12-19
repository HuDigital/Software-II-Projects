import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Sean-Paul Billups and Michael Hu
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer.isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF")
                : "" + "Violation of: <\"IF\"> is proper prefix of tokens";

        //start
        Reporter.assertElseFatalError(Tokenizer.isKeyword(tokens.dequeue()),
                "INVALID TOKEN");
        String tokenCondition = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(tokenCondition),
                "INVALID CONDITION");
        Statement.Condition c = parseCondition(tokenCondition);

        Reporter.assertElseFatalError(tokens.dequeue().equals("THEN"), "INVALID TOKEN");

        //body
        Statement ifBody = s.newInstance();
        ifBody.parseBlock(tokens);

        if (tokens.front().equals("ELSE")) {

            tokens.dequeue();
            Statement elseBody = s.newInstance();
            elseBody.parseBlock(tokens);
            s.assembleIfElse(c, ifBody, elseBody);

        } else {

            s.assembleIf(c, ifBody);

        }

        String front = tokens.front();

        //end

        Reporter.assertElseFatalError(tokens.dequeue().equals("END"), "INVALID TOKEN");

        Reporter.assertElseFatalError(Tokenizer.isKeyword(tokens.dequeue()),
                "INVALID TOKEN");

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE")
                : "" + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        // Checks whether the first token is a keyword
        Reporter.assertElseFatalError(Tokenizer.isKeyword(tokens.dequeue()),
                "INVALID TOKEN");

        // Parses the condition
        String tokenCondition = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(tokenCondition),
                "INVALID CONDITION");
        Statement.Condition c = parseCondition(tokenCondition);

        // Checks whether the next token is "DO"
        Reporter.assertElseFatalError(tokens.dequeue().equals("DO"), "INVALID TOKEN");

        // Parses the While statement, then assembles it
        Statement whileBody = s.newInstance();
        whileBody.parseBlock(tokens);
        s.assembleWhile(c, whileBody);

        //end
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"), "INVALID TOKEN");
        Reporter.assertElseFatalError(Tokenizer.isKeyword(tokens.dequeue()),
                "INVALID TOKEN");

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && Tokenizer.isIdentifier(tokens.front())
                : "" + "Violation of: identifier string is proper prefix of tokens";

        String call = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(call), "INVALID TOKEN");
        s.assembleCall(call);

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String token = tokens.front();
        Reporter.assertElseFatalError(
                Tokenizer.isIdentifier(token) || Tokenizer.isKeyword(token),
                "INVALID TOKEN");

        if (token.equals("IF")) {
            parseIf(tokens, this);
        } else if (token.equals("WHILE")) {
            parseWhile(tokens, this);
        } else {
            parseCall(tokens, this);
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Statement s = this.newInstance();
        while (!tokens.front().equals("END") && !tokens.front().equals("ELSE")
                && !tokens.front().equals(Tokenizer.END_OF_INPUT)) {
            s.parse(tokens);
            this.addToBlock(this.lengthOfBlock(), s);

        }

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
        //out.print("Enter valid BL statement(s) file name: ");
        //String fileName = in.nextLine();
        String fileName = "test/statement1.bl";
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
