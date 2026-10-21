package com.hms.util;
import java.nio.charset.StandardCharsets; import java.security.MessageDigest; import java.security.NoSuchAlgorithmException;
public final class PasswordUtil {
    private PasswordUtil() {}
    public static String sha256(String value) { try { byte[] bytes=MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)); StringBuilder s=new StringBuilder(); for(byte b:bytes)s.append(String.format("%02x", b)); return s.toString(); } catch(NoSuchAlgorithmException e){ throw new IllegalStateException(e); } }
}
