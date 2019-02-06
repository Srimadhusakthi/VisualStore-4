package com.visualstore.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.visualstore.Model.ProductLensDataModel;

import java.io.File;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String mTABLENAME = "tvstable";
    public static String mDATABASENAME = "vsdatabase";
    public static int mVSVERSION = 1;

    /*Values */
    public static String  mvsid = "mvsid";
    public static String      id = "id";
    public static String  lens_type = "lens_type";
    public static String  name = "name";
    public static String  edi_code = "edi_code";
    public static String  lens_code = "lens_code";
    public static String  available_coatings = "available_coatings";
    public static String  individual = "individual";
    public static String  addition = "addition";
    public static String  tint = "tint";


    private static String mCREATE_TABLE  =  " create table " + mTABLENAME + "("
                                              + mvsid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                              + id + " TEXT, "
                                              + lens_type + " TEXT, "
                                              + name + " TEXT, "
                                              + edi_code + " TEXT, "
                                              + lens_code + " TEXT, "
                                              + available_coatings + " TEXT, "
                                              + individual + " TEXT, "
                                              + addition + " TEXT, "
                                              + tint + " TEXT);";



    public DatabaseHelper(Context context){
        super(context,mDATABASENAME,null,mVSVERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mCREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS" + mCREATE_TABLE);
         onCreate(db);

    }




    public long onInsertValues(final ProductLensDataModel productlensModel){
        long id = -1;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.id,productlensModel.getId());
        contentValues.put(DatabaseHelper.id,productlensModel.getId());
        contentValues.put(DatabaseHelper.lens_type,productlensModel.getLens_type());
        contentValues.put(DatabaseHelper.name,productlensModel.getName());
        contentValues.put(DatabaseHelper.edi_code,productlensModel.getEdi_code());
        contentValues.put(DatabaseHelper.lens_code,productlensModel.getLens_code());
        contentValues.put(DatabaseHelper.available_coatings,productlensModel.getAvailable_coatings());
        contentValues.put(DatabaseHelper.individual,productlensModel.getIndividual());
        contentValues.put(DatabaseHelper.addition,productlensModel.getAddition());
        contentValues.put(DatabaseHelper.tint,productlensModel.getTint());
        id = database.insert(DatabaseHelper.mTABLENAME,null,contentValues);
        database.close();
        return  id;
    }




    public ArrayList<ProductLensDataModel> getAllValues(){
        ArrayList<ProductLensDataModel> mLensdata = new ArrayList<>();
        String mQuery = " SELECT * FROM " + mTABLENAME ;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(mQuery,null);
        if(cursor.moveToFirst())
        do{
            ProductLensDataModel productLensDataModel = new ProductLensDataModel();
            productLensDataModel.setId(cursor.getString(cursor.getColumnIndex(id)));
            productLensDataModel.setLens_type(cursor.getString(cursor.getColumnIndex(lens_type)));
            productLensDataModel.setName(cursor.getString(cursor.getColumnIndex(name)));
            productLensDataModel.setEdi_code(cursor.getString(cursor.getColumnIndex(edi_code)));
            productLensDataModel.setLens_code(cursor.getString(cursor.getColumnIndex(lens_code)));
            productLensDataModel.setAvailable_coatings(cursor.getString(cursor.getColumnIndex(available_coatings)));
            productLensDataModel.setIndividual(cursor.getString(cursor.getColumnIndex(individual)));
            productLensDataModel.setAddition(cursor.getString(cursor.getColumnIndex(addition)));
            productLensDataModel.setTint(cursor.getString(cursor.getColumnIndex(tint)));
            mLensdata.add(productLensDataModel);
        }while(cursor.moveToNext());
        database.close();
        return  mLensdata;
    }


    public ArrayList<ProductLensDataModel> getLensType(String mConditions,String mAdditionValues){
        ArrayList<ProductLensDataModel> mlist = new ArrayList<>();
        String mQuery = " SELECT * FROM " +  mTABLENAME + " WHERE " + lens_type + "='" + mConditions  + "' AND " + addition + "='" + mAdditionValues +  "'" ;
//        String mQuery = " SELECT * FROM " +  mTABLENAME + " WHERE " + lens_type + "='" + mConditions  +  "'" ;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(mQuery,null);
        if(cursor.moveToFirst())
            do{
             ProductLensDataModel productLensDataModel = new ProductLensDataModel();
             productLensDataModel.setId(cursor.getString(cursor.getColumnIndex(id)));
             productLensDataModel.setLens_type(cursor.getString(cursor.getColumnIndex(lens_type)));
             productLensDataModel.setName(cursor.getString(cursor.getColumnIndex(name)));
             productLensDataModel.setEdi_code(cursor.getString(cursor.getColumnIndex(edi_code)));
             productLensDataModel.setLens_code(cursor.getString(cursor.getColumnIndex(lens_code)));
             productLensDataModel.setAvailable_coatings(cursor.getString(cursor.getColumnIndex(available_coatings)));
             productLensDataModel.setIndividual(cursor.getString(cursor.getColumnIndex(individual)));
             productLensDataModel.setAddition(cursor.getString(cursor.getColumnIndex(addition)));
             productLensDataModel.setTint(cursor.getString(cursor.getColumnIndex(tint)));
             mlist.add(productLensDataModel);
            }while(cursor.moveToNext());
        database.close();
        return  mlist;
    }


    public void onDelete(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(mTABLENAME,null,null);

    }


}
