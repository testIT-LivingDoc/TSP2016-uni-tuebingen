package info.novatec.testit.livingdoc2.api.executables;

import java.util.List;


/**
 * @author Sebastian Letzel
 * <p/>
 * Interface for accessing the executable objects within a specification
 * Executable objects are right now tables and lists
 */
public interface Executable {

    /**
     * Adds an {@link Executable} object to the composite.
     *
     * @param executable added object
     */
    void add(Executable executable);

    /**
     * Returns the child requested child {@link Executable}.
     *
     * @param i position in the list
     * @return requested {@link Executable}
     */
    Executable get(int i);

    /**
     * Checks if the implemented list has further items at the given index.
     *
     * @param i index to check
     * @return {@link Boolean} for existence of further items
     */
    boolean hasNext(int i);

    /**
     * @return the whole list of {@link Executable}s in this part of the composition
     */
    List<Executable> getAll();
}
