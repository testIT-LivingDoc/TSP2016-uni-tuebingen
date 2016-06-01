package info.novatec.testit.livingdoc2.executables;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Empty ValueCell to avoid some nullpointer checks
 */
public class NullCell extends ValueCell {
    public NullCell() {
        super(null);
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public ExecutableStructure getStructure() {
        return null;
    }
}
