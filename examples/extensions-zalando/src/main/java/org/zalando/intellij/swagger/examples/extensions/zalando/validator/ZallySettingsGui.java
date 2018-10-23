package org.zalando.intellij.swagger.examples.extensions.zalando.validator;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import java.awt.*;
import javax.swing.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZallySettingsGui implements SearchableConfigurable {

  private JTextField zallyUrlTextField = new JTextField(50);

  @NotNull
  @Override
  public String getId() {
    return "Zally";
  }

  @Nullable
  @Override
  public Runnable enableSearch(String option) {
    return null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "Zally";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "Zally";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JPanel zallyUrlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    zallyUrlPanel.add(new JLabel("Zally API URL: "));
    zallyUrlPanel.add(zallyUrlTextField);
    zallyUrlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(zallyUrlPanel);

    return panel;
  }

  @Override
  public boolean isModified() {
    final ZallySettings settings = ServiceManager.getService(ZallySettings.class);

    return (settings.getZallyUrl() != null
            && !settings.getZallyUrl().equals(zallyUrlTextField.getText()))
        || settings.getZallyUrl() == null && zallyUrlTextField.getText() != null;
  }

  @Override
  public void apply() throws ConfigurationException {
    final ZallySettings settings = ServiceManager.getService(ZallySettings.class);

    settings.setZallyUrl(zallyUrlTextField.getText());
  }

  @Override
  public void reset() {
    final ZallySettings settings = ServiceManager.getService(ZallySettings.class);

    zallyUrlTextField.setText(settings.getZallyUrl());
  }

  @Override
  public void disposeUIResources() {}
}
