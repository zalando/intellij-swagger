package org.zalando.intellij.swagger.validator.field;

import com.google.common.collect.ImmutableSet;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.field.model.common.Field;

import java.util.List;
import java.util.Set;

public class UnknownJsonKeyValidator extends UnknownKeyValidator {

    private static final String VENDOR_EXTENSION_PREFIX = "x-";
    private static final Set<String> IGNORED_KEY_PREFIXES = ImmutableSet.of(VENDOR_EXTENSION_PREFIX);

    public UnknownJsonKeyValidator(final IntentionAction intentionAction) {
        super(intentionAction);
    }

    @Override
    protected boolean isInvalid(final String key, final List<Field> availableKeys) {
        return availableKeys.stream().noneMatch(field -> field.getName().equals(key));
    }

    @Override
    protected boolean shouldIgnore(final String key, final PsiElement element) {
        return IGNORED_KEY_PREFIXES.stream().anyMatch(key::startsWith);
    }

}
