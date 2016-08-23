package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;


public class SQLiteHandler extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "hidic_cs";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login table column names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creating tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + "INTEGER PRIMARY KEY," + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT," + KEY_PHONE + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Table created");
    }

    /**
     * Upgrading database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Create new one
        onCreate(db);
    }

    /**
     * Store patient details in database
     *
     * @param first_name of the patient
     * @param last_name  of the patient
     * @param phone      number of the patient
     * @param email      of the patient
     * @param uid        id the patient
     * @param created_at time added to the database
     */
    public void addPatient(String first_name, String last_name, String phone, String email, String uid, String created_at) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, first_name);
        values.put(KEY_LAST_NAME, last_name);
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAIL, email);
        values.put(KEY_UID, uid);
        values.put(KEY_CREATED_AT, created_at);

        // Inserting a row
        long id = sqLiteDatabase.insert(TABLE_LOGIN, null, values);

        // Close the database connection
        sqLiteDatabase.close();

        Log.d(TAG, "New patient added into sqlite: " + id);

    }

    /**
     * Retrieving patient data from
     *
     * @return the patient's details
     */
    public HashMap<String, String> getPatientDetails() {
        HashMap<String, String> patient = new HashMap<>();
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            patient.put("first_name", cursor.getString(1));
            patient.put("last_name", cursor.getString(2));
            patient.put("phone", cursor.getString(3));
            patient.put("email", cursor.getString(4));
            patient.put("uid", cursor.getString(5));
            patient.put("created_at", cursor.getString(6));
        }

        // close connections
        cursor.close();
        sqLiteDatabase.close();

        Log.d(TAG, "Fetching patient from Sqlite: " + patient.toString());

        return patient;

    }

    /**
     * Getting user login status return true if rows are there in table
     *
     * @return row count
     */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     */
    public void deletePatients() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();

        Log.d(TAG, "Deleted all patient info from sqlite");
    }

}
