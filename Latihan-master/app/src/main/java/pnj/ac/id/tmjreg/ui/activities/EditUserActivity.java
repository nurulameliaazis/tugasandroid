package pnj.ac.id.tmjreg.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pnj.ac.id.tmjreg.R;
import pnj.ac.id.tmjreg.database.DatabaseHelper;
import pnj.ac.id.tmjreg.ui.fragments.profile.ProfileFragment;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtBod, edtJamLahir, edtEmail, edtNama, edtPassword;
    Button actionUpdate;
    SharedPreferences sharedPreferences;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        sharedPreferences = getSharedPreferences("profile", MODE_PRIVATE);

        edtBod = findViewById(R.id.edtBod);
        edtJamLahir = findViewById(R.id.edtJamLahir);
        edtEmail = findViewById(R.id.edtEmail);
        edtNama = findViewById(R.id.edtNama);
        edtPassword = findViewById(R.id.edtPassword);
        actionUpdate = findViewById(R.id.actionUpdate);

        SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_user WHERE id=?", new String[]{"" + sharedPreferences.getInt("id", 0)});

        cursor.moveToFirst();
        edtBod.setText(cursor.getString(2));
        edtJamLahir.setText(cursor.getString(3));
        edtEmail.setText(cursor.getString(4));
        edtNama.setText(cursor.getString(1));
        edtPassword.setText(cursor.getString(5));

        cursor.close();
        database.close();

        edtBod.setOnClickListener(this);
        edtJamLahir.setOnClickListener(this);
        actionUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtBod:
                //event click BOD
                new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edtJamLahir:
                //event click Jam Lahir
                new TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
                break;
            case R.id.actionUpdate:
                if (edtNama.getText().toString().length() > 0 && edtEmail.getText().toString().length() > 0 && edtJamLahir.getText().toString().length() > 0 && edtBod.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0) {
                    updateUserInformation();
                } else {
                    Toast.makeText(this, "Please fill all the blank form", Toast.LENGTH_SHORT).show();
                }

        }
    }

    private void updateUserInformation() {
        SQLiteDatabase database = new DatabaseHelper(this).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", edtNama.getText().toString());
        contentValues.put("email", edtEmail.getText().toString());
        contentValues.put("jam_lahir", edtJamLahir.getText().toString());
        contentValues.put("bod", edtBod.getText().toString());
        contentValues.put("password", edtPassword.getText().toString());

        long update = database.update("tb_user", contentValues, "id=?", new String[]{"" + sharedPreferences.getInt("id", 0)});
        if (update != -1) {
            Toast.makeText(this, "Update Successs", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }

    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            edtBod.setText(dateFormat.format(calendar.getTime()));

        }
    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            edtJamLahir.setText(timeFormat.format(calendar.getTime()));
        }
    };
}