package com.hanyang.datastore.utill;
import java.util.UUID;

public class RandomStringGenerator {
    public static String generateRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
