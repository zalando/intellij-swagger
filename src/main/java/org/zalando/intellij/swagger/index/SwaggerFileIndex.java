package org.zalando.intellij.swagger.index;

import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileBasedIndexExtension;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.ID;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Set;

public class SwaggerFileIndex extends FileBasedIndexExtension<String, Set<String>> {

    static final String MAIN_SWAGGER_FILE = "MAIN_SWAGGER_FILE";
    static final String PARTIAL_SWAGGER_FILES = "PARTIAL_SWAGGER_FILES";

    static final ID<String, Set<String>> SWAGGER_INDEX_ID = ID.create("SwaggerFileIndex");
    private static final int VERSION = 1;

    @NotNull
    @Override
    public ID<String, Set<String>> getName() {
        return SWAGGER_INDEX_ID;
    }

    @NotNull
    @Override
    public DataIndexer<String, Set<String>, FileContent> getIndexer() {
        return new SwaggerDataIndexer();
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    @NotNull
    @Override
    public DataExternalizer<Set<String>> getValueExternalizer() {
        return DATA_EXTERNALIZER;
    }

    private static final DataExternalizer<Set<String>> DATA_EXTERNALIZER = new DataExternalizer<Set<String>>() {
        @Override
        public void save(@NotNull DataOutput out, Set<String> value) throws IOException {
            out.writeInt(value.size());
            for (String s : value) {
                out.writeUTF(s);
            }
        }

        @Override
        public Set<String> read(@NotNull DataInput in) throws IOException {
            final int size = in.readInt();

            if (size < 0 || size > 65535) { // 65K: maximum number of resources for a given type
                // Something is very wrong (corrupt index); trigger an index rebuild.
                throw new IOException("Corrupt Index: Size " + size);
            }

            final Set<String> result = new HashSet<String>(size);

            for (int i = 0; i < size; i++) {
                final String s = in.readUTF();
                result.add(s);
            }
            return result;
        }
    };

    @Override
    public int getVersion() {
        return VERSION;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return file -> file.getName().endsWith("json")
                || file.getName().endsWith("yaml")
                || file.getName().endsWith("yml");
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

}
