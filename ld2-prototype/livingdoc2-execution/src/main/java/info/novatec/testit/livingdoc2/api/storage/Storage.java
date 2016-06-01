package info.novatec.testit.livingdoc2.api.storage;

/**
 * @author Sebastian Letzel
 *         <p/>
 *         Interface for accessing Storage classes.
 */
public interface Storage {
    void put(Object key, Object value);

    Object remove(Object key);

    Object get(Object key);

    void clear();
}
