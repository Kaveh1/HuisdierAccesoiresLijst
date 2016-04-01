package com.example.kaveh.huisdieraccesoireslijst;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private DataSource datasource;
    private Accessory accessory;
    private EditText infoEdit;
    private EditText webEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        datasource = new DataSource(this);
        long accessoryId = getIntent().getLongExtra(MainActivity.EXTRA_ACCESSORY_ID, -1);
        accessory = datasource.getAccessory(accessoryId);

        TextView firstTextView = (TextView) findViewById(R.id.textView_info);
        firstTextView.setText(accessory.getAccessory());

        TextView secondTextView = (TextView) findViewById(R.id.textView_web);
        secondTextView.setText(accessory.getWebsite());

        infoEdit = (EditText) findViewById(R.id.details_info_accessory_edittext);
        webEdit = (EditText) findViewById(R.id.details_input_web);

        Button updateButton = (Button) findViewById(R.id.details_updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessory.setAccessory(infoEdit.getText().toString());
                accessory.setWebsite(webEdit.getText().toString());
                datasource.updateAccessory(accessory);
                Toast.makeText(DetailsActivity.this, "Accessoire is geupdatet",
                        Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
