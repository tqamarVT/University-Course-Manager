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
            while (fileScanner.hasNext()) { // what about blank lines? Prob
                                            // won't happen
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
                String middleName = null;
                String lastName = null;
                int currentIndex = 14;
                // System.out.println("numRecords: " + numRecords);
                for (int i = 0; i < numRecords; i++) {
                    pid = ((0xFF & bytes[currentIndex++]) << 56) | ((0xFF
                        & bytes[currentIndex++]) << 48) | ((0xFF
                            & bytes[currentIndex++]) << 40) | ((0xFF
                                & bytes[currentIndex++]) << 32) | ((0xFF
                                    & bytes[currentIndex++]) << 24) | ((0xFF
                                        & bytes[currentIndex++]) << 16) | ((0xFF
                                            & bytes[currentIndex++]) << 8)
                        | (0xFF & bytes[currentIndex++]);
                    byte[] fnBytes = new byte[16];
                    int tempIndex = 0;
                    while (bytes[currentIndex] != 0x24) { // 0x24 == $
                        // System.out.println(String.valueOf(currentIndex) + ":
                        // "
                        // + bytes[currentIndex]);
                        fnBytes[tempIndex] = bytes[currentIndex];
                        currentIndex++;
                        tempIndex++;
                    }
                    currentIndex++; // because $
                    firstName = new String(fnBytes, "UTF-8").trim();
                    byte[] mnBytes = new byte[16];
                    tempIndex = 0;
                    while (bytes[currentIndex] != 0x24) { // 0x24 == $
                        mnBytes[tempIndex] = bytes[currentIndex];
                        currentIndex++;
                        tempIndex++;
                    }
                    currentIndex++;
                    middleName = new String(mnBytes, "UTF-8").trim();
                    byte[] lnBytes = new byte[16];
                    tempIndex = 0;
                    while (bytes[currentIndex] != 0x24) { // 0x24 == $
                        lnBytes[tempIndex] = bytes[currentIndex];
                        currentIndex++;
                        tempIndex++;
                    }
                    currentIndex++;
                    lastName = new String(lnBytes, "UTF-8").trim();
                    studs.add(new DetailedStudent((int)pid, firstName,
                        middleName, lastName));
                    currentIndex += 8; // because GOHOKIES
                }
            }
            catch (IOException e) {
                // System.out.println("binary file not found to load student
                // data");
                return null;
            }
        }
        else

        {
            System.out.println("Unable to read file to load student data");
            return null;
        }
        System.out.println(filename + " successfully loaded"); // won't test
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
     * Reads all of the course info from this SaveAndLoad's filename and
     * returns a BST of CourseStudents with the corresponding information
     * (sorted by PID). Section numbers are included in CourseStudent to
     * indicate the section into which each should be inserted.
     * 
     * @return a BST of CourseStudents with the corresponding
     *         information (sorted by PID) or null if error
     */
    public BST<String, CourseStudent> loadCourseData() {
        if (!StudentManager.isInitialized()) {
            System.out.println("Course Load Failed. You have to load Student "
                + "Information file first.");
            return null;
        }
        BST<String, CourseStudent> studs = new BST<>();
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
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(filename));
                int numSections = ((0xFF & bytes[10]) << 24) | ((0xFF
                    & bytes[11]) << 16) | ((0xFF & bytes[12]) << 8) | (0xFF
                        & bytes[13]); // converts byte array [10, 13] to int
                long pid = -1;
                String firstName = null;
                String lastName = null;
                int score = -1;
                String grade = null;
                int currentIndex = 14;
                // System.out.println("numRecords: " + numRecords);
                for (int i = 1; i <= numSections; i++) {
                    int studPerSection = ((0xFF & bytes[currentIndex++]) << 24)
                        | ((0xFF & bytes[currentIndex++]) << 16) | ((0xFF
                            & bytes[currentIndex++]) << 8) | (0xFF
                                & bytes[currentIndex++]);
                    for (int j = 0; j < studPerSection; j++) {
                        pid = ((0xFF & bytes[currentIndex++]) << 56) | ((0xFF
                            & bytes[currentIndex++]) << 48) | ((0xFF
                                & bytes[currentIndex++]) << 40) | ((0xFF
                                    & bytes[currentIndex++]) << 32) | ((0xFF
                                        & bytes[currentIndex++]) << 24) | ((0xFF
                                            & bytes[currentIndex++]) << 16)
                            | ((0xFF & bytes[currentIndex++]) << 8) | (0xFF
                                & bytes[currentIndex++]);
                        byte[] fnBytes = new byte[16];
                        int tempIndex = 0;
                        while (bytes[currentIndex] != 0x24) { // 0x24 == $
                            fnBytes[tempIndex] = bytes[currentIndex];
                            currentIndex++;
                            tempIndex++;
                        }
                        currentIndex++; // because $
                        firstName = new String(fnBytes, "UTF-8").trim();
                        byte[] lnBytes = new byte[16];
                        tempIndex = 0;
                        while (bytes[currentIndex] != 0x24) { // 0x24 == $
                            lnBytes[tempIndex] = bytes[currentIndex];
                            currentIndex++;
                            tempIndex++;
                        }
                        currentIndex++;
                        lastName = new String(lnBytes, "UTF-8").trim();
                        score = ((0xFF & bytes[currentIndex++]) << 24) | ((0xFF
                            & bytes[currentIndex++]) << 16) | ((0xFF
                                & bytes[currentIndex++]) << 8) | (0xFF
                                    & bytes[currentIndex++]);
                        byte[] gradeBytes = { bytes[currentIndex++],
                            bytes[currentIndex++] };
                        grade = new String(gradeBytes, "UTF-8").trim();
                        // verify student info
                        if (StudentManager.find(String.valueOf(pid)) == null) {
                            System.out.println("Warning: Student " + firstName
                                + " " + lastName + " is not loaded to section "
                                + i
                                + " since he/she doesn't exist in the loaded student records.");
                            return studs;
                        }
                        Student studFound = StudentManager.find(String.valueOf(
                            pid));
                        if (studFound != new Student(String.valueOf(pid),
                            firstName, lastName)) {
                            System.out.println("Warning: Student " + firstName
                                + " " + lastName + " is not loaded to section "
                                + i
                                + " since the corresponding pid belongs to another student.");
                            return studs;
                        }
                        // consider other sections
                        if (studs.find(String.valueOf(pid)) != null) {
                            CourseStudent found = studs.find(String.valueOf(
                                pid));
                            if (found.getSectionID() != i) {
                                System.out.println("Warning: Student "
                                    + firstName + " " + lastName
                                    + " is not loaded to section " + i
                                    + "since "
                                    + "he/she is already enrolled in section "
                                    + String.valueOf(found.getSectionID()));
                                return studs;
                            }
                            studs.remove(String.valueOf(pid), studs.find(String
                                .valueOf(pid)));
                        }
                        // score and grade will always be in .data file
                        studs.insert(String.valueOf(pid), new CourseStudent(i,
                            (int)pid, firstName, lastName, score, grade));
                    }
                    currentIndex += 8; // because GOHOKIES
                }
            }
            catch (IOException e) {
                // System.out.println("binary file not found to load student
                // data");
                return null;
            }
        }
        else {
            System.out.println("Unable to read file to load course data");
        }
        return studs;
    }


    /**
     * Reads a line of text for the load student data method and adds a new
     * CourseStudent with values from the text to studs
     * 
     * @param line
     *            a line of text
     * @param studs
     *            the BST of CourseStudents to which a new CourseStudent
     *            will be added
     * @return
     *         the BST of CourseStudents after a new CourseStudent is added
     */
    private BST<String, CourseStudent> readLineForLCD(
        String line,
        BST<String, CourseStudent> studs) {
        BST<String, CourseStudent> result = studs;
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter("\\s*,\\s*");
        int sectionID = -1;
        int pid = -1;
        String firstName = null;
        String lastName = null;
        String score = "";
        String grade = "";
        if (lineScanner.hasNext()) {
            sectionID = lineScanner.nextInt();
            pid = Integer.parseInt(lineScanner.next());
            firstName = lineScanner.next();
            lastName = lineScanner.next();
            score = lineScanner.next();
            grade = lineScanner.next();
            grade = Name.format(grade);
            if (StudentManager.find(String.valueOf(pid)) == null) {
                System.out.println("Warning: Student " + firstName + " "
                    + lastName + " is not loaded to section " + sectionID
                    + " since he/she doesn't exist in the loaded student records.");
                lineScanner.close();
                return result;
            }
            Student studFound = StudentManager.find(String.valueOf(pid));
            if (studFound != new Student(String.valueOf(pid), firstName,
                lastName)) {
                System.out.println("Warning: Student " + firstName + " "
                    + lastName + " is not loaded to section " + sectionID
                    + " since the corresponding pid belongs to another student.");
                lineScanner.close();
                return result;
            }
            if (studs.find(String.valueOf(pid)) != null) {
                // need to verify, then consider different section
                CourseStudent found = studs.find(String.valueOf(pid));
                if (found.getSectionID() != sectionID) {
                    System.out.println("Warning: Student " + firstName + " "
                        + lastName + " is not loaded to section " + sectionID
                        + "since " + "he/she is already enrolled in section "
                        + String.valueOf(found.getSectionID()));
                    lineScanner.close();
                    return result;
                }
                studs.remove(String.valueOf(pid), studs.find(String.valueOf(
                    pid)));
            }

            if (grade.length() == 0 && score.length() == 0) {
                studs.insert(String.valueOf(pid), new CourseStudent(sectionID,
                    pid, firstName, lastName));
            }
            else if (score.length() == 0) {
                studs.insert(String.valueOf(pid), new CourseStudent(sectionID,
                    pid, firstName, lastName, grade));
            }
            else if (grade.length() == 0) {
                studs.insert(String.valueOf(pid), new CourseStudent(sectionID,
                    pid, firstName, lastName, Integer.parseInt(score)));
            }
            else {
                studs.insert(String.valueOf(pid), new CourseStudent(sectionID,
                    pid, firstName, lastName, Integer.parseInt(score), grade));
            }

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
            buffer1 = vtStudents.getBytes("UTF-8");
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
            buffer2 = goHokies.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding in saveStudentData 2");
        }
        String dollarSign = "$";
        byte[] money = new byte[1];
        try {
            money = dollarSign.getBytes("UTF-8");
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
        int currentIndex = buffer1.length + indexLength.length;
        for (int i = 0; i < size; i++) {
            currentStudent = students.remove(0);
            pid = ByteBuffer.allocate(Long.BYTES).putLong(Long.parseLong(
                currentStudent.getPID())).array();
            for (int j = 0; j < pid.length; j++) {
                toFile[currentIndex] = pid[j];
                currentIndex++;
            }
            try {
                fName = currentStudent.getName().getFirst().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 4 "
                    + i);
            }
            for (int j = 0; j < fName.length; j++) {
                toFile[currentIndex] = fName[j];
                currentIndex++;
            }
            toFile[currentIndex] = money[0];
            currentIndex++;
            try {
                mName = currentStudent.getMiddleName().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 5 "
                    + i);
            }
            for (int j = 0; j < mName.length; j++) {
                toFile[currentIndex] = mName[j];
                currentIndex++;
            }
            toFile[currentIndex] = money[0];
            currentIndex++;
            try {
                lName = currentStudent.getName().getLast().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 6 "
                    + i);
            }
            for (int j = 0; j < lName.length; j++) {
                toFile[currentIndex] = lName[j];
                currentIndex++;
            }
            toFile[currentIndex] = money[0];
            currentIndex++;
            for (int j = 0; j < buffer2.length; j++) {
                toFile[currentIndex] = buffer2[j];
                currentIndex++;
            }
        }
        byte[] finalFile = new byte[currentIndex];
        for (int i = 0; i < currentIndex; i++) {
            finalFile[i] = toFile[i];
        }
        try {
            Files.write(Paths.get(filename), finalFile);
            System.out.println("Saved all Students data to " + filename);
        }
        catch (IOException e) {
            System.out.println("Error writing to file to save student data");
        }
    }


    /**
     * Writes the information contained in the array of Sections to a
     * binary (.data) file
     * 
     * @param sections
     *            the array containing all of the sections to write to the
     *            file
     */
    public void saveCourseData(Section[] sections) {
        ArrayList<Section> students = new ArrayList<Section>(initialCapacity);
        for (int i = 0; i < sections.length; i++) {
            students.add(sections[i]);
        }
        Integer size = students.size();
        // unsure how big a section will be (make huge)
        byte[] toFile = new byte[initialCapacity];
        String vtStudents = "CS3114atVT";
        byte[] buffer1 = new byte[10];
        try {
            buffer1 = vtStudents.getBytes("UTF-8");
            for (int i = 0; i < buffer1.length; i++) {
                toFile[i] = buffer1[i];
            }
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding in saveCourseData 1");
        }
        String goHokies = "GOHOKIES";
        byte[] buffer2 = new byte[8];
        try {
            buffer2 = goHokies.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding in saveCourseData 2");
        }
        String dollarSign = "$";
        byte[] money = new byte[1];
        try {
            money = dollarSign.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding in saveCourseData 3");
        }
        byte[] indexLength = ByteBuffer.allocate(Integer.BYTES).putInt(size)
            .array(); // this is numSections
        for (int i = 0; i < indexLength.length; i++) {
            toFile[i + buffer1.length] = indexLength[i];
        }
        byte[] pid = new byte[maxPIDLength];
        byte[] fName = new byte[maxNameLength];
        byte[] mName = new byte[maxNameLength];
        byte[] lName = new byte[maxNameLength];
        DetailedStudent currentStudent = null;
        int currentIndex = buffer1.length + indexLength.length;
        for (int i = 0; i < size; i++) {
            currentStudent = students.remove(0);
            pid = ByteBuffer.allocate(Long.BYTES).putLong(Long.parseLong(
                currentStudent.getPID())).array();
            for (int j = 0; j < pid.length; j++) {
                toFile[currentIndex] = pid[j];
                currentIndex++;
            }
            try {
                fName = currentStudent.getName().getFirst().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 4 "
                    + i);
            }
            for (int j = 0; j < fName.length; j++) {
                toFile[currentIndex] = fName[j];
                currentIndex++;
            }
            toFile[currentIndex] = money[0];
            currentIndex++;
            try {
                mName = currentStudent.getMiddleName().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 5 "
                    + i);
            }
            for (int j = 0; j < mName.length; j++) {
                toFile[currentIndex] = mName[j];
                currentIndex++;
            }
            toFile[currentIndex] = money[0];
            currentIndex++;
            try {
                lName = currentStudent.getName().getLast().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported Encoding in saveStudentData 6 "
                    + i);
            }
            for (int j = 0; j < lName.length; j++) {
                toFile[currentIndex] = lName[j];
                currentIndex++;
            }
            toFile[currentIndex] = money[0];
            currentIndex++;
            for (int j = 0; j < buffer2.length; j++) {
                toFile[currentIndex] = buffer2[j];
                currentIndex++;
            }
        }
        byte[] finalFile = new byte[currentIndex];
        for (int i = 0; i < currentIndex; i++) {
            finalFile[i] = toFile[i];
        }
        try {
            Files.write(Paths.get(filename), finalFile);
            System.out.println("Saved all Students data to " + filename);
        }
        catch (IOException e) {
            System.out.println("Error writing to file to save student data");
        }
        System.out.println("Saved all course data to " + filename);
    }

}
