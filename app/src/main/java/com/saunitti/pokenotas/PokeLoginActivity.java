package com.saunitti.pokenotas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.saunitti.BD.DbPerfis;

public class PokeLoginActivity extends AppCompatActivity {
    DbPerfis db;

    TextView nome;
    TextView senha;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_login);
        db = new DbPerfis(getBaseContext());

        nome = (TextView) findViewById(R.id.email);
        senha = (TextView) findViewById(R.id.password);

        if (getIntent() != null
                && getIntent().getExtras() != null
                && getIntent().getExtras().getString("nome") != null
                && getIntent().getExtras().getString("senha") != null
                && !getIntent().getExtras().getString("nome").toString().equals("")
                && !getIntent().getExtras().getString("senha").toString().equals("")) {

            nome.setText(getIntent().getExtras().getString("nome").toString());
            senha.setText(getIntent().getExtras().getString("senha").toString());
        }
    }

    public void Logar(View v) {
        id = db.logar(nome.getText().toString(), senha.getText().toString());
        if (id == 0) {
            Toast.makeText(this, "Nome ou Senha Incorreto!", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, PokeNotasActivity.class);
            i.putExtra("id", id);
            startActivity(i);
        }
    }

    public void Cadastrar(View v) {
        Intent i = new Intent(this, PokePerfilActivity.class);
        startActivity(i);
    }
}
