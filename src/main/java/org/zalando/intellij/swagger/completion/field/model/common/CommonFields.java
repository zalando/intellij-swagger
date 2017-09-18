package org.zalando.intellij.swagger.completion.field.model.common;

import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;

import java.util.List;

public class CommonFields {

    public static List<Field> info() {
        return ImmutableList.of(
                new StringField("title", true),
                new StringField("description"),
                new StringField("termsOfService"),
                new ContactField(),
                new LicenseField(),
                new StringField("version", true)
        );
    }

    public static List<Field> tag() {
        return ImmutableList.of(
                new StringField("name", true),
                new StringField("description"),
                new ExternalDocsField()
        );
    }

    public static List<Field> externalDocs() {
        return ImmutableList.of(
                new StringField("url", true),
                new StringField("description")
        );
    }

    public static List<Field> contact() {
        return ImmutableList.of(
                new StringField("name"),
                new StringField("url"),
                new StringField("email")
        );
    }

    public static List<Field> license() {
        return ImmutableList.of(
                new StringField("name", true),
                new StringField("url")
        );
    }

    public static List<Field> responses() {
        return ImmutableList.of(
                new ObjectField("default"),
                new ResponseField("200"),
                new ResponseField("201"),
                new ResponseField("202"),
                new ResponseField("203"),
                new ResponseField("204"),
                new ResponseField("205"),
                new ResponseField("206"),
                new ResponseField("207"),
                new ResponseField("208"),
                new ResponseField("226"),

                new ResponseField("300"),
                new ResponseField("301"),
                new ResponseField("302"),
                new ResponseField("303"),
                new ResponseField("304"),
                new ResponseField("305"),
                new ResponseField("306"),
                new ResponseField("307"),
                new ResponseField("308"),

                new ResponseField("400"),
                new ResponseField("401"),
                new ResponseField("402"),
                new ResponseField("403"),
                new ResponseField("404"),
                new ResponseField("405"),
                new ResponseField("406"),
                new ResponseField("407"),
                new ResponseField("408"),
                new ResponseField("409"),
                new ResponseField("410"),
                new ResponseField("411"),
                new ResponseField("412"),
                new ResponseField("413"),
                new ResponseField("414"),
                new ResponseField("415"),
                new ResponseField("416"),
                new ResponseField("417"),
                new ResponseField("418"),
                new ResponseField("421"),
                new ResponseField("422"),
                new ResponseField("423"),
                new ResponseField("424"),
                new ResponseField("426"),
                new ResponseField("428"),
                new ResponseField("429"),
                new ResponseField("431"),
                new ResponseField("451"),

                new ResponseField("500"),
                new ResponseField("501"),
                new ResponseField("502"),
                new ResponseField("503"),
                new ResponseField("504"),
                new ResponseField("505"),
                new ResponseField("506"),
                new ResponseField("507"),
                new ResponseField("508"),
                new ResponseField("510"),
                new ResponseField("511")
        );
    }

