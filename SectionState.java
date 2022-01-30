
/**
 * All of the possible modes for a section
 * 
 * @author Taimoor Qamar (tqamar)
 * @version 2019.07.15
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
