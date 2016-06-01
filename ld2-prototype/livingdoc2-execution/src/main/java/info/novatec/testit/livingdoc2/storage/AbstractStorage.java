package info.novatec.testit.livingdoc2.storage;

import java.util.HashMap;

import info.novatec.testit.livingdoc2.api.storage.Storage;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         This class offers functionality shared between storages
 */
abstract class AbstractStorage implements Storage {

    HashMap<Object, Object> storageMap = new HashMap<>();

    @Override
    public void put(Object key, Object value) {
        storageMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return storageMap.remove(key);
    }

    @Override
    public Object get(Object key) {
        return storageMap.get(key);
    }

    @Override
    public void clear() {
        storageMap.clear();
    }
}
