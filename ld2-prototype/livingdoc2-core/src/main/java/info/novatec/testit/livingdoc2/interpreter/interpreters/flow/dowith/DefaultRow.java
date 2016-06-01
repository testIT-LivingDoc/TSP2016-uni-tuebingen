package info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith;

import java.util.List;

import info.novatec.testit.livingdoc2.action.TrueExpectationAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.interpreter.interpreters.flow.AbstractRowType;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adaption of LivingDoc 1.0 DefaultRow
 *         <p/>
 *         Creates calls for a {@link ValueRow} without keyword in the first cell
 */
public class DefaultRow extends AbstractRowType {

    public DefaultRow(ValueRow row) {
        super(row);
    }

    /**
     * DefaultRow assumes, that this will be always a true check or returns void.
     * <p/>
     * It will set the cells to be formated based on the results to all keywordcells.
     * Exceptions will be displayed in the first cell of the keywords.
     *
     * @return the created {@link Call}
     */
    @Override
    public Call createCall() {
        ValueCell firstCell = valueRow.getFirstCell();
        List<ValueCell> keywordCells = keywordCells();
        List<ValueCell> argCells = argCells();

        String methodName = buildMethodName(keywordCells);
        List<String> args = buildArgList(argCells);

        CallBuilder builder = new CallBuilder();
        Action action = new TrueExpectationAction(methodName, args);
        builder.withCommand(action);

        builder.forCell(firstCell);

        keywordCells.remove(0);
        builder.addCellListToFormat(keywordCells);

        return builder.build();
    }

    /**
     * The default row has its keywords (aka method name) in the even cells.
     * Therefore it flips them.
     *
     * @return {@link List} of even cells of the current {@link ValueRow}
     */
    @Override
    protected List<ValueCell> keywordCells() {
        return super.argCells();
    }

    /**
     * The default row has its arguments stored in the uneven cells.
     * Therefore it flips them.
     *
     * @return {@link List} of uneven cells of the current {@link ValueRow}
     */
    @Override
    protected List<ValueCell> argCells() {
        return super.keywordCells();
    }
}
