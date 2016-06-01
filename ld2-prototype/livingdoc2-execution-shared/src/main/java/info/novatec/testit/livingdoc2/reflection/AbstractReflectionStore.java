package info.novatec.testit.livingdoc2.reflection;

import java.util.HashMap;
import java.util.Set;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         General implementation of stores which hold items loaded by reflection
 */
public abstract class AbstractReflectionStore {

    private final HashMap<String, Class<?>> itemMap = new HashMap<>();

    /**
     * Checks if the item key is already present and adds it if not.
     *
     * @param name of the item to add
     * @param itemClass class associated with the name
     */
    protected void checkAndPut(String name, Class<?> itemClass) {
        if (!isItemAlreadyPresent(name)) {
            itemMap.put(name, itemClass);
        } else {
            System.err.println(name + " already present");
        }
    }

    public boolean isItemAlreadyPresent(String itemName) {
        return itemMap.containsKey(itemName);
    }

    public boolean isNotFilled() {
        return itemMap.isEmpty();
    }

    public void cleanup() {
        itemMap.clear();
    }

    protected Class<?> getItem(String itemName) {
        return itemMap.get(itemName);
    }

    public void addItemList(Set<Class<?>> items) {
        items.forEach(this::checkAndAdd);
    }

    public void addItem(Class<?> item) {
        checkAndAdd(item);
    }

    protected abstract void checkAndAdd(Class<?> itemClass);

    /**
     * Removes possible extension. If no extension is present, returns the unmodified name.
     *
     * @return name if no extension to cut, or name after cutting extension
     */
    protected String stripExtension(String simpleName, String extension) {
        int startIndex = simpleName.indexOf(extension);
        if (startIndex < 1) {
            return simpleName;
        } else {
            return simpleName.substring(0, startIndex);
        }
    }
}
