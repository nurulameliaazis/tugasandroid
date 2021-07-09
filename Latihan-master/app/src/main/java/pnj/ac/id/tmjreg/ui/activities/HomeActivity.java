package pnj.ac.id.tmjreg.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pnj.ac.id.tmjreg.R;
import pnj.ac.id.tmjreg.ui.fragments.home.HomeFragment;
import pnj.ac.id.tmjreg.ui.fragments.notification.NotificationFragment;
import pnj.ac.id.tmjreg.ui.fragments.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    HomeFragment homeFragment = new HomeFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    SharedPreferences preferences;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = getSharedPreferences("profile", MODE_PRIVATE);
        setTitle(preferences.getString("nama", ""));

        bottomNavigation = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, homeFragment).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener);
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.menu_home:
                selectedFragment = homeFragment;
                break;
            case R.id.menu_notification:
                selectedFragment = notificationFragment;
                break;
            case R.id.menu_profile:
                selectedFragment = profileFragment;
                break;
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
        }
        return false;
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.actionMenu1:
//                Toast.makeText(this, "Menu 1 dipilih", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.actionMenu2:
//                Toast.makeText(this, "Menu 2 dipilih", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.actionMenu3:
//                Toast.makeText(this, "Menu 3 dipilih", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}