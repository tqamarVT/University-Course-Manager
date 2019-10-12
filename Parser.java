
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Peter Dolan (peter8)

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Parses commands from command file and calls respective functions from the
 * CourseManager.
 * 
 * @author Taimoor Qamar
 * @author Peter Dolan
 * @version 8/29/19
 *
 */
public class Parser {
    /**
     * Reads the file for commands and calls other methods to execute commands.
     * 
     * @param filename
     *            name of the file to parse
     */
    public static void parseFile(String filename) {
        // System.out.println(new File(".").getAbsoluteFile());
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return;
        }
        String line = null;
        while (fileScanner.hasNext()) { // maybe use hasNextLine() ?
            line = fileScanner.nextLine();
            parseLine(line);
        }
        fileScanner.close(); // hopefully good enough

    }


    /**
     * reads the line and calls the appropriate function from the Section class
     * 
     * @param line
     *            line of text from the file (includes command and arguments)
     */
    private static void parseLine(String line) {
        Scanner lineScanner = new Scanner(line);
        if (lineScanner.hasNext()) {
            String commandType = lineScanner.next();
            switch (commandType) { // hopefully the command will be lower-case
                case "loadstudentdata":
                    loadstudentdata(lineScanner);
                    break;
                case "loadcoursedata":
                    loadcoursedata(lineScanner);
                    break;
                case "section": // unsure if command file is guaranteed to be
                                // formatted properly...
                    section(lineScanner);
                    break;
                case "insert":
                    insert(lineScanner); // different
                    break;
                case "searchid":
                    search(lineScanner);
                    break;
                case "score":
                    score(lineScanner);
                    break;
                case "remove":
                    remove(lineScanner); // different
                    break;
                case "clearsection":
                    removeSection(lineScanner);
                    break;
                case "dumpsection":
                    CourseManager.dumpSection();
                    break;
                case "grade":
                    CourseManager.grade();
                    break;
                case "stat":
                    CourseManager.stat();
                    break;
                case "list":
                    list(lineScanner);
                    break;
                case "findpair":
                    findpair(lineScanner);
                    break;
                case "merge":
                    CourseManager.merge();
                    break;
                case "savestudentdata":
                    savestudentdata(lineScanner);
                    break;
                case "savecoursedata":
                    savecoursedata(lineScanner);
                    break;
                case "clearcoursedata":
                    CourseManager.clearcoursedata();
                    break;
                default:
                    System.out.println("Invalid command");
                    break;

            }
        }
        lineScanner.close();
    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.loadstudentdata() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void loadstudentdata(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        CourseManager.loadstudentdata(arg1);
    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.loadcoursedata() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void loadcoursedata(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        CourseManager.loadcoursedata(arg1);
    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.section() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void section(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        int sectionNumber = 1;
        try {
            sectionNumber = Integer.parseInt(arg1);
        }
        catch (NumberFormatException e) {
            System.out.println("Failed parsing " + arg1 + " into an integer");
        }
        CourseManager.section(sectionNumber);

    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.insert() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void insert(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        String arg2 = lineScanner.next();
        String arg3 = lineScanner.next();
        CourseManager.insert(arg1, arg2, arg3);
    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.search() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void search(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        String arg2 = null;
        if (lineScanner.hasNext()) { // not sure about this
            arg2 = lineScanner.next();
            CourseManager.search(arg1, arg2);
        }
        else {
            CourseManager.search(arg1);
        }

    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.score() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void score(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        int score = 0;
        try {
            score = Integer.parseInt(arg1);
        }
        catch (NumberFormatException e) {
            System.out.println("Failed parsing " + arg1 + " into an integer");
        }
        CourseManager.score(score);

    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.remove() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void remove(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        String arg2 = null;
        if (lineScanner.hasNext()) { // not sure about this
            arg2 = lineScanner.next();
            CourseManager.remove(arg1, arg2);
        }
        else {
            CourseManager.remove(arg1);
        }
    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.clearSection() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void removeSection(Scanner lineScanner) {
        if (lineScanner.hasNext()) {
            String arg1 = lineScanner.next();
            int alsoSectionNumber = 0;
            try {
                alsoSectionNumber = Integer.parseInt(arg1);
            }
            catch (NumberFormatException e) {
                System.out.println("Failed parsing " + arg1
                    + " into an integer");
            }
            CourseManager.clearSection(alsoSectionNumber);
        }
        else {
            CourseManager.clearSection();
        }

    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.list() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void list(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        arg1 = Name.format(arg1);
        CourseManager.list(arg1);
    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.findpair() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void findpair(Scanner lineScanner) {
        String arg1 = null;
        int alsoScore = 0;
        if (lineScanner.hasNext()) {
            arg1 = lineScanner.next();

            try {
                alsoScore = Integer.parseInt(arg1);
            }
            catch (NumberFormatException e) {
                System.out.println("Failed parsing " + arg1
                    + " into an integer");
            }
        }
        CourseManager.findpair(alsoScore);

    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.savestudentdata() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void savestudentdata(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        CourseManager.savestudentdata(arg1);
    }


    /**
     * Reads arguments from the file using the passed Scanner and calls
     * CourseManager.savecoursedata() with the arguments from the file.
     * 
     * @param lineScanner
     *            Scanner object at the position in the file after reading the
     *            command but before reading the arguments
     */
    public static void savecoursedata(Scanner lineScanner) {
        String arg1 = lineScanner.next();
        CourseManager.savecoursedata(arg1);
    }

}
