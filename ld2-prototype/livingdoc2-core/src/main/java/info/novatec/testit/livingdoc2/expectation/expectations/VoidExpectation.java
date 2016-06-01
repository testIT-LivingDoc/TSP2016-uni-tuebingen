package info.novatec.testit.livingdoc2.expectation.expectations;

import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.executionresult.VoidResult;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Class to signal succesfull methodcalls wich return an {@link VoidResult}
 *         <p/>
 *         It does not need an implementation as it is an interpretation of null-object
 */
public class VoidExpectation implements Expectation {

    @Override
    public StringBuilder describeTo(StringBuilder string) {
        return null;
    }

    @Override
    public boolean meets(Object result) {
        return result.equals("void");
    }
}
