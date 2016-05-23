package org.zalando.intellij.swagger.completion.type;

import com.google.common.collect.Sets;

import java.util.Set;

public enum FieldType {

    ARRAY, OBJECT, STRING;

    private static final Set<String> ARRAY_FIELDS = Sets.newHashSet(
            "enum",
            "tags",
            "consumes",
            "produces",
            "schemes",
            "security",
            "allOf"
    );

    private static final Set<String> OBJECT_FIELDS = Sets.newHashSet(
            "items",
            "externalDocs",
            "parameters",
            "responses",
            "schema",
            "get",
            "put",
            "post",
            "delete",
            "options",
            "head",
            "patch",
            "parameters",
            "headers",
            "examples",
            "info",
            "paths",
            "definitions",
            "parameters",
            "responses",
            "securityDefinitions",
            "properties",
            "additionalProperties",
            "xml",
            "scopes",
            "license",
            "default",
            "200",
            "201",
            "202",
            "203",
            "204",
            "205",
            "206",
            "207",
            "208",
            "226",
            "300",
            "301",
            "302",
            "303",
            "304",
            "305",
            "306",
            "307",
            "308",
            "400",
            "401",
            "402",
            "403",
            "404",
            "405",
            "406",
            "407",
            "408",
            "409",
            "410",
            "411",
            "412",
            "413",
            "414",
            "415",
            "416",
            "417",
            "418",
            "421",
            "422",
            "423",
            "424",
            "426",
            "428",
            "429",
            "431",
            "451",
            "500",
            "501",
            "502",
            "503",
            "504",
            "505",
            "506",
            "507",
            "508",
            "510",
            "511"
    );

    private static final Set<String> STRING_FIELDS = Sets.newHashSet(
            "name",
            "url",
            "email",
            "description",
            "type",
            "format",
            "collectionFormat",
            "maximum",
            "exclusiveMaximum",
            "minimum",
            "exclusiveMinimum",
            "maxLength",
            "minLength",
            "pattern",
            "maxItems",
            "minItems",
            "uniqueItems",
            "multipleOf",
            "title",
            "termsOfService",
            "contact",
            "version",
            "summary",
            "operationId",
            "deprecated",
            "in",
            "required",
            "allowEmptyValue",
            "$ref",
            "swagger",
            "host",
            "basePath",
            "discriminator",
            "readOnly",
            "flow",
            "authorizationUrl",
            "tokenUrl",
            "namespace",
            "prefix",
            "attribute",
            "wrapped"
    );

    public static FieldType fromFieldName(final String fieldName) {
        if (ARRAY_FIELDS.contains(fieldName)) {
            return ARRAY;
        } else if (OBJECT_FIELDS.contains(fieldName)) {
            return OBJECT;
        } else {
            return STRING;
        }
    }
}
