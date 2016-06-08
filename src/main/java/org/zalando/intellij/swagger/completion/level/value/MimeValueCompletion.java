package org.zalando.intellij.swagger.completion.level.value;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import com.google.common.net.MediaType;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.ArrayList;
import java.util.List;

class MimeValueCompletion extends ValueCompletion {

    private static final List<String> MEDIA_TYPES = new ArrayList<>();

    static {
        MEDIA_TYPES.add(MediaType.ANY_TYPE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ANY_TYPE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ANY_TEXT_TYPE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ANY_IMAGE_TYPE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ANY_AUDIO_TYPE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ANY_VIDEO_TYPE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ANY_APPLICATION_TYPE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.CACHE_MANIFEST_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.CSS_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.CSV_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.HTML_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.I_CALENDAR_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.PLAIN_TEXT_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.TEXT_JAVASCRIPT_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.TSV_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.VCARD_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.WML_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.XML_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.BMP.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.CRW.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.GIF.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ICO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.JPEG.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.PNG.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.PSD.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.SVG_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.TIFF.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.WEBP.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MP4_AUDIO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MPEG_AUDIO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OGG_AUDIO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.WEBM_AUDIO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MP4_VIDEO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MPEG_VIDEO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OGG_VIDEO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.QUICKTIME.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.WEBM_VIDEO.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.WMV.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.APPLICATION_XML_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ATOM_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.BZIP2.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.EOT.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.EPUB.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.FORM_DATA.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.KEY_ARCHIVE.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.APPLICATION_BINARY.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.GZIP.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.JAVASCRIPT_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.JSON_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.KML.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.KMZ.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MBOX.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MICROSOFT_EXCEL.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MICROSOFT_POWERPOINT.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.MICROSOFT_WORD.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OCTET_STREAM.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OGG_CONTAINER.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OOXML_DOCUMENT.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OOXML_PRESENTATION.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OOXML_SHEET.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OPENDOCUMENT_GRAPHICS.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OPENDOCUMENT_PRESENTATION.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OPENDOCUMENT_SPREADSHEET.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.OPENDOCUMENT_TEXT.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.PDF.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.POSTSCRIPT.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.PROTOBUF.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.RDF_XML_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.RTF_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.SFNT.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.SHOCKWAVE_FLASH.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.SKETCHUP.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.TAR.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.WOFF.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.XHTML_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.XRD_UTF_8.withoutParameters().toString());
        MEDIA_TYPES.add(MediaType.ZIP.withoutParameters().withoutParameters().toString());
    }

    MimeValueCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        MEDIA_TYPES.stream().forEach(mediaType ->
                result.addElement(create(mediaType, optional(positionResolver))));

    }
}
