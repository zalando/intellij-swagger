package org.zalando.intellij.swagger.completion.field.model.swagger;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.zalando.intellij.swagger.completion.field.model.common.*;

public class SwaggerFields {

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
        new ExternalDocsField());
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
        new ObjectField("items"),
        new ArrayField("allOf"),
        new ObjectField("properties"),
        new ObjectField("additionalProperties"),
        new StringField("discriminator"),
        new StringField("readOnly"),
        new XmlField(),
        new ExternalDocsField(),
        new ObjectField("example"));
  }

  public static List<Field> schemaItems() {
    return schema();
  }

  public static List<Field> header() {
    return ImmutableList.of(
        new StringField("description"),
        new StringField("type", true),
        new StringField("format"),
        new ParameterItemsField(),
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
        new StringField("multipleOf"));
  }

  public static List<Field> parameterItems() {
    return ImmutableList.of(
        new StringField("type", true),
        new StringField("format"),
        new ParameterItemsField(),
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
        new StringField("multipleOf"));
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
        new ArrayField("security"));
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
        new ParameterItemsField(),
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
        new StringField("multipleOf"));
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
        new ParameterItemsField(),
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
        new StringField("multipleOf"));
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
        new ArrayField("parameters"));
  }

  public static List<Field> response() {
    return ImmutableList.of(
        new RefField(),
        new StringField("description", true),
        new ObjectField("schema"),
        new ObjectField("headers"),
        new ObjectField("examples"));
  }

  public static List<Field> responseDefinition() {
    return ImmutableList.of(
        new StringField("description", true),
        new ObjectField("schema"),
        new ObjectField("headers"),
        new ObjectField("examples"));
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
        new ObjectField("scopes", true));
  }

  public static List<Field> xml() {
    return ImmutableList.of(
        new StringField("name"),
        new StringField("namespace"),
        new StringField("prefix"),
        new StringField("attribute"),
        new StringField("wrapped"));
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
        new HeadersField(HttpHeaders.WWW_AUTHENTICATE));
  }
}
