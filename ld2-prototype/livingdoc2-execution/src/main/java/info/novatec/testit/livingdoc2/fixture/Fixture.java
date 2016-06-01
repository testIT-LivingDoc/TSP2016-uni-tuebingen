package info.novatec.testit.livingdoc2.fixture;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import info.novatec.testit.livingdoc2.util.StringUtil;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Holds all relevant information about the Fixture and its methods
 */
public class Fixture {

    private final Map<String, Method> methodMap = new HashMap<>();
    private final Object invokedFixture;

    public Fixture(Object fixtureClass, Map<String, Method> methodMap) {
        invokedFixture = fixtureClass;
        this.methodMap.putAll(methodMap);
    }

    public Method getGetter(String name) throws NoSuchMethodException {
        Method getter = getMethod("get " + name);
        if (getter == null) {
            getter = getMethod("is " + name);
        }
        if (getter == null) {
            throw new NoSuchMethodException("Getter for " + name + " not found");
        }
        return getter;
    }

    public Method getSetter(String name) throws NoSuchMethodException {
        Method setter = getMethod("set " + name);

        if (setter == null) {
            setter = getMethod("with " + name);
        }
        if (setter == null) {
            throw new NoSuchMethodException("Setter for " + name + " not found");
        }
        return setter;
    }


    public Method getMethod(String name) {
        String methodName = StringUtil.toMethodForm(name);
        if (hasKey(methodName)) {
            return methodMap.get(methodName);
        } else {
            return null;
        }
    }

    private boolean hasKey(String key) {
        return methodMap.containsKey(key);
    }

    public Object getInvokedFixture() {
        return invokedFixture;
    }
}
