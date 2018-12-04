package org.zalando.intellij.swagger.documentation;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public abstract class ApiDocumentProvider extends AbstractDocumentationProvider {

  protected String handleParameterReference(final PsiElement targetElement) {
    final Optional<String> name =
        getUnquotedFieldValue(targetElement, "name").map(n -> "name: " + n);

    final String inValue = getUnquotedFieldValue(targetElement, "in").orElse("undefined location");
    final String in = String.format("in: %s", inValue);

    final Optional<String> description = getUnquotedFieldValue(targetElement, "description");

    return toHtml(Stream.of(name, Optional.of(in), description));
  }

  protected String handleResponseReference(final PsiElement targetElement) {
    final Optional<String> description = getUnquotedFieldValue(targetElement, "description");

    return toHtml(Stream.of(description));
  }

  protected String handleSchemaReference(
      final PsiElement targetElement, final PsiElement originalElement) {
    final Optional<String> type = getType(targetElement, originalElement);
    final Optional<String> title = getUnquotedFieldValue(targetElement, "title");
    final Optional<String> description = getUnquotedFieldValue(targetElement, "description");

    return toHtml(Stream.of(type, title, description));
  }

  private Optional<String> getType(
      final PsiElement targetElement, final PsiElement originalElement) {
    final String unquotedText = StringUtil.unquoteString(originalElement.getText());

    final Optional<String> refName =
        Optional.of(StringUtils.substringAfterLast(unquotedText, "/"))
            .map(v -> v.isEmpty() ? unquotedText : v);

    return refName.map(
        name -> {
          String type = getUnquotedFieldValue(targetElement, "type").orElse("undefined type");
          return String.format("%s: %s", name, type);
        });
  }

  protected String toHtml(Stream<Optional<String>> streamOfItems) {
    final Optional<String> doc =
        streamOfItems
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce((left, right) -> left + "<br/>" + right)
            .map(content -> "<div>" + content + "</div>");

    return doc.orElse(null);
  }

  protected Optional<String> getUnquotedFieldValue(
      final PsiElement targetElement, final String fieldName) {
    return new PathFinder()
        .findByPathFrom(fieldName, targetElement)
        .map(PsiElement::getLastChild)
        .map(PsiElement::getText)
        .map(StringUtil::unquoteString);
  }
}
