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
    private DetailedStudent[] sortedArray;
    private SaveAndLoad saveAndLoad;


    /**
     * Makes a new StudentManager by loading data from the given .csv file.
     */
    public StudentManager(String filename) {
        saveAndLoad = new SaveAndLoad(filename); // maybe check if .csv file?
        sortedArray = saveAndLoad.loadStudentData();
        Arrays.sort(sortedArray);
    }


    /**
     * Returns the Student with the given pid or null if not found
     * 
     * @param pid
     *            the pid of the Student to find
     * @return the found Student or null or not found
     */
    public Student find(String pid) {
        return findHelp(pid, 0, sortedArray.length - 1);
    }


    /**
     * Binary Search Algorithm
     * 
     * @param pid
     *            looking for element with this pid
     * @param min
     *            we know the element's index is either >= to this index or the
     *            element is not in the array
     * @param max
     *            we know the element's index is either <= to this index or the
     *            element is not in the array
     * @return found DetailedStudent or null if not found
     */
    private DetailedStudent findHelp(String pid, int min, int max) {
        if (min >= max) {
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
    public void save(String filename) {
        SaveAndLoad save = new SaveAndLoad(filename);
        save.saveStudentData(sortedArray);
    }


    /**
     * Loads a .data file with the given filename (given filename must include
     * .data) that stores all of the information in this StudentManager.
     * 
     * @param filename
     *            file from which to load
     */
    public void load(String filename) {
        SaveAndLoad load = new SaveAndLoad(filename);
        sortedArray = load.loadStudentData();
    }

}
