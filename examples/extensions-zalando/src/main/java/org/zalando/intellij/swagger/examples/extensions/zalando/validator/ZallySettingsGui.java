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

  private JTextField zallyUrlTextField = new JTextField(30);
  private JTextField ztokenPathTextField = new JTextField(30);

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
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel zallyLabel = new JLabel("Zally API URL:");
    zallyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel ztokenLabel = new JLabel("Ztoken path:");
    ztokenLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(zallyLabel);
    panel.add(zallyUrlTextField);
    zallyUrlTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
    zallyUrlTextField.setMaximumSize(zallyUrlTextField.getPreferredSize());

    panel.add(ztokenLabel);
    panel.add(ztokenPathTextField);
    ztokenPathTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
    ztokenPathTextField.setMaximumSize(ztokenPathTextField.getPreferredSize());

    return panel;
  }

  @Override
  public boolean isModified() {
    final ZallySettings settings = ServiceManager.getService(ZallySettings.class);

    return zallyUrlUpdated(settings)
        || zallyUrlCreated(settings)
        || ztokenPathUpdated(settings)
        || ztokenPathCreated(settings);
  }

  private boolean zallyUrlUpdated(final ZallySettings settings) {
    return settings.getZallyUrl() != null
        && !settings.getZallyUrl().equals(zallyUrlTextField.getText());
  }

  private boolean zallyUrlCreated(final ZallySettings settings) {
    return settings.getZallyUrl() == null && zallyUrlTextField.getText() != null;
  }

  private boolean ztokenPathUpdated(final ZallySettings settings) {
    return settings.getZtokenPath() != null
        && !settings.getZtokenPath().equals(ztokenPathTextField.getText());
  }

  private boolean ztokenPathCreated(final ZallySettings settings) {
    return settings.getZtokenPath() == null && ztokenPathTextField.getText() != null;
  }

  @Override
  public void apply() throws ConfigurationException {
    final ZallySettings settings = ServiceManager.getService(ZallySettings.class);

    settings.setZallyUrl(zallyUrlTextField.getText());
    settings.setZtokenPath(ztokenPathTextField.getText());
  }

  @Override
  public void reset() {
    final ZallySettings settings = ServiceManager.getService(ZallySettings.class);

    zallyUrlTextField.setText(settings.getZallyUrl());
    ztokenPathTextField.setText(settings.getZtokenPath());
  }

  @Override
  public void disposeUIResources() {}
}
