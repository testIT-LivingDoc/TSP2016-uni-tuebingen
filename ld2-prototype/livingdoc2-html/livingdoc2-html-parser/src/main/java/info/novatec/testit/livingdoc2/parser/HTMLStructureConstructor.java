package info.novatec.testit.livingdoc2.parser;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import info.novatec.testit.livingdoc2.api.executables.Executable;
import info.novatec.testit.livingdoc2.api.executables.ExecutableCell;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.executables.BasicExecutableStructure;
import info.novatec.testit.livingdoc2.executables.CellWithExecutableStructure;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 * <p/>
 * This class utilises the jSoup functionalities to fill the {@link Specification} with its components from the parsed HTML
 */
public final class HTMLStructureConstructor {

    private HTMLStructureConstructor() {
    }

    public static Specification create(Document html, String name) {
        Specification specification = new Specification("html", name);

        addDocumentToSpecification(specification, html);
        addTablesToSpecification(specification, html);

        return specification;
    }

    /**
     * Starts method cascade to add each viable executable table found in the specification to the {@link Specification}.
     *
     * @param specification the {@link Specification} which will be edited
     * @param html jSoup parsed {@link Document}
     */
    private static void addTablesToSpecification(Specification specification, Document html) {
        Elements tables = html.getElementsByTag("table");
        int structureCounter = 0;
        for (Element table : tables) {
            structureCounter++;
            Executable executable = createTable(table);
            if (executable != null) {
                specification.addSpecificationNode("structure" + structureCounter, executable);
            }
        }
    }

    /**
     * //TODO check table in table.
     * @param tableElement extracted table {@link Element} from jSoup
     * @return returns thea created table in {@link BasicExecutableStructure} form
     */
    private static Executable createTable(Element tableElement) {
        String firstHeaderCell = getFirstHeaderCellContent(tableElement);
        boolean tableIsNotExecutable = filterNotExecutableElements(firstHeaderCell);

        if (!tableElement.hasText() || tableIsNotExecutable) {
            return null;
        }

        Executable table = new BasicExecutableStructure(new ArrayList<>());

        Elements rows = tableElement.getElementsByTag("tr");
        rows.forEach(r -> createAndAddRows(r, table));

        return table;
    }

    private static void createAndAddRows(Element rowElement, Executable table) {
        Executable row = new ValueRow(new ArrayList<>());
        Elements cells = rowElement.children();

        cells.forEach(c -> createAndAddCells(c, row));

        table.add(row);
    }

    private static void createAndAddCells(Element cellElement, Executable row) {
        ExecutableCell cell;

        if (!cellElement.getElementsByTag("table").isEmpty()) {
            cell = new CellWithExecutableStructure(( ExecutableStructure ) createTable(cellElement));
        } else {
            cell = new ValueCell(cellElement.text());
        }
        row.add(cell);
    }

    /**
     * Rudimentary filter method for unwanted elements in the specification.
     * Can be expanded to filter a wider range. To set and use filters you
     * have to extend the IOConfig of the InputCore
     * and implement them in a shared library for parsers and InputCore. Input-Shared would not be suitable
     * because the core does not need to know about filters.
     * <p/>
     * Right now only tables have a filters
     *
     * @param firstHeaderCellContent has the text of the first cell of the table
     * @return boolean for matching a filter.
     */
    private static boolean filterNotExecutableElements(String firstHeaderCellContent) {
        return firstHeaderCellContent.equalsIgnoreCase("Begin Info") || firstHeaderCellContent.equalsIgnoreCase("End Info");
    }

    /**
     * Because of the jSoup library capabilities it is enough to add the jSoup parsed {@link Document} to the map.
     * The reporter can later modify this {@link Document} directly based on the results stored in the {@link
     * ExecutableCell}s
     *
     * @param specification the {@link Specification} which the parsed jSoup {@link Document} will be added to
     * @param html jSoups parsed HTML-Document
     */
    private static void addDocumentToSpecification(Specification specification, Document html) {
        specification.addSpecificationNode("jSoupDocument", html);
    }

    /**
     * Checks for the type of the first header cell (td or th) and returns its content.
     *
     * @param tableElement element to check for first cell
     * @return content of first cell as {@link String}
     */
    private static String getFirstHeaderCellContent(Element tableElement) {

        Element contentHeaderCell = tableElement.getElementsByTag("th").first();
        Element contentTdCell = tableElement.getElementsByTag("td").first();

        return contentHeaderCell != null ? contentHeaderCell.text() : contentTdCell.text();
    }
}
