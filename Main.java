/**
 * @author Taimoor Qamar
 * @version 9/15/19
 *
 */

public class Main {
    

    /**
     * The main program. It reads the command-line arguments and passes the
     * filename to the parser class.
     * 
     * @param args
     *            the name of the command file
     */
    public static void main(String[] args) {
        // System.out.println(args[0]);
        if (args != null && args.length > 0) {
            Parser.parseFile(args[0]);
        }

    }

}
