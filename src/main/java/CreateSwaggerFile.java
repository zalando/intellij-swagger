import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.json.JsonFileType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.yaml.YAMLFileType;

public class CreateSwaggerFile extends CreateFileFromTemplateAction implements DumbAware {

  public CreateSwaggerFile() {
    super(
        "Swagger/OpenAPI File",
        "Create a Swagger or an OpenAPI file from the specified template",
        null);
  }

  @Override
  protected void buildDialog(
      Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
    builder
        .setTitle("New API Specification")
        .addKind("Swagger file (YAML)", YAMLFileType.YML.getIcon(), "Swagger File (YAML).yaml")
        .addKind("Swagger file (JSON)", JsonFileType.INSTANCE.getIcon(), "Swagger File (JSON).json")
        .addKind("OpenAPI file (YAML)", YAMLFileType.YML.getIcon(), "OpenAPI File (YAML).yaml")
        .addKind(
            "OpenAPI file (JSON)", JsonFileType.INSTANCE.getIcon(), "OpenAPI File (JSON).json");
  }

  @Override
  protected String getActionName(PsiDirectory directory, String newName, String templateName) {
    return "Create API Specification " + newName;
  }
}
