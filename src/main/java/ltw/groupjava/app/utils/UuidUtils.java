package ltw.groupjava.app.utils;

import java.util.UUID;

public class UuidUtils {
    public static UUID parse(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            return null;
        }
    }
}
