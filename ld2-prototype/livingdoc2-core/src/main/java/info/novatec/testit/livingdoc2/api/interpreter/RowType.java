package info.novatec.testit.livingdoc2.api.interpreter;

import info.novatec.testit.livingdoc2.call.Call;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         API for classes representing a {@link RowType} of a flow {@link info.novatec.testit.livingdoc2.api.executables.ExecutableStructure}
 */
public interface RowType {
    Call createCall();
}
