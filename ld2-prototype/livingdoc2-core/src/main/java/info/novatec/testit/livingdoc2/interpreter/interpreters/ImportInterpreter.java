package info.novatec.testit.livingdoc2.interpreter.interpreters;

import java.util.List;

import info.novatec.testit.livingdoc2.action.ImportAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocInterpreter;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Interpreter for Import structures
 */
@LivingDocInterpreter
public class ImportInterpreter extends AbstractInterpreter {

    public ImportInterpreter(ExecutableStructure executableStructure) {
        super(executableStructure);
        setCountType("none");
    }

    /**
     * Overrides to omit fixture call generation.
     */
    @Override
    public void prepare() {
        List<ValueRow> importRows = getAllRows();
        interpretRows(importRows);
    }

    /**
     * This method omits the structure indicator by starting the loop at '1'.
     * Assumes that the {@link ExecutableStructure} has only one column
     * After skipping the first in creates a parallel stream as order is not important with import {@link Call}ss
     */
    @Override
    protected void interpretRows(List<ValueRow> importRows) {
        importRows.stream().skip(1).parallel().forEach(this::generateCall);
    }

    private void generateCall(ValueRow row) {
        CallBuilder builder = new CallBuilder();
        String importPackage = row.getValueCell(0).getContent();
        Action importAction = new ImportAction(importPackage);

        builder.withCommand(importAction);
        addToCallList(builder.build());
    }
}
