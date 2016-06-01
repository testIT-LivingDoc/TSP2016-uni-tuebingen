package info.novatec.testit.livingdoc2.executables;

import java.util.ArrayList;
import java.util.List;

import info.novatec.testit.livingdoc2.api.executables.Executable;


/**
 * @author Sebastian Letzel
 * <p/>
 * Composite to consolidate Cells in Rows and Rows in {@link info.novatec.testit.livingdoc2.api.executables.ExecutableCell}s
 */
abstract class AbstractExecutable implements Executable {

    private List<Executable> children = new ArrayList<>();

    protected int getLastIndex() {
        return children.size() - 1;
    }

    @Override
    public void add(Executable executable) {
        children.add(executable);
    }

    @Override
    public Executable get(int i) {
        return children.get(i);
    }

    @Override
    public boolean hasNext(int i) {
        return i < children.size();
    }

    @Override
    public List<Executable> getAll() {
        return children;
    }
}
