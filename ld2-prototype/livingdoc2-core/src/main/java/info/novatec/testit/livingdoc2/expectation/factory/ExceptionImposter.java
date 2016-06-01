package info.novatec.testit.livingdoc2.expectation.factory;

/**
 * Adapted from LivingDoc 1.0.
 * <p/>
 * Extra exception for {@link Factory}
 */
@SuppressWarnings("serial")
public class ExceptionImposter extends RuntimeException {
    private final Throwable imposterized;

    public ExceptionImposter(Throwable e) {
        super(e.getMessage(), e.getCause());
        imposterized = e;
        setStackTrace(e.getStackTrace());
    }

    public static RuntimeException imposterize(Throwable t) {
        if (t instanceof RuntimeException) {
            return ( RuntimeException ) t;
        }

        return new ExceptionImposter(t);
    }

    public Throwable getRealException() {
        return imposterized;
    }

    @Override
    public String toString() {
        return imposterized.toString();
    }

}
