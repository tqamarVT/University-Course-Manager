// Virginia Tech Honor Code Pledge:
//
// On my honor:
//
// - I have not used source code obtained from another student, or any other
// unauthorized source,
// either modified or unmodified.
//
// - All source code and documentation used in my program is either my original
// work,
// or was derived by me from the source code published in the textbook for this
// course.
//
// I have not discussed coding details about this project with anyone other than
// my partner
// (in the case of a joint submission), instructor, ACM/UPE tutors or the TAs
// assigned to this course.
// I understand that I may discuss the concepts of this program with other
// students, and that another
// student may help me debug my program so long as neither of us writes anything
// during the discussion
// or modifies any computer file during the discussion. I have violated neither
// the spirit nor the letter
// of this restriction.

/**
 * @author Taimoor Qamar
 * @author Peter Dolan
 * @version 9/15/19
 *
 */
public class Coursemanager2 {

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
