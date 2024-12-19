import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map2;
import components.queue.Queue;
import components.queue.Queue2;
import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program shall asks the user for an input file and spits out an HTML file
 * displaying the name of the input file in a heading followed by a table
 * listing the words in alphabetical order and their corresponding counts.
 *
 * @author Michael Hu
 */
public final class WordCounter {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
        // no code needed here
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        // Open streams
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        // Ask for input file
        out.println("Name of input file: ");
        SimpleReader inFile = new SimpleReader1L(in.nextLine());

        // Ask for output file name
        out.println("Name of output file: ");
        SimpleWriter outFile = new SimpleWriter1L(in.nextLine());

        // Create a set of separators used for word splitting
        Set2<Character> seperators = new Set2<Character>();
        createSeparators(seperators);

        // Create a map to store words and their counts
        Map<String, Integer> words = new Map2<String, Integer>();

        // Process the input file and populate the map with word counts
        initializeItems(inFile, words, seperators);

        // Generate an HTML page based on the word count data
        initializeHTMLPage(words, outFile);

        // Close input/output streams
        out.close();
        in.close();
        inFile.close();
        outFile.close();
    }

    /**
     * Populates the given set with separator characters.
     *
     * @param seperatorSet
     *            the set to be populated with separators
     */
    public static void createSeparators(Set<Character> seperatorSet) {
        seperatorSet.add(' ');
        seperatorSet.add('(');
        seperatorSet.add(')');
        seperatorSet.add('.');
        seperatorSet.add(',');
        seperatorSet.add(';');
        seperatorSet.add(':');
        seperatorSet.add('?');
        seperatorSet.add('!');
        seperatorSet.add('-');
        seperatorSet.add('"');
        seperatorSet.add('~');
    }

    /**
     * Reads the input file and counts occurrences of words, storing them in the
     * map.
     *
     * @param file
     *            the input file reader
     * @param map
     *            the map to store word counts
     * @param set
     *            the set of separator characters
     */
    public static void initializeItems(SimpleReader file, Map<String, Integer> map,
            Set<Character> set) {

        String line = "";
        int position = 0;
        String element = "";

        // Process each line in the input file
        while (!file.atEOS()) {
            line = file.nextLine();
            // Position to 0 again
            position = 0;

            while (position < line.length()) {
                // Extract the next word or separator
                element = nextWordOrSeparator(line, position, set).toLowerCase();
                position = position + element.length();

                // If the element is a word (not a separator), update the map
                if (!set.contains(element.charAt(0))) {
                    // Increment count if the word is already in the map
                    if (map.hasKey(element)) {
                        map.replaceValue(element, map.value(element) + 1);
                    } else {
                        // Add the word to the map with an initial count of 1
                        map.add(element, 1);
                    }
                }
            }
        }

    }

    /**
     * Extracts the next word or sequence of separator characters from the
     * string.
     *
     * @param s
     *            the string to process
     * @param position
     *            the starting position in the string
     * @param separators
     *            the set of separator characters
     * @return the next word or separator sequence
     */
    public static String nextWordOrSeparator(String s, int position,
            Set<Character> separators) {

        int count = 0;
        char returnedPiece = 'a';
        String returned = "";
        // Check if the character at the starting position is a separator
        if (separators.contains(s.charAt(position))) {
            // While loop to process a sequence of separator characters
            while (count < s.substring(position, s.length()).length()) {
                returnedPiece = s.charAt(position + count);
                if (separators.contains(s.charAt(position + count))) {
                    // Append the separator to the return string
                    returned = returned + returnedPiece;
                    count++;
                } else {
                    count = s.substring(position, s.length()).length();
                }
            }
            count = 0;
        } else {
            // While loop to process a sequence of nonseparator characters (a word)
            while (count < s.substring(position, s.length()).length()) {
                returnedPiece = s.charAt(position + count);
                // Continue adding characters if they are not separators
                if (!separators.contains(s.charAt(position + count))) {
                    // Append the separator to the return string
                    returned = returned + returnedPiece;
                    count++;
                } else {
                    count = s.substring(position, s.length()).length();
                }
            }
            count = 0;
        }
        return returned;
    }

    /**
     * Generates an HTML page displaying the words and their counts.
     *
     * @param map
     *            the map containing words and their counts
     * @param out
     *            the output file writer
     */
    public static void initializeHTMLPage(Map<String, Integer> map, SimpleWriter out) {

        Comparator<String> alphabetize = new Alpha();

        // Begin HTML document
        out.println("<!DOCTYPE html>");
        out.println("<html>\n\t<head>\n\t<style>\n" + "table, th, td{\nborder: "
                + "3px solid black;\n}th, " + "td \n\t</style>");

        // Set title and header for the page
        out.println("\n<title>" + "Words count" + "</title>\n\t</head>\n\t<body>\n"
        // Heading
                + "\t<h2><b>" + out.name() + "</b></h2>\n" + "<table style='width:30%'>\n"
                // Column names
                + "<tr><th>Word<th>Count");

        // Create a queue to store all words in alphabetical order
        Queue<String> allWords = new Queue2<String>();
        for (Pair<String, Integer> pair : map) {
            allWords.enqueue(pair.key());
        }

        // Sort the words alphabetically
        allWords.sort(alphabetize);

        // Output each word and its count in a table row
        for (String str : allWords) {
            if (map.hasKey(str)) {
                out.print("\t\t<tr>\n\t<td>" + str + "</td><td>" + map.value(str)
                        + "</td>\n\t</tr>\n");
            }
        }

        // Close the HTML tags
        out.print("\t\t</table>\n" + "\t</body>\n" + "</html>");
    }

    /**
     * Comparator to alphabetize strings. returns a negative integer, zero, or a
     * positive integer if the first string is less than, equal to, or greater
     * than the second string.
     *
     */

    private static final class Alpha implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }
}
