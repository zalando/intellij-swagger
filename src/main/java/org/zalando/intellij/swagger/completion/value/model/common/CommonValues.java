package org.zalando.intellij.swagger.completion.value.model.common;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class CommonValues {

    public static List<Value> types() {
        return ImmutableList.of(
                new StringValue("object"),
                new StringValue("string"),
                new StringValue("number"),
                new StringValue("integer"),
                new StringValue("boolean"),
                new StringValue("array")
        );
    }

    public static List<Value> booleans() {
        return ImmutableList.of(
                new BooleanValue("true"),
                new BooleanValue("false")
        );
    }

    public static List<Value> formats() {
        return ImmutableList.of(
                new StringValue("int32"),
                new StringValue("int64"),
                new StringValue("float"),
                new StringValue("double"),
                new StringValue("byte"),
                new StringValue("binary"),
                new StringValue("date"),
                new StringValue("date-time"),
                new StringValue("password"),
                new StringValue("email"),
                new StringValue("uuid")
        );
    }
}
