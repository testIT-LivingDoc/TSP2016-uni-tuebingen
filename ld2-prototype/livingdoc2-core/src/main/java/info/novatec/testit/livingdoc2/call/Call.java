package info.novatec.testit.livingdoc2.call;

import java.util.List;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executables.ExecutableCell;
import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ContentModificationIndicator;
import info.novatec.testit.livingdoc2.executables.ValueCell;


/**
 * Created by Sebastian Letzel on 17.12.2015.
 * <p/>
 * Class responsible for connecting {@link ExecutableCell} with {@link
 * ExecutionResult} and {@link Action}.
 *
 * Also holds a {@link List} of {@link ValueCell}s which will be also be formatted/get an indicator.
 */
public class Call {
    private final String uniqueCallID;
    private final Action actionMessage;
    private final ValueCell valueCell;
    private final List<ValueCell> additionalCellsToFormat;
    private final Expectation expectation;

    public Call(CallBuilder builder) {
        additionalCellsToFormat = builder.getToFormatCells();
        actionMessage = builder.getActionMessage();
        valueCell = builder.getValueCell();
        uniqueCallID = builder.getUniqueID();
        expectation = builder.getExpectation();
    }

    public String getUniqueCallID() {
        return uniqueCallID;
    }

    public Action getActionMessage() {
        return actionMessage;
    }

    public Expectation getExpectation() {
        return expectation;
    }

    public ExecutionResult getResult() {
        return valueCell.getResult();
    }

    public void setResult(ExecutionResult result) {
        valueCell.setResult(result);
    }

    public void setContentModification(ContentModificationIndicator indicator) {
        valueCell.setIndicator(indicator);
        if (!additionalCellsToFormat.isEmpty() || indicator == ContentModificationIndicator.EXCEPTION) {
            additionalCellsToFormat.forEach(c -> c.setIndicator(indicator));
        }
    }
}
