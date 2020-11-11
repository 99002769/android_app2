package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    String[] languages;
    private static final String TAG = HomeActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.w(TAG,"onCreate");
        languages = new String[]{"english","hindi","urdu","kannada"};
        //get the intent which started this activity
        Intent intent = getIntent();
        //from the intent get the extras
        Bundle extras = intent.getExtras();
        //from extras get the string with the key = mykey
        if(extras!= null){
            String data = extras.getString("mykey");
            //put the string extraCTED into a textview
            TextView resultTextView = findViewById(R.id.textViewResult);
            resultTextView.setText(data);
        }

        ListView countriesListView = findViewById(R.id.countriesListview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.row_listview,
                // android.R.layout.simple_list_item_1, //layout file of each row in the listview
                languages);
        countriesListView.setAdapter(adapter);
    }
}