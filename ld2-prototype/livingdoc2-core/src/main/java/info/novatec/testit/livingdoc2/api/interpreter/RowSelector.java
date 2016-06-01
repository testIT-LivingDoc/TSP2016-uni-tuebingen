package info.novatec.testit.livingdoc2.api.interpreter;

import info.novatec.testit.livingdoc2.executables.ValueRow;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adapted from LivingDoc 1.0.
 *         <p/>
 *         Api for {@link RowSelector} classes to find appropriate {@link RowType}s for given {@link ValueRow}
 */
public interface RowSelector {
    /**
     * Method to choose the appropriate {@link RowType} for given {@link ValueRow}.
     * Returns the invoked {@link RowType} to create {@link info.novatec.testit.livingdoc2.call.Call}s for given row based
     * on the keywords in the first cell
     *
     * @param contentOfFirstCellOfRow possible keyword of the first cell of the current row
     * @return invoked {@link RowType} class
     */
    RowType select(ValueRow contentOfFirstCellOfRow);
}
