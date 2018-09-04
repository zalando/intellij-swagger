package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.http;

import com.google.common.collect.ImmutableMap;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.components.ServiceManager;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.ZallySettings;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.ZallyService;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingRequest;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponseErrorDecoder;

import java.util.Map;

public class HttpZallyService implements ZallyService {

    public LintingResponse validate(final String spec) {
        return connect().validate(zallyHeaders(), new LintingRequest(spec));
    }

    private Map<String, Object> zallyHeaders() {
        return ImmutableMap.of(
                "User-Agent", "intellij-swagger-plugin/0.0.1",
                "Authorization", "Bearer " + TokenService.getToken(),
                "Content-Type", "application/json"
        );
    }

    private static ZallyApi connect() {
        final String zallyUrl = ServiceManager.getService(ZallySettings.class).zallyUrl;

        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        final Decoder decoder = new GsonDecoder(gson);

        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(decoder)
                .errorDecoder(new LintingResponseErrorDecoder())
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(ZallyApi.class, zallyUrl);
    }

}
