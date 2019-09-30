import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.ByteBuffer;
import java.nio.file.Files;

/**
 * Handles reading from and writing to .csv and .data files
 * 
 * @author Peter Dolan
 * @version 9/28/19
 */
public class SaveAndLoad {
    private final int initialCapacity = 100000;
    private String filename;
    private final int maxNameLength = 15;
    private final int maxPIDLength = 8; // in bytes


    /**
     * Makes a new SaveAndLoad
     * 
     * @param fname
     *            the file name used for saving and loading
     */
    public SaveAndLoad(String fname) {
        filename = fname;

    }


    /**
     * Gives the filename associated with this SaveAndLoad
     * 
     * @return filename
     */
    public String getFilename() {
        return filename;
    }


    /**
     * Sets the filename to be newFileName
     * 
     * @param newFileName
     *            the String to become the file name.
     */
    public void setFilename(String newFileName) {
        filename = newFileName;
    }


    /**
     * Reads all of the student info from this SaveAndLoad's filename and
     * returns
     * an array of DetailedStudents with the corresponding information
     * 
     * @return an array of DetailedStudents with the corresponding
     *         information or null if error
     */
    public DetailedStudent[] loadStudentData() {
        ArrayList<DetailedStudent> studs = new ArrayList<>(initialCapacity);
        if (filename.contains(".csv")) {
            Scanner fileScanner = null;
            try {
                fileScanner = new Scanner(new File(filename));
            }
            catch (FileNotFoundException e) {
                System.out.println(".csv File not found to load student data");
                return null;
            }
            while (fileScanner.hasNext()) { // what about blank lines?
                String line = fileScanner.nextLine();
                // System.out.println(line);
                readLineForLSD(line, studs);
            }
            fileScanner.close();
        }
        else if (filename.contains(".data")) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(filename));
                int numRecords = ((0xFF & bytes[10]) << 24) | ((0xFF
                    & bytes[11]) << 16) | ((0xFF & bytes[12]) << 8) | (0xFF
                        & bytes[13]); // converts byte array [10, 13] to int
                long pid = -1;
                String firstName = null;
                String middleName = null; // I recognize I did not do this
                String lastName = null; // how the spec said
                for (int i = 14; i < bytes.length; i += 64) {
                    pid = ((0xFF & bytes[i]) << 56) | ((0xFF & bytes[i
                        + 1]) << 48) | ((0xFF & bytes[i + 2]) << 40) | ((0xFF
                            & bytes[i + 3]) << 32) | ((0xFF & bytes[i
                                + 4]) << 24) | ((0xFF & bytes[i + 5]) << 16)
                        | ((0xFF & bytes[i + 6]) << 8) | (0xFF & bytes[i + 7]);
                    byte[] fnBytes = { bytes[i + 8], bytes[i + 9], bytes[i
                        + 10], bytes[i + 11], bytes[i + 12], bytes[i + 13],
                        bytes[i + 14], bytes[i + 15], bytes[i + 16], bytes[i
                            + 17], bytes[i + 18], bytes[i + 19], bytes[i + 20],
                        bytes[i + 21], bytes[i + 22] }; // read 15 chars
                    firstName = new String(fnBytes, "US-ASCII").trim();
                    byte[] mnBytes = { bytes[i + 24], bytes[i + 25], bytes[i
                        + 26], bytes[i + 27], bytes[i + 28], bytes[i + 29],
                        bytes[i + 30], bytes[i + 31], bytes[i + 32], bytes[i
                            + 33], bytes[i + 34], bytes[i + 35], bytes[i + 36],
                        bytes[i + 37], bytes[i + 38] };
                    middleName = new String(mnBytes, "US-ASCII").trim();
                    byte[] lnBytes = { bytes[i + 40], bytes[i + 41], bytes[i
                        + 42], bytes[i + 43], bytes[i + 44], bytes[i + 45],
                        bytes[i + 46], bytes[i + 47], bytes[i + 48], bytes[i
                            + 49], bytes[i + 50], bytes[i + 51], bytes[i + 52],
                        bytes[i + 53], bytes[i + 54] };
                    lastName = new String(lnBytes, "US-ASCII").trim();
                    studs.add(new DetailedStudent((int)pid, firstName,
                        middleName, lastName));
                }
            }
            catch (IOException e) {
                System.out.println(
                    "binary file not found to load student data");
                return null;
            }
        }
        else {
            System.out.println("Unable to read file to load student data");
            return null;
        }

        return studs.toArray(new DetailedStudent[1]);
    }


    /**
     * Reads a line of text for the load student data method and adds a new
     * DeatiledStudent with values from the text to studs
     * 
     * @param line
     *            a line of text
     * @param studs
     *            the list of DetailedStudents to which a new DetailedStudent
     *            will be added
     * @return
     *         the list of DetailedStudents after a new DetailedStudent is added
     */
    private ArrayList<DetailedStudent> readLineForLSD(
        String line,
        ArrayList<DetailedStudent> studs) {
        ArrayList<DetailedStudent> result = studs;
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter("\\s*,\\s*");
        int pid = -1;
        String firstName = null;
        String middleName = null;
        String lastName = null;
        if (lineScanner.hasNext()) {
            pid = lineScanner.nextInt();
            firstName = lineScanner.next();
            middleName = lineScanner.next();
            lastName = lineScanner.next();
            lastName = lastName.trim();
            studs.add(new DetailedStudent(pid, firstName, middleName,
                lastName));
        }
        lineScanner.close();
        return result;
    }


    /**
     * Reads all of the student info from this SaveAndLoad's filename and
     * returns
     * an array of CourseStudents with the corresponding information
     * 
     * @return an ArrayList of CourseStudents with the corresponding
     *         information
     */
    public CourseStudent[] loadCourseData() {
        ArrayList<CourseStudent> studs = new ArrayList<>(initialCapacity);
        if (filename.contains(".csv")) { // can file names contain .s? (i.e.
                                         // fd.csv.data) (I am guessing no)
            Scanner fileScanner = null;
            try {
                fileScanner = new Scanner(new File(filename));
            }
            catch (FileNotFoundException e) {
                System.out.println(".csv File not found to load course data");
            }
            while (fileScanner.hasNext()) { // what about blank lines?
                readLineForLCD(fileScanner.nextLine(), studs);
            }
            fileScanner.close();
        }
        else if (filename.contains(".data")) {

        }
        else {
            System.out.println("Unable to read file to load course data");
        }
        return studs.toArray(new CourseStudent[1]);
    }


    /**
     * Reads a line of text for the load student data method and adds a new
     * CourseStudent with values from the text to studs
     * 
     * @param line
     *            a line of text
     * @param studs
     *            the list of CourseStudents to which a new CourseStudent
     *            will be added
     * @return
     *         the list of CourseStudents after a new CourseStudent is added
     */
    private ArrayList<CourseStudent> readLineForLCD(
        String line,
        ArrayList<CourseStudent> studs) {
        ArrayList<CourseStudent> result = studs;
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter("\\s+,");
        int sectionID = -1;
        int pid = -1;
        String firstName = null;
        String lastName = null;
        int score = -1;
        String grade = ""; // unsure how this will be represented
        grade = grade + ""; // temporary
        if (lineScanner.hasNext()) {
            sectionID = lineScanner.nextInt();
            pid = lineScanner.nextInt();
            firstName = lineScanner.next();
            lastName = lineScanner.next();
            score = lineScanner.nextInt();
            grade = lineScanner.next();
            studs.add(new CourseStudent(sectionID, pid, firstName, lastName,
                score));
        }
        lineScanner.close();
        return result;
    }


    /**
     * Writes the information contained in the array of DetailedStudents to
     * a binary (.data) file
     * 
     * @param students
     *            the array containing all of the DetailedStudents to write
     *            to the file
     */
    public void saveStudentData(DetailedStudent[] studs) {
        ArrayList<DetailedStudent> students = new ArrayList<DetailedStudent>(
            initialCapacity);
        for (int i = 0; i < studs.length; i++) {
            students.add(studs[i]);
        }
        Integer size = students.size();
        byte[] toFile = new byte[14 + size * 64];
        String vtStudents = "VTSTUDENTS";
        byte[] buffer1 = new byte[10];
        try {
            buffer1 = vtStudents.getBytes("US-ASCII");
            for (int i = 0; i < buffer1.length; i++) {
                toFile[i] = buffer1[i];
            }
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding in saveStudentData 1");
        }
        String goHokies = "GOHOKIES";
        byte[] buffer2 = new byte[8];
        try {
            buffer2 = goHokies.getBytes("US-ASCII");
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding in saveStudentData 2");
        }
        String dollarSign = "$";
        byte[] money = new byte[1];
        try {
            money = dollarSign.getBytes("US-ASCII");
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding in saveStudentData 3");
        }
        byte[] indexLength = ByteBuffer.allocate(Integer.BYTES).putInt(size)
            .array();
        for (int i = 0; i < indexLength.length; i++) {
            toFile[i + buffer1.length] = indexLength[i];
        }
        byte[] pid = new byte[maxPIDLength];
        byte[] fName = new byte[maxNameLength];
        byte[] mName = new byte[maxNameLength];
        byte[] lName = new byte[maxNameLength];
        DetailedStudent currentStudent = null;
        for (int i = 0; i < size; i++) {
            currentStudent = students.remove(0);
            pid = ByteBuffer.allocate(Long.BYTES).putLong((long)currentStudent
                .getPID()).array();
            for (int j = 0; j < pid.length; j++) {
                toFile[j + (64 * i) + buffer1.length + indexLength.length] =
                    pid[j];
            }
            try {
                fName = currentStudent.getFirstName().getBytes("US-ASCII");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 4 "
                    + i);
            }
            for (int j = 0; j < fName.length; j++) {
                toFile[j + (64 * i) + maxPIDLength + buffer1.length
                    + indexLength.length] = fName[j];
            }
            toFile[maxNameLength + maxPIDLength + buffer1.length
                + indexLength.length] = money[0];
            try {
                mName = currentStudent.getMiddleName().getBytes("US-ASCII");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 5 "
                    + i);
            }
            for (int j = 0; j < mName.length; j++) {
                toFile[money.length + j + (64 * i) + maxNameLength
                    + maxPIDLength + buffer1.length + indexLength.length] =
                        mName[j];
            }
            toFile[money.length + 2 * maxNameLength + maxPIDLength
                + buffer1.length + indexLength.length] = money[0];
            try {
                lName = currentStudent.getLastName().getBytes("US-ASCII");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 6 "
                    + i);
            }
            for (int j = 0; j < lName.length; j++) {
                toFile[2 * money.length + j + (64 * i) + 2 * maxNameLength
                    + maxPIDLength + buffer1.length + indexLength.length] =
                        lName[j];
            }
            toFile[2 * money.length + 3 * maxNameLength + maxPIDLength
                + buffer1.length + indexLength.length] = money[0];
            for (int j = 0; j < buffer2.length; j++) {
                toFile[3 * money.length + j + (64 * i) + 3 * maxNameLength
                    + maxPIDLength + buffer1.length + indexLength.length] =
                        buffer2[j];
            }
        }
        try {
            Files.write(Paths.get(filename), toFile);
        }
        catch (IOException e) {
            System.out.println("Error writing to file to save student data");
        }
    }


    /**
     * Writes the information contained in the array of CourseStudents to a
     * binary
     * (.data) file
     * 
     * @param students
     *            the array containing all of the students to write to the
     *            file
     */
    public void saveCourseData(CourseStudent[] students) {

    }

}
