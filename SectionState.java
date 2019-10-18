// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Peter Dolan (peter8)

/**
 * All of the possible modes for a section
 * 
 * @author Peter Dolan
 * @version 10/6/19
 *
 */
public enum SectionState {
    /**
     * Section is empty
     */
    Clear,
    /**
     * Section contains student data.
     */
    Occupied,
    /**
     * Section is a current combination of all other occupied sections.
     */
    Merged
}
