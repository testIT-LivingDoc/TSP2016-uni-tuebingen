package info.novatec.testit.livingdoc2.execution;

import java.util.HashMap;

import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.execution.Executor;
import info.novatec.testit.livingdoc2.api.perform.ActionPerformer;
import info.novatec.testit.livingdoc2.perform.AfterSpecPerformer;
import info.novatec.testit.livingdoc2.perform.AnnotationPerformer;
import info.novatec.testit.livingdoc2.perform.BeforeSpecPerformer;
import info.novatec.testit.livingdoc2.perform.FixtureInstantiatePerformer;
import info.novatec.testit.livingdoc2.perform.GetterInvocationPerformer;
import info.novatec.testit.livingdoc2.perform.ImportActionPerformer;
import info.novatec.testit.livingdoc2.perform.SetterInvocationPerformer;
import info.novatec.testit.livingdoc2.perform.SimpleExpectationPerformer;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Abstract class for Java execution of specifications
 */
public abstract class AbstractJavaExecutor implements Executor {

    private final HashMap<String, ActionPerformer> performerMap;

    public AbstractJavaExecutor() {
        performerMap = new HashMap<>();
        initMap();
    }

    /**
     * Initializes the map which maps the types of {@link Action}s to the specific {@link ActionPerformer}.
     */
    private void initMap() {
        performerMap.put("import", new ImportActionPerformer());
        performerMap.put("fixture", new FixtureInstantiatePerformer());
        performerMap.put("beforespec", new BeforeSpecPerformer());
        performerMap.put("beforestructure", new AnnotationPerformer());
        performerMap.put("beforerow", new AnnotationPerformer());
        performerMap.put("beforefirstexpectation", new AnnotationPerformer());
        performerMap.put("afterrow", new AnnotationPerformer());
        performerMap.put("afterstructure", new AnnotationPerformer());
        performerMap.put("afterspec", new AfterSpecPerformer());
        performerMap.put("setter", new SetterInvocationPerformer());
        performerMap.put("getter", new GetterInvocationPerformer());
        performerMap.put("expectation", new SimpleExpectationPerformer());
    }

    public ActionPerformer getActionPerformer(Action action) {
        return performerMap.get(action.getType());
    }
}
