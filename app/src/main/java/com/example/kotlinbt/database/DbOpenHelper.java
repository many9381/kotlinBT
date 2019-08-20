package com.example.kotlinbt.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DB에 대한 함수가 정의된 곳
 *
 */
public class DbOpenHelper {

    private static final String DATABASE_NAME = "modulelist.db";
    private static final int DATABASE_VERSION = 1;
    //public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private ArrayList<ItemData> itemDatas = null;
    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE);

        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DataBases.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        //mDB = mDBHelper.getWritableDatabase();
        return this;
    }
    /**
     * DB에 데이터 추가
    */
    public void DbInsert(ItemData itemData ){

        SQLiteDatabase mDB;
        mDB = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();


        if(itemData.identName == null || itemData.identName.equals(""))
            values.put(DataBases.CreateDB.IDENTIFICATION_NAME, "UnKnown");
        else
            values.put(DataBases.CreateDB.IDENTIFICATION_NAME, itemData.identName);



        values.put(DataBases.CreateDB.IDENTIFICATION_NUM, itemData.identNum);
        values.put(DataBases.CreateDB.TARGETCHECK, 0);

        mDB.insert(DataBases.CreateDB._TABLENAME,null,values);

    }

    /**
     * DB항목 업그레이드 - 수정할 때 사용
     */
    public void DbUpdate(ItemData itemData ){

        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.IDENTIFICATION_NAME, itemData.identName);
        values.put(DataBases.CreateDB.IDENTIFICATION_NUM, itemData.identNum);
        values.put(DataBases.CreateDB.TARGETCHECK, itemData.targetCheck);

        SQLiteDatabase mDB;
        mDB = mDBHelper.getWritableDatabase();

        String selection = DataBases.CreateDB.IDENTIFICATION_NUM + " = ?";
        mDB.update(DataBases.CreateDB._TABLENAME, values, selection, new String[]{itemData.identNum});

    }

    /**
     * 항목 삭제하는 함수
     * @param id
     */
    public void DbDelete(String id) {

        SQLiteDatabase mDB;
        mDB = mDBHelper.getWritableDatabase();
        String selection = DataBases.CreateDB._ID + " = ?";
        mDB.delete(DataBases.CreateDB._TABLENAME, selection, new String[]{id});
    }


    /**
     * modulelist테이블에 저장되어있는 값들을 반환하는 함수 - 리스트뷰 뿌릴 때 호출
     * @return
     */
    public ArrayList<ItemData> DbMainSelect(){
        SQLiteDatabase getDb;
        getDb = mDBHelper.getReadableDatabase();
        Cursor c = getDb.rawQuery( "select * from moduleinfo" , null);

        itemDatas = new ArrayList<ItemData>();
//

        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex(DataBases.CreateDB._ID));
            String identName = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NAME));
            String identNum = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NUM));
            int targetCheck = c.getInt(c.getColumnIndex(DataBases.CreateDB.TARGETCHECK));

            ItemData listViewItem = new ItemData(_id,identName,identNum,targetCheck);

            itemDatas.add(listViewItem);

        }


        return itemDatas;
    }

   public ItemData DbFind(String address){
        SQLiteDatabase getDb;
        getDb = mDBHelper.getReadableDatabase();
        Cursor c = getDb.rawQuery( "select * from " + DataBases.CreateDB._TABLENAME + " where " + DataBases.CreateDB.IDENTIFICATION_NUM + " = '"+ address+"'" , null);

        ItemData item = null;

        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex(DataBases.CreateDB._ID));
            String identName = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NAME));
            String identNum = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NUM));
            int targetCheck = c.getInt(c.getColumnIndex(DataBases.CreateDB.TARGETCHECK));

            item = new ItemData(_id,identName,identNum,targetCheck);

        }


        return item;
    }


    public ItemData DbTargetFind(String address){
        SQLiteDatabase getDb;
        getDb = mDBHelper.getReadableDatabase();
        Cursor c = getDb.rawQuery( "select * from " + DataBases.CreateDB._TABLENAME + " where " + DataBases.CreateDB.TARGETCHECK + " = 1 AND " + DataBases.CreateDB.IDENTIFICATION_NUM + " = '"+ address+"'" , null);

        ItemData item = null;

        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex(DataBases.CreateDB._ID));
            String identName = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NAME));
            String identNum = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NUM));
            int targetCheck = c.getInt(c.getColumnIndex(DataBases.CreateDB.TARGETCHECK));

            item = new ItemData(_id,identName,identNum,targetCheck);

        }


        return item;
    }

    public ArrayList<ItemData> DbTarget(){
        SQLiteDatabase getDb;
        getDb = mDBHelper.getReadableDatabase();
        Cursor c = getDb.rawQuery( "select * from " + DataBases.CreateDB._TABLENAME + " where " + DataBases.CreateDB.TARGETCHECK + " = 1" , null);

        itemDatas = new ArrayList<ItemData>();

        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex(DataBases.CreateDB._ID));
            String identName = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NAME));
            String identNum = c.getString(c.getColumnIndex(DataBases.CreateDB.IDENTIFICATION_NUM));
            int targetCheck = c.getInt(c.getColumnIndex(DataBases.CreateDB.TARGETCHECK));

            ItemData listViewItem = new ItemData(_id,identName,identNum,targetCheck);

            itemDatas.add(listViewItem);

        }


        return itemDatas;
    }

    public void close(){
        mDBHelper.close();
    }

}
