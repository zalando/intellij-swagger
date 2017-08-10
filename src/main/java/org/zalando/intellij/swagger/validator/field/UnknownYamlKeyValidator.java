package org.zalando.intellij.swagger.validator.field;

import com.google.common.collect.ImmutableSet;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

import java.util.List;
import java.util.Set;

public class UnknownYamlKeyValidator extends UnknownKeyValidator {

    private static final String VENDOR_EXTENSION_PREFIX = "x-";
    private static final String MERGE_KEY = "<<";

    private static final Set<String> IGNORED_KEY_PREFIXES = ImmutableSet.of(VENDOR_EXTENSION_PREFIX);
    private static final Set<String> IGNORED_KEYS = ImmutableSet.of(MERGE_KEY);

    private final YamlTraversal yamlTraversal;

    public UnknownYamlKeyValidator(final IntentionAction intentionAction, final YamlTraversal yamlTraversal) {
        super(intentionAction);
        this.yamlTraversal = yamlTraversal;
    }

    @Override
    protected boolean isInvalid(final String key, final List<Field> availableKeys) {
        return availableKeys.stream().noneMatch(field -> field.getName().equals(key));
    }

    @Override
    protected boolean shouldIgnore(final String key, final PsiElement element) {
        return IGNORED_KEY_PREFIXES.stream().anyMatch(key::startsWith) ||
                IGNORED_KEYS.stream().anyMatch(key::equals) ||
                yamlTraversal.isAnchorKey(element) ||
                yamlTraversal.isChildOfAnchorKey(element);
    }

}
