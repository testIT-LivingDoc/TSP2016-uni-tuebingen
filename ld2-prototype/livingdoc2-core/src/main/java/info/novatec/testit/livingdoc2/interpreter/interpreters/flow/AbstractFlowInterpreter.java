package info.novatec.testit.livingdoc2.interpreter.interpreters.flow;

import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.interpreter.RowSelector;
import info.novatec.testit.livingdoc2.api.interpreter.RowType;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.interpreter.interpreters.AbstractInterpreter;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adaption of the LivingDoc 1.0 AbstractFlowInterpreter.
 *         <p/>
 *         Interprets a Command table specifications.
 *         <p/>
 *         Process a table containing a series of command. Each line of table correspond
 *         to a command and its parameters.
 */
public abstract class AbstractFlowInterpreter extends AbstractInterpreter {

    public AbstractFlowInterpreter(ExecutableStructure executableStructure) {
        super(executableStructure);
    }

    /**
     * Iterates over the {@link List} of given {@link ValueRow}s to create the calls for the decorated {@link
     * info.novatec.testit.livingdoc2.api.interpreter.Interpreter}.
     * <p/>
     * Calls {@code createCallsForRowType} to create the {@link info.novatec.testit.livingdoc2.call.Call}s for the current
     * {@link
     * ValueRow}.
     *
     * @param selector {@link RowSelector} chosen by the {@link info.novatec.testit.livingdoc2.api.interpreter.Interpreter}
     * @param rows {@link List} of {@link ValueRow}s to analyze
     */
    public void createCallsForFlowStructure(RowSelector selector, List<ValueRow> rows) {
        rows.stream().skip(1).forEach(row -> createCallForRowType(row, selector));
    }

    /**
     * Finds appropriate {@link info.novatec.testit.livingdoc2.api.interpreter.RowType} via the given {@link RowSelector}
     * and
     * executes the {@link info.novatec.testit.livingdoc2.call.Call} creation through the invoked {@link
     * info.novatec.testit.livingdoc2.api.interpreter.RowType} class.
     *
     * @param currentRow {@link ValueRow} to be analyzed
     * @param selector {@link RowSelector} received from decorated {@link info.novatec.testit.livingdoc2.api.interpreter.Interpreter}
     */
    private void createCallForRowType(ValueRow currentRow, RowSelector selector) {
        RowType rowType = selector.select(currentRow);
        if (rowType == null) {
            return;
        }
        Call rowCall = rowType.createCall();

        addToCallList(rowCall);
    }
}

