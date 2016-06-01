package info.novatec.testit.livingdoc2.reportgenerator;

import org.jsoup.nodes.Element;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.executables.ContentModificationIndicator;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Modifies the content of the {@link Element} and its equivalent {@link info.novatec.testit.livingdoc2.executables.ValueCell}
 *         based on the {@link info.novatec.testit.livingdoc2.executables.ContentModificationIndicator} and {@link
 *         ExecutionResult}
 */
public final class IndicationProcessor {

    private final static String BACKGROUND_COLOR = "background-color:";
    private final static String STYLE = "style";

    private IndicationProcessor() {
    }

    public static void format(ExecutionResult result, ContentModificationIndicator indicator, Element htmlCell) {
        if (indicator == ContentModificationIndicator.RIGHT) {
            htmlCell.attr(STYLE, BACKGROUND_COLOR + Colors.GREEN);
        }
        if (indicator == ContentModificationIndicator.WRONG) {
            htmlCell.attr(STYLE, BACKGROUND_COLOR + Colors.RED);
            String expected = "<b>" + "expected" + ": </b>";
            htmlCell.prepend(expected);
            String received = " <b>" + "received" + ": </b>" + result.getStringResult();
            htmlCell.append(received);
        }
        if (indicator == ContentModificationIndicator.DISPLAY) {
            htmlCell.attr(STYLE, BACKGROUND_COLOR + Colors.GRAY);
            htmlCell.text(result.getStringResult());
        }
    }
}
