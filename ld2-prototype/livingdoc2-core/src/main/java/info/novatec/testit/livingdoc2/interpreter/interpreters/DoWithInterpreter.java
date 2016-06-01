package info.novatec.testit.livingdoc2.interpreter.interpreters;

import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.interpreter.RowSelector;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.interpreter.interpreters.flow.AbstractFlowInterpreter;
import info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith.DoWithRowSelector;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocInterpreter;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Interpreter for DoWith structure flows
 */
@LivingDocInterpreter
public class DoWithInterpreter extends AbstractFlowInterpreter {

    public DoWithInterpreter(ExecutableStructure executableStructure) {
        super(executableStructure);
        setCountType("row");
    }

    /**
     * The {@link DoWithInterpreter} only sets the {@link RowSelector} for this {@link AbstractFlowInterpreter} and creates
     * the fixture {@link info.novatec.testit.livingdoc2.call.Call}.
     * <p/>
     * After that all implementation is located in the {@link AbstractFlowInterpreter} and {@link
     * info.novatec.testit.livingdoc2.api.interpreter.RowType}s
     *
     * @param rows {@link ValueRow}s to interpret.
     */
    @Override
    protected void interpretRows(List<ValueRow> rows) {
        RowSelector selector = new DoWithRowSelector();
        createCallsForFlowStructure(selector, rows);
    }
}
