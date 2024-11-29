/*
 * Nome: Felipe Bernardes Cisilo
 * RA: 1110482413017
 */
package br.edu.fateczl.biblioteca2.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import br.edu.fateczl.biblioteca2.R;
import br.edu.fateczl.biblioteca2.view.fragments.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Nome: Felipe Bernardes Cisilo - RA: 1110482413017");
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.menu_livro) {
            fragment = new LivroFragment();
        } else if (itemId == R.id.menu_revista) {
            fragment = new RevistaFragment();
        } else if (itemId == R.id.menu_aluno) {
            fragment = new AlunoFragment();
        } else if (itemId == R.id.menu_aluguel) {
            fragment = new AluguelFragment();
        }

        if (fragment != null) {
            loadFragment(fragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}