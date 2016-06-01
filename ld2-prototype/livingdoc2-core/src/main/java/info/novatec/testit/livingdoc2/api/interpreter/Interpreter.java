package info.novatec.testit.livingdoc2.api.interpreter;

import java.util.Map;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.call.Call;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Entrypoint for usage of interpreters.
 *         <p/>
 *         Also the link to {@link ExecutableStructure} for decorating the objects with the specific interpreter
 *         functionality
 */
public interface Interpreter extends ExecutableStructure {

    /**
     * Entry method to start the generation of {@link Call}s for the given {@link ExecutableStructure}.
     */
    void prepare();

    /**
     * @return the decorated {@link ExecutableStructure}
     */
    ExecutableStructure getOriginalStructure();

    /**
     * @return the generated {@link Map} of the {@link Call}s
     */
    Map<String, Call> getCallMap();
}
