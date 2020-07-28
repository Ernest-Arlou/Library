package by.jwd.library.service.util;

public class BCrypt {

    private static final int logRounds = 13;

    private BCrypt() {
    }

    public static String hash(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt(logRounds));
    }

    public static boolean verifyHash(String password, String hash) {
        return org.mindrot.jbcrypt.BCrypt.checkpw(password, hash);
    }
}
