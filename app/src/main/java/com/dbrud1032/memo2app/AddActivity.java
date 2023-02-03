package com.dbrud1032.memo2app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dbrud1032.memo2app.data.DatabaseHandler;
import com.dbrud1032.memo2app.model.Memo;

public class AddActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddActivity.this, "필수항목 입니다. 메모를 작성해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 묶어서 처리할 Memo 객체를 하나 만든다.
                Memo memo = new Memo(title, content);

                // DB 에 저장한다.
                DatabaseHandler db = new DatabaseHandler(AddActivity.this);
                db.addMemo(memo);

                // 유저한테 잘 저장되었다고, 알려주고
                Toast.makeText(AddActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();

                // 다 완료되면, 메모생성 화면은 필요가 없다.
                // 따라서 이 액티비티는 종료
                finish();



            }
        });

    }
}