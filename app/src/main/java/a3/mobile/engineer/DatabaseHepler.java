package a3.mobile.engineer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus on 31.10.2015.
 */
public class DatabaseHepler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "engineer.db";
    private static final String TBL_PARAM = "TBL_PARAM";

    private static final String TBL_FILTERS = "TBL_FILTERS";
    private static final String COL_FILTER_ID = "FILTER_ID";
    private static final String COL_FILTER_NAME = "FILTER_NAME";
    private static final String COL_FILTER_QUAL = "FILTER_QUAL";

    public DatabaseHepler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    public void updateFilters(JSONArray jFilters) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TBL_FILTERS);

        String query = "";
        String cmd = "INSERT INTO " + TBL_FILTERS + " (" +
                COL_FILTER_ID + ","+ COL_FILTER_NAME +", " + COL_FILTER_QUAL + ") ";

        for (int i=0; i<jFilters.length(); i++) {

            JSONObject jObj = null;
            try {
                jObj = jFilters.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try{
                ContentValues values = new ContentValues();
                values.put(COL_FILTER_ID,   jObj.getString("FilterID"));
                values.put(COL_FILTER_NAME, jObj.getString("FilterName"));
                values.put(COL_FILTER_QUAL, "");
                db.insert(TBL_FILTERS, null, values);

            }catch (Exception ex){
                Log.e("DB UPDATE FILTERS", ex.toString());
            }

        }

        db.close();
        Log.d("DB UPDATE FILTERS", "Complete");

    }

    public String getParamVal(String paramName) {
        String query = "Select * FROM " + TBL_PARAM + " WHERE PARAM_NAME = \"" + paramName + "\"";
        String paramValue;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            paramValue = cursor.getString(0);
            cursor.close();
        } else {
            paramValue = null;
        }
        db.close();
        return paramValue;
    }

    public void setParamVal(String paramName, String paramValue) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TBL_PARAM + " WHERE PARAM_NAME = \"" + paramName + "\"");

        ContentValues values = new ContentValues();
        values.put("PARAM_NAME", paramName);
        values.put("PARAM_VALUE", paramValue);
        db.insert(TBL_PARAM, null, values);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PARAM_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_PARAM + " (PARAM_ID INTEGER PRIMARY KEY," +
                "PARAM_NAME TEXT, PARAM_VALUE TEXT" + ")";
        db.execSQL(CREATE_PARAM_TABLE);

        String CREATE_FILTERS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_FILTERS + " ("+ COL_FILTER_ID +" TEXT," +
                COL_FILTER_NAME + " TEXT, " + COL_FILTER_QUAL + " TEXT" + ")";
        db.execSQL(CREATE_FILTERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TBL_PARAM");
        onCreate(db);
    }
}
