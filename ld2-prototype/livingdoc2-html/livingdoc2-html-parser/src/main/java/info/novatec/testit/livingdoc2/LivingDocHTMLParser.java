package info.novatec.testit.livingdoc2;

import java.io.IOException;
import java.nio.file.Path;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import info.novatec.testit.livingdoc2.api.parser.InputParser;
import info.novatec.testit.livingdoc2.parser.HTMLStructureConstructor;
import info.novatec.testit.livingdoc2.specification.Specification;


/**
 * @author Sebastian Letzel
 * Entry point for HTML-Parser
 */
public class LivingDocHTMLParser implements InputParser {

    @Override
    public Specification parse(Path file) throws IOException {
        Path fileName = file.getFileName();
        String name = stripExtension(fileName);
        Document html = Jsoup.parse(file.toFile(), "UTF-8");

        return HTMLStructureConstructor.create(html, name);
    }

    private String stripExtension(Path fileName) {
        if (fileName == null) {
            return "";
        }
        String temp = fileName.toString();
        temp = temp.substring(0, temp.lastIndexOf('.'));
        return temp;
    }

}
