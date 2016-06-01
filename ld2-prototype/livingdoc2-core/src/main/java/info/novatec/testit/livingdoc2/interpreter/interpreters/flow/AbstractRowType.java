package info.novatec.testit.livingdoc2.interpreter.interpreters.flow;

import java.util.List;
import java.util.stream.Collectors;

import info.novatec.testit.livingdoc2.api.interpreter.RowType;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adaption of LivingDoc 1.0 AbstractRow
 */
public abstract class AbstractRowType implements RowType {

    protected final ValueRow valueRow;

    protected AbstractRowType(ValueRow row) {
        valueRow = row;
    }

    /**
     * Method appends a whitespace after every cell to allow camel casing.
     *
     * @param actionCells {@link List} of cells containing the split method name
     * @return the built method name
     */
    protected String buildMethodName(List<ValueCell> actionCells) {
        StringBuilder name = new StringBuilder();
        actionCells.forEach(c -> name.append(c.getContent()).append(" "));
        return name.toString();
    }

    /**
     * @return the {@link List} of {@link ValueCell}s containing the cells describing the action of the current {@link
     * ValueRow}
     */
    protected List<ValueCell> argCells() {
        return valueRow.getListOfEvenCells();
    }

    /**
     * Creates a {@link String} {@link List} of the given argument {@link ValueCell}s.
     * If the {@code argCells} list is empty it returns an empty {@link List} to avoid nullpointers
     *
     * @param argCells {@link List} of {@link ValueCell}s containing the argument {@link String}s
     * @return {@link List} of {@link String}s of the content from the {@link ValueCell}s
     */
    protected List<String> buildArgList(List<ValueCell> argCells) {
        return argCells.stream().map(ValueCell::getContent).collect(Collectors.toList());
    }

    /**
     * Keywords build in most {@link RowType}s the method name.
     *
     * @return the {@link List} of {@link ValueCell}s containing the keywords of the current {@link ValueRow}
     */
    protected List<ValueCell> keywordCells() {
        return valueRow.getListOfUnevenCells();
    }

    /**
     * Non-{@link info.novatec.testit.livingdoc2.interpreter.interpreters.flow.dowith.DefaultRow} {@link RowType}s have an
     * indicator keyword in the first {@link ValueCell} of the {@link ValueRow}.
     * <p/>
     * As this indicator won't be an method argument it has to be removed from the argument list.
     *
     * @return {@link List} of all argument cells without the row indicator
     */
    protected List<ValueCell> argCellsWithoutRowIndicator() {
        List<ValueCell> temp = argCells();
        temp.remove(0);
        return temp;
    }
}
