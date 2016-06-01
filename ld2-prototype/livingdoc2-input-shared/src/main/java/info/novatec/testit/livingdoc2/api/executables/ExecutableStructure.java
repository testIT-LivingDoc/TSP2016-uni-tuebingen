package info.novatec.testit.livingdoc2.api.executables;

import java.util.List;

import info.novatec.testit.livingdoc2.executables.ValueRow;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Entrypoint for functional decorator interface
 */
public interface ExecutableStructure {
    ExecutableCell getFirstCell();

    ValueRow getRow(int rowindex);

    ExecutableCell getCell(int rowindex, int cellindex);

    List<ValueRow> getAllRows();

    /**
     * Count type represents the method of counting the statistics in the {@link info.novatec.testit.livingdoc2.api.reporter.ReportGenerator}.
     * Usually differentiated between "cell" and "row".
     * This prevents multiple results for {@link ValueRow}s which format multiple {@link
     * info.novatec.testit.livingdoc2.executables.ValueCell}s based on one {@link info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult}.
     *
     * @return the type as {@link String}
     */
    String getCountType();

    void setCountType(String countType);

    String getKey();

    /**
     * Sets the key for later merging with the {@link info.novatec.testit.livingdoc2.specification.Specification}.
     */
    void setKey(String key);
}
