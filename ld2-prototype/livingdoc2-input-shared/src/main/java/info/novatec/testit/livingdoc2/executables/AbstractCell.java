package info.novatec.testit.livingdoc2.executables;

import info.novatec.testit.livingdoc2.api.executables.Executable;
import info.novatec.testit.livingdoc2.api.executables.ExecutableCell;


/**
 * @author Sebastian Letzel
 *
 * Abstract Class to split cells with String content from Cells containing new Executable structures
 */
abstract class AbstractCell extends AbstractExecutable implements ExecutableCell {

    @Override
    public void add(Executable executable) {
        //implementation not needed as cells don't need the list
    }

}
