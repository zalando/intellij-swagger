package org.zalando.intellij.swagger.examples.extensions.zalando.value.completion.swagger;

import com.google.common.collect.ImmutableList;
import org.zalando.intellij.swagger.completion.value.model.common.StringValue;
import org.zalando.intellij.swagger.completion.value.model.common.Value;

import java.util.List;

public class SwaggerValues {

    public static List<Value> audience() {
        return ImmutableList.of(
                new StringValue("component-internal"),
                new StringValue("business-unit-internal"),
                new StringValue("company-internal"),
                new StringValue("external-partner"),
                new StringValue("external-public")
        );
    }
}
