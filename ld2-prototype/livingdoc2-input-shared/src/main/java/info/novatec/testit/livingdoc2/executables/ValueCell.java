package info.novatec.testit.livingdoc2.executables;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;


/**
 * @author Sebastian Letzel
 *
 * Executable object containing text values.
 * These values can be keywords, input parameters, methodnames and so on for the actions.
 *
 * The {@link ExecutionResult} from the executor is also saved here, which will be evaluated by the core.
 *
 * Based on the evaluation of the {@link ExecutionResult} the core will set the {@link ContentModificationIndicator}
 */
public class ValueCell extends AbstractCell {
    private ExecutionResult result;
    private String content;
    private ContentModificationIndicator indicator;

    public ValueCell (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    /**
     * @return null as {@link ValueCell}s don't hold {@link ExecutableStructure}s
     */
    @Override
    public ExecutableStructure getStructure() {
        return null;
    }

    public ExecutionResult getResult() {
        return result;
    }

    public void setResult(ExecutionResult result) {
        this.result = result;
    }

    public ContentModificationIndicator getIndicator() {
        return indicator;
    }

    public void setIndicator(ContentModificationIndicator indicator) {
        this.indicator = indicator;
    }
}
