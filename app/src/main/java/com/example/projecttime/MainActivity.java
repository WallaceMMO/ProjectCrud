/*
 *@author:<Wallace Moura Machado de Oliveira;1110482413004>
 */
package com.example.projecttime;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_principal);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragmento = null;

        if (item.getItemId() == R.id.menu_time) {
            fragmento = new TimeFragment();
        } else if (item.getItemId() == R.id.menu_jogador) {
            fragmento = new JogadorFragment();
        } else {
            return super.onOptionsItemSelected(item);
        }

        if (fragmento != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragmento)
                    .commit();
        }
        return true;
    }
}