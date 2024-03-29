import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Section class which holds student and course data. Stores the student data in
 * three separate BSTs and stores section data in a StudentIndex object.
 * 
 * @author Taimoor Qamar
 * @version 2019.09.23
 *
 */
public class Section {
    // FIELDS
    private StudentIndex<Student> dataArray; // Array to store student/section
                                             // data
    private BST<String, Integer> pIDTree; // BST for pID
    private BST<Name, Integer> nameTree; // BST for name
    private BST<Integer, Integer> scoreTree; // BST for score
    private HashMap<String, Integer> grades;
    private HashMap<String, ArrayList<Student>> list;
    private int sectionNumber;
    private int index; // The index (or latest entry) for the dataArray
    private int scoreReference; // Reference to data for the score method
    private SectionState state;


    // ----------------------------------------------------------
    /**
     * Constructor method for Section class.
     * 
     * @param sectionNumber
     *            The number of the section.
     */
    public Section(int sectionNumber) {
        dataArray = new StudentIndex<Student>();
        pIDTree = new BST<String, Integer>();
        nameTree = new BST<Name, Integer>();
        scoreTree = new BST<Integer, Integer>();
        this.sectionNumber = sectionNumber;
        index = 0;
        state = SectionState.Clear;
    }


    // ----------------------------------------------------------

    /**
     * Inserts a record into the dataArray after verification of unique pID.
     * 
     * @param pID
     *            the pID of the student to insert.
     * @param first
     *            the first name of the student to insert.
     * @param last
     *            the last name of the student to insert.
     * @return boolean value indicating if insert was successful.
     */
    public boolean insert(String pID, String first, String last) {
        // If pID is invalid, belongs to another student, or exists in this
        // section, reject the insert command.
        Student studManager = StudentManager.find(pID);
        if (studManager == null) {
            System.out.print(first + " " + last
                + " insertion failed. Wrong student information. "
                + "ID doesn't exist\r\n");
            return false;
        }
        else if (studManager.getName().compareTo(new Name(first, last)) != 0) {
            System.out.print(first + " " + last
                + " insertion failed. Wrong student information. "
                + "ID belongs to another student\r\n");
            return false;
        }
        else if (pIDTree.find(pID) != null) {
            System.out.print(first + " " + last + " is already in section" + " "
                + sectionNumber + "\r\n");
            return false;
        }
        else {
            Student temp = new Student(pID, first, last);
            pIDTree.insert(pID, index);
            nameTree.insert(new Name(first, last), index);
            scoreTree.insert(temp.getScore(), index);
            dataArray.add(temp);
            scoreReference = index;
            index++;
            System.out.print(first + " " + last + " inserted.\r\n");
            return true;

        }

    }


