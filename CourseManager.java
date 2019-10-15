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
    private BST<String, CourseStudent> courseStudentBST;
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
        SaveAndLoad saveAndLoad = new SaveAndLoad(fileName);
        courseStudentBST = saveAndLoad.loadCourseData();
        if (courseStudentBST == null) {
            return;
        }
        Iterator<CourseStudent> iterator = courseStudentBST.iterator();
        while (iterator.hasNext()) {
            CourseStudent next = iterator.next();
            section(next.getSectionID());
            insertForLoad(next.getPID(), next.getName().getFirst(), next
                .getName().getLast(), next.getScore());
        }
        updateStates();
        scoreFlag = false;

    }


    public void section(int n) {
        if (n < 1 || n > 21) {
            // invalid section, return
            return;
        }
        currentSection = sections[n];
        currentSectionNumber = n;
        scoreFlag = false;
        // System.out.print("switch to section " + currentSectionNumber +
        // "\r\n");
    }


    public void insert(String PID, String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot insert after merging.");
            return;
        }
        else if (!StudentManager.isInitialized()) {// change later
            System.out.println("Cannot insert until student data is loaded.");
            return;
        }
        else if (inDiffSection(PID, firstName, lastName)) {
            System.out.println(firstName + " " + lastName
                + " is already registered in a different section");
            return;

        }
        else {
            scoreFlag = currentSection.insert(PID, firstName, lastName);
            currentSection.setState(SectionState.Occupied);
        }
    }


    public void searchid(String PID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            return;
        }
        scoreFlag = currentSection.searchId(PID);
    }


    public void search(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            return;
        }
        scoreFlag = currentSection.search(firstName, lastName);
    }


    public void search(String name) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
            return;
        }
        scoreFlag = currentSection.search(name);
    }


    public void score(int score) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot score after merging");
            return;
        }
        if (!scoreFlag) {
            System.out.print("score command can only be called"
                + " after an insert command or a successful search "
                + "command with one exact output. \n");
            return;
        }
        scoreFlag = currentSection.score(score);
    }


    public void remove(String PID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
            return;
        }
        else {
            scoreFlag = currentSection.remove(PID);
            currentSection.setState(SectionState.Occupied);
        }
    }


    public void remove(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
            return;
        }
        else {
            scoreFlag = currentSection.remove(firstName, lastName);
            currentSection.setState(SectionState.Occupied);
        }
    }


    public void clearsection() {
        currentSection = new Section(currentSectionNumber);
        currentSection.setState(SectionState.Clear);
        scoreFlag = false;
        System.out.print("Section " + currentSectionNumber + "cleared\r\n");
    }


    public void dumpsection() {
        scoreFlag = currentSection.dumpSection();

    }


    public void grade() {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot grade after merging");
            return;
        }
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
                "Section needs to be empty before merging to it.");
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
                            temp[j].getScore());
                    }
                }
            }
        }
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
        }
        StudentManager.save(filename);
    }


    public void savecoursedata() {
        saveAndLoad.saveCourseData(sections);
    }


    public void clearcoursedata() {
        // maybe save course data somewhere first?
        for (int i = 1; i < 22; i++) {
            sections[i] = new Section(i);
        }
        // do I change the current section?
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
        // If the PID exists, the name associated with the PID matches the name
        // being inserted, and the student associated with the PID and Name is
        // in another section, returns true; Otherwise returns false.
        if (courseStudentBST.find(PID) == null) {
            return false;
        }
        else if (courseStudentBST.find(PID).getName().compareTo(new Name(
            firstName, lastName)) != 0) {
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
        int score) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot insert after merging.");
            return;
        }
        else if (!StudentManager.isInitialized()) {// change later
            System.out.println("Cannot insert until student data is loaded.");
            return;
        }
        else if (inDiffSection(PID, firstName, lastName)) {
            System.out.println(firstName + " " + lastName
                + " is already registered in a different section\r\n");
            return;

        }
        else {
            currentSection.insertForLoad(PID, firstName, lastName, score);
        }
    }


    /**
     * For testing purposes.
     */
    public void getSectionState() {
        System.out.print(currentSection.getState().toString() + "\n");
    }

}
