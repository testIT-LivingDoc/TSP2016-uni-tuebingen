package info.novatec.testit.livingdoc2.interpreter.interpreters;

import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.executables.ValueRow;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Interpretation of null-object-pattern for {@link ExecutableStructure}s which won't be executed, because they did
 *         not find a suitable {@link info.novatec.testit.livingdoc2.api.interpreter.Interpreter}.
 */
public class NullInterpreter extends AbstractInterpreter {
    public NullInterpreter(ExecutableStructure executableStructure) {
        super(executableStructure);
    }

    @Override
    protected void interpretRows(List<ValueRow> rows) {
        //Implementation not needed.
    }

}
