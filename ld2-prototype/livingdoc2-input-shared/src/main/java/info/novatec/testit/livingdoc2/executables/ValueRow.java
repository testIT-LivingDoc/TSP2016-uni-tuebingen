package info.novatec.testit.livingdoc2.executables;

import java.util.ArrayList;
import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.Executable;
import info.novatec.testit.livingdoc2.api.executables.ExecutableCell;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Row class to consolidate {@link ExecutableCell}s
 */
public class ValueRow extends AbstractExecutable {

    public ValueRow(List<ExecutableCell> cells) {
        cells.forEach(this::add);
    }

    /**
     * As {@link ValueRow}s can only have {@link List}s of {@link ExecutableCell}s the return of this method ist cast from
     * {@link Executable} to {@link ExecutableCell}.
     *
     * @param i index of the requested {@link ExecutableCell}
     * @return requested {@link ExecutableCell}
     */
    public ValueCell getValueCell(int i) {
        return ( ValueCell ) this.get(i);
    }

    public List<ValueCell> getListOfUnevenCells() {
        return splitCells(this.getAll(), false);
    }

    public List<ValueCell> getListOfEvenCells() {
        return splitCells(this.getAll(), true);
    }

    /**
     * Method using a bit operator to check if the current iteration counter is equal to an even or uneven number.
     *
     * @param rowCells {@link List} of {@link ValueCell}s of the current {@link ValueRow}
     * @param even {@link Boolean} for setting the {@code checkBit}
     * @return the split {@link List}
     */
    private List<ValueCell> splitCells(List<Executable> rowCells, boolean even) {
        List<ValueCell> splitCells = new ArrayList<>();
        int checkBit = 1;

        if (even) {
            checkBit = 0;
        }

        for (int i = 0; i < rowCells.size(); i++) {
            if ((i & 1) == checkBit) {
                splitCells.add(( ValueCell ) rowCells.get(i));
            }
        }
        return splitCells;
    }

    public ValueCell getFirstCell() {
        return getValueCell(0);
    }

    public ValueCell getLastCell() {
        int lastIndex = this.getLastIndex();

        return getValueCell(lastIndex);
    }

    public int size() {
        return this.getAll().size();
    }
}
