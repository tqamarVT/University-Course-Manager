import java.util.Arrays;

/**
 * 
 */

/**
 * The StudentManager class stores information about all Students and can find a
 * Student when given the Student's pid.
 * 
 * @author Taimoor Qamar
 * @author Peter Dolan
 * @version 10/5/19
 */
public class StudentManager {
    private static DetailedStudent[] sortedArray;
    private static SaveAndLoad saveAndLoad;


    /**
     * Checks to see if any data has been loaded into the StudentManager
     * 
     * @return whether data has been loaded
     */
    public static boolean isInitialized() {
        return sortedArray != null;
    }


    /**
     * Resets all data in StudentManager to how it was at the beginning.
     */
    public static void clear() {
        sortedArray = null;
        saveAndLoad = null;
    }


    /**
     * Returns the Student with the given pid or null if not found
     * 
     * @param pid
     *            the pid of the Student to find
     * @return the found Student or null or not found
     */
    public static Student find(String pid) {
        if (isInitialized()) {
            return findHelp(pid, -1, sortedArray.length);
        }
        else {
            System.out.println("Student Manager: find: Nothing to find");
            return null;
        }
    }


    /**
     * Binary Search Algorithm
     * 
     * @param pid
     *            looking for element with this pid
     * @param min
     *            we know the element's index is either > to this index or the
     *            element is not in the array
     * @param max
     *            we know the element's index is either < to this index or the
     *            element is not in the array
     * @return found DetailedStudent or null if not found
     */
    private static DetailedStudent findHelp(String pid, int min, int max) {
        if (max - min < 2) {
            return null;
        }
        if (sortedArray[(min + max) / 2].getPID().compareTo(pid) > 0) {
            return findHelp(pid, min, (min + max) / 2);
        }
        if (sortedArray[(min + max) / 2].getPID().compareTo(pid) < 0) {
            return findHelp(pid, (min + max) / 2, max);
        }
        if (sortedArray[(min + max) / 2].getPID().compareTo(pid) == 0) {
            return sortedArray[(min + max) / 2];
        }
        return null;
    }


    /**
     * Creates a .data file with the given filename (given filename must include
     * .data) that stores all of the information in this StudentManager.
     * 
     * @param filename
     *            where to save this
     */
    public static void save(String fileName) {
        if (isInitialized()) {
            SaveAndLoad save = new SaveAndLoad(fileName);
            save.saveStudentData(sortedArray);
        }
        else {
            System.out.println("Student Manager: save: Nothing to save");
        }
    }


    /**
     * Loads a .data or .csv file with the given filename (given filename must
     * include
     * .data or .csv) that stores all of the information in this StudentManager.
     * 
     * @param filename
     *            file from which to load
     */
    public static void load(String fileName) {
        SaveAndLoad load = new SaveAndLoad(fileName);
        sortedArray = load.loadStudentData();
        Arrays.sort(sortedArray);
    }

}
