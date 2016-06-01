package info.novatec.testit.livingdoc2.executionresult;

/**
 * @author Sebastian Letzel
 *         <p/>
 *         Null result for performers who have the possibility for no result return
 *         Singleton as all {@link VoidResult}s will be the same (see also null object pattern)
 */
public final class VoidResult extends AbstractExecutionResult {

    public VoidResult(String callID) {
        super(callID);
    }

    @Override
    public Object getResult() {
        return "void";
    }
}
