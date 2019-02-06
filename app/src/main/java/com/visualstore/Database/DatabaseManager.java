package com.visualstore.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.visualstore.Model.ProductLensDataModel;
import com.visualstore.Model.ProductlensModel;

import java.util.ArrayList;

public class DatabaseManager {
    private Context context;
    private DatabaseHelper mDatabasehelper;
    private SQLiteDatabase mDatabase;


    public DatabaseManager(Context contexts){
        context = contexts;
    }


    public DatabaseManager open() throws SQLException {
        mDatabasehelper = new DatabaseHelper(context);
        mDatabase = mDatabasehelper.getWritableDatabase();
        return  this;
    }

    public void close(){
        mDatabasehelper.close();
    }

    public void onInsert(String id,String lens_type,String name,String edi_code,String lens_code,String available_coatings,String indijual,String addition,String tint){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.id,id);
        contentValues.put(DatabaseHelper.lens_type,lens_type);
        contentValues.put(DatabaseHelper.name,name);
        contentValues.put(DatabaseHelper.edi_code,edi_code);
        contentValues.put(DatabaseHelper.lens_code,lens_code);
        contentValues.put(DatabaseHelper.available_coatings,available_coatings);
        contentValues.put(DatabaseHelper.individual,indijual);
        contentValues.put(DatabaseHelper.addition,addition);
        contentValues.put(DatabaseHelper.tint,tint);
        mDatabase.insert(DatabaseHelper.mTABLENAME,null,contentValues);
    }








    private ArrayList<ProductlensModel> onFetchValue(String condition){
        ArrayList<ProductlensModel> productlensModels = new ArrayList<>();
        String mQuery = "SELECT * FROM " + DatabaseHelper.mTABLENAME + " WHERE " + condition ;



        return productlensModels;
    }
    public void onUpdate(long _id,String id,String lens_type,String name,String edi_code,String lens_code,String available_coatings,String indijual,String addition,String tint){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.id,id);
        contentValues.put(DatabaseHelper.lens_type,lens_type);
        contentValues.put(DatabaseHelper.name,name);
        contentValues.put(DatabaseHelper.edi_code,edi_code);
        contentValues.put(DatabaseHelper.lens_code,lens_code);
        contentValues.put(DatabaseHelper.available_coatings,available_coatings);
        contentValues.put(DatabaseHelper.individual,indijual);
        contentValues.put(DatabaseHelper.addition,addition);
        contentValues.put(DatabaseHelper.tint,tint);
        mDatabase.update(DatabaseHelper.mTABLENAME,contentValues,DatabaseHelper.mvsid + " = " + _id,null);
    }

    private void onDelete(long _id){
        mDatabase.delete(DatabaseHelper.mTABLENAME,DatabaseHelper.mvsid +"=" +_id,null);
    }



}
