package test.app.simpleworkflow.impl.security;

import java.util.UUID;

public class SignHelper {

    public static String sign(UUID message) {
        return message.toString().hashCode() + "";
    }
}
