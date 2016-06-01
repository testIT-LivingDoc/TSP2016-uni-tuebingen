package info.novatec.testit.livingdoc2.action;

import java.util.List;


/**
 * @author Sebastian Letzel
 *         <p>
 *         This class holds the packagename which is to be imported in the executor
 */
public class ImportAction extends AbstractAction {

    private final String packageName;

    public ImportAction(String content) {
        super("import");
        packageName = content;
    }

    public String getTarget() {
        return packageName;
    }

    @Override
    public List<String> getArguments() {
        return null;
    }
}
