import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.zalando.intellij.swagger.file.icon.Icons;

public class CreateSwaggerFile extends CreateFileFromTemplateAction implements DumbAware {

  public CreateSwaggerFile() {
    super(
        "Swagger/OpenAPI File",
        "Create a Swagger or an OpenAPI file from the specified template",
        Icons.SWAGGER_API_ICON);
  }

  @Override
  protected void buildDialog(
      Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
    builder
        .setTitle("New API Specification")
        .addKind("Swagger file (YAML)", Icons.SWAGGER_API_ICON, "Swagger File (YAML).yaml")
        .addKind("Swagger file (JSON)", Icons.SWAGGER_API_ICON, "Swagger File (JSON).json")
        .addKind("OpenAPI file (YAML)", Icons.OPEN_API_ICON, "OpenAPI File (YAML).yaml")
        .addKind("OpenAPI file (JSON)", Icons.OPEN_API_ICON, "OpenAPI File (JSON).json");
  }

  @Override
  protected String getActionName(PsiDirectory directory, String newName, String templateName) {
    return "Create API Specification " + newName;
  }
}
