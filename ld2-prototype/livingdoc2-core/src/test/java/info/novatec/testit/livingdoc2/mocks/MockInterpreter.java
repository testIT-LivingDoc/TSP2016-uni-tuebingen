package info.novatec.testit.livingdoc2.mocks;

import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.interpreter.interpreters.AbstractInterpreter;
import info.novatec.testit.livingdoc2.reflection.annotations.LivingDocInterpreter;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Mock interpreter for testing purposes
 */
@LivingDocInterpreter
public class MockInterpreter extends AbstractInterpreter {

    protected MockInterpreter(ExecutableStructure executableStructure) {
        super(executableStructure);
    }

    @Override
    protected void interpretRows(List<ValueRow> rows) {
        //Not needed
    }
}
