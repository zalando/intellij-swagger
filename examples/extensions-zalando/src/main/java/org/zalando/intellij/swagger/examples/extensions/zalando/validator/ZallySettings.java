package org.zalando.intellij.swagger.examples.extensions.zalando.validator;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(name = "SwaggerSetting",
        storages = @Storage("intellij-swagger.xml"))
public class ZallySettings implements PersistentStateComponent<ZallySettings> {

    public String zallyUrl;

    @Nullable
    @Override
    public ZallySettings getState() {
        return this;
    }

    @Override
    public void loadState(ZallySettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
