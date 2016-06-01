package info.novatec.testit.livingdoc2.executables;

import java.util.List;
import java.util.stream.Collectors;

import info.novatec.testit.livingdoc2.api.executables.ExecutableCell;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Represents an executable structure of the specification like a table or a list.
 *         Can also be used to describe a text scenario.
 *         <p/>
 *         Contains a list of rows which stand for rows of a table, lines of a list or lines of text
 */
public class BasicExecutableStructure extends AbstractExecutable implements ExecutableStructure {

    private String countType;
    private String mapKey;

    public BasicExecutableStructure(List<ValueRow> rows) {
        rows.forEach(this::add);
    }

    @Override
    public ExecutableCell getFirstCell() {
        return getCell(0, 0);
    }

    /**
     * @return all {@link ValueRow}s in the list with cast as only {@link ValueRow}s can exist in the list of this class.
     */
    public List<ValueRow> getAllRows() {
        return this.getAll().parallelStream().map(ValueRow.class::cast).collect(Collectors.toList());
    }

    @Override
    public ValueRow getRow(int rowIndex) {
        return ( ValueRow ) this.get(rowIndex);
    }

    @Override
    public ExecutableCell getCell(int rowIndex, int cellIndex) {
        return ( ExecutableCell ) getRow(rowIndex).get(cellIndex);
    }

    @Override
    public String getCountType() {
        return countType;
    }

    @Override
    public void setCountType(String countType) {
        this.countType = countType;
    }

    @Override
    public String getKey() {
        return mapKey;
    }

    @Override
    public void setKey(String key) {
        mapKey = key;
    }
}
