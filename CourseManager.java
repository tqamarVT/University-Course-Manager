import java.util.Iterator;

/**
 * 
 */

/**
 * @author Taimoor Qamar
 * @author Peter Dolan
 * @version 2019.10.17
 *
 */
public class CourseManager {
    private Section[] sections = new Section[22]; // [1,21]
    private Section currentSection;
    private int currentSectionNumber;
    private BST<String, CourseStudent> courseStudentBST; // checks diffSection
    private Boolean scoreFlag;
    private SaveAndLoad saveAndLoad;


    /**
     * 
     */
    public CourseManager() {
        for (int i = 1; i < 22; i++) {
            sections[i] = new Section(i);
        }
        currentSection = sections[1]; // I think
        currentSectionNumber = 1;
        courseStudentBST = new BST<>();
    }


    /**
     * Initializes the Static StudentManager class with Student data.
     * 
     * @param fileName
     *            The file holding the student data.
     */
    public void loadstudentdata(String fileName) {
        StudentManager.load(fileName);
        scoreFlag = false;

    }


    /**
     * Initializes the CourseManager class with Course data.
     * 
     * @param fileName
     *            The file holding the course data.
     */
    public void loadcoursedata(String fileName) {
        saveAndLoad = new SaveAndLoad(fileName);
        BST<String, CourseStudent> newLoad = saveAndLoad.loadCourseData();
        if (newLoad == null) {
            scoreFlag = false;
            return;
        }
        Iterator<CourseStudent> iterator = newLoad.iterator();
        while (iterator.hasNext()) {
            CourseStudent next = iterator.next();
            sectionNoPrint(next.getSectionID());
            insertForLoad(next.getPID(), next.getName().getFirst(), next
                .getName().getLast(), next.getScore(), next.getGrade());
        }
        updateStates();
        scoreFlag = false;

    }


    /**
     * Method used to change the currentSection field variable.
     * 
     * @param n
     *            the section id.
     */
    public void section(int n) {
        if (n < 1 || n > 21) {
            scoreFlag = false;
            return;
        }
        currentSection = sections[n];
        currentSectionNumber = n;
        scoreFlag = false;
        System.out.print("switch to section " + currentSectionNumber + "\r\n");
    }


    /**
     * <ethod used to change the currentSection field variable, without the
     * system printing anything to the screen. Useful for loading course data.
     * 
     * @param n
     *            the section id.
     */
    public void sectionNoPrint(int n) {
        if (n < 1 || n > 21) {
            return;
        }
        currentSection = sections[n];
        currentSectionNumber = n;

    }


