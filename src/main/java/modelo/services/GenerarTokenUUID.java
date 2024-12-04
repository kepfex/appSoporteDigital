package modelo.services;

import java.util.UUID;

/**
 *
 * @author KEVIN
 */
public class GenerarTokenUUID {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateUUIDWithoutDashes() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateNameBasedUUID(String name) {
        return UUID.nameUUIDFromBytes(name.getBytes()).toString();
    }
}
