package org.zalando.intellij.swagger.index.openapi;

import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Set;

public class OpenApiFileIndex extends FileBasedIndexExtension<String, Set<String>> {

    static final String MAIN_OPEN_API_FILE = "MAIN_OPEN_API_FILE";
    static final String PARTIAL_OPEN_API_FILES = "PARTIAL_OPEN_API_FILES";

    static final ID<String, Set<String>> OPEN_API_INDEX_ID = ID.create("OpenApiFileIndex");
    private static final int VERSION = 1;

    private final FileDetector fileDetector = new FileDetector();

    @NotNull
    @Override
    public ID<String, Set<String>> getName() {
        return OPEN_API_INDEX_ID;
    }

    @NotNull
    @Override
    public DataIndexer<String, Set<String>, FileContent> getIndexer() {
        return new OpenApiDataIndexer();
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

            if (size < 0) {
                // Something is very wrong (corrupt index); trigger an index rebuild.
                throw new IOException("Corrupt Index: Size " + size);
            }

            final Set<String> result = new HashSet<>(size);

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
        return fileDetector::isSwaggerContentCompatible;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

}
