package ie.lyit.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ie.lyit.notes.Note;

/**
 * Created by barry on 16/11/2016.
 * desc - local db.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "notes.db";
    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_HEADER = "note_header";
    private static final String COLUMN_BODY = "note_body";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        String query = "CREATE TABLE " + TABLE_NOTES + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_HEADER + " TEXT, " +
                COLUMN_BODY + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    /**
     *
     * @param note
     */
    public void addNote(Note note){
        ContentValues values =  new ContentValues();
        values.put(COLUMN_HEADER, note.getHeader());
        values.put(COLUMN_BODY, note.getBody());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    /**
     *
     * @param note_id
     */
    public void deleteContact(int note_id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NOTES + " WHERE "+ COLUMN_ID + " = " + note_id);
    }

    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NOTES+ " WHERE 1";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            Note note =  new Note();
            if(cursor.getString(cursor.getColumnIndex("note_header")) != null){
                note.setHeader(cursor.getString(cursor.getColumnIndex("note_header")));
            }
            if(cursor.getString(cursor.getColumnIndex("note_body")) != null){
                note.setBody(cursor.getString(cursor.getColumnIndex("note_body")));
            }
            notes.add(note);

            //move cursor to next element
            cursor.moveToNext();

        }
        return notes;
    }
}
