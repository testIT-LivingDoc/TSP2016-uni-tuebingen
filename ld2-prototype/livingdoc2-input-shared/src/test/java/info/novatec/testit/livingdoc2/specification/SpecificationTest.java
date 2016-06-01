package info.novatec.testit.livingdoc2.specification;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import info.novatec.testit.livingdoc2.api.executables.Executable;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.executables.BasicExecutableStructure;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;


public class SpecificationTest {

    Specification spec;

    @Before
    public void setup() throws Exception {
        spec = new Specification("test", "testi");

        spec.addSpecificationNode("test", "tester");
        spec.addSpecificationNode("test2", "tester2");
        spec.addSpecificationNode("table1", new BasicExecutableStructure(new ArrayList<>()));
        spec.addSpecificationNode("test3", "tester3");
        spec.addSpecificationNode("table2", new BasicExecutableStructure(new ArrayList<>()));
        spec.addSpecificationNode("table3", new BasicExecutableStructure(new ArrayList<>()));
        spec.addSpecificationNode("test3", "tester3");
    }

    @Test
    public void shouldReturnFilteredSpecObjects() throws Exception {
        assertEquals(3, spec.getExecutablesList().size());
    }

    @Test
    public void shouldReturnRightType() throws Exception {
        assertEquals("test", spec.getInputFormat());
    }

    @Test
    public void shouldCountKeyUpForSpecObjects() throws Exception {
        Map<String, Object> specification = spec.getContentMap();

        assertTrue(specification.get("table1") instanceof Executable);
        assertTrue(specification.get("table2") instanceof Executable);
        assertTrue(specification.get("table3") instanceof Executable);
    }

    @Test
    public void shouldOverwriteStructure() throws Exception {

        List<ValueRow> rows = new ArrayList<>();
        rows.add(new ValueRow(Arrays.asList(new ValueCell("TestCell1"), new ValueCell("TestCell2"))));
        ExecutableStructure mergeTable = new BasicExecutableStructure(rows);

        spec.mergeStructureIntoContentMap("table1", mergeTable);

        List<ExecutableStructure> mergedList = spec.getExecutablesList();
        ExecutableStructure merged = mergedList.get(0);

        assertEquals("TestCell1", merged.getFirstCell().getContent());
        assertEquals("TestCell2", merged.getCell(0, 1).getContent());
    }

}
