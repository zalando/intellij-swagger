package org.zalando.intellij.swagger.index.openapi;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.FileBasedIndex;
import org.zalando.intellij.swagger.file.OpenApiFileType;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

public class OpenApiIndexService {

    public boolean isMainOpenApiFile(final VirtualFile virtualFile, final Project project) {
        final FileBasedIndex index = FileBasedIndex.getInstance();
        final Collection<VirtualFile> openApiFiles =
                index.getContainingFiles(OpenApiFileIndex.OPEN_API_INDEX_ID,
                        OpenApiFileIndex.MAIN_OPEN_API_FILE, GlobalSearchScope.allScope(project));

        return openApiFiles.contains(virtualFile);
    }

    public boolean isPartialOpenApiFile(final VirtualFile virtualFile, final Project project) {
        final Set<VirtualFile> partialOpenApiFiles = getPartialOpenApiFiles(project);
        return partialOpenApiFiles.contains(virtualFile);
    }

    public OpenApiFileType getOpenApiFileType(final VirtualFile virtualFile, final Project project) {
        final Set<String> partialOpenApiFilesWithTypeInfo = getPartialOpenApiFilesWithTypeInfo(project);

        final Collection<VirtualFile> mainOpenApiFiles =
                FileBasedIndex.getInstance().getContainingFiles(
                        OpenApiFileIndex.OPEN_API_INDEX_ID, OpenApiFileIndex.MAIN_OPEN_API_FILE,
                        GlobalSearchScope.allScope(project));

        if (mainOpenApiFiles.isEmpty()) {
            return OpenApiFileType.UNDEFINED;
        }

        final VirtualFile mainOpenApiFileFolder = mainOpenApiFiles.iterator().next().getParent();

        return partialOpenApiFilesWithTypeInfo.stream()
                .filter(nameWithTypeInfo -> {
                    final VirtualFile foundFile =
                            mainOpenApiFileFolder.findFileByRelativePath(substringBeforeLast(nameWithTypeInfo, OpenApiDataIndexer.DELIMITER));
                    return virtualFile.equals(foundFile);
                })
                .findFirst()
                .map(nameWithTypeInfo -> substringAfterLast(nameWithTypeInfo, OpenApiDataIndexer.DELIMITER))
                .map(OpenApiFileType::valueOf)
                .orElse(OpenApiFileType.UNDEFINED);
    }

    private Set<VirtualFile> getPartialOpenApiFiles(final Project project) {
        final Set<String> partialOpenApiFilesWithTypeInfo = getPartialOpenApiFilesWithTypeInfo(project);

        final Collection<VirtualFile> mainOpenApiFiles =
                FileBasedIndex.getInstance().getContainingFiles(
                        OpenApiFileIndex.OPEN_API_INDEX_ID,
                        OpenApiFileIndex.MAIN_OPEN_API_FILE, GlobalSearchScope.allScope(project));

        if (mainOpenApiFiles.isEmpty()) {
            return new HashSet<>();
        }

        final VirtualFile mainOpenApiFileFolder = mainOpenApiFiles.iterator().next().getParent();

        return partialOpenApiFilesWithTypeInfo.stream()
                .map(v -> substringBeforeLast(v, OpenApiDataIndexer.DELIMITER))
                .map(mainOpenApiFileFolder::findFileByRelativePath)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Set<String> getPartialOpenApiFilesWithTypeInfo(final Project project) {
        final FileBasedIndex index = FileBasedIndex.getInstance();
        return index.getValues(OpenApiFileIndex.OPEN_API_INDEX_ID,
                OpenApiFileIndex.PARTIAL_OPEN_API_FILES, GlobalSearchScope.allScope(project))
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