    /**
     * Enrolls a student with <pID #> and a name given by that <first name> and
     * <last name> to the current section.
     * 
     * @param pID
     *            the pID of the student.
     * @param firstName
     *            the first name of the student.
     * @param lastName
     *            the last name of the student.
     */
    public void insert(String pID, String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println(
                "command insert is not valid for merged sections");
            scoreFlag = false;
            return;
        }
        else if (!StudentManager.isInitialized()) {
            System.out.println("Cannot insert until student data is loaded.");
            scoreFlag = false;
            return;
        }
        else if (inDiffSection(pID, firstName, lastName)) {
            System.out.println(firstName + " " + lastName
                + " is already registered in a different section");
            scoreFlag = false;
            return;

        }
        else {
            scoreFlag = currentSection.insert(pID, firstName, lastName);
            if (scoreFlag) {
                courseStudentBST.insert(pID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(pID), firstName,
                    lastName));
                currentSection.setState(SectionState.Occupied);
            }
        }
    }


    /**
     * Searches for the specific student record in the current section with the
     * pID # given.
     * 
     * @param pID
     *            the pID of the student to search for.
     */
    public void searchid(String pID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            scoreFlag = false;
            return;
        }
        scoreFlag = currentSection.searchId(pID);
    }


    /**
     * Searches for all student records in the current section with the name
     * given by <first name> and <last name>.
     * 
     * @param firstName
     *            the first name of the student.
     * @param lastName
     *            the last name of the student.
     */
    public void search(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            scoreFlag = false;
            return;
        }
        scoreFlag = currentSection.search(firstName, lastName);
    }


    /**
     * Report all students in the current section that have the specific <name>.
     * Note that this name may appear in either first name or last name.
     * 
     * @param name
     *            Either first or last name of studen to search for.
     */
    public void search(String name) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            scoreFlag = false;
            return;
        }
        scoreFlag = currentSection.search(name);
    }


    /**
     * Assigns a score given by <number> for the specific student last
     * referenced by one of the valid pre-score method calls.
     * 
     * @param score
     *            the score value to update.
     */
    public void score(int score) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot score after merging");
            scoreFlag = false;
            return;
        }
        if (!scoreFlag) {
            System.out.print("score command can only be called"
                + " after an insert command or a successful search "
                + "command with one exact output. \n");
            scoreFlag = false;
            return;
        }
        scoreFlag = currentSection.score(score);
    }


    /**
     * Removes the record in the current section with the given pID #. If there
     * is no such a student, or this student is not enrolled in the current
     * section, returns out of the method.
     * 
     * @param pID
     *            the pID of the student to remove.
     */
    public void remove(String pID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
            scoreFlag = false;
            return;
        }
        else {
            scoreFlag = currentSection.remove(pID);
            if (scoreFlag) {
                Student found = StudentManager.find(pID);
                courseStudentBST.remove(pID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(pID), found.getName()
                        .getFirst(), found.getName().getLast()));
            }
            updateStates();
        }
    }


    /**
     * Removes the record in the current section with the name given by <first
     * name> <lastname>. If there are multiple students with this full name or
     * there is no such a student in the current section, the method returns.
     * 
     * @param firstName
     *            the first name of the student to remove.
     * @param lastName
     *            the last name of the student to remove.
     */
    public void remove(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
            scoreFlag = false;
            return;
        }
        else {
            scoreFlag = false;
            String removedpID = currentSection.remove(firstName, lastName);
            if (removedpID != null) {
                courseStudentBST.remove(removedpID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(removedpID),
                    firstName, lastName));
            }
            updateStates();
        }
    }


    /**
     * Removes all records associated with the current section..
     */
    public void clearsection() {
        sections[currentSectionNumber] = new Section(currentSectionNumber);
        currentSection = sections[currentSectionNumber];
        scoreFlag = false;
        System.out.print("Section " + currentSectionNumber + " cleared\r\n");
        courseStudentBST = new BST<>();
    }


    /**
     * Returns a “dump” of the three BSTs associated with the current section.
     */
    public void dumpsection() {
        scoreFlag = currentSection.dumpSection();

    }


    /**
     * Goes through the current section and check the score of each student,
     * assign corresponding letter grades.
     */
    public void grade() {
        scoreFlag = currentSection.grade();

    }


    /**
     * Reports how many students are in each grade level, from A to F.
     */
    public void stat() {
        scoreFlag = currentSection.stat();
    }


    /**
     * List all students that are in particular grade level(s) given by <grade>.
     * 
     * @param grade
     *            the grade for which to list the student records
     */
    public void list(String grade) {
        String formattedGrade = Name.format(grade);
        scoreFlag = currentSection.list(formattedGrade);

    }


    /**
     * Reports all student pairs whose scores are within a difference given by
     * (less than or equal to) <score> in the current section. If no <score> is
     * given, then the default value is 0, which means that the pair should have
     * the same score.
     * 
     * @param score
     *            the score which estabilishes the range to search in.
     */
    public void findpair(int score) {
        scoreFlag = currentSection.findPair(score);
    }


    /**
     * Merges all occupied course sections into one separate section.
     */
    public void merge() {
        if (currentSection.getState() != SectionState.Clear) {
            System.out.println(
                "sections could only be merged to an empty section. section "
                    + currentSectionNumber + " is not empty.");
            scoreFlag = false;
            return;
        }
        for (int i = 1; i < sections.length; i++) {
            if (sections[i].getState() != SectionState.Occupied) {
                continue;
            }
            else {
                Student[] temp = sections[i].toArray();
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j] == null) {
                        continue;
                    }
                    else {
                        currentSection.insertForLoad(temp[j].getPID(), temp[j]
                            .getName().getFirst(), temp[j].getName().getLast(),
                            temp[j].getScore(), temp[j].getGrade());
                    }
                }
            }
        }
        System.out.print("all sections merged at section "
            + currentSectionNumber + "\n");
        currentSection.setState(SectionState.Merged);
        scoreFlag = false;

    }


    /**
     * Saves the student data to the specified binary file
     * 
     * @param filename
     *            This should contain .data
     */
    public void savestudentdata(String filename) {
        if (!StudentManager.isInitialized()) { // will change later
            System.out.println(
                "Cannot save student data if I don't have any student data.");
            scoreFlag = false;
            return;
        }
        StudentManager.save(filename);
        scoreFlag = false;
    }


    /**
     * Saves all course data into a binary file.
     * 
     * @param filename
     *            the name of the file to create and save to.
     */
    public void savecoursedata(String filename) {
        saveAndLoad = new SaveAndLoad(filename);
        saveAndLoad.saveCourseData(sections);
        scoreFlag = false;
    }


    /**
     * Saves the coursedata info and clear all BST and array space associated
     * with the course.
     */
    public void clearcoursedata() {
        for (int i = 1; i < 22; i++) {
            sections[i] = new Section(i);
        }
        currentSection = sections[currentSectionNumber];
        System.out.print("all course data cleared.\n");
        scoreFlag = false;
    }


    /**
     * Helper method for the insert method, determines if the student being
     * inserted into the currentSection already exists in another section.
     * 
     * @return true if pID matches name passed, and exists in another section.
     *         false for all other conditions.
     */
    private boolean inDiffSection(
        String pID,
        String firstName,
        String lastName) {
        if (courseStudentBST.find(pID) == null) {
            return false;
        }
        return courseStudentBST.find(pID)
            .getSectionID() != currentSectionNumber;

    }


    /**
     * Helper method for the loadcoursedata method. Loops through the sections
     * array and updates the state of each section.
     */
    private void updateStates() {
        for (int i = 1; i < 22; i++) {
            if (sections[i].getSize() != 0 && sections[i]
                .getState() != SectionState.Merged) {
                sections[i].setState(SectionState.Occupied);
            }
            else if (sections[i].getState() != SectionState.Merged) {
                sections[i].setState(SectionState.Clear);
            }
        }
    }


    /**
     * Alternative insert method used in the loadcoursedata method, ensuring
     * insert update messages are not printed out to the screen during
     * loadcoursedata process, and that scoreFlag is not manipulated.
     * 
     * 
     * @param pID
     *            pID of the student
     * @param firstName
     *            first name of the student
     * @param lastName
     *            last name of the student
     * @param score
     *            score of the student
     */
    private void insertForLoad(
        String pID,
        String firstName,
        String lastName,
        int score,
        String grade) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println(
                "command insert is not valid for merged sections");
            return;
        }

        else if (inDiffSection(pID, firstName, lastName)) {
            System.out.println("Warning: Student " + firstName + " " + lastName
                + " is not loaded to section " + String.valueOf(
                    currentSectionNumber)
                + " since he/she is already enrolled in section " + String
                    .valueOf(courseStudentBST.find(pID).getSectionID()));
            return;

        }
        else {
            boolean wasOverwrite = currentSection.insertForLoad(pID, firstName,
                lastName, score, grade);
            // assert Student was inserted
            if (!wasOverwrite) { // name will be wrong but w/e
                courseStudentBST.insert(pID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(pID), firstName,
                    lastName));
            }

        }
    }
}
