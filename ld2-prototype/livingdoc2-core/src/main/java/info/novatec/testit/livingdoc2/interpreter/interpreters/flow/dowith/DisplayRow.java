package info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith;

import java.util.List;

import info.novatec.testit.livingdoc2.action.DisplayAction;
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
 *         Adaption of LivingDoc 1.0 DisplayRow
 *         <p/>
 *         Create {@link Call} for a {@link ValueRow} with the indicator "display" in the first cell.
 *         Gets the return value of the called method and displays it.
 */
@LivingDocRowType
public class DisplayRow extends AbstractRowType {

    public DisplayRow(ValueRow row) {
        super(row);
    }

    /**
     * Generates an additional {@link ValueCell} to indicate a extension of the {@link ValueRow}.
     * <p/>
     * The result will be displayed in this cell.
     *
     * @return generated {@link Call}
     */
    @Override
    public Call createCall() {
        List<ValueCell> keywordCells = keywordCells();
        List<ValueCell> argCells = argCellsWithoutRowIndicator();

        String methodName = buildMethodName(keywordCells);
        List<String> args = buildArgList(argCells);

        ValueCell displayCell = new ValueCell("");
        valueRow.add(displayCell);

        CallBuilder builder = new CallBuilder();
        Action action = new DisplayAction(methodName, args);
        builder.withCommand(action);
        builder.forCell(displayCell);

        return builder.build();
    }
}
