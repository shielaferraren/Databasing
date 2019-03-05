package com.databasing.shiela.databasing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EditUserActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etConfirmPassword, etName;
    RadioButton rbMale, rbFemale;
    String username, password, confirmpassword, name, gender;
    int formsuccess, userID;

    DbHelper db;

    ArrayList<HashMap<String, String>> selected_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = new DbHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etName = findViewById(R.id.etName);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        Intent intent = getIntent();
        userID = intent.getIntExtra(db.TBL_USER_ID, 0);

        selected_user = db.getSelectedUserData(userID);

        etUsername.setText(selected_user.get(0).get(db.TBL_USER_USERNAME));
        etPassword.setText(selected_user.get(0).get(db.TBL_USER_PASSWORD));
        etConfirmPassword.setText(selected_user.get(0).get(db.TBL_USER_PASSWORD));
        etName.setText(selected_user.get(0).get(db.TBL_USER_NAME));
        if (selected_user.get(0).get(db.TBL_USER_GENDER).equals("Female")) {
            rbFemale.setChecked(true);
        }
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

                formsuccess = 5;
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                confirmpassword = etConfirmPassword.getText().toString();
                name = etName.getText().toString();
                gender = "Male";
                if (rbFemale.isChecked()) {
                    gender = "Female";
                }

                if (username.equals("")) {
                    etUsername.setError("This Field is required");
                    formsuccess--;
                }

                if (password.equals("")) {
                    etPassword.setError("This Field is required");
                    formsuccess--;
                }

                if (!confirmpassword.equals(password)) {
                    etConfirmPassword.setError("Password Mismatched");
                    formsuccess--;
                }

                if (confirmpassword.equals("")) {
                    etConfirmPassword.setError("This Field is required");
                    formsuccess--;
                }

                if (name.equals("")) {
                    etName.setError("This Field is required");
                    formsuccess--;
                }

                if (formsuccess == 5) {
                    HashMap<String, String> map_user = new HashMap();
                    map_user.put(db.TBL_USER_ID, String.valueOf(userID));
                    map_user.put(db.TBL_USER_USERNAME, username);
                    map_user.put(db.TBL_USER_PASSWORD, password);
                    map_user.put(db.TBL_USER_NAME, name);
                    map_user.put(db.TBL_USER_GENDER, gender);

                    db.updateUser(map_user);
                    Toast.makeText(this, "Data Successfully Updated", Toast.LENGTH_SHORT).show();
                    this.finish();
                }

                break;
            case R.id.btnCancel:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}