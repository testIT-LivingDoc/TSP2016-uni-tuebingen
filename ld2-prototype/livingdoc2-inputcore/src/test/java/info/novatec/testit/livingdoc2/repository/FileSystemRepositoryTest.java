package info.novatec.testit.livingdoc2.repository;

public class FileSystemRepositoryTest {

    private String specificationhtml() {
        return "<html><table border='1' cellspacing='0'>" + "<tr><td colspan='3'>My Fixture</td></tr>"
            + "<tr><td>a</td><td>b</td><td>sum()</td></tr>" + "<tr><td>1</td><td>2</td><td>3</td></tr>"
            + "<tr><td>2</td><td>3</td><td>15</td></tr>" + "<tr><td>2</td><td>3</td><td>a</td></tr>" + "</table></html>";
    }
}
