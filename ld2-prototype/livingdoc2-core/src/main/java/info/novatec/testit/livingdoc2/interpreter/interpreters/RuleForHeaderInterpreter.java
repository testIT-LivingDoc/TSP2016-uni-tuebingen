package info.novatec.testit.livingdoc2.interpreter.interpreters;

import com.google.common.base.Strings;

import info.novatec.testit.livingdoc2.action.DisplayAction;
import info.novatec.testit.livingdoc2.action.SetVariableAction;
import info.novatec.testit.livingdoc2.action.SimpleExpectationAction;
import info.novatec.testit.livingdoc2.api.action.Action;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Adaption of LivingDoc 1.0 HeaderForm.
 *         Class for analyzing table headers and returning appropriate {@link Action}s.
 */
public final class RuleForHeaderInterpreter {

    private final String header;

    private RuleForHeaderInterpreter(String header) {
        this.header = header;
    }

    /**
     * Sets the header text for interpretation.
     *
     * @param headerText {@link String} of a header cell
     * @return instance of the {@link RuleForHeaderInterpreter} with the set parameter
     */
    public static RuleForHeaderInterpreter parse(String headerText) {
        return new RuleForHeaderInterpreter(headerText);
    }

    /**
     * @return the current header stripped from indicators to allow method finding in the executor
     */
    private String headerWithStrippedTypeIndicators() {
        return header().replaceAll("=", "").replaceAll("\\?", "").replaceAll("\\(\\)", "");
    }

    /**
     * Checks the current header for its {@link Action} type and returns an instance of it with the content of the checked
     * cell.
     *
     * @param cellContent of the {@link info.novatec.testit.livingdoc2.executables.ValueCell}
     * @return appropriate {@link Action} for this cell
     */
    public Action getCommand(String cellContent) {

        if (isExpected()) {
            if (!Strings.isNullOrEmpty(cellContent)) {
                return new SimpleExpectationAction(headerWithStrippedTypeIndicators(), null);
            } else {
                return new DisplayAction(headerWithStrippedTypeIndicators(), null);
            }
        }

        return new SetVariableAction(header(), cellContent);
    }

    public boolean isExpected() {
        return header().endsWith("()") || header().endsWith("?");
    }

    private String header() {
        return header.trim();
    }
}
