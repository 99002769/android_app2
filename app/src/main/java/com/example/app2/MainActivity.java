package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.example.app2.database.dbAccessObj;
import com.example.app2.database.FeedReaderContract.FeedEntry;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MYPREFS = "myprefs";
    public static final String NAMEKEY = "namekey";
    public static final String PWDKEY = "pwdkey";
    EditText nameEditText,pwdEditText;
    dbAccessObj dbAccessObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate");
        nameEditText = findViewById(R.id.TextName);
        pwdEditText = findViewById(R.id.TextPassword);
        dbAccessObj = new dbAccessObj(this);
        dbAccessObj.openDb();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
        ListView dbListView = findViewById(R.id.dblistview);
        //Cursor dataCursor = dbAccessObj.getRows();
        //Uri uriSms = Uri.parse("content://sms/inbox");
        Uri callogUri = CallLog.Calls.CONTENT_URI;
        Cursor dataCursor =  getContentResolver().query(callogUri,null,null,null,null);
        //put the data into adapter
        CursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.row_listview,
                dataCursor,
               // new String[]{"body","address"},
                new String[]{CallLog.Calls.NUMBER,CallLog.Calls.CACHED_NAME},
                //FeedEntry.COLUMN_NAME_TITLE,FeedEntry.COLUMN_NAME_SUBTITLE},
                //"title","subtitle"},
                //"title","subtitle"},
                new int[] {R.id.textviewRow,R.id.textViewsubtitle});
        //set the adapter onto the listview
        dbListView.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
        saveData();
    }

    private void saveData() {
        Log.i(TAG,"saveData");
        String name = nameEditText.getText().toString();
        String pwd = pwdEditText.getText().toString();
        SharedPreferences preferences = getSharedPreferences(MYPREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAMEKEY,name);
        editor.putString(PWDKEY,pwd);
        editor.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        restoreData();
    }

    private void restoreData() {
        Log.i(TAG,"restoreData");
        SharedPreferences preferences = getSharedPreferences(MYPREFS,MODE_PRIVATE);
        String name = preferences.getString(NAMEKEY,"");
        String pwd = preferences.getString(PWDKEY,"");
        nameEditText.setText(name);
        pwdEditText.setText(pwd);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    public void clickHandler(View view) {


        Log.e(TAG,"clickHandler");

        switch (view.getId()){
            case R.id.buttonLogin:
                getCredentials();
                //showHome();
                break;
            case R.id.buttonCancel:
                //Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:1234567" ));
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234567" ));
                startActivity(dialIntent);
                break;
        }

    }
    private void getCredentials() {
       // String pwd = dbAccessObj.query(nameEditText.getText().toString());
        //pwdEditText.setText(pwd);
        dbAccessObj.query(nameEditText.getText().toString());
    }


    private void showHome() {
        Intent hintent = new Intent(MainActivity.this, HomeActivity.class);
        hintent.putExtra("mykey","Divya");
        int c = add(10,20);
        startActivity(hintent);
    }

    private int add(int a, int b) {
        return a+b;
    }

    public void handleDB(View view) {
        switch(view.getId())
        {
            case R.id.buttonput:
                String title = nameEditText.getText().toString();
                String subtitle = pwdEditText.getText().toString();
                dbAccessObj.createRow(title,subtitle);
                break;
            case R.id.buttonget:
                String data =  dbAccessObj.readRow();
                //set the data onto textview
                TextView dbTextView = findViewById(R.id.textViewdb);
                dbTextView.setText(data);
                break;
        }
    }
}