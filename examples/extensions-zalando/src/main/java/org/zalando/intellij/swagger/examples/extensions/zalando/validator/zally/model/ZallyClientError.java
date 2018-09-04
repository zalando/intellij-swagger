package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

public class ZallyClientError extends RuntimeException {

    private final String message;

    public ZallyClientError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
