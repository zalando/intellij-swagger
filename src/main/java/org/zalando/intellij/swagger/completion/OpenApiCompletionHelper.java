package org.zalando.intellij.swagger.completion;

import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.traversal.Traversal;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolver;

public class OpenApiCompletionHelper extends CompletionHelper {

  private final PathResolver pathResolver;

  public OpenApiCompletionHelper(
      final PsiElement psiElement, final Traversal traversal, final PathResolver pathResolver) {
    super(psiElement, traversal);
    this.pathResolver = pathResolver;
  }

  public boolean completeRootKey() {
    return pathResolver.childOfRoot(psiElement);
  }

  public boolean completeInfoKey() {
    return pathResolver.childOfInfo(psiElement);
  }

  public boolean completeContactKey() {
    return pathResolver.childOfContact(psiElement);
  }

  public boolean completeLicenseKey() {
    return pathResolver.childOfLicense(psiElement);
  }

  public boolean completePathKey() {
    return pathResolver.childOfPath(psiElement);
  }

  public boolean completeOperationKey() {
    return pathResolver.childOfOperation(psiElement);
  }

  public boolean completeExternalDocsKey() {
    return pathResolver.childOfExternalDocs(psiElement);
  }

  public boolean completeParametersKey() {
    return pathResolver.childOfParameters(psiElement);
  }

  public boolean completeResponsesKey() {
    return pathResolver.childOfResponses(psiElement);
  }

  public boolean completeResponseKey() {
    return pathResolver.childOfResponse(psiElement);
  }

  public boolean completeHeaderKey() {
    return pathResolver.childOfHeader(psiElement);
  }

  public boolean completeTagKey() {
    return pathResolver.childOfTag(psiElement);
  }

  public boolean completeSchemaKey() {
    return pathResolver.childOfSchema(psiElement);
  }

  public boolean completeSchemaRefValue() {
    return pathResolver.isSchemaRefValue(psiElement);
  }

  public boolean completeParameterRefValue() {
    return pathResolver.isParameterRefValue(psiElement);
  }

  public boolean completeExampleRefValue() {
    return pathResolver.isExampleRefValue(psiElement);
  }

  public boolean completeRequestBodyRefValue() {
    return pathResolver.isRequestBodyRefValue(psiElement);
  }

  public boolean completeHeaderRefValue() {
    return pathResolver.isHeaderRefValue(psiElement);
  }

  public boolean completeLinkRefValue() {
    return pathResolver.isLinkRefValue(psiElement);
  }

  public boolean completeCallbackRefValue() {
    return pathResolver.isCallbackRefValue(psiElement);
  }

  public boolean completeMappingRefValue() {
    return pathResolver.isMappingRefValue(psiElement);
  }

  public boolean completeBooleanValue() {
    return pathResolver.isBooleanValue(psiElement);
  }

  public boolean completeTypeValue() {
    return pathResolver.isTypeValue(psiElement);
  }

  public boolean completeFormatValue() {
    return pathResolver.isFormatValue(psiElement);
  }

  public boolean completeStyleValue() {
    return pathResolver.isStyleValue(psiElement);
  }

  public boolean completeInValue() {
    return pathResolver.isInValue(psiElement);
  }

  public boolean completeResponseRefValue() {
    return pathResolver.isResponseRefValue(psiElement);
  }

  public boolean completeServerKey() {
    return pathResolver.childOfServer(psiElement);
  }

  public boolean completeServerVariableKey() {
    return pathResolver.childOfServerVariable(psiElement);
  }

  public boolean completeComponentKey() {
    return pathResolver.childOfComponent(psiElement);
  }

  public boolean completeRequestBodyKey() {
    return pathResolver.childOfRequestBody(psiElement);
  }

  public boolean completeMediaTypeKey() {
    return pathResolver.childOfMediaType(psiElement);
  }

  public boolean completeExampleKey() {
    return pathResolver.childOfExample(psiElement);
  }

  public boolean completeEncodingKey() {
    return pathResolver.childOfEncoding(psiElement);
  }

  public boolean completeLinkKey() {
    return pathResolver.childOfLink(psiElement);
  }

  public boolean completeCallbackKey() {
    return pathResolver.childOfCallback(psiElement);
  }

  public boolean completeSecuritySchemeKey() {
    return pathResolver.childOfSecurityScheme(psiElement);
  }

  public boolean completeContentKey() {
    return pathResolver.childOfContent(psiElement);
  }

  public boolean completeDiscriminatorKey() {
    return pathResolver.childOfDiscriminator(psiElement);
  }
}
