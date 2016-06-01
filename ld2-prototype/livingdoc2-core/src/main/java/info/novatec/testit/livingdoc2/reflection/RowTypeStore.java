package info.novatec.testit.livingdoc2.reflection;

import info.novatec.testit.livingdoc2.api.interpreter.RowType;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocRowType;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Store for classes of the {@link RowType}
 *         Uses double checked locking for singleton instantiation
 */
public final class RowTypeStore extends AbstractReflectionStore {

    public static RowTypeStore getInstance() {
        return RowTypeStoreHolder.INSTANCE;
    }

    /**
     * Method for adding Items to the {@link RowTypeStore}.
     *
     * @param possibleRowTypeClass the class to add.
     */
    @Override
    protected void checkAndAdd(Class<?> possibleRowTypeClass) throws ClassCastException {
        try {
            Class<? extends RowType> rowType = possibleRowTypeClass.asSubclass(RowType.class);
            addRowType(rowType);
        } catch (ClassCastException e) {
            System.err.println(e + " is not subclass of RowType");
        }
    }

    /**
     * Adds the {@link RowType} to the {@code itemMap}.
     * Also looks for possible aliases in the {@link LivingDocRowType} annotation an adds them, too.
     *
     * @param rowTypeClass {@link RowType} to add
     */
    private void addRowType(Class<? extends RowType> rowTypeClass) {
        String interpreterName = stripRowExtension(rowTypeClass.getSimpleName());

        checkAndPut(interpreterName, rowTypeClass);

        LivingDocRowType annotationValues = rowTypeClass.getAnnotation(LivingDocRowType.class);
        if (annotationValues.value().length > 0) {
            for (String alias : annotationValues.value()) {
                checkAndPut(alias, rowTypeClass);
            }
        }
    }

    public Class<?> getRowType(String rowTypeName) {
        return getItem(rowTypeName);
    }

    /**
     * This method removes optional "Row"-String at the end of the classname.
     * e.g.: DefaultRow -> Default
     *
     * @param simpleName of the {@link RowType} implementing class
     * @return stripped name
     */
    private String stripRowExtension(String simpleName) {
        String extension = "Row";
        return stripExtension(simpleName, extension);
    }

    private static class RowTypeStoreHolder {
        public static final RowTypeStore INSTANCE = new RowTypeStore();
    }
}
