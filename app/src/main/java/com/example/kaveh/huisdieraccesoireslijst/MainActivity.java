package com.example.kaveh.huisdieraccesoireslijst;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Views
    private ListView listView;
    public static final String EXTRA_ACCESSORY_ID = "extraAccessory";
    public static final String EXTRA_ACCESSORY_WEB = "extraWeb";

    //Adapter and Datasource
    private ArrayAdapter<Accessory> accessoryArrayAdapter;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize the views
        listView = (ListView) findViewById(R.id.accessorie_list);

        //Set empty view for ListView
        TextView emptyView = (TextView) findViewById(R.id.empty_accessorie_list);
        listView.setEmptyView(emptyView);

        //Initialize datasource
        datasource = new DataSource(this);

        //Create the list of accessories
        List<Accessory> accessories = datasource.getAllAccessories();

        //Create the Array Adapter, give it a layout and a list of values
        accessoryArrayAdapter = new ArrayAdapter<Accessory>(this,
                android.R.layout.simple_list_item_1, accessories);

        //Set the newly created adapter as the adapter for the lisview
        listView.setAdapter(accessoryArrayAdapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(MainActivity.EXTRA_ACCESSORY_ID, accessoryArrayAdapter.getItem(position).getId());
                startActivityForResult(intent, 2);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddAccessoryActivity.class),1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Handle data
                long assignmentId = data.getLongExtra(EXTRA_ACCESSORY_ID, -1);
                if (assignmentId != -1) {
                    Accessory accessory = datasource.getAccessory(assignmentId);
                    accessoryArrayAdapter.add(accessory);
                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateAccessoryListView();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Verwijderen van accessoire");
        menu.add(0, v.getId(), 0, "Verwijderen");
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        if (item.getTitle() == "Verwijderen") {
            Toast.makeText(getApplicationContext(), "Accessoire is verwijderd",
                    Toast.LENGTH_LONG).show();
            Accessory accessory = accessoryArrayAdapter.getItem(info.position);
            accessoryArrayAdapter.remove(accessory);
            datasource.deleteAssignment(accessory);

            updateAccessoryListView();
        } else {
            return false;
        }
        return true;
    }

    public void updateAccessoryListView() {
        List<Accessory> accessories = datasource.getAllAccessories();
        accessoryArrayAdapter = new ArrayAdapter<Accessory>(this,
                android.R.layout.simple_list_item_1, accessories);
        listView.setAdapter(accessoryArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
