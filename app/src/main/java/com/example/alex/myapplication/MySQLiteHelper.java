package com.example.alex.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/** Class that acts as an interface between the database and the application */
public class MySQLiteHelper extends SQLiteOpenHelper {

    /** The name of the id column */
    public static final String ID = "_id";

    private SQLiteDatabase db;

    public MySQLiteHelper(Context context) {
        super(context, "Scouting_Data", null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

    }

    @Override
    /** deletes all tables from the database */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        for(String tableName : this.getTableNames()) {
            this.doesTableExists(tableName);
        }
    }

    /** Uses the sqlite master table to return a list of table names
     * @return An ArrayList of all table names */
    public ArrayList<String> getTableNames(){
        ArrayList<String> tableList = new ArrayList();

        Cursor tables = this.rawQuery("SELECT name FROM sqlite_master WHERE type='table'");
        if(tables.moveToFirst()){
            do{
                tableList.add(tables.getString(0));
            }while(tables.moveToNext());
        }

        return tableList;
    }

    /** Makes a new table in the database gimen a DatabaseTable object
     * @param table instance of the DatabaseTable class containing the name and colunmns of the table to be created*/
    public void createTable(DatabaseTable table){
        ArrayList<ConfigEntry> columns = table.getColumns();

        Iterator<ConfigEntry> columnsIterator = columns.iterator();

        String createTeamTable = "CREATE TABLE  " + table.getName() + "( _id INTEGER PRIMARY KEY";

        while (columnsIterator.hasNext()) {
            createTeamTable += ", ";
            ConfigEntry entry = columnsIterator.next();
            String name = entry.getColName();
            String type = "";
            if (entry.getType().equals("String") || entry.getType().equals("YorN") || entry.getType().equals("Options_Spinner")) {
                type = "TEXT";
            }
            if (entry.getType().equals("int")) {
                type = "INTEGER";
            }
            createTeamTable +=  name + " " + type;
        }

        createTeamTable += ")";

        Log.v("MYSQLiteHelper", "execing code " + createTeamTable);

        this.execSQL(createTeamTable);
    }

    /** Runs commands on the databas
     * @param sqlCommand A String of the command to be executed
     */
    public void execSQL(String sqlCommand){
        db.execSQL(sqlCommand);
        Log.v("MySQLiteHelper", "Exec SQL :" + sqlCommand);
    }

    public void dropTable(String table){
        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    public void addValues(String table ,ContentValues values){
        synchronized (db) {
            db.insert(table, null, values);
            Log.v("MySQLiteHelper", "added a value to the database");
        }
    }

    public void updateCell(String table, String columnToChange, String newValue, String identifyStatement){
        String sqlStatement = "update " + table + " set " + columnToChange + "='" + newValue + "' where " + identifyStatement;
        Log.v("MySQLiteHelper", sqlStatement);
        db.execSQL(sqlStatement);
    }

    public Cursor rawQuery(String query){
        return db.rawQuery(query, null);
    }

    public Cursor selectFromTable(String column, String table){
        return db.rawQuery("SELECT " + column + " FROM " + table, null);
    }

    public Cursor selectFromTableWhere(String column, String table, String condition){
        return db.rawQuery("SELECT " + column + " FROM " + table + " WHERE " + condition, null);
    }

    public Cursor selectFromTableExcept(String column, String table, String condition){
        return db.rawQuery("SELECT " + column + " FROM " + table + " EXCEPT " + condition, null);
    }



    boolean doesTableExists(String tableName){
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE name = '" + tableName + "' and type='table'" , null);
        //Log.v("AAAHHH", "does " + tableName + " exists? cursor length is " + cursor.getCount());
        return cursor.getCount() == 1;
    }

    public void deleteRowFromTable(String tableName, String rowID){
        db.delete(tableName, ID + " = ? ", new String[] {(String.valueOf(rowID))});
    }

    public int countRowsInTable(String tableName){
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

}
