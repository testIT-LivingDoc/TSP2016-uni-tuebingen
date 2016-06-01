package info.novatec.testit.livingdoc2.api.expectation;

/**
 * Adapted from LivingDoc 1.0.
 * <p/>
 * Represents classes which hold and compare more than one {@link Expectation}
 */
public interface ExpectationCollation {
    Expectation toExpectation();
}
