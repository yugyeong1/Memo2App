package com.dbrud1032.memo2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dbrud1032.memo2app.adapter.MemoAdapter;
import com.dbrud1032.memo2app.data.DatabaseHandler;
import com.dbrud1032.memo2app.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
//    ImageView imgSearch;
    ImageView imgDelete;

    Button btnAdd;

    // 리사이클러뷰를 사용할 때!
    // RecyclerView, Adapter, ArrayList 를 쌍으로 적어라!
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        editSearch = findViewById(R.id.editSearch);
//        imgSearch = findViewById(R.id.imgSearch);
        imgDelete = findViewById(R.id.imgDelete);

        // 리사이클러뷰를 화면에 연결하고,
        // 쌍으로 같이 다니는 코드도 작성.
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        // edittext 에 검색어 입력하면 자동으로 검색어가 포함되는 리스트를 보여주는 코드
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keyword = editSearch.getText().toString().trim();

                // 2글자 이상 입력했을때만, 검색이 되도록 한다.
                if (keyword.length() < 2 && keyword.length() >0 ) {
                    return;
                }

                //  유저가 입력한 키워드 뽑아서
                // db 에서 검색한다. => 메모 리스트를 가져온다.
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.searchMemo(keyword);

                // 화면에 표시
                // 어댑터를 만든다.
                adapter = new MemoAdapter(MainActivity.this, memoList);
                // 어댑터를 리사이클러뷰에 셋팅!
                recyclerView.setAdapter(adapter);

                db.close();
            }
        });


//        imgSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String keyword = editSearch.getText().toString().trim();
//
//                if (keyword.isEmpty()) {
//                   editSearch.setText("");
//                }
//
//
//                // db 에서 검색한다. => 메모 리스트를 가져온다.
//                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
//                memoList = db.searchMemo(keyword);
//
//                // 화면에 표시
//                // 어댑터를 만든다.
//                adapter = new MemoAdapter(MainActivity.this, memoList);
//                // 어댑터를 리사이클러뷰에 셋팅!
//                recyclerView.setAdapter(adapter);
//
//                db.close();
//            }
//        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSearch.setText("");

                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                memoList = db.getAllMemo();
                // 어댑터를 만든다.
                adapter = new MemoAdapter(MainActivity.this, memoList);
                // 어댑터를 리사이클러뷰에 셋팅!
                recyclerView.setAdapter(adapter);

                db.close();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 앱 실행시 저장된 데이터를 화면에 보여준다
        // db 로부터 데이터를 가져와서,
        // 리사이클러뷰에 표시하자.
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllMemo();
        // 어댑터를 만든다.
        adapter = new MemoAdapter(MainActivity.this, memoList);
        // 어댑터를 리사이클러뷰에 셋팅!
        recyclerView.setAdapter(adapter);

        db.close();
    }
}
