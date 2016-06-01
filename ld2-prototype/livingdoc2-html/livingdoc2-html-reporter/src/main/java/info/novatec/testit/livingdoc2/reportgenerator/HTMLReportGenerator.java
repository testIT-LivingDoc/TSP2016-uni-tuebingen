package info.novatec.testit.livingdoc2.reportgenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import info.novatec.testit.livingdoc2.Statistics;
import info.novatec.testit.livingdoc2.api.executables.ExecutableStructure;
import info.novatec.testit.livingdoc2.executables.ContentModificationIndicator;
import info.novatec.testit.livingdoc2.executables.ValueCell;
import info.novatec.testit.livingdoc2.executables.ValueRow;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 *         <p/>
 *         Creates the output as HTML and writes it to the given location
 */
public final class HTMLReportGenerator {

    private HTMLReportGenerator() {
    }

    public static Statistics writeOutput(Specification spec, Path output) throws IOException {
        Map<String, Object> specMap = spec.getContentMap();

        return modifyStructuresAndPrint(specMap, output);
    }

    private static Statistics modifyStructuresAndPrint(Map<String, Object> specMap, Path output) throws IOException {
        Statistics specificationStats = new Statistics();
        Document html = ( Document ) specMap.get("jSoupDocument");
        Elements tables = html.getElementsByTag("table");

        for (int i = 0; i < tables.size(); i++) {
            String key = "structure" + (i + 1);

            if (specMap.containsKey(key)) {
                ExecutableStructure structure = ( ExecutableStructure ) specMap.get(key);
                Element table = tables.get(i);

                specificationStats.tally(modifyStructure(structure, table));
            }
        }

        printHTML(html, output);

        return specificationStats;
    }

    private static Statistics modifyStructure(ExecutableStructure structure, Element table) {
        Statistics structureStats = new Statistics();
        Elements rows = table.getElementsByTag("tr");

        String countType = structure.getCountType();

        for (int i = 0; i < rows.size(); i++) {
            ValueRow structureRow = structure.getRow(i);
            Element htmlTableRow = rows.get(i);
            structureStats.tally(modifyRow(structureRow, htmlTableRow, countType));
        }
        return structureStats;
    }

    private static Statistics modifyRow(ValueRow structureRow, Element htmlTableRow, String countType) {
        Statistics rowStats = new Statistics();

        int cellDifference = structureRow.size() - htmlTableRow.children().size();

        if (cellDifference > 0) {
            for (int i = 0; i < cellDifference; i++) {
                htmlTableRow.appendElement("td");
            }
        }

        Elements cells = htmlTableRow.children();
        Statistics cellStats = new Statistics();

        for (int i = 0; i < cells.size(); i++) {
            ValueCell structureCell = structureRow.getValueCell(i);
            Element htmlCell = cells.get(i);
            cellStats.tally(modifyCell(structureCell, htmlCell));
        }

        if (countType.equals("cell")) {
            rowStats.tally(cellStats);
        } else {
            rowStats.merge(cellStats);
        }
        return rowStats;
    }

    private static Statistics modifyCell(ValueCell structureCell, Element htmlCell) {
        ContentModificationIndicator indicator = structureCell.getIndicator();

        if (indicator != null) {
            IndicationProcessor.format(structureCell.getResult(), indicator, htmlCell);
        }

        return new Statistics(indicator);
    }

    private static void printHTML(Document html, Path output) throws IOException {
        if (!Files.exists(output)) {
            Files.createFile(output);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            writer.write(html.toString());
        }
    }
}
