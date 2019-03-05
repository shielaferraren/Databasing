package com.databasing.shiela.databasing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etConfirmPassword, etName;
    RadioButton rbMale, rbFemale;
    String username, password, confirmPassword, name, gender;
    int formsuccess;
    DbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DbHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etName = findViewById(R.id.etName);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_cancel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSave:
                formsuccess = 6;

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                name = etName.getText().toString();
                gender = "Male";

                if (username.equals("")) {
                    etUsername.setError("This field is required");
                    formsuccess--;
                }

                if (password.equals("")) {
                    etPassword.setError("This field is required");
                    formsuccess--;
                }

                if (!confirmPassword.equals(password)) {
                    etConfirmPassword.setError("Password mismatch");
                    formsuccess--;
                }

                if (name.equals("")) {
                    etName.setError("This field is required");
                    formsuccess--;
                }

                if (confirmPassword.equals("")) {
                    etConfirmPassword.setError("This field is required");
                    formsuccess--;
                }

                if (rbFemale.isChecked()) {
                    gender = "Female";
                }

                if (formsuccess==6) {
                    HashMap<String, String> map_user = new HashMap();
                    map_user.put(db.TBL_USER_USERNAME, username);
                    map_user.put(db.TBL_USER_PASSWORD, password);
                    map_user.put(db.TBL_USER_NAME, name);
                    map_user.put(db.TBL_USER_GENDER, gender);

                    if(db.addUser(map_user) > 0) {
                        etUsername.setError("Username already taken");
                    }
                    else {
                        Toast.makeText(this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }

                }

                break;
            case R.id.btnCancel:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


