package org.zalando.intellij.swagger.completion.value.model;

import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;

import java.util.List;

public class Values {

    public static List<Value> booleans() {
        return ImmutableList.of(
                new BooleanValue("true"),
                new BooleanValue("false")
        );
    }

    public static List<Value> in() {
        return ImmutableList.of(
                new StringValue("query"),
                new StringValue("header"),
                new StringValue("path"),
                new StringValue("formData"),
                new StringValue("body")
        );
    }

    public static List<Value> schemes() {
        return ImmutableList.of(
                new StringValue("http"),
                new StringValue("https"),
                new StringValue("ws"),
                new StringValue("wss")
        );
    }

    public static List<Value> types() {
        return ImmutableList.of(
                new StringValue("string"),
                new StringValue("number"),
                new StringValue("integer"),
                new StringValue("boolean"),
                new StringValue("array")
        );
    }

    public static List<Value> mimeTypes() {
        return ImmutableList.of(
                new StringValue(MediaType.ANY_TYPE.withoutParameters().toString()),
                new StringValue(MediaType.ANY_TYPE.withoutParameters().toString()),
                new StringValue(MediaType.ANY_TEXT_TYPE.withoutParameters().toString()),
                new StringValue(MediaType.ANY_IMAGE_TYPE.withoutParameters().toString()),
                new StringValue(MediaType.ANY_AUDIO_TYPE.withoutParameters().toString()),
                new StringValue(MediaType.ANY_VIDEO_TYPE.withoutParameters().toString()),
                new StringValue(MediaType.ANY_APPLICATION_TYPE.withoutParameters().toString()),
                new StringValue(MediaType.CACHE_MANIFEST_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.CSS_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.CSV_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.HTML_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.I_CALENDAR_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.PLAIN_TEXT_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.TEXT_JAVASCRIPT_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.TSV_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.VCARD_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.WML_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.XML_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.BMP.withoutParameters().toString()),
                new StringValue(MediaType.CRW.withoutParameters().toString()),
                new StringValue(MediaType.GIF.withoutParameters().toString()),
                new StringValue(MediaType.ICO.withoutParameters().toString()),
                new StringValue(MediaType.JPEG.withoutParameters().toString()),
                new StringValue(MediaType.PNG.withoutParameters().toString()),
                new StringValue(MediaType.PSD.withoutParameters().toString()),
                new StringValue(MediaType.SVG_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.TIFF.withoutParameters().toString()),
                new StringValue(MediaType.WEBP.withoutParameters().toString()),
                new StringValue(MediaType.MP4_AUDIO.withoutParameters().toString()),
                new StringValue(MediaType.MPEG_AUDIO.withoutParameters().toString()),
                new StringValue(MediaType.OGG_AUDIO.withoutParameters().toString()),
                new StringValue(MediaType.WEBM_AUDIO.withoutParameters().toString()),
                new StringValue(MediaType.MP4_VIDEO.withoutParameters().toString()),
                new StringValue(MediaType.MPEG_VIDEO.withoutParameters().toString()),
                new StringValue(MediaType.OGG_VIDEO.withoutParameters().toString()),
                new StringValue(MediaType.QUICKTIME.withoutParameters().toString()),
                new StringValue(MediaType.WEBM_VIDEO.withoutParameters().toString()),
                new StringValue(MediaType.WMV.withoutParameters().toString()),
                new StringValue(MediaType.APPLICATION_XML_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.ATOM_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.BZIP2.withoutParameters().toString()),
                new StringValue(MediaType.EOT.withoutParameters().toString()),
                new StringValue(MediaType.EPUB.withoutParameters().toString()),
                new StringValue(MediaType.FORM_DATA.withoutParameters().toString()),
                new StringValue(MediaType.KEY_ARCHIVE.withoutParameters().toString()),
                new StringValue(MediaType.APPLICATION_BINARY.withoutParameters().toString()),
                new StringValue(MediaType.GZIP.withoutParameters().toString()),
                new StringValue(MediaType.JAVASCRIPT_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.JSON_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.KML.withoutParameters().toString()),
                new StringValue(MediaType.KMZ.withoutParameters().toString()),
                new StringValue(MediaType.MBOX.withoutParameters().toString()),
                new StringValue(MediaType.MICROSOFT_EXCEL.withoutParameters().toString()),
                new StringValue(MediaType.MICROSOFT_POWERPOINT.withoutParameters().toString()),
                new StringValue(MediaType.MICROSOFT_WORD.withoutParameters().toString()),
                new StringValue(MediaType.OCTET_STREAM.withoutParameters().toString()),
                new StringValue(MediaType.OGG_CONTAINER.withoutParameters().toString()),
                new StringValue(MediaType.OOXML_DOCUMENT.withoutParameters().toString()),
                new StringValue(MediaType.OOXML_PRESENTATION.withoutParameters().toString()),
                new StringValue(MediaType.OOXML_SHEET.withoutParameters().toString()),
                new StringValue(MediaType.OPENDOCUMENT_GRAPHICS.withoutParameters().toString()),
                new StringValue(MediaType.OPENDOCUMENT_PRESENTATION.withoutParameters().toString()),
                new StringValue(MediaType.OPENDOCUMENT_SPREADSHEET.withoutParameters().toString()),
                new StringValue(MediaType.OPENDOCUMENT_TEXT.withoutParameters().toString()),
                new StringValue(MediaType.PDF.withoutParameters().toString()),
                new StringValue(MediaType.POSTSCRIPT.withoutParameters().toString()),
                new StringValue(MediaType.PROTOBUF.withoutParameters().toString()),
                new StringValue(MediaType.RDF_XML_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.RTF_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.SFNT.withoutParameters().toString()),
                new StringValue(MediaType.SHOCKWAVE_FLASH.withoutParameters().toString()),
                new StringValue(MediaType.SKETCHUP.withoutParameters().toString()),
                new StringValue(MediaType.TAR.withoutParameters().toString()),
                new StringValue(MediaType.WOFF.withoutParameters().toString()),
                new StringValue(MediaType.XHTML_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.XRD_UTF_8.withoutParameters().toString()),
                new StringValue(MediaType.ZIP.withoutParameters().withoutParameters().toString())
        );
    }
}
