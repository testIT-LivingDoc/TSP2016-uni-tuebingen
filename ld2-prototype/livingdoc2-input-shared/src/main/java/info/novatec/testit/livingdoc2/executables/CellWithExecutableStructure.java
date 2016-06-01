package info.novatec.testit.livingdoc2.executables;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;


/**
 * @author Sebastian Letzel
 *
 * Contains a table in table (or list in table construct.)
 */
public class CellWithExecutableStructure extends AbstractCell {

    private ExecutableStructure object;

    public CellWithExecutableStructure(ExecutableStructure object) {
        this.object = object;
    }

    public ExecutableStructure getStructure() {
        return object;
    }

    /**
     * @return null as {@link CellWithExecutableStructure} does not have a {@link String} content
     */
    @Override
    public String getContent() {
        return null;
    }
}
