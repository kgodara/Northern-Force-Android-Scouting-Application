package com.example.alex.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by Alex on 2/19/2015.
 */
public class Tests {


    String sampleCSVFile =
            "Name, Data 1, Data 2\n" +
                    "column 1, data 1.1, data 1.2\n" +
                    "column 2, data 2.1, data 2.2";

    public void testDocumentParserGetLengthReturnsCorrect() {
        DocumentParser documentParser = new DocumentParser();
        documentParser.consumeDocument(sampleCSVFile);

        int docLength = documentParser.getDocLength();
        boolean areEqual = docLength == 3;
        if (areEqual) {
            Log.v("Tests", "The docLength is correct");
        } else if (!areEqual) {
            Log.e("Tests", "The docLength is not correct");
        }

    }

    public final void testDocumentParserGetValueReturnsCorrect() {
        DocumentParser documentParser = new DocumentParser();
        documentParser.consumeDocument(sampleCSVFile);

        String docValue = documentParser.getValue(2, "Name");
        boolean areEqual = docValue.equals("column 1");
        if (areEqual) {
            Log.v("Tests", "The docValue is correct");
        } else if (!areEqual) {
            Log.e("Tests", "The docValue is not correct." + "What was returned was (" + docValue + ")");
        }

        docValue = null;
        areEqual = false;
        docValue = documentParser.getValue(3, "Data 2");
        areEqual = docValue.equals("data 2.2");
        if (areEqual) {
            Log.v("Tests", "The docValue is correct");
        } else if (!areEqual) {
            Log.e("Tests", "The docValue is not correct." + "What was returned was (" + docValue + ")");
        }
    }

    public void testSQLite(Context context) {
        MySQLiteHelper db = new MySQLiteHelper(context);

        db.onUpgrade(db.getWritableDatabase(), 0, 1);

        /*db.addContact(new TeamTable("172", 0));
        db.addContact(new TeamTable("2003",0));
        db.addContact(new TeamTable("1524", 0));

        List<TeamTable> contacts = db.getAllContacts();

        for (TeamTable cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Team Number: " + cn.getTeamNumber() +  " ,Average Score: " + cn.getAverageScore();

            // Writing Contacts to log
            Log.v("Tests", log);
        }*/


    }

    public void testConfigParser(Context context){
        ConfigParser configParser = new ConfigParser();
        AssetManager am = context.getAssets();

        try {
            InputStream is = am.open("configuration_file");

            ArrayList<DatabaseTable> tables = configParser.parse(is);
            Iterator<DatabaseTable> tableIterator = tables.iterator();

            while(tableIterator.hasNext()){
                ArrayList<ConfigEntry> entries = tableIterator.next().getColumns();
                Iterator<ConfigEntry> entryIterator = entries.iterator();
                while(entryIterator.hasNext()){
                    Log.v("Tests", "entries: " + entryIterator.next().getText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("Tests", "ConfigParser IOExepction");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.v("Tests", "ConfigParser XMLPullParserException");
        }


    }
}