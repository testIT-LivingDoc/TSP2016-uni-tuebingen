package info.novatec.testit.livingdoc2.repository.exceptions;

/**
 * @author Sebastian Letzel
 * Consolidates different ParseExceptions in the repository to reduce {@link Exception} clutter
 */
public class LivingDocParseException extends AbstractRepositoryException {

    public LivingDocParseException(Throwable cause) {
        super(cause);
    }

    public LivingDocParseException() {

    }
}
