package info.novatec.testit.livingdoc2.util;

import java.rmi.server.UID;


/**
 * @author Sebastian Letzel
 */
public final class UIDGenerator {

    private UIDGenerator() {
    }

    public static String nextUID() {
        return new UID().toString();
    }
}
