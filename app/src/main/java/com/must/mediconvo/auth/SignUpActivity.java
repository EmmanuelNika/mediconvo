package com.must.mediconvo.auth;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.must.mediconvo.R;
import com.must.mediconvo.datastorage.DBHelper;

public class SignUpActivity extends AppCompatActivity {

    private static final int REQUESCODE = 10;

    EditText nameEdit, mobileEdit, emailEdit, passwordEdit, cPasswordEdit;

    String nameString, mobileString, emailString, passwordString, cPasswordString;

    protected DBHelper DB = new DBHelper(SignUpActivity.this);

    private int PReqCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initialization();
    }

    private void initialization() {

        nameEdit = findViewById(R.id.name);
        mobileEdit = findViewById(R.id.phone);
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);
        cPasswordEdit = findViewById(R.id.cPassword);
//        checkAndRequestForPermission();
//        saveLocalData = new SaveLocalData(SignUpActivity.this);
//        showLoader = new ShowLoader(SignUpActivity.this);

    }

    public void signUpImplementation(View view) {
        nameString = nameEdit.getText().toString();
        mobileString = mobileEdit.getText().toString();
        emailString = emailEdit.getText().toString();
        passwordString = passwordEdit.getText().toString();
        cPasswordString = cPasswordEdit.getText().toString();

        if (nameString.isEmpty() || mobileString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty() || cPasswordString.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please verify all the fields", Toast.LENGTH_SHORT).show();

        } else {

            if (passwordString.equals(cPasswordString)) {
                Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();

                addEntry(nameString, mobileString, emailString, passwordString);

            } else {
                Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addEntry(String userName, String phone, String email, String password) {

        SQLiteDatabase db = DB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", userName);
        values.put("phone", phone);
        values.put("email", email);
        values.put("password", password);

        try {
            db.insert(DBHelper.DATABASE_TABLE_NAME, null, values);

            Toast.makeText(getApplicationContext(), "User registered successfully...", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), String.format("Error registering user! %s", e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        DB.close();
    }
}
