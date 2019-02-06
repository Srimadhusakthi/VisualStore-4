package com.visualstore.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.visualstore.Model.CoatingTintDataModel;
import com.visualstore.Model.TintDataModel;

import java.io.File;
import java.util.ArrayList;

public class DatabaseHelperTint extends SQLiteOpenHelper {

    public static String mTableName = "mTintTablename";
    public static String mDBName = "mTintDBname";
    public static int mTintVersion = 1;

    public static String mTint_Id = "mtid";
    public static String mTintId = "tID";
    public static String mTintcode = "code";
    public static String mTintdisplay_name = "display_name";
    public static String mTintname = "name";


    private static String mTintCreateTable = " CREATE TABLE " +  mTableName + "("
            + mTint_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," + mTintId + " TEXT, " + mTintcode + " TEXT, "
            + mTintdisplay_name + " TEXT, " + mTintname + " TEXT);";


    public DatabaseHelperTint(Context context){
        super(context,mTableName,null,mTintVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mTintCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL(" DROP TABLE IF EXISTS " + mTintCreateTable);
      onCreate(db);
    }


    /*TINT INSERT*/
    public long inserTintValues(TintDataModel tintDataModel){
        long id= -1;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mTint_Id,tintDataModel.getId());
        contentValues.put(mTintcode,tintDataModel.getCode());
        contentValues.put(mTintdisplay_name,tintDataModel.getDisplay_name());
        contentValues.put(mTintname,tintDataModel.getName());
        id = database.insert(mTableName,null,contentValues);
        database.close();
        return id;
    }

    public ArrayList<TintDataModel> getAllValues(){
        ArrayList<TintDataModel> mTinitlist = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String mQuery = " SELECT * FROM " +  mTableName ;
        Cursor cursor = database.rawQuery(mQuery,null);
        if(cursor.moveToFirst())
            do{
              TintDataModel model = new TintDataModel();
              model.setId(cursor.getString(cursor.getColumnIndex(mTintId)));
              model.setCode(cursor.getString(cursor.getColumnIndex(mTintcode)));
              model.setDisplay_name(cursor.getString(cursor.getColumnIndex(mTintdisplay_name)));
              model.setName(cursor.getString(cursor.getColumnIndex(mTintname)));
              mTinitlist.add(model);
            }while(cursor.moveToNext());
        database.close();
        return  mTinitlist;
    }

    public void onDelete(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(mTableName,null,null);

    }
}
