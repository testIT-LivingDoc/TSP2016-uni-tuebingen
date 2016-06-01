package info.novatec.testit.livingdoc2.api.executionresult;

/**
 * @author Sebastian Letzel
 *
 * Interface to access all types of results
 */
public interface ExecutionResult {
    String getCallID();

    void setResultAsString(String resultAsString);

    String getStringResult();

    Object getResult();
}
