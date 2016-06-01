package info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith;

import java.util.List;

import info.novatec.testit.livingdoc2.action.FalseExpectationAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.expectation.ExpectationCollation;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.expectation.ShouldBe;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocRowType;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adaption of LivingDoc 1.0 RejectRow
 *         <p/>
 *         Create {@link Call} for a {@link ValueRow} with the indicator "reject" in the first cell.
 *         Checks if a given value is returns a false {@link Boolean} or an exception.
 */
@LivingDocRowType
public class RejectRow extends AbstractBooleanRow {

    public RejectRow(ValueRow row) {
        super(row);
    }

    /**
     * The {@link RejectRow} checks for a false {@link Boolean} or an {@link Exception} and will mark it's indicator cell.
     *
     * @return generated {@link Call}
     */
    @Override
    protected Call buildCall(ValueCell indicatorCell, String methodName, List<String> args) {
        ExpectationCollation expectationBundle = ShouldBe.either(ShouldBe.FALSE).or(ShouldBe.instanceOf(Exception.class));

        CallBuilder builder = new CallBuilder();
        Action action = new FalseExpectationAction(methodName, args);
        builder.withCommand(action).forCell(indicatorCell).withExpectation(expectationBundle);

        return builder.build();
    }
}
