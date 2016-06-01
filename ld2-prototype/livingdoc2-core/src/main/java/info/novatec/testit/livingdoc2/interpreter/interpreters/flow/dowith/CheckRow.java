package info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith;

import java.util.List;

import info.novatec.testit.livingdoc2.action.SimpleExpectationAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.interpreter.interpreters.flow.AbstractRowType;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocRowType;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adaption of LivingDoc 1.0 CheckRow
 *         <p/>
 *         Create {@link Call} for a {@link ValueRow} with the indicator "check" in the first cell.
 *         Checks if a given value is returned from the system under test.
 */
@LivingDocRowType
public class CheckRow extends AbstractRowType {

    public CheckRow(ValueRow row) {
        super(row);
    }

    /**
     * {@link CheckRow} assumes that te expected value is in the last cell of the {@link ValueRow}.
     *
     * @return the created {@link Call}
     */
    @Override
    public Call createCall() {
        ValueCell expectedCell = valueRow.getLastCell();
        List<ValueCell> keywordCells = keywordCells();
        List<ValueCell> argCells = createCheckRowArgs();

        String methodName = buildMethodName(keywordCells);
        List<String> args = buildArgList(argCells);

        CallBuilder builder = new CallBuilder();
        Action action = new SimpleExpectationAction(methodName, args);
        builder.withCommand(action);
        builder.forCell(expectedCell);

        return builder.build();
    }

    /**
     * Gets all argument {@link ValueCell}s and removes the {@code expectedCell} from this list.
     *
     * @return a {@link List} of all method arguments
     */
    private List<ValueCell> createCheckRowArgs() {
        List<ValueCell> argCells = argCellsWithoutRowIndicator();
        int lastIndex = argCells.size() - 1;
        argCells.remove(lastIndex);
        return argCells;
    }

}
