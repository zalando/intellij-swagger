package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;
import org.apache.http.HttpHeaders;

import java.util.List;

public class Fields {

    public static List<Field> root() {
        return ImmutableList.of(
                new StringField("swagger", true),
                new InfoField(),
                new StringField("host"),
                new StringField("basePath"),
                new ArrayField("schemes"),
                new ArrayField("consumes"),
                new ArrayField("produces"),
                new ObjectField("paths", true),
                new ObjectField("definitions"),
                new ObjectField("parameters"),
                new ObjectField("responses"),
                new ObjectField("securityDefinitions"),
                new ArrayField("security"),
                new ArrayField("tags"),
                new ExternalDocsField()
        );
    }

    public static List<Field> contact() {
        return ImmutableList.of(
                new StringField("name"),
                new StringField("url"),
                new StringField("email")
        );
    }

    public static List<Field> schema() {
        return ImmutableList.of(
                new RefField(),
                new StringField("format"),
                new StringField("title"),
                new StringField("description"),
                new StringField("default"),
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
                new ArrayField("required"),
                new ArrayField("enum"),
                new StringField("type"),
                new ItemsField(),
                new ArrayField("allOf"),
                new ObjectField("properties"),
                new ObjectField("additionalProperties"),
                new StringField("discriminator"),
                new StringField("readOnly"),
                new XmlField(),
                new ExternalDocsField(),
                new ObjectField("example")
        );
    }

    public static List<Field> externalDocs() {
        return ImmutableList.of(
                new StringField("description"),
                new StringField("url")
        );
    }

    public static List<Field> header() {
        return ImmutableList.of(
                new StringField("description"),
                new StringField("type", true),
                new StringField("format"),
                new ItemsField(),
                new StringField("collectionFormat"),
                new StringField("default"),
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
                new ArrayField("enum"),
                new StringField("multipleOf")
        );
    }

    public static List<Field> info() {
        return ImmutableList.of(
                new StringField("title", true),
                new StringField("description"),
                new StringField("termsOfService"),
                new ContactField(),
                new LicenseField(),
                new StringField("version", true)
        );
    }

    public static List<Field> items() {
        return ImmutableList.of(
                new RefField(),
                new StringField("type", true),
                new StringField("format"),
                new ItemsField(),
                new StringField("collectionFormat"),
                new StringField("default"),
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
                new ArrayField("enum"),
                new StringField("multipleOf")
        );
    }

    public static List<Field> license() {
        return ImmutableList.of(
                new StringField("name", true),
                new StringField("url")
        );
    }

    public static List<Field> operation() {
        return ImmutableList.of(
                new ArrayField("tags"),
                new StringField("summary"),
                new StringField("description"),
                new ExternalDocsField(),
                new StringField("operationId"),
                new ArrayField("consumes"),
                new ArrayField("produces"),
                new ArrayField("parameters"),
                new ObjectField("responses", true),
                new ArrayField("schemes"),
                new StringField("deprecated"),
                new ArrayField("security")
        );
    }

    public static List<Field> parameters() {
        return ImmutableList.of(
                new StringField("name", true),
                new StringField("in", true),
                new StringField("description"),
                new StringField("required"),
                new ObjectField("schema"),
                new StringField("type"),
                new StringField("format"),
                new StringField("allowEmptyValue"),
                new ItemsField(),
                new StringField("collectionFormat"),
                new StringField("default"),
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
                new ArrayField("enum"),
                new StringField("multipleOf")
        );
    }

    public static List<Field> parametersWithRef() {
        return ImmutableList.of(
                new RefField(),
                new StringField("name", true),
                new StringField("in", true),
                new StringField("description"),
                new StringField("required"),
                new ObjectField("schema"),
                new StringField("type"),
                new StringField("format"),
                new StringField("allowEmptyValue"),
                new ItemsField(),
                new StringField("collectionFormat"),
                new StringField("default"),
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
                new ArrayField("enum"),
                new StringField("multipleOf")
        );
    }

    public static List<Field> path() {
        return ImmutableList.of(
                new RefField(),
                new OperationField("get"),
                new OperationField("put"),
                new OperationField("post"),
                new OperationField("delete"),
                new OperationField("options"),
                new OperationField("head"),
                new OperationField("patch"),
                new ArrayField("parameters")
        );
    }

    public static List<Field> response() {
        return ImmutableList.of(
                new RefField(),
                new StringField("description", true),
                new ObjectField("schema"),
                new ObjectField("headers"),
                new ObjectField("examples")
        );
    }

    public static List<Field> responseDefinition() {
        return ImmutableList.of(
                new StringField("description", true),
                new ObjectField("schema"),
                new ObjectField("headers"),
                new ObjectField("examples")
        );
    }

