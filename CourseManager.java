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


    public void loadstudentdata(String fileName) {
        StudentManager.load(fileName);
    }


    public void loadcoursedata(String fileName) {
        currentSection.setState(SectionState.Occupied); // will need to do this
                                                        // for all sections

    }


    public void section(int n) { // maybe check if valid section number?
        currentSection = sections[n];
        currentSectionNumber = n;
    }


    public void insert(String PID, String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot insert after merging.");
        }
        else if (!StudentManager.isInitialized()) {// change later
            System.out.println("Cannot insert until student data is loaded.");
        }
        else {
            currentSection.insert(PID, firstName, lastName);
            currentSection.setState(SectionState.Occupied);
        }
    }


    public void searchid(String PID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
        }
    }


    public void search(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
        }
    }


    public void search(String name) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot search after merging");
        }
    }


    public void score(int score) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot score after merging");
        }
    }


    public void remove(String PID) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
        }
        else {

            currentSection.setState(SectionState.Occupied);
        }
    }


    public void remove(String firstName, String lastName) {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot remove after merging.");
        }
        else {

            currentSection.setState(SectionState.Occupied);
        }
    }


    public void clearsection() {
        currentSection = new Section(currentSectionNumber);
    }


    public void dumpsection() {

    }


    public void grade() {
        if (currentSection.getState() == SectionState.Merged) {
            System.out.println("Cannot grade after merging");
        }

    }


    public void stat() {

    }


    public void list(String grade) {
        String formattedGrade = Name.format(grade);
    }


    public void findpair(int score) {

    }


    public void merge() {
        if (currentSection.getState() != SectionState.Clear) {
            System.out.println(
                "Section needs to be empty before merging to it.");
        }
        currentSection.setState(SectionState.Merged);

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

    }


    public void clearcoursedata() {
        // maybe save course data somewhere first?
        for (int i = 1; i < 22; i++) {
            sections[i] = new Section(i);
        }
        // do I change the current section?
    }

}
