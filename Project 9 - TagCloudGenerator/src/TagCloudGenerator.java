import java.io.IOException;
import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;
import components.utilities.Reporter;

/**
 * This program first prompts the user for an input file, then takes every word
 * in that file and counts the utilization of each. The program then outputs tag
 * cloud with the font size corresponding the count of each word in the top n
 * used entries.
 *
 * @author Sean-Paul Billups & Michael Hu
 *
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
    }

    /**
     * Comparator instance to be used in all test cases. // TODO - Change
     */
    private static final MaxOrder MAX_ORDER = new MaxOrder();

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final NumOrder NUM_ORDER = new NumOrder();

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final WordOrder WORD_ORDER = new WordOrder();

    /**
     * Comparator to be used for the comparison of integers.
     */
    private static final class MaxOrder implements Comparator<Integer> {

        @Override
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);

        }
    }

    /**
     * Comparator to be used for the comparison of Map.Pair values.
     */
    private static final class NumOrder implements Comparator<Map.Pair<String, Integer>> {

        @Override
        public int compare(Map.Pair<String, Integer> a, Map.Pair<String, Integer> b) {
            return b.value().compareTo(a.value());

        }
    }

    /**
     * Comparator to be used for the comparison of Map.Pair keys.
     */
    private static final class WordOrder
            implements Comparator<Map.Pair<String, Integer>> {

        @Override
        public int compare(Map.Pair<String, Integer> a, Map.Pair<String, Integer> b) {
            return a.key().compareTo(b.key());

        }
    }

    /**
     * Retrieves the words from the file and returns a sequence of the words.
     *
     * @param file
     *            The given file name
     * @return keys - The sequence of words from the text
     * @throws IOException
     */
    private static String getText(String file) throws IOException {

        // Initializes a file reader and string buffer
        SimpleReader reader = new SimpleReader1L(file);
        String s = "";

        // Adds the text file to a string
        while (!reader.atEOS()) {

            // Appends the next line to the string
            s += reader.nextLine() + " ";

        }

        // Closes the file reader and returns the
        reader.close();

        // Initializes a string equal to the text in the file and an empty map
        return s;

    }

    /**
     * Adds a given word to the map or increments its value.
     *
     * @param text
     *            The string to add to the map
     * @return words - The map of words and their word count
     */
    private static Map<String, Integer> createWordMap(String text) {

        // Initializes an empty map
        Map<String, Integer> words = new Map1L<>();

        // Initializes an empty string
        String str = "";

        // Adds each full word to the map as a key
        for (int i = 0; i < text.length(); i++) {

            // Concatenates the character (letter) to the end of the string
            if (Character.isLetter(text.charAt(i))) {
                str += text.charAt(i);

            } else if (!str.isBlank()) {

                str = str.toLowerCase();

                // Adds the word to the map or increments its word count
                if (!words.hasKey(str)) {

                    // Adds the word to the map
                    words.add(str, 1);

                } else {

                    // Increments the word count
                    words.replaceValue(str, words.value(str) + 1);

                }

                // Clears the string
                str = "";

            }
        }

        return words;
    }

    /**
     * Creates a SortingMachine of the pairs.
     *
     * @param fonts
     *            The SortingMachine of font orders
     * @param words
     *            The map for the words and their word count
     * @param num
     *            The number of desired elements
     * @return The SortingMachine of pairs
     */
    private static SortingMachine<Pair<String, Integer>> createSort(
            SortingMachine<Integer> max, Map<String, Integer> words, int num) {

        // Initializes a sorting machine that accounts for number order
        SortingMachine<Map.Pair<String, Integer>> numSort = new SortingMachine1L<>(
                NUM_ORDER);

        // Iterates through each pair and adds the pair to the SortingMachine
        for (Map.Pair<String, Integer> pair : words) {
            numSort.add(pair);

        }

        // Initializes a SortingMachine that accounts for alphabetical order
        SortingMachine<Map.Pair<String, Integer>> wordSort = new SortingMachine1L<>(
                WORD_ORDER);

        // Changes the number SortingMachine into ExtractionMode
        numSort.changeToExtractionMode();

        // Adds the pairs that contain the highest values
        for (int i = 0; i < num; i++) {
            Map.Pair<String, Integer> pair = numSort.removeFirst();

            max.add(pair.value());
            wordSort.add(pair);

        }

        // Returns the SortingMachine
        return wordSort;
    }

    /**
     * Prints the header of the HTML file.
     *
     * @param out
     *            The SimpleWriter
     * @param inputFile
     *            The given input file name
     * @param sm
     *            The SortingMachine
     */
    public static void printHTML(SimpleWriter out, String inputFile,
            SortingMachine<Map.Pair<String, Integer>> sm, int interval) {

        int numWords = sm.size();

        // Prints the header of the output file
        out.println("<html><head><title>Top " + numWords + " words in " + inputFile
                + "</title>");
        out.println(
                "<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println(
                "<link href=\"https://cse22x1.engineering.osu.edu/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        // out.println("<style>");
        // out.println(".word-cloud-box {");
        // out.println("    background-color: lightgray;"); // Box background color
        // out.println("    padding: 20px;"); // Inner padding
        // out.println("    border-radius: 1px;"); // Corners
        // out.println("    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);"); // Subtle shadow
        // out.println("    margin: 0px;"); // Centering the box
        // out.println("    width: fit-content;"); // Auto-sizing the box
        // out.println("    text-align: center;"); // Center-align the text
        // out.println("}");
        // out.println("</style>");
        out.println("</head><body>");

        out.println("<h2 style=\"color: blue;\">Top " + numWords + " words in "
                + inputFile + "</h2>");

        // Add the word cloud content inside the styled box
        //  out.println("<div class=\"word-cloud-box\">");

        sm.changeToExtractionMode();

        while (sm.size() > 0) {
            Map.Pair<String, Integer> pair = sm.removeFirst();
            int fontSize = 11 + (pair.value() / interval); // Calculate font size
            out.print("<span style=\"cursor:default; font-size:" + fontSize
                    + "px;\" title=\"count: " + pair.value() + "\">" + pair.key()
                    + "&nbsp;&nbsp;&nbsp;");

            if (sm.size() % 10 == 0) {
                out.print("<br>");
            }

            out.print("</span>");
        }

        //  out.println("</div>"); // Close the styled box

        // Prints the footer of the output file
        out.println("</body></html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // // Prompts user for an input file
        // out.print("What is the name of your input file? ");
        // String inFile = in.nextLine();

        // // Prompts user for an output file
        // out.print("What is the name of your output file? ");
        // String outFile = in.nextLine();

        // out.print("How many words do you want in the tag cloud? ");
        // int num = in.nextInteger();

        String inFile = "data/importance.txt";
        String outFile = "data/output.html";
        final int num = 100;

        Reporter.assertElseFatalError(num > 0, "Number of words must be positive (n.");

        // Creates a string of the text within the input file
        String text = getText(inFile);

        // Creates a map of the words and their word counts
        Map<String, Integer> words = createWordMap(text);

        SortingMachine<Integer> maxSort = new SortingMachine1L<>(MAX_ORDER);
        SortingMachine<Map.Pair<String, Integer>> sm = createSort(maxSort, words, num);

        // Creates an interval for which the font size will be incremented
        int interval = maxSort.removeFirst() / 37;

        SimpleWriter output = new SimpleWriter1L(outFile);

        // Prints the words and their word counts onto the output file
        printHTML(output, inFile, sm, interval);

        // Close input/output streams
        in.close();
        out.close();
        output.close();
    }

}
