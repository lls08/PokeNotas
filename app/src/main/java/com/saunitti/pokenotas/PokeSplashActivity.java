package com.saunitti.pokenotas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.saunitti.BD.DbPerfis;

public class PokeSplashActivity extends AppCompatActivity {

    ProgressBar progresso;
    DbPerfis db_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_splash);
        db_perfil = new DbPerfis(getBaseContext());

        progresso = (ProgressBar) findViewById(R.id.progressBar);
        progresso.setProgress(40);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                progresso.setProgress(60);
                Intent i = null;

                if (!db_perfil.verificaPerfis()) {
                    i = new Intent(PokeSplashActivity.this, PokePerfilActivity.class);
                } else {
                    progresso.setProgress(80);

                    int perfilId = db_perfil.getLogado();

                    if (perfilId != 0) {
                        i = new Intent(PokeSplashActivity.this, PokeNotasActivity.class);
                        i.putExtra("id",perfilId);
                    } else {
                        i = new Intent(PokeSplashActivity.this, PokeLoginActivity.class);
                    }
                }
                startActivity(i);

                finish();
                progresso.setProgress(100);
            }
        }, 100);
    }
}
