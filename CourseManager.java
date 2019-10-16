import java.util.Iterator;

/**
 * 
 */

/**
 * @author Taimoor Qamar
 * @author Peter Dolan
 *
 */
public class CourseManager {
    private Section[] sections = new Section[22]; // [1,21]
    private Section currentSection;
    private int currentSectionNumber;
    StudentManager studentManager = null;
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
     */
    public void loadstudentdata(String fileName) {
        StudentManager.load(fileName);
        scoreFlag = false;

    }


    /**
     * Initializes the CourseManager class with Course data.
     * 
     * @param fileName
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


    public void section(int n) {
        if (n < 1 || n > 21) {
            // invalid section, return
            scoreFlag = false;
            return;
        }
        currentSection = sections[n];
        currentSectionNumber = n;
        scoreFlag = false;
        System.out.print("switch to section " + currentSectionNumber + "\r\n");
    }


    public void sectionNoPrint(int n) {
        if (n < 1 || n > 21) {
            // invalid section, return
            return;
        }
        currentSection = sections[n];
        currentSectionNumber = n;
        // System.out.print("switch to section " + currentSectionNumber +
        // "\r\n");
    }


    public void insert(String PID, String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println(
                "command insert is not valid for merged sections");
            scoreFlag = false;
            return;
        }
        else if (!StudentManager.isInitialized()) {// change later
            System.out.println("Cannot insert until student data is loaded.");
            scoreFlag = false;
            return;
        }
        else if (inDiffSection(PID, firstName, lastName)) {
            System.out.println(firstName + " " + lastName
                + " is already registered in a different section");
            scoreFlag = false;
            return;

        }
        else {
            scoreFlag = currentSection.insert(PID, firstName, lastName);
            if (scoreFlag) {
                courseStudentBST.insert(PID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(PID), firstName,
                    lastName));
                currentSection.setState(SectionState.Occupied);
            }
        }
    }


    public void searchid(String PID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            scoreFlag = false;
            return;
        }
        scoreFlag = currentSection.searchId(PID);
    }


    public void search(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            scoreFlag = false;
            return;
        }
        scoreFlag = currentSection.search(firstName, lastName);
    }


    public void search(String name) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            scoreFlag = false;
            return;
        }
        scoreFlag = currentSection.search(name);
    }


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


    public void remove(String PID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
            scoreFlag = false;
            return;
        }
        else {
            scoreFlag = false;
            currentSection.remove(PID);
            if (scoreFlag) {
                Student found = StudentManager.find(PID);
                courseStudentBST.remove(PID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(PID), found.getName()
                        .getFirst(), found.getName().getLast()));
            }
            updateStates();
        }
    }


    public void remove(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
            scoreFlag = false;
            return;
        }
        else {
            scoreFlag = false;
            String removedPID = currentSection.remove(firstName, lastName);
            if (removedPID != null) {
                courseStudentBST.remove(removedPID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(removedPID),
                    firstName, lastName));
            }
            updateStates();
        }
    }


    public void clearsection() {
        sections[currentSectionNumber] = new Section(currentSectionNumber);
        currentSection = sections[currentSectionNumber];
        scoreFlag = false;
        System.out.print("Section " + currentSectionNumber + " cleared\r\n");
        courseStudentBST = new BST<>();
    }


    public void dumpsection() {
        scoreFlag = currentSection.dumpSection();

    }


    public void grade() {
        scoreFlag = currentSection.grade();

    }


    public void stat() {
        scoreFlag = currentSection.stat();
    }


    public void list(String grade) {
        String formattedGrade = Name.format(grade);
        scoreFlag = currentSection.list(formattedGrade);

    }


    public void findpair(int score) {
        scoreFlag = currentSection.findPair(score);
    }


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


    public void savecoursedata(String filename) {
        saveAndLoad = new SaveAndLoad(filename);
        saveAndLoad.saveCourseData(sections);
        scoreFlag = false;
    }


    public void clearcoursedata() {
        // maybe save course data somewhere first?
        for (int i = 1; i < 22; i++) {
            sections[i] = new Section(i);
        }
        currentSection = sections[currentSectionNumber];
        System.out.print("all course data cleared.\n");
        // do I change the current section?
        scoreFlag = false;
    }


    /**
     * Helper method for the insert method, determines if the student being
     * inserted into the currentSection already exists in another section.
     * 
     * @return true if pid matches name passed, and exists in another section.
     *         false for all other conditions.
     */
    private boolean inDiffSection(
        String PID,
        String firstName,
        String lastName) {
        if (courseStudentBST.find(PID) == null) {
            return false;
        }
        else if (courseStudentBST.find(PID)
            .getSectionID() != currentSectionNumber) {
            return true;
        }
        else {
            return false;
        }

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
     * @param PID
     *            pid of the student
     * @param firstName
     *            first name of the student
     * @param lastName
     *            last name of the student
     * @param score
     *            score of the student
     */
    private void insertForLoad(
        String PID,
        String firstName,
        String lastName,
        int score,
        String grade) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println(
                "command insert is not valid for merged sections");
            return;
        }

        else if (inDiffSection(PID, firstName, lastName)) {
            System.out.println("Warning: Student " + firstName + " " + lastName
                + " is not loaded to section " + String.valueOf(
                    currentSectionNumber)
                + " since he/she is already enrolled in section " + String
                    .valueOf(courseStudentBST.find(PID).getSectionID()));
            return;

        }
        else {
            boolean wasOverwrite = currentSection.insertForLoad(PID, firstName,
                lastName, score, grade);
            // assert Student was inserted
            if (!wasOverwrite) { // name will be wrong but w/e
                courseStudentBST.insert(PID, new CourseStudent(
                    currentSectionNumber, Integer.parseInt(PID), firstName,
                    lastName));
            }

        }
    }


    /**
     * For testing purposes.
     */
    public void getSectionState() {
        System.out.print(currentSection.getState().toString() + "\n");
    }

}
