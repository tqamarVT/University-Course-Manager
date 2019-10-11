/**
 * // Virginia Tech Honor Code Pledge:
 * //
 * // As a Hokie, I will conduct myself with honor and integrity at all times.
 * // I will not lie, cheat, or steal, nor will I accept the actions of those
 * // who do.
 * //
 * 
 */

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
    private BST<String, Integer> pidTree; // BST for PID
    private BST<Name, Integer> nameTree; // BST for name
    private BST<Integer, Integer> scoreTree; // BST for score
    private int sectionNumber;
    private int index; // The index (or latest entry) for the dataArray
    private boolean scoreFlag; // Flag for score method
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
        pidTree = new BST<String, Integer>();
        nameTree = new BST<Name, Integer>();
        scoreTree = new BST<Integer, Integer>();
        this.sectionNumber = sectionNumber;
        index = 0;
        scoreFlag = false;
        state = SectionState.Clear;
    }


    // ----------------------------------------------------------
    /**
     * Inserts a record into the dataArray after verification of unique pid.
     */
    public void insert(String pid, String first, String last) {
        // If a student with this pid exists in this section, reject the insert
        // command.
        if (pidTree.find(pid) != null) {
            scoreFlag = false;
            return;
        }
        Student studManager = StudentManager.find(pid);
        if (studManager == null || !studManager.getName().equals(new Name(first,
            last))) { // if not a valid student
            scoreFlag = false;
        }
        Student temp = new Student(pid, first, last);
        pidTree.insert(pid, index);
        nameTree.insert(new Name(first, last), index);
        scoreTree.insert(temp.getScore(), index);
        dataArray.add(temp);
        scoreReference = index;
        index++;
        scoreFlag = true;

    }


    // ----------------------------------------------------------
    /**
     * Searches for the specific student record in the current section with the
     * pid # given. If that pid # is not found in the current section, prints
     * out an error message. Otherwise, prints out the corresponding record.
     * 
     * @param pid
     *            the pid # to search for
     */
    public void search(String pid) {
        if (pidTree.find(pid) == null) {
            // print error message here
            scoreFlag = false;
            return;
        }
        scoreReference = pidTree.find(pid);
        System.out.print(dataArray.getAt(scoreReference).toString());
        scoreFlag = true;
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
     */
    public void search(String first, String last) {
        Name tempName = new Name(first, last);
        int numRecords = 0;
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

        if (numRecords == 1) {
            scoreFlag = true;
        }
        else {
            scoreFlag = false;
        }
        System.out.print("Number of records found: " + numRecords + "\n");
    }


    // ----------------------------------------------------------
    /**
     * Sets the score field of a student object if a valid insert or search
     * command was called prior.
     * 
     * @param score
     *            The score to set
     */
    public void score(int score) {
        if (!this.scoreFlag) {
            System.out.print("score command can only be called"
                + " after an insert command or a successful search "
                + "command with one exact output. \n");
            return;
        }
        else if (score < 0 || score > 100) {
            System.out.print("Scores have to be integers in range 0 to 100.\n");
            scoreFlag = false;
        }
        // Update the last referenced Student's score and the scoreFlag.
        else {
            dataArray.getAt(scoreReference).setScore(score);
            System.out.print("Update" + " " + dataArray.getAt(scoreReference)
                .getName().getFirst() + " " + dataArray.getAt(scoreReference)
                    .getName().getLast() + " " + "record, score = " + score
                + "\n");
            scoreFlag = false;
        }
    }


    // ----------------------------------------------------------
    /**
     * Removes the record in the current section with the given pid #. If there
     * is no such a student, or this student is not enrolled in this section,
     * prints out an error message. Otherwise, the record associated with that
     * name is removed from the section.
     * 
     * @param pid
     *            the pid of the record to be removed.
     */
    public void remove(String pid) {

        if (pidTree.find(pid) == null) {
            // Print out error message
            scoreFlag = false;
            return;
        }

        int tempRef = pidTree.find(pid);
        Name tempName = dataArray.getAt(tempRef).getName();
        int tempScore = dataArray.getAt(tempRef).getScore();
        dataArray.remove(tempRef);
        pidTree.remove(pid, tempRef);
        nameTree.remove(tempName, tempRef);
        scoreTree.remove(tempScore, tempRef);
        scoreFlag = false;
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
     */
    public void remove(String first, String last) {
        if (!this.canRemove(first, last)) {
            // Print out error message
            scoreFlag = false;
            return;
        }
        Name tempName = new Name(first, last);
        int tempRef = nameTree.find(tempName);
        int tempScore = dataArray.getAt(tempRef).getScore();
        String tempPID = dataArray.getAt(tempRef).getPID();
        dataArray.remove(tempRef);
        pidTree.remove(tempPID, tempRef);
        nameTree.remove(tempName, tempRef);
        scoreTree.remove(tempScore, tempRef);
        scoreFlag = false;

    }


    // ----------------------------------------------------------
    /**
     * For each BST node in each BST, prints that node's brief student record
     * (pid #, name, and score). After all three BSTs are printed, prints out
     * the size of the data.
     */
    public void dumpSection() {
        System.out.print("Section" + " " + sectionNumber + " " + "dump:\n");
        pidTree.inOrderTraversal();
        nameTree.inOrderTraversal();
        scoreTree.inOrderTraversal();
        scoreFlag = false;
    }


    // ----------------------------------------------------------
    /**
     * Goes through the current section and check the score of each
     * student,assign corresponding grades.
     * 
     */
    public void grade() {
        for (int i = 0; i <= index; i++) {
            Student temp = dataArray.getAt(i);
            if (temp == null) {
                continue;
            }
            else {
                temp.setGrade(Grades.getGrade(temp.getScore()).returnGrade());
            }
        }
        scoreFlag = false;
    }


    // ----------------------------------------------------------
    /**
     * Lists all students that are in particular grade level(s) given by
     * <grade>.
     * Then prints out the total number of students listed.
     * 
     * @param grade
     *            The grade to gather data on.
     * 
     */
    public void list(String grade) {

        for (int i = 0; i <= index; i++) {
            Student temp = dataArray.getAt(i);
            if (temp == null) {
                continue;
            }
            else {
                if (temp.getGrade().equalsIgnoreCase(grade)) {
                    System.out.print(temp.toString());
                }
            }
        }

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
     *            first name
     * @param last
     *            last name
     * @return true or false
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

        if (numRecords == 1) {
            return true;
        }
        else {
            return false;
        }
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
        return pidTree.getSize();
    }

}
