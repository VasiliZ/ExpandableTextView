package com.github.vasiliz.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandableTextView expandableTextView = findViewById(R.id.view);
        expandableTextView.setContent("qwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewdqwqwdqwedqewd");
    }
}
