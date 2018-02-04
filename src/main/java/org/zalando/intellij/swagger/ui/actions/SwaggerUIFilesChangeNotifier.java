package org.zalando.intellij.swagger.ui.actions;

import com.intellij.openapi.vfs.*;
import com.intellij.util.*;
import com.intellij.util.messages.*;

public interface SwaggerUIFilesChangeNotifier {

    Topic<SwaggerUIFilesChangeNotifier> SWAGGER_UI_FILES_CHANGED = Topic.create("Swagger UI Files", SwaggerUIFilesChangeNotifier.class);

    void swaggerHTMLFilesChanged(VirtualFile updatedFile, Url indexUrl);
}
