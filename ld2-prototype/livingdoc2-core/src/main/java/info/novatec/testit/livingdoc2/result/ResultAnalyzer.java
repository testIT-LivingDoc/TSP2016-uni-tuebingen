package info.novatec.testit.livingdoc2.result;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.executables.ContentModificationIndicator;
import info.novatec.testit.livingdoc2.executionresult.ExceptionResult;
import info.novatec.testit.livingdoc2.executionresult.VoidResult;
import info.novatec.testit.livingdoc2.expectation.expectations.ErrorExpectation;
import info.novatec.testit.livingdoc2.util.TypeConversion;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Analyzes the given {@link ExecutionResult} against the {@link Expectation} and sets the {@link
 *         info.novatec.testit.livingdoc2.executables.ContentModificationIndicator}
 *         vor the given {@link info.novatec.testit.livingdoc2.executables.ValueCell}
 */
public final class ResultAnalyzer {

    private Expectation expectation;
    private ExecutionResult result;
    private Call call;

    private ResultAnalyzer(Call currentCall) {
        expectation = currentCall.getExpectation();
        result = currentCall.getResult();
        call = currentCall;
    }

    /**
     * Creates a new {@link ResultAnalyzer} instance with given call.
     *
     * @param call {@link Call} to analyze
     * @return this with set parameters
     */
    public static ResultAnalyzer analyze(Call call) {
        return new ResultAnalyzer(call);
    }

    /**
     * Starts the analysis of the {@link ExecutionResult}.
     * Based on several factors will it set the {@link ContentModificationIndicator} of the {@link
     * info.novatec.testit.livingdoc2.executables.ValueCell} of the {@link Call}.
     */
    public void start() {
        if (isVoid()) {
            return;
        }
        if (isRight()) {
            call.setContentModification(ContentModificationIndicator.RIGHT);
            return;
        }
        if (isWrong()) {
            call.setContentModification(ContentModificationIndicator.WRONG);
            setStringResult();
            return;
        }
        if (isException()) {
            call.setContentModification(ContentModificationIndicator.EXCEPTION);
            return;
        }
        if (isDisplay()) {
            call.setContentModification(ContentModificationIndicator.DISPLAY);
            setStringResult();
        }
    }

    /**
     * Converts the result object of the {@link ExecutionResult} to string and sets it in the {@link ExecutionResult}.
     */
    private void setStringResult() {
        Object resultObject = result.getResult();
        result.setResultAsString(TypeConversion.toString(resultObject));
    }

    private boolean isVoid() {
        return result instanceof VoidResult;
    }

    public boolean isDisplay() {
        return !isException() && expectation == null;
    }

    public boolean isRight() {
        return !isException() && !isDisplay() && expectation.meets(result.getResult());
    }

    public boolean isWrong() {
        return !isException() && !isDisplay() && !isRight();
    }

    public boolean isException() {
        return !(expectation instanceof ErrorExpectation) && result instanceof ExceptionResult;
    }
}
