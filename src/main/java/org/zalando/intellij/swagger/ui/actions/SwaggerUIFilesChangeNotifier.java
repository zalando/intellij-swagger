package org.zalando.intellij.swagger.ui.actions;

import com.intellij.util.Url;
import com.intellij.util.messages.Topic;

public interface SwaggerUIFilesChangeNotifier {

    Topic<SwaggerUIFilesChangeNotifier> SWAGGER_UI_FILES_CHANGED = Topic.create("Swagger UI Files", SwaggerUIFilesChangeNotifier.class);

    void swaggerHTMLFilesChanged(Url indexUrl);
}
