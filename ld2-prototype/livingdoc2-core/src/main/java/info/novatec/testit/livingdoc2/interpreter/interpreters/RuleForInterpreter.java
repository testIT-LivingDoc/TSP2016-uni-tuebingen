package info.novatec.testit.livingdoc2.interpreter.interpreters;

import java.util.List;

import info.novatec.testit.livingdoc2.action.BeforeFirstExpectationAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocInterpreter;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Interpreter for RuleFor tables
 */
@LivingDocInterpreter
public class RuleForInterpreter extends AbstractInterpreter {

    private static final int HEADER_INDEX = 1;

    public RuleForInterpreter(ExecutableStructure executableStructure) {
        super(executableStructure);
        setCountType("cell");
    }

    /**
     * Entry method for {@link info.novatec.testit.livingdoc2.call.Call} generation.
     * <p>
     * Gets the {@code headerRow} for the {@link RuleForHeaderInterpreter} and starts creation on row 3 to omit fixture row
     * and
     * header row.
     * <p>
     * Before each row a {@link info.novatec.testit.livingdoc2.action.BeforeRowAction} will be created.
     * After each row a {@link info.novatec.testit.livingdoc2.action.AfterRowAction} will be created.
     *
     * @param rows list of all {@link ValueRow}s of the {@code decoratedStructure}
     */
    @Override
    protected void interpretRows(List<ValueRow> rows) {
        ValueRow headerRow = rows.get(HEADER_INDEX);

        for (int j = 2; j < rows.size(); j++) {
            ValueRow currentRow = rows.get(j);

            addBeforeRowAction();

            createCallsForRow(headerRow, currentRow);

            addAfterRowAction();
        }
    }

    /**
     * Creates the {@link info.novatec.testit.livingdoc2.call.Call}s for each {@link ValueCell} based on the {@code
     * headerRow}
     * <p>
     * Also creates a {@link BeforeFirstExpectationAction} at the appropriate place.
     *
     * @param headerRow {@link ValueRow} containing the header {@link ValueCell}s
     * @param currentRow {@link ValueRow} for which the calls will be created
     */
    private void createCallsForRow(ValueRow headerRow, ValueRow currentRow) {

        boolean beforeFirstExecution = true;
        CallBuilder builder = new CallBuilder();

        for (int cellIndex = 0; headerRow.hasNext(cellIndex); cellIndex++) {
            builder.clear();
            ValueCell currentCell = currentRow.getValueCell(cellIndex);
            String currentHeader = headerRow.getValueCell(cellIndex).getContent();

            if (beforeFirstExecution && RuleForHeaderInterpreter.parse(currentHeader).isExpected()) {
                builder.withCommand(new BeforeFirstExpectationAction());
                builder.forNullCell();
                addToCallList(builder.build());
                beforeFirstExecution = false;
            }

            Action action = RuleForHeaderInterpreter.parse(currentHeader).getCommand(currentCell.getContent());

            builder.forCell(currentCell);
            createAndAddCallForAction(action, builder);
        }
    }
}