    public static List<Field> mimeTypes() {
        return ImmutableList.of(
                new StringField(MediaType.ANY_TYPE.withoutParameters().toString()),
                new StringField(MediaType.ANY_TYPE.withoutParameters().toString()),
                new StringField(MediaType.ANY_TEXT_TYPE.withoutParameters().toString()),
                new StringField(MediaType.ANY_IMAGE_TYPE.withoutParameters().toString()),
                new StringField(MediaType.ANY_AUDIO_TYPE.withoutParameters().toString()),
                new StringField(MediaType.ANY_VIDEO_TYPE.withoutParameters().toString()),
                new StringField(MediaType.ANY_APPLICATION_TYPE.withoutParameters().toString()),
                new StringField(MediaType.CACHE_MANIFEST_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.CSS_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.CSV_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.HTML_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.I_CALENDAR_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.PLAIN_TEXT_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.TEXT_JAVASCRIPT_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.TSV_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.VCARD_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.WML_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.XML_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.BMP.withoutParameters().toString()),
                new StringField(MediaType.CRW.withoutParameters().toString()),
                new StringField(MediaType.GIF.withoutParameters().toString()),
                new StringField(MediaType.ICO.withoutParameters().toString()),
                new StringField(MediaType.JPEG.withoutParameters().toString()),
                new StringField(MediaType.PNG.withoutParameters().toString()),
                new StringField(MediaType.PSD.withoutParameters().toString()),
                new StringField(MediaType.SVG_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.TIFF.withoutParameters().toString()),
                new StringField(MediaType.WEBP.withoutParameters().toString()),
                new StringField(MediaType.MP4_AUDIO.withoutParameters().toString()),
                new StringField(MediaType.MPEG_AUDIO.withoutParameters().toString()),
                new StringField(MediaType.OGG_AUDIO.withoutParameters().toString()),
                new StringField(MediaType.WEBM_AUDIO.withoutParameters().toString()),
                new StringField(MediaType.MP4_VIDEO.withoutParameters().toString()),
                new StringField(MediaType.MPEG_VIDEO.withoutParameters().toString()),
                new StringField(MediaType.OGG_VIDEO.withoutParameters().toString()),
                new StringField(MediaType.QUICKTIME.withoutParameters().toString()),
                new StringField(MediaType.WEBM_VIDEO.withoutParameters().toString()),
                new StringField(MediaType.WMV.withoutParameters().toString()),
                new StringField(MediaType.APPLICATION_XML_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.ATOM_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.BZIP2.withoutParameters().toString()),
                new StringField(MediaType.EOT.withoutParameters().toString()),
                new StringField(MediaType.EPUB.withoutParameters().toString()),
                new StringField(MediaType.FORM_DATA.withoutParameters().toString()),
                new StringField(MediaType.KEY_ARCHIVE.withoutParameters().toString()),
                new StringField(MediaType.APPLICATION_BINARY.withoutParameters().toString()),
                new StringField(MediaType.GZIP.withoutParameters().toString()),
                new StringField(MediaType.JAVASCRIPT_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.JSON_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.KML.withoutParameters().toString()),
                new StringField(MediaType.KMZ.withoutParameters().toString()),
                new StringField(MediaType.MBOX.withoutParameters().toString()),
                new StringField(MediaType.MICROSOFT_EXCEL.withoutParameters().toString()),
                new StringField(MediaType.MICROSOFT_POWERPOINT.withoutParameters().toString()),
                new StringField(MediaType.MICROSOFT_WORD.withoutParameters().toString()),
                new StringField(MediaType.OCTET_STREAM.withoutParameters().toString()),
                new StringField(MediaType.OGG_CONTAINER.withoutParameters().toString()),
                new StringField(MediaType.OOXML_DOCUMENT.withoutParameters().toString()),
                new StringField(MediaType.OOXML_PRESENTATION.withoutParameters().toString()),
                new StringField(MediaType.OOXML_SHEET.withoutParameters().toString()),
                new StringField(MediaType.OPENDOCUMENT_GRAPHICS.withoutParameters().toString()),
                new StringField(MediaType.OPENDOCUMENT_PRESENTATION.withoutParameters().toString()),
                new StringField(MediaType.OPENDOCUMENT_SPREADSHEET.withoutParameters().toString()),
                new StringField(MediaType.OPENDOCUMENT_TEXT.withoutParameters().toString()),
                new StringField(MediaType.PDF.withoutParameters().toString()),
                new StringField(MediaType.POSTSCRIPT.withoutParameters().toString()),
                new StringField(MediaType.PROTOBUF.withoutParameters().toString()),
                new StringField(MediaType.RDF_XML_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.RTF_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.SFNT.withoutParameters().toString()),
                new StringField(MediaType.SHOCKWAVE_FLASH.withoutParameters().toString()),
                new StringField(MediaType.SKETCHUP.withoutParameters().toString()),
                new StringField(MediaType.TAR.withoutParameters().toString()),
                new StringField(MediaType.WOFF.withoutParameters().toString()),
                new StringField(MediaType.XHTML_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.XRD_UTF_8.withoutParameters().toString()),
                new StringField(MediaType.ZIP.withoutParameters().withoutParameters().toString())
        );
    }
}
