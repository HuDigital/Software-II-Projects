import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Michael Hu, Sean-Paul Billups
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {

        // Don't want repeat so we call CreateNewRep function
        this.createNewRep();

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        // Tests whether the input is equal to 0
        if (i == 0) {
            this.createNewRep();

        } else {
            // Gives the string the value of the integer i
            this.rep = String.valueOf(i);
        }

    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*")
                : "" + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        // Tests whether the input is equal to 0
        if (s.equals("0")) {
            this.createNewRep();

        } else {
            // Gives the string the value of the string s
            this.rep = s;

        }

    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";

        // Tests whether the input is equal to 0
        if (n.compareTo(new NaturalNumber1L(0)) == 0) {
            this.createNewRep();

        } else {
            // Gives the string the value of the natural number n
            this.rep = String.valueOf(n);

        }

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
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
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3
                : "" + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";

        // Concatenates k to the end of the string
        this.rep += k;

    }

    @Override
    public final int divideBy10() {
        int i = 0;

        //
        if (this.rep.length() != 0) {

            // Finds/initializes the character at the end of the string
            String s = this.rep.substring(this.rep.length() - 1);
            i = Integer.parseInt(s);
            // Modifies the current string
            this.rep = this.rep.substring(0, this.rep.length() - 1);

        }

        // Returns the integer version of the string
        return i;
    }

    @Override
    public final boolean isZero() {

        // Returns whether the length of the string is zero
        return this.rep.length() == 0;
    }

}
