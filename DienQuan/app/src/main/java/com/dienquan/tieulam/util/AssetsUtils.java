package com.dienquan.tieulam.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class AssetsUtils {
    static String tag = AssetsUtils.class.getSimpleName();

    private static String defaultFont = "";

    // Get text
    private static String getJsonByAssets(Context context, String uri) {
        // get input stream
        String wordList = "";
        BufferedReader br = null;
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(uri);
            br = new BufferedReader(new InputStreamReader(is));
            String word;
            while ((word = br.readLine()) != null) {
                // break txt file into different words, add to wordList
                wordList += "\n";
                wordList += word;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return wordList;
        } finally {
            try {
                if (br != null) {
                    br.close(); // stop reading
                }
            } catch (IOException e) {
//				LogUtils.e(tag, "getJsonByAssets error!", e);
                e.printStackTrace();
            }
        }

        return wordList;
    }

    public static String getStringFromFile(Context context, String fileName) {
        return getJsonByAssets(context, fileName);
    }

    public static String getJsonStringFromFile(Context context, String fileName) {
        return getStringFromFile(context, fileName + ".json");
    }

    // Get Drawable
    public static Drawable getDrawable(Context context, String imagePath) {
        AssetManager am = context.getAssets();
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(am.open(imagePath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    // Font
    public static void setFont(Context context, TextView textView, String font) {
        if (textView != null) {
            String suffix = font.substring(font.length() - 4, font.length());
            if (!suffix.equalsIgnoreCase(".ttf")
                    && !suffix.equalsIgnoreCase(".otf"))
                font += ".ttf";
            font = "font/" + font;
            try {
                Typeface typeface = Typeface.createFromAsset(
                        context.getAssets(), font);
                textView.setTypeface(typeface);
            } catch (Exception e) {
//				LogUtils.e(tag, "font not found in assets: " + font);
                e.printStackTrace();
            }

        }
    }

    public static void setFont(Context context, int textView, String font) {
        TextView txt = (TextView) ((Activity) context).findViewById(textView);
        setFont(context, txt, font);
    }

    public static String getDefaultFont() {
        return AssetsUtils.defaultFont;
    }

    public static void setDefaultFont(String defaultFont) {
        AssetsUtils.defaultFont = defaultFont;
    }

    public static Typeface getTypefacefromFontFile(Context context, String font) {
        Typeface typeface = null;
        String suffix = font.substring(font.length() - 4, font.length());
        if (!suffix.equalsIgnoreCase(".ttf")
                && !suffix.equalsIgnoreCase(".otf"))
            font += ".ttf";
        font = "" + font;
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), font);

        } catch (Exception e) {
//			LogUtils.e(tag, "font not found in assets: " + font);
            e.printStackTrace();
        }

        return typeface;
    }

    /*
     *
     */
    public static Document getXMLDocument(Context context, String fileName) {
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(fileName);
            return readXml(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read XML as DOM.
     */
    public static Document readXml(InputStream is) throws SAXException, IOException,
            ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setValidating(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
        // dbf.setCoalescing(true);
        // dbf.setExpandEntityReferences(true);
        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();
        db.setEntityResolver(new NullResolver());

        // db.setErrorHandler( new MyErrorHandler());

        return db.parse(is);
    }
}

class NullResolver implements EntityResolver {
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException,
            IOException {
        return new InputSource(new StringReader(""));
    }
}