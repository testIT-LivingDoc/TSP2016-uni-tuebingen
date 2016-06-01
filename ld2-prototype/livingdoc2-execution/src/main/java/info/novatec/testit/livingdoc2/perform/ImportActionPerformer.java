package info.novatec.testit.livingdoc2.perform;

import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.executionresult.VoidResult;
import info.novatec.testit.livingdoc2.reflection.ClassPathScanner;
import info.novatec.testit.livingdoc2.reflection.scanner.FixtureByNameScanner;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Executes an ImportAction
 */
public class ImportActionPerformer extends AbstractPerformer {
    @Override
    public ExecutionResult perform() {
        String packageName = action.getTarget();
        new ClassPathScanner().scan(new FixtureByNameScanner(packageName));

        return new VoidResult(getCallID());
    }
}
