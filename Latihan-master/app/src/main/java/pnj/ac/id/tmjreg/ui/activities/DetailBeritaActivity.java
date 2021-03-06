package pnj.ac.id.tmjreg.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pnj.ac.id.tmjreg.R;

public class DetailBeritaActivity extends AppCompatActivity {

    ImageView imgBerita;
    TextView txtJudul, txtIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        imgBerita = findViewById(R.id.imgBerita);
        txtJudul = findViewById(R.id.txtJudul);
        txtIsi = findViewById(R.id.txtIsiBerita);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            Picasso.get().load(extras.getString("image","")).into(imgBerita);
            txtIsi.setText(extras.getString("isi",""));
            txtJudul.setText(extras.getString("judul",""));
        }

    }
}