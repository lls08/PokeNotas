package com.saunitti.pokenotas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.saunitti.BD.DbHelper;
import com.saunitti.BD.DbPerfis;

public class PokePerfilActivity extends AppCompatActivity {
    private final int perfil = 42;
    private final int excluir = 99;

    DbPerfis db_perfis;
    Cursor c;

    int logado = 0;
    int old_senha = 0;
    String manter_logado = "";

    TextView nome;
    TextView senha;
    TextView confirma;
    Button cadastro;
    Switch stc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_perfil);
        db_perfis = new DbPerfis(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nome = (TextView) findViewById(R.id.poke_nome);
        senha = (TextView) findViewById(R.id.poke_senha);
        confirma = (TextView) findViewById(R.id.poke_senha_confirma);
        cadastro = (Button) findViewById(R.id.poke_cadastro);
        stc = (Switch) findViewById(R.id.poke_stc);

        if (getIntent() != null
                && getIntent().getExtras() != null
                && getIntent().getExtras().getInt("id") != 0) {
            c = db_perfis.getPerfil(Integer.toString(getIntent().getExtras().getInt("id")));

            logado = c.getInt(c.getColumnIndex(DbHelper.COD_PERFIL));
            nome.setText(c.getString(c.getColumnIndex(DbHelper.NOME)));
            old_senha = Integer.parseInt(c.getString(c.getColumnIndex(DbHelper.SENHA)));

            if (c.getString(c.getColumnIndex(DbHelper.MANTER_LOGADO)).toString().equals("S"))
                stc.setChecked(true);
            else
                stc.setChecked(false);

            cadastro.setVisibility(View.INVISIBLE);
        } else {
            cadastro.setVisibility(View.VISIBLE);
            senha.setHint("Senha");
        }
    }

    public void onBackPressed() {
        int new_senha = 0;
        if (logado == 0
                && (nome.getText().toString().equals("")
                || senha.getText().toString().equals("")
                || confirma.getText().toString().equals(""))) {
            Toast.makeText(this, "Cadastrado cancelado", Toast.LENGTH_SHORT).show();

        } else {
            if (senha.getText().toString().equals("")) {
                new_senha = old_senha;
            } else {
                if (senha.getText().length() < 4) {
                    Toast.makeText(this, "Senha muito curta", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirma.getText().toString().equals(senha.getText().toString())) {
                    Toast.makeText(this, "As Senhas não Conferem", Toast.LENGTH_SHORT).show();
                    return;
                }
                new_senha = Integer.parseInt(senha.getText().toString());
            }

            if (stc.isChecked())
                manter_logado = "S";
            else
                manter_logado = "N";

            Toast.makeText(this, db_perfis.alterar(
                    logado,
                    nome.getText().toString(),
                    new_senha,
                    manter_logado
            ), Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra("id", logado);
            setResult(perfil, i);
        }
        finish();
    }

    public void btnPokeCadastro(View v) {

        if (nome.getText().toString().equals("")) {
            Toast.makeText(this, "Preencha o Nome", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.getText().toString().equals("")) {
            Toast.makeText(this, "Preencha a Senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.getText().length() < 4) {
            Toast.makeText(this, "Senha muito curta", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!confirma.getText().toString().equals(senha.getText().toString())) {
            Toast.makeText(this, "As Senhas não Conferem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (stc.isChecked())
            manter_logado = "S";
        else
            manter_logado = "N";

        Toast.makeText(this,
                db_perfis.inserir(nome.getText().toString(), senha.getText().toString(), manter_logado),
                Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, PokeLoginActivity.class);
        Bundle b = new Bundle();
        b.putString("nome", nome.getText().toString());
        b.putString("senha", senha.getText().toString());
        i.putExtras(b);
        startActivity(i);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poke_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_poke_perfil_del) {
            String msg = "";
            if (logado == 0) {
                msg = "Exclusão apenas se estiver logado";
            } else {
                msg = db_perfis.excluir(logado);
                Intent i = new Intent();
                setResult(excluir, i);
                finish();
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
