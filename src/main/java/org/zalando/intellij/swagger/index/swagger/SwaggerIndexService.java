package org.zalando.intellij.swagger.index.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.FileBasedIndex;
import org.zalando.intellij.swagger.file.SwaggerFileType;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

public class SwaggerIndexService {

    public boolean isMainSwaggerFile(final VirtualFile virtualFile, final Project project) {
        final FileBasedIndex index = FileBasedIndex.getInstance();
        final Collection<VirtualFile> swaggerFiles =
                index.getContainingFiles(SwaggerFileIndex.SWAGGER_INDEX_ID,
                        SwaggerFileIndex.MAIN_SWAGGER_FILE, GlobalSearchScope.allScope(project));
        return swaggerFiles.contains(virtualFile);
    }

    public boolean isPartialSwaggerFile(final VirtualFile virtualFile, final Project project) {
        final Set<VirtualFile> partialSwaggerFiles = getPartialSwaggerFiles(project);
        return partialSwaggerFiles.contains(virtualFile);
    }

    public SwaggerFileType getSwaggerFileType(final VirtualFile virtualFile, final Project project) {
        final Set<String> partialSwaggerFilesWithTypeInfo = getPartialSwaggerFilesWithTypeInfo(project);

        final Collection<VirtualFile> mainSwaggerFiles =
                FileBasedIndex.getInstance().getContainingFiles(
                        SwaggerFileIndex.SWAGGER_INDEX_ID, SwaggerFileIndex.MAIN_SWAGGER_FILE,
                        GlobalSearchScope.allScope(project));

        if (mainSwaggerFiles.isEmpty()) {
            return SwaggerFileType.UNDEFINED;
        }

        final VirtualFile mainSwaggerFileFolder = mainSwaggerFiles.iterator().next().getParent();

        return partialSwaggerFilesWithTypeInfo.stream()
                .filter(nameWithTypeInfo -> {
                    final VirtualFile foundFile =
                            mainSwaggerFileFolder.findFileByRelativePath(substringBeforeLast(nameWithTypeInfo, SwaggerDataIndexer.DELIMITER));
                    return virtualFile.equals(foundFile);
                })
                .findFirst()
                .map(nameWithTypeInfo -> substringAfterLast(nameWithTypeInfo, SwaggerDataIndexer.DELIMITER))
                .map(SwaggerFileType::valueOf)
                .orElse(SwaggerFileType.UNDEFINED);
    }

    private Set<VirtualFile> getPartialSwaggerFiles(final Project project) {
        final Set<String> partialSwaggerFilesWithTypeInfo = getPartialSwaggerFilesWithTypeInfo(project);

        final Collection<VirtualFile> mainSwaggerFiles =
                FileBasedIndex.getInstance().getContainingFiles(
                        SwaggerFileIndex.SWAGGER_INDEX_ID,
                        SwaggerFileIndex.MAIN_SWAGGER_FILE, GlobalSearchScope.allScope(project));

        if (mainSwaggerFiles.isEmpty()) {
            return new HashSet<>();
        }

        final VirtualFile mainSwaggerFileFolder = mainSwaggerFiles.iterator().next().getParent();

        return partialSwaggerFilesWithTypeInfo.stream()
                .map(v -> substringBeforeLast(v, SwaggerDataIndexer.DELIMITER))
                .map(mainSwaggerFileFolder::findFileByRelativePath)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<String> getPartialSwaggerFilesWithTypeInfo(final Project project) {
        final FileBasedIndex index = FileBasedIndex.getInstance();
        return index.getValues(SwaggerFileIndex.SWAGGER_INDEX_ID,
                SwaggerFileIndex.PARTIAL_SWAGGER_FILES, GlobalSearchScope.allScope(project))
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
