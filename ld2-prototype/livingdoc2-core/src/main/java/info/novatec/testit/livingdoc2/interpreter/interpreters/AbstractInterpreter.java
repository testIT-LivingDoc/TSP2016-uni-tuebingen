package info.novatec.testit.livingdoc2.interpreter.interpreters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import info.novatec.testit.livingdoc2.action.AfterRowAction;
import info.novatec.testit.livingdoc2.action.BeforeRowAction;
import info.novatec.testit.livingdoc2.action.FixtureInstantiateAction;
import info.novatec.testit.livingdoc2.api.action.Action;
import info.novatec.testit.livingdoc2.api.executables.ExecutableCell;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.api.interpreter.Interpreter;
import info.novatec.testit.livingdoc2.call.Call;
import info.novatec.testit.livingdoc2.call.builder.CallBuilder;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Abstract implementation for decorating Interpreters.
 *         Implements base functionality and the necessary methods.
 */
public abstract class AbstractInterpreter implements Interpreter {

    private static final int FIXTURE_ROW_INDEX = 0;
    private static final int FIXTURE_NAME_INDEX = 1;
    private static final int FIXTURE_ARG_START_INDEX = 2;
    private final ExecutableStructure decoratedStructure;
    private final Map<String, Call> callMap = new LinkedHashMap<>();

    public AbstractInterpreter(ExecutableStructure executableStructure) {
        Objects.requireNonNull(executableStructure, "The argument 'executableStructure' must not be null");
        decoratedStructure = executableStructure;
    }

    public ExecutableStructure getOriginalStructure() {
        return decoratedStructure;
    }

    public void addToCallList(Call call) {
        callMap.put(call.getUniqueCallID(), call);
    }

    /**
     * Entry point of the workflow for generating the {@link Call}s.
     */
    public void prepare() {
        List<ValueRow> importRows = getAllRows();
        createFixtureCall(importRows.get(FIXTURE_ROW_INDEX));
        interpretRows(importRows);
    }

    /**
     * Uses the second and following cells to create the {@link FixtureInstantiateAction}.
     * <p/>
     * Only the cells in even columns are used as arguments.
     * Omits the first uneven {@link ValueCell}, as this is the {@link Interpreter} name
     *
     * @param row {@link ValueRow} representing the fixture row
     */
    public void createFixtureCall(ValueRow row) {
        CallBuilder builder = new CallBuilder();
        ValueCell fixtureCell = row.getValueCell(FIXTURE_NAME_INDEX);
        String fixtureName = fixtureCell.getContent();
        List<String> fixtureArgs = null;

        if (row.hasNext(FIXTURE_ARG_START_INDEX)) {
            fixtureArgs = createFixtureArgs(row);
        }
        Action fixtureAction = new FixtureInstantiateAction(fixtureName, fixtureArgs);
        builder.forCell(fixtureCell);

        createAndAddCallForAction(fixtureAction, builder);
    }

    private List<String> createFixtureArgs(ValueRow row) {
        List<ValueCell> argCells = row.getListOfUnevenCells();
        return argCells.stream().skip(FIXTURE_NAME_INDEX).map(ValueCell::getContent).collect(Collectors.toList());
    }

    protected void addBeforeRowAction() {
        CallBuilder builder = new CallBuilder();
        builder.forNullCell();
        createAndAddCallForAction(new BeforeRowAction(), builder);
    }

    protected void addAfterRowAction() {
        CallBuilder builder = new CallBuilder();
        builder.forNullCell();
        createAndAddCallForAction(new AfterRowAction(), builder);
    }

    protected void createAndAddCallForAction(Action action, CallBuilder builder) {
        builder.withCommand(action);
        addToCallList(builder.build());
    }

    /**
     * abstract implementation of the method flow for generating the {@link LinkedHashMap} for the {@link Call}s.
     * Is to be implemented by every {@link Interpreter}
     *
     * @param rows {@link ValueRow}s to interpret.
     */
    protected abstract void interpretRows(List<ValueRow> rows);

    @Override
    public Map<String, Call> getCallMap() {
        return callMap;
    }

    /**
     * Below are all methods inherited by the {@link ExecutableStructure} interface.
     */
    @Override
    public List<ValueRow> getAllRows() {
        return decoratedStructure.getAllRows();
    }

    @Override
    public ExecutableCell getFirstCell() {
        return decoratedStructure.getFirstCell();
    }

    @Override
    public ValueRow getRow(int rowindex) {
        return decoratedStructure.getRow(rowindex);
    }

    @Override
    public ExecutableCell getCell(int rowindex, int cellindex) {
        return decoratedStructure.getCell(rowindex, cellindex);
    }

    @Override
    public String getCountType() {
        return decoratedStructure.getCountType();
    }

    @Override
    public void setCountType(String countType) {
        decoratedStructure.setCountType(countType);
    }

    @Override
    public String getKey() {
        return decoratedStructure.getKey();
    }

    @Override
    public void setKey(String key) {
        //Not needed in Interpreter
    }
}
