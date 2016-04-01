package com.example.kaveh.huisdieraccesoireslijst;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddAccessoryActivity extends AppCompatActivity {

    private EditText infoEditText;
    private EditText webEditText;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accessory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize datasource and EditText
        infoEditText = (EditText) findViewById(R.id.add_info_accessory_edittext);
        webEditText = (EditText) findViewById(R.id.input_web);
        datasource = new DataSource(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long accessoryInfo = datasource.createAccessory(infoEditText.getText().toString(),
                        webEditText.getText().toString());
                Intent intent = new Intent(AddAccessoryActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_ACCESSORY_ID, accessoryInfo);
                intent.putExtra(MainActivity.EXTRA_ACCESSORY_WEB, accessoryInfo);
                startActivity(intent);
                finish();
                Toast.makeText(AddAccessoryActivity.this, "Accessoire is toegevoegd",
                        Toast.LENGTH_SHORT).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
