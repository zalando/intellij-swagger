package org.zalando.intellij.swagger.completion.field.model.openapi;

import com.google.common.collect.ImmutableList;
import org.zalando.intellij.swagger.completion.field.model.common.*;

import java.util.List;

public class OpenApiFields {

    public static List<Field> root() {
        return ImmutableList.of(
                new StringField("openapi", true),
                new InfoField(),
                new ArrayField("servers"),
                new ObjectField("paths", true),
                new ObjectField("components"),
                new ArrayField("security"),
                new ArrayField("tags"),
                new ExternalDocsField()
        );
    }

    public static List<Field> server() {
        return ImmutableList.of(
                new StringField("url", true),
                new StringField("description"),
                new ObjectField("variables")
        );
    }

    public static List<Field> serverVariable() {
        return ImmutableList.of(
                new ArrayField("enum"),
                new StringField("default", true),
                new StringField("description")
        );
    }

    public static List<Field> path() {
        return ImmutableList.of(
                new RefField(),
                new StringField("summary"),
                new StringField("description"),
                new OperationField("get"),
                new OperationField("put"),
                new OperationField("post"),
                new OperationField("delete"),
                new OperationField("options"),
                new OperationField("head"),
                new OperationField("patch"),
                new OperationField("trace"),
                new ArrayField("servers"),
                new ArrayField("parameters")
        );
    }

    public static List<Field> operation() {
        return ImmutableList.of(
                new ArrayField("tags"),
                new StringField("summary"),
                new StringField("description"),
                new ExternalDocsField(),
                new StringField("operationId"),
                new ArrayField("parameters"),
                new ObjectField("requestBody"),
                new ObjectField("responses", true),
                new ObjectField("callbacks"),
                new StringField("deprecated"),
                new ArrayField("security"),
                new ArrayField("servers")
        );
    }

    public static List<Field> parameter() {
        return ImmutableList.of(
                new RefField(),
                new StringField("name", true),
                new StringField("in", true),
                new StringField("description"),
                new StringField("required"),
                new StringField("deprecated"),
                new StringField("allowEmptyValue"),
                new StringField("style"),
                new StringField("explode"),
                new StringField("allowReserved"),
                new ObjectField("schema"),
                new StringField("example"),
                new ObjectField("examples"),
                new ObjectField("content")
        );
    }

    public static List<Field> component() {
        return ImmutableList.of(
                new ObjectField("schemas"),
                new ObjectField("responses"),
                new ObjectField("parameters"),
                new ObjectField("examples"),
                new ObjectField("requestBodies"),
                new ObjectField("headers"),
                new ObjectField("securitySchemes"),
                new ObjectField("links"),
                new ObjectField("callbacks")
        );
    }

    public static List<Field> requestBody() {
        return ImmutableList.of(
                new RefField(),
                new StringField("description"),
                new ObjectField("content"),
                new StringField("required")
        );
    }

    public static List<Field> mediaType() {
        return ImmutableList.of(
                new ObjectField("schema"),
                new ObjectField("example"),
                new ObjectField("examples"),
                new ObjectField("encoding")
        );
    }

    public static List<Field> schema() {
        return ImmutableList.of(
                new RefField(),
                new StringField("title"),
                new StringField("multipleOf"),
                new StringField("maximum"),
                new StringField("exclusiveMaximum"),
                new StringField("minimum"),
                new StringField("exclusiveMinimum"),
                new StringField("maxLength"),
                new StringField("minLength"),
                new StringField("pattern"),
                new StringField("maxItems"),
                new StringField("minItems"),
                new StringField("uniqueItems"),
                new StringField("maxProperties"),
                new StringField("minProperties"),
                new StringField("required"),
                new StringField("enum"),
                new StringField("type"),
                new ArrayField("allOf"),
                new ArrayField("oneOf"),
                new ArrayField("anyOf"),
                new ObjectField("not"),
                new ObjectField("items"),
                new ObjectField("properties"),
                new StringField("additionalProperties"),
                new StringField("description"),
                new StringField("format"),
                new StringField("default"),
                new StringField("nullable"),
                new ObjectField("discriminator"),
                new StringField("readOnly"),
                new StringField("writeOnly"),
                new ObjectField("xml"),
                new ObjectField("externalDocs"),
                new ObjectField("example"),
                new StringField("deprecated")
        );
    }

    public static List<Field> example() {
        return ImmutableList.of(
                new RefField(),
                new StringField("summary"),
                new StringField("description"),
                new StringField("value"),
                new StringField("externalValue")
        );
    }

    public static List<Field> encoding() {
        return ImmutableList.of(
                new StringField("contentType"),
                new ObjectField("headers"),
                new StringField("style"),
                new StringField("explode"),
                new StringField("allowReserved")
        );
    }

    public static List<Field> response() {
        return ImmutableList.of(
                new RefField(),
                new StringField("description"),
                new ObjectField("headers"),
                new StringField("content"),
                new StringField("links")
        );
    }

    public static List<Field> header() {
        return ImmutableList.of(
                new RefField(),
                new StringField("description"),
                new StringField("required"),
                new StringField("deprecated"),
                new StringField("allowEmptyValue"),
                new StringField("style"),
                new StringField("explode"),
                new StringField("allowReserved"),
                new ObjectField("schema"),
                new StringField("example"),
                new ObjectField("examples"),
                new ObjectField("content")
        );
    }

    public static List<Field> link() {
        return ImmutableList.of(
                new RefField(),
                new StringField("operationRef"),
                new StringField("operationId"),
                new ObjectField("parameters"),
                new StringField("requestBody"),
                new StringField("description"),
                new ObjectField("server")
        );
    }

    public static List<Field> securityScheme() {
        return ImmutableList.of(
                new RefField(),
                new StringField("type", true),
                new StringField("description"),
                new StringField("name", true),
                new StringField("in", true),
                new StringField("scheme", true),
                new StringField("bearerFormat"),
                new ObjectField("flows", true),
                new StringField("openIdConnectUrl", true)
        );
    }

    public static List<Field> callback() {
        return path();
    }
}
