package pnj.ac.id.tmjreg.ui.fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pnj.ac.id.tmjreg.R;
import pnj.ac.id.tmjreg.database.DatabaseHelper;
import pnj.ac.id.tmjreg.ui.activities.EditUserActivity;

public class ProfileFragment extends Fragment {
    TextView edtBod, edtJamLahir, edtEmail, edtNama;
    Button actionEdit;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

        edtBod = view.findViewById(R.id.edtBod);
        edtJamLahir = view.findViewById(R.id.edtJamLahir);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtNama = view.findViewById(R.id.edtNama);
        actionEdit = view.findViewById(R.id.actionEdit);

        SQLiteDatabase database = new DatabaseHelper(getContext()).getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_user WHERE id=?", new String[]{"" + sharedPreferences.getInt("id", 0)});

        cursor.moveToFirst();
        edtBod.setText(cursor.getString(2));
        edtJamLahir.setText(cursor.getString(3));
        edtEmail.setText(cursor.getString(4));
        edtNama.setText(cursor.getString(1));

        cursor.close();
        database.close();

        actionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        SQLiteDatabase database = new DatabaseHelper(getContext()).getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_user WHERE id=?", new String[]{"" + sharedPreferences.getInt("id", 0)});

        cursor.moveToFirst();
        edtBod.setText(cursor.getString(2));
        edtJamLahir.setText(cursor.getString(3));
        edtEmail.setText(cursor.getString(4));
        edtNama.setText(cursor.getString(1));

        cursor.close();
        database.close();
    }
}