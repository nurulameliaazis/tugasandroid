package pnj.ac.id.tmjreg.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pnj.ac.id.tmjreg.R;
import pnj.ac.id.tmjreg.database.DatabaseHelper;


public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button actionLogin;
    TextView txtRegister;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("profile", MODE_PRIVATE);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        actionLogin = findViewById(R.id.actionLogin);
        txtRegister = findViewById(R.id.txtRegister);

        actionLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0) {
                    //cek login
                    SQLiteDatabase database = new DatabaseHelper(LoginActivity.this).getReadableDatabase();
                    Cursor cursor = database.rawQuery("SELECT * FROM tb_user WHERE email=? and password=?", new String[]{edtEmail.getText().toString(), edtPassword.getText().toString()});

                    if (cursor.getCount() > 0) {
                        //sukses login
                        SharedPreferences.Editor editor = preferences.edit();

                        cursor.moveToFirst();
                        editor.putInt("id", cursor.getInt(0));
                        editor.putString("email", cursor.getString(4));
                        editor.putString("nama", cursor.getString(1));
                        editor.putString("bod", cursor.getString(2));
                        editor.putString("jam_lahir", cursor.getString(3));
                        editor.putBoolean("isLogin", true);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //gagal login
                        Toast.makeText(LoginActivity.this, "Email & Password Incorrect", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    database.close();
//                    if (edtEmail.getText().toString().equals("syamsi@mail.com") && edtPassword.getText().toString().equals("1234")) {
//                        //sukses login
//                        SharedPreferences.Editor edit = preferences.edit();
//
//                        edit.putString("email", "syamsi@mail.com");
//                        edit.putString("nama", "syamsi dwi cahya");
//                        edit.putBoolean("isLogin", true);
//                        edit.commit();
//
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Mohon Maaf Email dan Password Salah", Toast.LENGTH_SHORT).show();
//                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Mohon Lengkapi Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}