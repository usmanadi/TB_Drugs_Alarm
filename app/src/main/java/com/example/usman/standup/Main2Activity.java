package com.example.usman.standup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    static Context cont;
    TextView j,e,f,g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cont = getApplicationContext();
        Intent intent = getIntent();
        String a = intent.getExtras().getString("judul");
        String b = intent.getExtras().getString("bb");
        String c = intent.getExtras().getString("obat");
        String d = intent.getExtras().getString("date");

        j = (TextView)findViewById(R.id.judul2);
        e = (TextView)findViewById(R.id.judulBb2);
        f = (TextView)findViewById(R.id.obat2);
        g = (TextView)findViewById(R.id.judulDate2);

        j.setText(a);
        e.setText(b);
        f.setText(c);
        g.setText(d);

    }
    public static Context getContext(){
        return cont;
    }
}