    public static List<Field> responses() {
        return ImmutableList.of(
                new ObjectField("default"),
                new ResponseField("200"),
                new ResponseField("201"),
                new ResponseField("202"),
                new ResponseField("203"),
                new ResponseField("204"),
                new ResponseField("205"),
                new ResponseField("206"),
                new ResponseField("207"),
                new ResponseField("208"),
                new ResponseField("226"),

                new ResponseField("300"),
                new ResponseField("301"),
                new ResponseField("302"),
                new ResponseField("303"),
                new ResponseField("304"),
                new ResponseField("305"),
                new ResponseField("306"),
                new ResponseField("307"),
                new ResponseField("308"),

                new ResponseField("400"),
                new ResponseField("401"),
                new ResponseField("402"),
                new ResponseField("403"),
                new ResponseField("404"),
                new ResponseField("405"),
                new ResponseField("406"),
                new ResponseField("407"),
                new ResponseField("408"),
                new ResponseField("409"),
                new ResponseField("410"),
                new ResponseField("411"),
                new ResponseField("412"),
                new ResponseField("413"),
                new ResponseField("414"),
                new ResponseField("415"),
                new ResponseField("416"),
                new ResponseField("417"),
                new ResponseField("418"),
                new ResponseField("421"),
                new ResponseField("422"),
                new ResponseField("423"),
                new ResponseField("424"),
                new ResponseField("426"),
                new ResponseField("428"),
                new ResponseField("429"),
                new ResponseField("431"),
                new ResponseField("451"),

                new ResponseField("500"),
                new ResponseField("501"),
                new ResponseField("502"),
                new ResponseField("503"),
                new ResponseField("504"),
                new ResponseField("505"),
                new ResponseField("506"),
                new ResponseField("507"),
                new ResponseField("508"),
                new ResponseField("510"),
                new ResponseField("511")
        );
    }

    public static List<Field> securityDefinitions() {
        return ImmutableList.of(
                new StringField("type", true),
                new StringField("description"),
                new StringField("name", true),
                new StringField("in", true),
                new StringField("flow", true),
                new StringField("authorizationUrl", true),
                new StringField("tokenUrl", true),
                new ObjectField("scopes", true)
        );
    }

    public static List<Field> tags() {
        return ImmutableList.of(
                new StringField("name", true),
                new StringField("description"),
                new ExternalDocsField()
        );
    }

    public static List<Field> xml() {
        return ImmutableList.of(
                new StringField("name"),
                new StringField("namespace"),
                new StringField("prefix"),
                new StringField("attribute"),
                new StringField("wrapped")
        );
    }

    public static List<Field> headers() {
        return ImmutableList.of(
                new HeadersField(HttpHeaders.ACCEPT),
                new HeadersField(HttpHeaders.ACCEPT_CHARSET),
                new HeadersField(HttpHeaders.ACCEPT_ENCODING),
                new HeadersField(HttpHeaders.ACCEPT_LANGUAGE),
                new HeadersField(HttpHeaders.ACCEPT_RANGES),
                new HeadersField(HttpHeaders.AGE),
                new HeadersField(HttpHeaders.ALLOW),
                new HeadersField(HttpHeaders.AUTHORIZATION),
                new HeadersField(HttpHeaders.CACHE_CONTROL),
                new HeadersField(HttpHeaders.CONNECTION),
                new HeadersField(HttpHeaders.CONTENT_ENCODING),
                new HeadersField(HttpHeaders.CONTENT_LANGUAGE),
                new HeadersField(HttpHeaders.CONTENT_LENGTH),
                new HeadersField(HttpHeaders.CONTENT_LOCATION),
                new HeadersField(HttpHeaders.CONTENT_MD5),
                new HeadersField(HttpHeaders.CONTENT_RANGE),
                new HeadersField(HttpHeaders.CONTENT_TYPE),
                new HeadersField(HttpHeaders.DATE),
                new HeadersField(HttpHeaders.DAV),
                new HeadersField(HttpHeaders.DEPTH),
                new HeadersField(HttpHeaders.DESTINATION),
                new HeadersField(HttpHeaders.ETAG),
                new HeadersField(HttpHeaders.EXPECT),
                new HeadersField(HttpHeaders.EXPIRES),
                new HeadersField(HttpHeaders.FROM),
                new HeadersField(HttpHeaders.HOST),
                new HeadersField(HttpHeaders.IF),
                new HeadersField(HttpHeaders.IF_MATCH),
                new HeadersField(HttpHeaders.IF_MODIFIED_SINCE),
                new HeadersField(HttpHeaders.IF_NONE_MATCH),
                new HeadersField(HttpHeaders.IF_RANGE),
                new HeadersField(HttpHeaders.IF_UNMODIFIED_SINCE),
                new HeadersField(HttpHeaders.LAST_MODIFIED),
                new HeadersField(HttpHeaders.LOCATION),
                new HeadersField(HttpHeaders.LOCK_TOKEN),
                new HeadersField(HttpHeaders.MAX_FORWARDS),
                new HeadersField(HttpHeaders.OVERWRITE),
                new HeadersField(HttpHeaders.PRAGMA),
                new HeadersField(HttpHeaders.PROXY_AUTHENTICATE),
                new HeadersField(HttpHeaders.PROXY_AUTHORIZATION),
                new HeadersField(HttpHeaders.RANGE),
                new HeadersField(HttpHeaders.REFERER),
                new HeadersField(HttpHeaders.RETRY_AFTER),
                new HeadersField(HttpHeaders.SERVER),
                new HeadersField(HttpHeaders.STATUS_URI),
                new HeadersField(HttpHeaders.TE),
                new HeadersField(HttpHeaders.TIMEOUT),
                new HeadersField(HttpHeaders.TRAILER),
                new HeadersField(HttpHeaders.TRANSFER_ENCODING),
                new HeadersField(HttpHeaders.UPGRADE),
                new HeadersField(HttpHeaders.USER_AGENT),
                new HeadersField(HttpHeaders.VARY),
                new HeadersField(HttpHeaders.VIA),
                new HeadersField(HttpHeaders.WARNING),
                new HeadersField(HttpHeaders.WWW_AUTHENTICATE)
        );
    }
}
