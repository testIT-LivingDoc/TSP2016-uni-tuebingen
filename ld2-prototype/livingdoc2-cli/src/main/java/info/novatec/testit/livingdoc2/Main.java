package info.novatec.testit.livingdoc2;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;

import info.novatec.testit.livingdoc2.api.runner.SpecificationRunner;
import info.novatec.testit.livingdoc2.cli.CommandLineInterpreter;


/**
 * @author Sebastian Letzel
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {

        try {
            SpecificationRunner runner = new CommandLineInterpreter(args).getRunner();
            runner.run();
        } catch (MissingArgumentException e) {
            e.printStackTrace();//NOPMD
        } catch (ParseException e) {
            System.out.println("Commandline could not be read.  Reason: " + e.getMessage());//NOPMD
        }
    }
}
