package info.novatec.testit.livingdoc2.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import info.novatec.testit.livingdoc2.action.AfterSpecAction;
import info.novatec.testit.livingdoc2.action.BeforeSpecAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.execution.Executor;
import info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult;
import info.novatec.testit.livingdoc2.api.input.ExecutableListHandler;
import info.novatec.testit.livingdoc2.api.interpreter.Interpreter;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.config.ExecutionConfig;
import info.novatec.testit.livingdoc2.execution.LocalJavaExecutor;
import info.novatec.testit.livingdoc2.interpreter.decorator.ExecutablesDecorator;
import info.novatec.testit.livingdoc2.reflection.ClassPathScanner;
import info.novatec.testit.livingdoc2.reflection.InterpreterStore;
import info.novatec.testit.livingdoc2.reflection.scanner.InterpreterScanner;
import info.novatec.testit.livingdoc2.result.ResultAnalyzer;


/**
 * @author Sebastian Letzel
 *         <p>
 *         Entry point for core
 *         Receives the list with executables and handles the preperation before sending the Commands to the executor
 */
public class LocalExecutableListHandler implements ExecutableListHandler {

    private ExecutionConfig executionConfig;
    private Executor executor;

    public LocalExecutableListHandler() {
        initializeInterpreterStore();
    }

    @Override
    public void initializeExecutor() {
        executor = new LocalJavaExecutor(executionConfig);
    }

    @Override
    public void setExecutionConfig(ExecutionConfig executionConfig) {
        this.executionConfig = executionConfig;
        initializeExecutor();
    }

    /**
     * Checks if the {@link InterpreterStore} was already scanned. If not it performs the scan.
     */
    public void initializeInterpreterStore() {
        if (InterpreterStore.getInstance().isNotFilled()) {
            new ClassPathScanner().scan(new InterpreterScanner());
        }
    }

    @Override
    public List<ExecutableStructure> handleExecutableList(List<ExecutableStructure> executableList)
        throws ReflectiveOperationException {

        List<Interpreter> decorated = ExecutablesDecorator.decorate(executableList);

        return processInterpreter(decorated);
    }

    /**
     * This method performs the workflow of:
     * - Preparation: Generates the {@link Call}s for the performing {@link Interpreter} for execution
     * - Extracting: The {@link Action}s from the {@link Call}s tho reduce load sent to the {@link Executor}
     * - Sending: The {@link List} of {@link Action}s to the {@link Executor} for execution
     * - Merging: The {@link ExecutionResult}s with associated {@link Call}s
     * - Analyzing: The {@link ExecutionResult}s with the {@link ResultAnalyzer} to mark the {@link
     * info.novatec.testit.livingdoc2.executables.ValueCell}s with appropriate indicators
     * - Returning: The {@link List} of executed {@link ExecutableStructure}s.
     *
     * @param decorated {@link List} of with {@link Interpreter} decorated {@link ExecutableStructure}s
     * @return the {@link List} of modified/executed {@link ExecutableStructure}s
     */
    private List<ExecutableStructure> processInterpreter(List<Interpreter> decorated) {
        List<ExecutableStructure> executed = new ArrayList<>();

        sendBeforeSpecAction();

        for (Interpreter interpreter : decorated) {
            interpreter.prepare();

            Map<String, Call> callMap = interpreter.getCallMap();
            List<Action> actions = fillActionList(callMap);

            List<ExecutionResult> results = sendActionListToExecutor(actions);
            mergeResultsWithStructure(callMap, results);

            analyzeResults(callMap);

            ExecutableStructure executedStructure = interpreter.getOriginalStructure();
            executed.add(executedStructure);
        }

        sendAfterSpecAction();

        return executed;
    }

    /**
     * Sends a single {@link Action} to the {@link Executor} for execution.
     *
     * @param action the {@link Action} to execute
     * @return the {@link ExecutionResult} from this execution
     */
    public ExecutionResult sendActionToExecutor(Action action) {
        return executor.executeSingleAction(action);
    }

    /**
     * Sends a {@link List} of {@link Action}s to the executor for execution.
     *
     * @param actions the {@link List} of {@link Action}s to execute
     * @return the {@link List} of {@link ExecutionResult}s from this execution
     */
    public List<ExecutionResult> sendActionListToExecutor(List<Action> actions) {
        return executor.executeActionList(actions);
    }

    /**
     * Uses the {@code callID} to map the {@link ExecutionResult} to the {@link Call} which holds the {@link Action} that
     * produced this {@link ExecutionResult}.
     * <p>
     * If there are no {@link ExecutionResult}s the {@link Call}s will not be modified.
     *
     * @param calls the map of {@link Call}s of the current {@link Interpreter}
     * @param results the {@link List} of produced {@link ExecutionResult}s
     */
    private void mergeResultsWithStructure(Map<String, Call> calls, List<ExecutionResult> results) {
        if (results.isEmpty()) {
            return;
        }
        for (ExecutionResult result : results) {
            String resultID = result.getCallID();
            Call call = calls.get(resultID);
            if (call != null) {
                call.setResult(result);
            }
        }
    }

    private void sendAfterSpecAction() {
        sendActionToExecutor(new AfterSpecAction());
    }

    private void sendBeforeSpecAction() {
        sendActionToExecutor(new BeforeSpecAction());
    }

    /**
     * This methods gets the list of {@link Action}s from the map with {@link Call}s
     * to prepare for execution in the {@link Executor}.
     *
     * @param calls the list of calls from the {@link Interpreter}
     * @return the list of {@link Action}s present in the calls
     */
    private List<Action> fillActionList(Map<String, Call> calls) {
        return calls.entrySet().stream().map(Map.Entry::getValue).map(Call::getActionMessage).collect(Collectors.toList());
    }

    /**
     * Calls the {@link ResultAnalyzer} to check the {@link ExecutionResult}s vs the {@link
     * info.novatec.testit.livingdoc2.api.expectation.Expectation}s.
     * Uses a parallel stream as order is not important and it speeds the analysis up.
     *
     * @param calls the map of {@link Call}s from the current {@link Interpreter}
     */
    private void analyzeResults(Map<String, Call> calls) {
        calls.entrySet().parallelStream().forEach(k -> ResultAnalyzer.analyze(k.getValue()).start());
    }

}
