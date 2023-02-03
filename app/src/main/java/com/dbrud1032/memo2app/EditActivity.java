package com.dbrud1032.memo2app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dbrud1032.memo2app.data.DatabaseHandler;
import com.dbrud1032.memo2app.model.Memo;

public class EditActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSave;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // 화면 연결
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        Memo memo = (Memo) getIntent().getSerializableExtra("memo");
        id = memo.id;

        editTitle.setText(memo.title);
        editContent.setText(memo.content);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();

                // 메모가 비어있을 경우 토스트 띄우기
                if (title.isEmpty() || content.isEmpty()){
                    Toast.makeText(EditActivity.this, "필수 항목 입니다. 메모를 작성해주세요", Toast.LENGTH_SHORT).show();
                }

                // db 에 저장한다.
                DatabaseHandler db = new DatabaseHandler(EditActivity.this);

                // 이름, 전화번호
                Memo memo = new Memo(id, title, content);

                db.updateMemo(memo);

                // 이 액티비티를 종료하고, 원래 있던 메인엑티비티가 나오도록!
                finish();




            }
        });


    }
}