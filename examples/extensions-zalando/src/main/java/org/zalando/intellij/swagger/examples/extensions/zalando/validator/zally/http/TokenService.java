package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.http;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenService {

    private static final String CACHE_KEY = "token";

    private static final LoadingCache<String, String> TOKEN_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, String>() {
                        public String load(String key) throws Exception {
                            return createToken();
                        }
                    });

    public static String getToken() {
        try {
            return TOKEN_CACHE.get(CACHE_KEY);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createToken() throws IOException {
        ProcessBuilder pb =
                new ProcessBuilder("/bin/bash", "-c", "/usr/local/bin/ztoken");
        pb.environment().put("LANG", "en_US.UTF-8");

        Process p = pb.start();

        final String token = new BufferedReader(new
                InputStreamReader(p.getInputStream())).readLine();

        if (token == null) {
            throw new RuntimeException("Could not get a token using `ztoken`. Make sure you are logged in with `zaws login`");
        }

        return token;
    }
}
