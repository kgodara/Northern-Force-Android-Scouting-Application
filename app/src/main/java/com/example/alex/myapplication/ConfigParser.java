package com.example.alex.myapplication;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by alex on 4/18/15.
 */
public class ConfigParser {

    private static final String ns = null;


    public ConfigParser() {
    }

    public ArrayList parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(in, null);
            parser.nextTag();
            //Log.v("ConfigParser", "returning readFeed");
            return readFeed(parser);
        } finally {
            in.close();
        }
        }

    private ArrayList readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<DatabaseTable> tables = new ArrayList<DatabaseTable>();

        parser.require(XmlPullParser.START_TAG, ns, "config");
        Log.v("ConfigParser", "did parser.require");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                //Log.v("CP - readFeed", "Start tag "+parser.getName());
                if(parser.getName() == null){
                    break;
                }
                if(parser.getName().equals("table")) {
                    String name = parser.getAttributeValue(null, "name");
                    Log.v("CP - readFeed", "Found a table " + name);
                    tables.add(new DatabaseTable(name, readTable(parser)));
                    Log.v("CP - readFeed", "Got out of making table " + name);
                }
                else{
                    eventType = parser.next();
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                //System.out.println("End tag "+parser.getName());
                eventType = parser.next();
            } else if (eventType == XmlPullParser.TEXT) {
                //System.out.println("Text "+parser.getText());
                eventType = parser.next();
            }

        }

        return tables;
    }

    public ArrayList<ConfigEntry> readTable(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<ConfigEntry> list = new ArrayList<ConfigEntry>();
        Log.v("CP", "Trying to parser a table");

        int eventType = parser.next(); //next to skip the first table tag
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                Log.v("CP - readTable", "Start tag "+parser.getName());
                if(parser.getName().equals("column")){
                    String name =  parser.getAttributeValue(null, "name");
                    String type = parser.getAttributeValue(null, "type");
                    String text = parser.getAttributeValue(null, "text");
                    String options = parser.getAttributeValue(null, "options");

                    Log.v("CP - readTable", "Found a colum " + name);
                    list.add(new ConfigEntry(type, name, text, options));
                }
                if(parser.getName().equals("table")){
                    Log.v("CP - readTable", "found a table tag: " + parser.getAttributeValue(null, "name"));

                    break;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                //System.out.println("End tag "+parser.getName());
            } else if (eventType == XmlPullParser.TEXT) {
                //System.out.println("Text "+parser.getText());
            }
            eventType = parser.next();
        }
        return list;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        //Log.v("ConfigParser", "result: " + result);
        return result;
    }

}

