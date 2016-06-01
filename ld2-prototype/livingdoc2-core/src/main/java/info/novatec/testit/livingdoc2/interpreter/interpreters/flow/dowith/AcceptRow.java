package info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith;

import java.util.List;

import info.novatec.testit.livingdoc2.action.TrueExpectationAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocRowType;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adaption of LivingDoc 1.0 AcceptRow
 *         <p/>
 *         Create {@link Call} for a {@link ValueRow} with the indicator "accept" in the first cell.
 *         Checks if a given value is returns a true {@link Boolean}.
 */
@LivingDocRowType
public class AcceptRow extends AbstractBooleanRow {

    public AcceptRow(ValueRow row) {
        super(row);
    }

    /**
     * The {@link AcceptRow} checks for a true {@link Boolean} and will mark it's indicator cell.
     *
     * @return generated {@link Call}
     */
    @Override
    protected Call buildCall(ValueCell indicatorCell, String methodName, List<String> args) {
        CallBuilder builder = new CallBuilder();

        Action action = new TrueExpectationAction(methodName, args);
        builder.withCommand(action).forCell(indicatorCell);

        return builder.build();
    }
}
