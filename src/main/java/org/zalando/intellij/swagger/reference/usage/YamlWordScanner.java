package org.zalando.intellij.swagger.reference.usage;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.yaml.YAMLTokenTypes;
import org.jetbrains.yaml.lexer.YAMLFlexLexer;


class YamlWordScanner extends DefaultWordsScanner {

    public YamlWordScanner() {
        super(new YAMLFlexLexer(), TokenSet.create(YAMLTokenTypes.SCALAR_KEY),
                TokenSet.create(YAMLTokenTypes.COMMENT),
                TokenSet.create(YAMLTokenTypes.SCALAR_STRING, YAMLTokenTypes.SCALAR_DSTRING, YAMLTokenTypes.TEXT));
        setMayHaveFileRefsInLiterals(true);
    }
}
