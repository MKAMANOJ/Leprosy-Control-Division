package np.gov.shris.lcd.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import np.gov.shris.lcd.Models.News;

/**
 * Created by mka on 9/26/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "lcdNews";

    // Contacts table name
    private static final String TABLE_NEWS = "news";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NEWS_TITLE = "news_title";
    private static final String KEY_FILENAME = "filename";
    private static final String KEY_ORIGINAL_FILENAME = "filename";
    private static final String KEY_FILE_LINK = "file_link";
    private static final String KEY_FILE_TYPE = "file_type";
    private static final String KEY_FILE_SIZE = "file_size";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
                + KEY_ID + " INTEGER," + KEY_NEWS_TITLE + " TEXT,"
                + KEY_ORIGINAL_FILENAME + " TEXT," + KEY_FILE_LINK + " TEXT,"
                + KEY_FILE_TYPE + " TEXT," + KEY_FILE_SIZE + " TEXT,"
                + KEY_UPDATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);

        // Create tables again
        onCreate(db);
    }

    public void truncate(){
        //Truncate Table

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NEWS);
        db.close();
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new news
    public void addNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, news.getId());
        values.put(KEY_NEWS_TITLE, news.getNewsTitle());
        values.put(KEY_ORIGINAL_FILENAME, news.getOrginalFilename());
        values.put(KEY_FILE_LINK, news.getFileLink());
        values.put(KEY_FILE_TYPE, news.getFileType());
        values.put(KEY_FILE_SIZE, news.getFileSize());
        values.put(KEY_UPDATED_AT, news.getUpdatedAt());

        // Inserting Row
        db.insert(TABLE_NEWS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single News
    News getNews(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NEWS, new String[] { KEY_ID,
                        KEY_NEWS_TITLE, KEY_ORIGINAL_FILENAME, KEY_FILE_LINK,
                        KEY_FILE_TYPE, KEY_FILE_SIZE, KEY_UPDATED_AT}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        News news = new News(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6));
        // return news
        db.close();
        return news;
    }

    // Getting All News
    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<News>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(Integer.parseInt(cursor.getString(0)));
                news.setNewsTitle(cursor.getString(1));
                news.setOrginalFilename(cursor.getString(2));
                news.setFileLink(cursor.getString(3));
                news.setFileType(cursor.getString(4));
                news.setFileSize(cursor.getString(5));
                news.setUpdatedAt(cursor.getString(6));

                // Adding news to list
                newsList.add(news);
            } while (cursor.moveToNext());
        }

        db.close();
        // return news list
        return newsList;
    }

    // Deleting single contact
    public void deleteContact(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS, KEY_ID + " = ?",
                new String[] { String.valueOf(news.getId()) });
        db.close();
    }


    // Getting news Count
    public int getNewsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NEWS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

}
