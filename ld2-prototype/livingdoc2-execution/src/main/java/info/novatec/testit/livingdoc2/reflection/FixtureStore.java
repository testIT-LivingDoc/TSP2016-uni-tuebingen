package info.novatec.testit.livingdoc2.reflection;

import info.novatec.testit.livingdoc2.fixture.Fixture;
import info.novatec.testit.livingdoc2.reflection.annotations.FixtureClass;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Saves all Fixtures found by the {@link FixtureClass}
 *         and returns the requested fixture based on name or alias.
 *         <p/>
 *         Uses double checked locking for singleton instantiation
 */
public final class FixtureStore extends AbstractReflectionStore {

    private boolean annotationScanned;
    private Fixture currentFixture;

    public static FixtureStore getInstance() {
        return FixtureStoreHolder.INSTANCE;
    }

    /**
     * Method for adding Items to the {@link FixtureStore}
     * <p>
     * Adds the Fixture to the {@code itemMap}.
     * Also looks for possible aliases in the {@link FixtureClass} annotation an adds them, too.
     *
     * @param fixtureClass the class to add.
     */
    @Override
    protected void checkAndAdd(Class<?> fixtureClass) {
        String fixtureNameWithPackage = fixtureClass.getName();
        String fixtureNameWithExtension = fixtureClass.getSimpleName();
        String fixtureName = stripFixtureExtension(fixtureNameWithExtension);

        checkAndPut(fixtureNameWithPackage, fixtureClass);
        checkAndPut(fixtureNameWithExtension, fixtureClass);
        checkAndPut(fixtureName, fixtureClass);

        FixtureClass annotationValues = fixtureClass.getAnnotation(FixtureClass.class);
        if (annotationValues != null && annotationValues.value().length > 0) {
            for (String alias : annotationValues.value()) {
                checkAndPut(alias, fixtureClass);
            }
        }
    }

    public Class<?> getFixtureClass(String fixtureName) {
        return getItem(fixtureName);
    }

    /**
     * This method removes optional "Fixture"-String at the end of the classname.
     * e.g.: BankFixture -> Bank
     *
     * @param simpleName of the FixtureClass
     * @return stripped name
     */
    private String stripFixtureExtension(String simpleName) {
        String extension = "Fixture";
        return stripExtension(simpleName, extension);
    }

    public Fixture getCurrentFixture() {
        return currentFixture;
    }

    public void setCurrentFixture(Fixture currentFixture) {
        this.currentFixture = currentFixture;
    }

    public boolean isAnnotationScanned() {
        return annotationScanned;
    }

    public void setAnnotationScanned(boolean annotationScanned) {
        this.annotationScanned = annotationScanned;
    }

    private static class FixtureStoreHolder {
        public static final FixtureStore INSTANCE = new FixtureStore();
    }
}
