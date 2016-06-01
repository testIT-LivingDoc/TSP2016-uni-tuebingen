package info.novatec.testit.livingdoc2.specification;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;


/**
 * @author Sebastian Letzel
 *         Class that holds the complete information of the parsed Specification for easier manipulation or printing
 *         The {@code contentMap} allows with the value type of {@link Object}, that the {@link
 *         info.novatec.testit.livingdoc2.api.parser.InputParser} can decide how to store the contents of the {@link
 *         Specification}.
 */
public class Specification {
    private final Map<String, Object> contentMap = new LinkedHashMap<>();
    private final String inputFormat;
    private final String fileName;

    public Specification(String type, String fileName) {
        this.inputFormat = type;
        this.fileName = fileName;
    }

    /**
     * Adds an object to the {@code contentMap}. If the object is an instance of {@link ExecutableStructure} it sets the key
     * as {@link String} in the {@link ExecutableStructure} to allow the later use of {@code mergeStructureIntoContentMap}.
     *
     * @param object generated object from the {@link info.novatec.testit.livingdoc2.api.parser.InputParser}
     */
    public void addSpecificationNode(String key, Object object) {
        if (object instanceof ExecutableStructure) {
            (( ExecutableStructure ) object).setKey(key);
        }
        contentMap.put(key, object);
    }

    /**
     * Filters the {@code contentMap} for all {@link ExecutableStructure}s and returns them as a {@link LinkedList} to
     * retain their order.
     *
     * @return {@link LinkedList} of the {@link ExecutableStructure}s
     */
    public List<ExecutableStructure> getExecutablesList() {
        return contentMap.entrySet()
            .stream()
            .filter(key -> key.getValue() instanceof ExecutableStructure)
            .map(key -> ( ExecutableStructure ) key.getValue())
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Overwrites existing {@link ExecutableStructure} for the given key to allow replacing the unmodified {@link
     * ExecutableStructure} with the one containing the {@link info.novatec.testit.livingdoc2.api.executionresult.ExecutionResult}s.
     *
     * @param key {@link String} identifier for the structure
     * @param structure the {@link ExecutableStructure} which is the replacement
     */
    public void mergeStructureIntoContentMap(String key, ExecutableStructure structure) {
        contentMap.put(key, structure);
    }

    public Map<String, Object> getContentMap() {
        return contentMap;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public String getFileName() {
        return fileName;
    }
}
