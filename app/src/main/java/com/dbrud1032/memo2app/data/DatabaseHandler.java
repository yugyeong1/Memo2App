package com.dbrud1032.memo2app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dbrud1032.memo2app.util.Util;
import com.dbrud1032.memo2app.model.Memo;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DB_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 테이블 생성
        String CREATE_CONTACT_TABLE = "create table memo ( id integer primary key, title text, content text )";

        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 기존의 테이블을 삭제하고, 새 테이블을 다시 만든다.

        String DROP_TABLE = "drop table memo";
        sqLiteDatabase.execSQL(DROP_TABLE);

        onCreate(sqLiteDatabase);

    }

    // CRUD 부분!!
    // C 부분
    public void addMemo(Memo memo) {
        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. 저장가능한 형식으로 만들어 준다.
        ContentValues values = new ContentValues();
        values.put(Util.KEY_Title, memo.title);
        values.put(Util.KEY_CONTENT, memo.content);

        // 3. insert 한다.
        db.insert(Util.TABLE_NAME, null, values);

        // 4. db 사용이 끝나면, 닫아준다.
        db.close();

    }

    // 2. 저장된 메모를 모두 가져오는 메소드 R
    public ArrayList<Memo> getAllMemo(){

        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. 쿼리문 만든다.
        String query = "select * from memo";


        // 3. 쿼리문을 실행하여, 커서로 받는다.
        Cursor cursor = db.rawQuery(query, null);

        // 3-1. 여러 데이터를 저장할 어레이리스트 만든다.
        ArrayList<Memo> MemoArrayList = new ArrayList<>();

        // 4. 커서에서 데이터를 뽑아낸다.
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);

                Log.i("Memo_TABLE", id +", " + title +", " + content);

                // 이 데이터를, 화면에 표시하기 위해서는
                // 메모리에 전부 다 남아있어야 한다!
                Memo memo = new Memo(id, title, content);
                MemoArrayList.add(0, memo);

            } while (cursor.moveToNext());
        }

        // 5. db 닫기
        db.close();

        // 6. DB에서 읽어온 연락처 정보를 리턴해야 한다.
        return MemoArrayList;
    }

    // U 부분
    public void updateMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update memo " +
                        "set title = ?, content = ? " +
                        "where id =?";

        db.execSQL(query, new String[] { memo.title, memo.content, memo.id+"" });

        db.close();
    }

    // D 부분
    public void deleteMemo(Memo memo) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "delete from memo " +
                "where id = ?";

        String[] args = new String[] { memo.id +""};

        db.execSQL(query, args);
        db.close();

    }

    public ArrayList<Memo> searchMemo(String keyword) {
        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. 쿼리문 만든다.
        String query = "select * from memo " +
                        "where content like '%" + keyword + "%'" +
                " or title like '%"+ keyword + "%'";


        // 3. 쿼리문을 실행하여, 커서로 받는다.
        Cursor cursor = db.rawQuery(query, null);

        // 3-1. 여러 데이터를 저장할 어레이리스트 만든다.
        ArrayList<Memo> MemoArrayList = new ArrayList<>();

        // 4. 커서에서 데이터를 뽑아낸다.
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);

                Log.i("Memo_TABLE", id +", " + title +", " + content);

                // 이 데이터를, 화면에 표시하기 위해서는
                // 메모리에 전부 다 남아있어야 한다!
                Memo memo = new Memo(id, title, content);
                MemoArrayList.add(0, memo);

            } while (cursor.moveToNext());
        }

        // 5. db 닫기
        db.close();

        // 6. DB에서 읽어온 연락처 정보를 리턴해야 한다.
        return MemoArrayList;

    }
}
