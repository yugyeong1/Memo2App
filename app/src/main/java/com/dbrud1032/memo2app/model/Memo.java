package com.dbrud1032.memo2app.model;

import java.io.Serializable;

public class Memo implements Serializable {

    public int id;
    public String title;
    public String content;

    // 디폴트 생성자
    public Memo(){

    }

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Memo(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }


}
