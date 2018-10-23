package org.zalando.intellij.swagger.examples.extensions.zalando.validator;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(name = "SwaggerSetting", storages = @Storage("intellij-swagger.xml"))
public class DefaultZallySettings
    implements PersistentStateComponent<DefaultZallySettings>, ZallySettings {

  public String zallyUrl;

  @Nullable
  @Override
  public DefaultZallySettings getState() {
    return this;
  }

  @Override
  public void loadState(DefaultZallySettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  @Override
  public String getZallyUrl() {
    return zallyUrl;
  }

  @Override
  public void setZallyUrl(String zallyUrl) {
    this.zallyUrl = zallyUrl;
  }
}