    // ----------------------------------------------------------
    /**
     * Searches for the specific student record in the current section with the
     * pID # given. If that pID # is not found in the current section, prints
     * out an error message. Otherwise, prints out the corresponding record.
     * 
     * @param pID
     *            the pID # to search for
     * @return a boolean value indicated if the search was succesful.
     */
    public boolean searchId(String pID) {
        if (pIDTree.find(pID) == null) {
            System.out.print("Search Failed. Couldn't find any student with ID "
                + pID + "\r\n");
            return false;
        }
        scoreReference = pIDTree.find(pID);
        System.out.print("Found " + dataArray.getAt(scoreReference).toString());
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Search for all student records in the current section with the name given
     * by <first name> and <last name>. Print out all the records associate with
     * that name. Then print out the total number of records found.
     * 
     * @param first
     *            first name
     * @param last
     *            last name
     * @return boolean value indicating if the search was successful or not, for
     *         updating the scoreFlag.
     */
    public boolean search(String first, String last) {
        Name tempName = new Name(first, last);
        int numRecords = 0;
        System.out.print("search results for " + first + " " + last + ":\n");
        // Since duplicates exist, check for the given name with every index
        for (int i = 0; i <= index; i++) {
            if (nameTree.findByValue(tempName, i) == null) {
                continue;
            }
            else {
                // Update print out record here when you get the format
                scoreReference = nameTree.findByValue(tempName, i);
                System.out.print(dataArray.getAt(scoreReference).toString());
                numRecords++;
            }
        }

        System.out.print(first + " " + last + " was found in " + numRecords
            + " records in section " + sectionNumber + "\r\n");

        return numRecords == 1;
    }


    /**
     * This method searches through the BST for students with a first or last
     * name match with the name passed in the parameter. If found, this method
     * prints out the records of all students with matches, otherwise prints out
     * an error message.
     * 
     * @param name
     *            The first or last name of the student.
     * @return boolean value indicating if the search was successful for the
     *         scoreFlag.
     */
    public boolean search(String name) {
        int count = 0;
        System.out.print("search results for " + name + ":\n");
        for (int i = 0; i < index; i++) {
            Student temp = dataArray.getAt(i);
            if (temp == null) {
                continue;
            }
            if (temp.getName().getFirst().compareToIgnoreCase(name) == 0 || temp
                .getName().getLast().compareToIgnoreCase(name) == 0) {
                scoreReference = i;
                System.out.print(temp.toString());
                count++;
            }
            else {
                continue;
            }
        }
        System.out.print(name + " " + "was found in " + count + " "
            + "records in section " + sectionNumber + "\n");
        return count == 1;
    }


    // ----------------------------------------------------------
    /**
     * Sets the score field of a student object if a valid insert or search
     * command was called prior.
     * 
     * @param score
     *            The score to set
     * @return boolean false for the scoreFlag.
     */
    public boolean score(int score) {
        if (score < 0 || score > 100) {
            System.out.print("Scores have to be integers in range 0 to 100.\n");
            return false;
        }
        else {
            scoreTree.remove(dataArray.getAt(scoreReference).getScore(),
                scoreReference);
            scoreTree.insert(score, scoreReference);
            dataArray.getAt(scoreReference).setScore(score);
            System.out.print("Update" + " " + dataArray.getAt(scoreReference)
                .getName().getFirst() + " " + dataArray.getAt(scoreReference)
                    .getName().getLast() + " " + "record, score = " + score
                + "\n");
            return false;
        }
    }


    // ----------------------------------------------------------
    /**
     * Removes the record in the current section with the given pID #. If there
     * is no such a student, or this student is not enrolled in this section,
     * prints out an error message. Otherwise, the record associated with that
     * name is removed from the section.
     * 
     * @param pID
     *            the pID of the record to be removed.
     * @return whether the student was removed
     */
    public boolean remove(String pID) {
        Student studManager = StudentManager.find(pID);
        if (studManager == null) {
            System.out.print("Remove failed: couldn't find any student with id "
                + pID + "\r\n");
            return false;
        }
        else if (pIDTree.find(pID) == null) {
            System.out.print("Remove failed: couldn't find any student with id "
                + pID + "\r\n");
            return false;
        }
        else {
            int tempRef = pIDTree.find(pID);
            Name tempName = dataArray.getAt(tempRef).getName();
            int tempScore = dataArray.getAt(tempRef).getScore();
            dataArray.remove(tempRef);
            pIDTree.remove(pID, tempRef);
            nameTree.remove(tempName, tempRef);
            scoreTree.remove(tempScore, tempRef);
            System.out.print("Student " + tempName.getFirst() + " " + tempName
                .getLast() + " get removed from section " + sectionNumber
                + " \r\n");
            return true;
        }
    }


    // ----------------------------------------------------------
    /**
     * Removes the record in the current section with the name given by <first
     * name> <last name>. If there are multiple students with this full name or
     * there is no such a student in the current section, rejects this remove
     * command and prints out corresponding error messages.
     * 
     * @param first
     *            first name
     * @param last
     *            last name
     * @return pID of Student removed or null if not removed
     */
    public String remove(String first, String last) {
        if (!this.canRemove(first, last)) {
            System.out.print("Remove failed. Student " + first + " " + last
                + " doesn't exist in section " + sectionNumber + "\r\n");
            return null;
        }
        Name tempName = new Name(first, last);
        int tempRef = nameTree.find(tempName);
        int tempScore = dataArray.getAt(tempRef).getScore();
        String temppID = dataArray.getAt(tempRef).getPID();
        dataArray.remove(tempRef);
        pIDTree.remove(temppID, tempRef);
        nameTree.remove(tempName, tempRef);
        scoreTree.remove(tempScore, tempRef);
        System.out.print("Student " + first + " " + last
            + " get removed from section " + sectionNumber + "\r\n");
        return temppID;

    }


    // ----------------------------------------------------------
    /**
     * For each BST node in each BST, prints that node's brief student record
     * (pID #, name, and score). After all three BSTs are printed, prints out
     * the size of the data.
     * 
     * @return boolean false for the scoreFlag.
     */
    public boolean dumpSection() {
        System.out.print("Section" + " " + sectionNumber + " " + "dump:\n");
        Iterator<Integer> pIDIterator = pIDTree.iterator();
        Iterator<Integer> nameIterator = nameTree.iterator();
        Iterator<Integer> scoreIterator = scoreTree.iterator();
        System.out.print("BST by ID:\r\n");
        while (pIDIterator.hasNext()) {
            int tempIndex = pIDIterator.next();
            System.out.print(dataArray.getAt(tempIndex).toString());
        }
        System.out.print("BST by name:\r\n");
        while (nameIterator.hasNext()) {
            int tempIndex = nameIterator.next();
            System.out.print(dataArray.getAt(tempIndex).toString());
        }
        System.out.print("BST by score:\r\n");
        while (scoreIterator.hasNext()) {
            int tempIndex = scoreIterator.next();
            System.out.print(dataArray.getAt(tempIndex).toString());
        }
        System.out.print("Size = " + pIDTree.getSize() + "\r\n");
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Goes through the current section and check the score of each
     * student,assign corresponding grades.
     * 
     * @return boolean false for the scoreFlag.
     */
    public boolean grade() {
        initGrades();
        for (int i = 0; i <= index; i++) {
            Student temp = dataArray.getAt(i);
            if (temp == null) {
                continue;
            }
            else {
                temp.setGrade(Grades.getGrade(temp.getScore()).returnGrade());
                grades.put(temp.getGrade(), grades.get(temp.getGrade()) + 1);
                list.get(temp.getGrade()).add(temp);
            }
        }
        System.out.print("grading completed\r\n");
        return false;
    }


    /**
     * Report how many students are in each grade level, from A to F.
     * 
     * @return boolean false for the scoreFlag.
     */
    public boolean stat() {
        gradeNoPrint();
        System.out.print("Statistics of section " + sectionNumber + ":\r\n");
        if (grades == null) {
            return false;
        }
        for (String key : grades.keySet()) {
            if (grades.get(key) != 0) {
                System.out.print(grades.get(key) + " " + "students with grade "
                    + key + "\n");
            }
        }
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Lists all students that are in particular grade level(s) given by
     * <grade>.
     * Then prints out the total number of students listed.
     * 
     * @param grade
     *            The grade to gather data on.
     * @return boolean false for the scoreFlag.
     */
    public boolean list(String grade) {
        gradeNoPrint();
        int size = 0;
        System.out.print("Students with grade " + grade + " are:\n");
        if (list == null) {
            return false;
        }
        if (grade.length() > 1 && grade.contains("*")) {
            String g = grade.substring(0, 1);
            String gPlus = grade.substring(0, 1) + "+";
            String gMinus = grade.substring(0, 1) + "-";

            if (list.get(gPlus) != null) {
                for (int i = 0; i < list.get(gPlus).size(); i++) {
                    String print = list.get(gPlus).get(i).toString();
                    print = print.substring(0, print.length() - 1);
                    System.out.print(print + ", grade = " + list.get(gPlus).get(
                        i).getGrade() + "\r\n");
                }
                size = size + list.get(gPlus).size();
            }

            if (list.get(gMinus) != null) {
                for (int i = 0; i < list.get(gMinus).size(); i++) {
                    String print = list.get(gMinus).get(i).toString();
                    print = print.substring(0, print.length() - 1);
                    System.out.print(print + ", grade = " + list.get(gMinus)
                        .get(i).getGrade() + "\r\n");
                }
                size = size + list.get(gMinus).size();
            }

            if (list.get(g) != null) {
                for (int i = 0; i < list.get(g).size(); i++) {
                    String print = list.get(g).get(i).toString();
                    print = print.substring(0, print.length() - 1);
                    System.out.print(print + ", grade = " + list.get(g).get(i)
                        .getGrade() + "\r\n");
                }
                size = size + list.get(g).size();
            }
            System.out.print("Found " + size + " students\r\n");
        }
        else {
            if (list.get(grade) != null) {
                for (int i = 0; i < list.get(grade).size(); i++) {
                    String print = list.get(grade).get(i).toString();
                    print = print.substring(0, print.length() - 1);
                    System.out.print(print + ", grade = " + list.get(grade).get(
                        i).getGrade() + "\r\n");
                    size = list.get(grade).size();
                }
            }
            System.out.print("Found " + size + " students\r\n");
        }
        return false;
    }


    /**
     * Report all student pairs whose scores are within a difference given by
     * (less than or equal to) <score>.
     * 
     * @param score
     *            The score with which to set the range to search in.
     * @return boolean false for the scoreFlag.
     */
    public boolean findPair(int score) {
        int lowerBound = 0;
        int upperBound = 0;
        int count = 0;
        System.out.print("Students with score difference less than or equal "
            + score + ":\n");
        for (int i = 0; i < index; i++) {
            Student outerTemp = dataArray.getAt(i);
            if (outerTemp == null) {
                continue;
            }
            lowerBound = outerTemp.getScore() - score;
            upperBound = outerTemp.getScore() + score;
            for (int j = i + 1; j < index; j++) {
                Student innerTemp = dataArray.getAt(j);
                if (innerTemp == null) {
                    continue;
                }
                if (innerTemp.getScore() >= lowerBound && innerTemp
                    .getScore() <= upperBound) {
                    System.out.print(outerTemp.getName().getFirst() + " "
                        + outerTemp.getName().getLast() + ", " + innerTemp
                            .getName().getFirst() + " " + innerTemp.getName()
                                .getLast() + "\n");
                    count++;
                }
            }
        }
        System.out.print("found " + count + " " + "pairs \n");
        return false;
    }


    // ----------------------------------------------------------

    /**
     * Getter method for the index variable.
     * 
     * @return current index
     */
    public int getIndex() {
        return index;
    }


    // ----------------------------------------------------------

    /**
     * Helper method for the remove by name command used to verify that this
     * section has one and only one record matching the name provided.
     * 
     * @param first
     *            first name of student to be removed.
     * @param last
     *            last name of student to be removed.
     * @return true if student cant be removed, or false.
     * 
     */
    private boolean canRemove(String first, String last) {
        Name tempName = new Name(first, last);
        int numRecords = 0;
        for (int i = 0; i <= index; i++) {
            if (nameTree.findByValue(tempName, i) == null) {
                continue;
            }
            else {
                numRecords++;
            }
        }

        return numRecords == 1;
    }


    /**
     * Returns the current state of this Section
     * 
     * @return the current state
     */
    public SectionState getState() {
        return state;
    }


    /**
     * Changes the current state of this Section to newState
     * 
     * @param newState
     *            the state to which this Section will change
     */
    public void setState(SectionState newState) {
        state = newState; // seems like bad design because client can make state
                          // invalid but cbf
    }


    /**
     * Get the number of student records in this Section
     * 
     * @return the size
     */
    public int getSize() {
        return pIDTree.getSize();
    }


    /**
     * Makes a deep copy of this Section's Student Index that does not include
     * null Students
     * 
     * @return an array of all students in this section
     */
    public Student[] toArray() {
        Student[] result = new Student[pIDTree.getSize()];
        int count = 0;

        for (int i = 0; i < result.length; i++) {
            while (dataArray.getAt(count) == null) {
                count++;
            }
            result[i] = dataArray.getAt(count);
            count++;
        }
        return result;
    }


    /**
     * Alternative insert method used in the loadcoursedata method, ensuring
     * insert update messages are not printed out toe the screen during
     * loadcoursedata process, and that scoreFlag is not manipulated in either
     * class.
     * 
     * @param pID
     *            student pID
     * @param first
     *            student first name
     * @param last
     *            student last name
     * @param score
     *            score for student
     * @param grade
     *            letter grade for the student.
     * @return whether the inserted Student overrided an existing Student
     */
    public boolean insertForLoad(
        String pID,
        String first,
        String last,
        int score,
        String grade) {

        if (pIDTree.find(pID) != null) {
            removeForLoad(pID);
            Student temp = new Student(pID, first, last);
            temp.setScore(score);
            temp.setGrade(grade);
            pIDTree.insert(pID, index);
            nameTree.insert(new Name(first, last), index);
            scoreTree.insert(score, index);
            dataArray.add(temp);
            scoreReference = index;
            index++;
            return true;
        }
        else {
            Student temp = new Student(pID, first, last);
            temp.setScore(score);
            temp.setGrade(grade);
            pIDTree.insert(pID, index);
            nameTree.insert(new Name(first, last), index);
            scoreTree.insert(score, index);
            dataArray.add(temp);
            scoreReference = index;
            index++;
            return false;
        }

    }


    /**
     * Helper method for overwriting old student data during course load,
     * without printing to the screen.
     * 
     * @param pID
     *            of student to be removed during course load overwrite.
     * @return
     */
    private boolean removeForLoad(String pID) {
        Student studManager = StudentManager.find(pID);
        if (studManager == null) {
            return false;
        }
        else if (pIDTree.find(pID) == null) {
            return false;
        }
        else {
            int tempRef = pIDTree.find(pID);
            Name tempName = dataArray.getAt(tempRef).getName();
            int tempScore = dataArray.getAt(tempRef).getScore();
            dataArray.remove(tempRef);
            pIDTree.remove(pID, tempRef);
            nameTree.remove(tempName, tempRef);
            scoreTree.remove(tempScore, tempRef);
            return false;
        }
    }


    /**
     * Helper method for the grades and stat command to initalize the HashMap
     * holding the section statistics.
     */
    private void initGrades() {
        String[] gradeArr = Grades.getGradeArr();
        grades = new HashMap<String, Integer>();
        list = new HashMap<String, ArrayList<Student>>();
        for (int i = 0; i < gradeArr.length; i++) {
            grades.put(gradeArr[i], 0);
            list.put(gradeArr[i], new ArrayList<Student>());
        }
    }


    /**
     * Fill in later.
     * 
     */
    private void gradeNoPrint() {
        initGrades();
        for (int i = 0; i <= index; i++) {
            Student temp = dataArray.getAt(i);
            if (temp == null) {
                continue;
            }
            else {
                temp.setGrade(Grades.getGrade(temp.getScore()).returnGrade());
                grades.put(temp.getGrade(), grades.get(temp.getGrade()) + 1);
                list.get(temp.getGrade()).add(temp);
            }
        }
    }

}
