package com.saunitti.pokenotas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.saunitti.BD.DbNotas;
import com.saunitti.BD.DbPerfis;
import com.saunitti.objetos.PokeLista;
import com.saunitti.objetos.PokeNota;

import java.util.ArrayList;

public class PokeNotasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int nova_nota = 0;
    private final int altera_nota = 1;
    private final int altera_perfil = 42;
    private final int exclui_perfil = 99;

    DbNotas db_notas;
    DbPerfis db_perfis;

    ListView lista;

    private int perfilID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_notas);
        db_notas = new DbNotas(getBaseContext());
        db_perfis = new DbPerfis(getBaseContext());
        perfilID = getIntent().getExtras().getInt("id");

        ImageView fundo = (ImageView) findViewById(R.id.poke_menu_fundo);
        fundo.setAlpha(127);

        Toolbar toolbar = (Toolbar) findViewById(R.id.poke_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.poke_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPokeNota(null);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.poke_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.poke_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listarNotas();
    }

    public void onActivityResult(int request, int result, Intent i) {
        if (result == nova_nota && i != null) {
            listarNotas();
        } else if (result == 1) {
            Toast.makeText(this, "PokeNota cancelada!", Toast.LENGTH_SHORT).show();
        } else if (result == exclui_perfil) {
            btnPokeSair(null);
        }
    }

    private void listarNotas() {
        final ArrayList<PokeNota> notas = db_notas.getLista(Integer.toString(perfilID));

        lista = (ListView) findViewById(R.id.poke_nota_lista);
        PokeLista adapter = new PokeLista(this, notas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(PokeNotasActivity.this, PokeNotaActivity.class);

                i.putExtra("id", Integer.toString(notas.get(position).getCodigo()));
                startActivityForResult(i, altera_nota);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alert;

                AlertDialog.Builder builder = new AlertDialog.Builder(PokeNotasActivity.this);
                builder.setTitle("PokeNote");
                builder.setMessage("Deseja excluir esta nota?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        PokeNotasActivity.this.db_notas.excluir(
                                notas.get(position).getCodigo());
                        listarNotas();

                        Toast.makeText(PokeNotasActivity.this, "Exclusão efetuada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(PokeNotasActivity.this, "Exclusão cancelada!", Toast.LENGTH_SHORT).show();
                    }
                });

                alert = builder.create();
                alert.show();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.poke_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(this, "You don't have a power here! Para sair use o menu.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poke_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onNavigationItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_poke_nota_add) {
            btnPokeNota(null);
        } else if (id == R.id.nav_poke_perfil) {
            btnPokePerfil(null);
        } else if (id == R.id.nav_sair) {
            btnPokeSair(null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.poke_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void btnPokeNota(View v) {
        Intent i = new Intent(this, PokeNotaActivity.class);
        i.putExtra("perfilID", perfilID);
        startActivityForResult(i, nova_nota);
    }

    public void btnPokePerfil(View v) {
        Intent i = new Intent(this, PokePerfilActivity.class);
        i.putExtra("id", perfilID);
        startActivityForResult(i, altera_perfil);
    }

    public void btnPokeSair(View v) {
        db_perfis.ultimoLogado(perfilID, "N");
        perfilID = 0;
        Intent i = new Intent(this, PokeLoginActivity.class);
        startActivity(i);
    }
}

