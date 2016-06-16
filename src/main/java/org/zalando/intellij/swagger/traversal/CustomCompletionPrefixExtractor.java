package org.zalando.intellij.swagger.traversal;

import static com.intellij.patterns.StandardPatterns.character;
import com.intellij.codeInsight.completion.CompletionData;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;

import java.util.Optional;

public class CustomCompletionPrefixExtractor {

    private static final ElementPattern<Character> NOT_REF_VALUE_CHARACTER =
            CompletionData.NOT_JAVA_ID.andNot(character().oneOf('/', '#'));

    private final Traversal traversal;

    public CustomCompletionPrefixExtractor(final Traversal traversal) {
        this.traversal = traversal;
    }

    /**
     * By default platform considers all the {@link Character#isJavaIdentifierPart(char)} characters at the left
     * of caret as a prefix. Sometimes this has to be overridden (see #25 for the requirement to include also
     * '/' and '#'). This method allows to override standard behavior for specific locations.
     */
    final public Optional<String> getPrefix(PsiElement psiElement, int caretOffsetInFile) {
        if (!traversal.isDefinitionRefValue(psiElement) &&
                !traversal.isParameterRefValue(psiElement) &&
                !traversal.isResponseRefValue(psiElement)) {
            return Optional.empty();
        }
        String result = CompletionData.findPrefixStatic(psiElement, caretOffsetInFile, NOT_REF_VALUE_CHARACTER);
        return Optional.ofNullable(result);
    }


}
