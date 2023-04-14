package com.must.mediconvo.auth;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.must.mediconvo.MainActivity;
import com.must.mediconvo.R;
import com.must.mediconvo.datastorage.DBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText phoneEdit, passwordEdit;
    String phoneString, passwordString;

    DBHelper DB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initialization();
    }

    public void initialization() {
        phoneEdit = findViewById(R.id.phone);
        passwordEdit = findViewById(R.id.password);
    }

    public void loginImplementation(View view) {
        phoneString = phoneEdit.getText().toString();
        passwordString = passwordEdit.getText().toString();

        if (phoneString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please verify all the fields", Toast.LENGTH_SHORT).show();

        } else {

            boolean validLogin = validateLogin(phoneString, passwordString, getBaseContext());
            if (validLogin) {
                //System.out.println("Invalid");
                Intent in = new Intent(getBaseContext(), MainActivity.class);
                in.putExtra("UserName", phoneString.toString());

                // starts main activity class
                startActivity(in);
                // finish();
            }

        }
    }

    private boolean validateLogin(String username, String password, Context baseContext) {
        DB = new DBHelper(getBaseContext());
        SQLiteDatabase db = DB.getReadableDatabase();

        String[] columns = {"_id"};

        String selection = "username=? AND password=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = null;
        try {
            cursor = db.query(DBHelper.DATABASE_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            startManagingCursor(cursor);

        } catch (Exception e) {
            e.printStackTrace();
        }

        int numberOfRows = cursor.getCount();


        if (numberOfRows <= 0) {
            Toast.makeText(getApplicationContext(), "Phone and Password miss match...\nPlease Try Again", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));

            return false;
        }

        return true;
    }
}
