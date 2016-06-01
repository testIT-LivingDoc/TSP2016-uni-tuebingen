package info.novatec.testit.livingdoc2.storage;

import info.novatec.testit.livingdoc2.api.storage.Storage;


/**
 * @author Sebastian Letzel
 */
public final class StorageFactory {
    private static Storage specificationStorage = new SpecificationStorage();
    private static Storage executableStructureStorage = new ExecutableStructureStorage();

    private StorageFactory() {
    }

    /**
     * @return The current instance of the SpecificationStorage
     */
    public static Storage getSpecificationStorage() {
        return specificationStorage;
    }

    /**
     * @return The current instance of the ExecutableStructureStorage
     */
    public static Storage getExecutableStructureStorage() {
        return executableStructureStorage;
    }

}
