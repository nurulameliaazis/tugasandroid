package pnj.ac.id.tmjreg.ui.fragments.notification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pnj.ac.id.tmjreg.R;
import pnj.ac.id.tmjreg.adapter.AdapterUser;
import pnj.ac.id.tmjreg.database.DatabaseHelper;
import pnj.ac.id.tmjreg.model.UserModel;

public class NotificationFragment extends Fragment {

    ListView listView;
    AdapterUser adapterUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listViewUser);
        adapterUser = new AdapterUser(getActivity(), R.layout.item_user);
        listView.setAdapter(adapterUser);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel userModel = (UserModel) parent.getAdapter().getItem(position);

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Do you want to delete this User?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase database = new DatabaseHelper(getActivity()).getWritableDatabase();
                        long delete = database.delete("tb_user", "id=?", new String[]{"" + userModel.getId()});
                        if (delete != -1) {
                            Toast.makeText(getActivity(), "Delete Success", Toast.LENGTH_SHORT).show();
                            getData();
                        } else {
                            Toast.makeText(getActivity(), "Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.show();

                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        adapterUser.clear();
        ArrayList<UserModel> datas = new ArrayList<>();
        SQLiteDatabase database = new DatabaseHelper(getContext()).getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_user", null);

        if (cursor.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(cursor.getInt(0));
                userModel.setNama(cursor.getString(1));
                userModel.setBod(cursor.getString(2));
                userModel.setJamLahir(cursor.getString(3));
                userModel.setEmail(cursor.getString(4));
                datas.add(userModel);
            } while (cursor.moveToNext());
        }
        adapterUser.addAll(datas);
        adapterUser.notifyDataSetChanged();
    }
}