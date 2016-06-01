package info.novatec.testit.livingdoc2.api.executables;

/**
 * @author Sebastian Letzel
 *         <p/>
 *         Entry point for the two different cell types.
 */
public interface ExecutableCell extends Executable {

    /**
     * Returns, if existent, the string content of a {@link info.novatec.testit.livingdoc2.executables.ValueCell}.
     *
     * @return the content as {@link String}
     */
    String getContent();

    /**
     * Returns a {@link ExecutableStructure} within a {@link info.novatec.testit.livingdoc2.executables.CellWithExecutableStructure}.
     *
     * @return the stored {@link ExecutableStructure}
     */
    ExecutableStructure getStructure();
}
