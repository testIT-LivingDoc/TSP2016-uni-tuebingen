package info.novatec.testit.livingdoc2.call.builder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import info.novatec.testit.livingdoc2.action.DisplayAction;
import info.novatec.testit.livingdoc2.action.FalseExpectationAction;
import info.novatec.testit.livingdoc2.action.FixtureInstantiateAction;
import info.novatec.testit.livingdoc2.action.SetVariableAction;
import info.novatec.testit.livingdoc2.action.SimpleExpectationAction;
import info.novatec.testit.livingdoc2.action.TrueExpectationAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.expectation.Expectation;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.expectation.ShouldBe;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         This class finds the appropriate {@link info.novatec.testit.livingdoc2.api.expectation.Expectation} based on
 *         given action.
 *         If there is no match it returns null.
 */
public final class ExpectationSetter {

    public static final Map<Class<? extends Action>, Expectation> EXPECTATION_ALLOCATION;

    static {
        Map<Class<? extends Action>, Expectation> expectations = new HashMap<>(15);
        expectations.put(TrueExpectationAction.class, ShouldBe.TRUE);
        expectations.put(FalseExpectationAction.class, ShouldBe.FALSE);
        expectations.put(DisplayAction.class, null);
        expectations.put(FixtureInstantiateAction.class, ShouldBe.VOID);
        expectations.put(SetVariableAction.class, ShouldBe.VOID);

        EXPECTATION_ALLOCATION = Collections.unmodifiableMap(expectations);
    }

    private ExpectationSetter() {
    }

    public static Expectation parse(Action action, ValueCell valueCell) {
        Class actionClass = action.getClass();

        if (EXPECTATION_ALLOCATION.containsKey(actionClass)) {
            return EXPECTATION_ALLOCATION.get(actionClass);
        }
        if (actionClass.equals(SimpleExpectationAction.class)) {
            return ShouldBe.literal(valueCell.getContent());
        }
        return null;
    }
}
