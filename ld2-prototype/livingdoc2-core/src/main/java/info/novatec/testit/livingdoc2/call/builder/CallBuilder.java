package info.novatec.testit.livingdoc2.call.builder;

import java.util.ArrayList;
import java.util.List;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.api.expectation.ExpectationCollation;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.executables.NullCell;
import info.novatec.testit.livingdoc2.executables.ValueCell;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Used to generate an immutable Call object
 */
public class CallBuilder {
    private Action actionMessage;
    private ValueCell valueCell = new NullCell();
    private String uniqueID;
    private Expectation expectation;
    private List<ValueCell> cellsToFormat = new ArrayList<>();

    public CallBuilder() {
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public ValueCell getValueCell() {
        return valueCell;
    }

    public Action getActionMessage() {
        return actionMessage;
    }

    public Expectation getExpectation() {
        return expectation;
    }

    public List<ValueCell> getToFormatCells() {
        return cellsToFormat;
    }

    /**
     * Sets the {@code actionMessage} and returns a reference to this Builder so that the methods can be chained
     * together.
     *
     * @param action the {@code withCommand} to set
     * @return a reference to this Builder
     */
    public CallBuilder withCommand(Action action) {
        actionMessage = action;
        uniqueID = action.getCallID();
        return this;
    }

    /**
     * Clears expectation and cell if you don't want to reuse them.
     *
     * @return a reference to this Builder
     */
    public CallBuilder clear() {
        expectation = null;
        valueCell = null;
        return this;
    }

    /**
     * Sets the {@code valueCell} and returns a reference to this Builder so that the methods can be chained together.
     *
     * @param valueCell the {@code valueCell} to set
     * @return a reference to this Builder
     */
    public CallBuilder forCell(ValueCell valueCell) {
        this.valueCell = valueCell;
        return this;
    }

    /**
     * Adds an empty {@link ValueCell} in form of a {@link NullCell} to avoid some nullpointer checks
     * e.g. for annotation calls
     * Has to be called if someone wants to use the same builder to copy previous settings but for no cell
     *
     * @return a reference to this Builder
     */
    public CallBuilder forNullCell() {
        this.valueCell = new NullCell();
        return this;
    }

    /**
     * @param formatCell the {@link ValueCell}s which will be formatted based on the {@link
     * info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult} of the {@code valueCell}
     * @return a reference to this Builder
     */
    public CallBuilder addCellToFormat(ValueCell formatCell) {
        this.cellsToFormat.add(formatCell);
        return this;
    }

    /**
     * @param formatCellList the {@link List} of {@link ValueCell}s which will be formatted based on the {@link
     * info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult} of the {@code valueCell}
     * @return a reference to this Builder
     */
    public CallBuilder addCellListToFormat(List<ValueCell> formatCellList) {
        formatCellList.forEach(cellsToFormat::add);
        return this;
    }

    /**
     * @param expectation the {@link Expectation} to set for the current {@code valueCell}
     * @return a reference to this builder.
     */
    public CallBuilder withExpectation(Expectation expectation) {
        this.expectation = expectation;
        return this;
    }

    /**
     * Wrapper method to collect instances of {@link ExpectationCollation} as the current {@link Expectation}.
     *
     * @param expectationBundle the {@link ExpectationCollation} of {@link Expectation}s to set for the current {@code
     * valueCell}
     * @return a reference to this builder.
     */
    public CallBuilder withExpectation(ExpectationCollation expectationBundle) {
        return withExpectation(expectationBundle.toExpectation());
    }

    /**
     * Returns a {@code Call} built from the parameters previously set.
     *
     * @return a {@code Call} built with parameters of this {@code Call.Builder}
     */
    public Call build() {
        if (expectation == null) {
            createExpectation(actionMessage);
        }
        return new Call(this);
    }

    /**
     * Creates the appropriate expectations for given {@link Action} if no special {@link Expectation} is set.
     * Is used to determine which {@link info.novatec.testit.livingdoc2.executables.ContentModificationIndicator} the {@link
     * ValueCell} {@code valueCell} will get
     *
     * @param action {@link Action} on which the expectation is created
     */
    private void createExpectation(Action action) {
        expectation = ExpectationSetter.parse(action, valueCell);
    }
}
