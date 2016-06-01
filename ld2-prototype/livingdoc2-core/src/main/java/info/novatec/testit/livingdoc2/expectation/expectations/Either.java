package info.novatec.testit.livingdoc2.expectation.expectations;

import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.api.expectation.ExpectationCollation;


/**
 * Adapted from LivingDoc 1.0.
 * <p/>
 * Wrapper for {@link OrExpectation} and {@link NotExpectation} for fluid code.
 */
public class Either implements ExpectationCollation {
    private Expectation expectation;

    public Either(Expectation expectation) {
        this.expectation = expectation;
    }

    public Either or(Expectation other) {
        this.expectation = new OrExpectation(this.expectation, other);
        return this;
    }

    public Either negate() {
        this.expectation = new NotExpectation(this.expectation);
        return this;
    }

    @Override
    public Expectation toExpectation() {
        return expectation;
    }
}
