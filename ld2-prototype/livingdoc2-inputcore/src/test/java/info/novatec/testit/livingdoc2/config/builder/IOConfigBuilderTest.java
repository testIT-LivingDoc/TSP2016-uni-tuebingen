package info.novatec.testit.livingdoc2.config.builder;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import info.novatec.testit.livingdoc2.config.IOConfig;
import info.novatec.testit.livingdoc2.repository.FileSystemRepository;


public class IOConfigBuilderTest {

    private IOConfigBuilder builder;
    private IOConfig conf;

    @Before
    public void setup() throws Exception {
        builder = new IOConfigBuilder();
    }

    @Test
    public void shouldCreateValidDefaultIOConfig() throws Exception {
        Path outputdirPath = Paths.get(".").resolve("LivingDocResults");

        conf = builder.build();

        assertEquals(outputdirPath, conf.getOutputDirectory());
        assertEquals(FileSystemRepository.class, conf.getRepository().getClass());
        assertEquals(0, conf.getOutputFormats().size());
    }

    @Test
    public void shouldCreateIOConfigWithOutputFormats() throws Exception {
        builder.withOutputFormats(new String[] { "xml", "html", "wiki" });

        conf = builder.build();

        assertEquals("xml", conf.getOutputFormats().get(0));
        assertEquals("html", conf.getOutputFormats().get(1));
        assertEquals("wiki", conf.getOutputFormats().get(2));

    }
}
