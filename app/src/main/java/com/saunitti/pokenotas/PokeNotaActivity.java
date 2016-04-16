package com.saunitti.pokenotas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.saunitti.BD.DbNotas;
import com.saunitti.objetos.Pokemon;
import com.saunitti.objetos.PokeNota;

import java.util.Random;

public class PokeNotaActivity extends ActionBarActivity {
    private final int salvar = 0;
    private final int cancelar = 1;

    DbNotas db_notas;
    Cursor c;
    int perfilID = 0;

    EditText titulo;
    EditText texto;
    Button btn;
    PokeNota nota = new PokeNota();

    ImageView fundo;
    int fundo_aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_nota);
        db_notas = new DbNotas(getBaseContext());

        titulo = (EditText) findViewById(R.id.poke_nota_titulo);
        texto = (EditText) findViewById(R.id.poke_nota_texto);
        fundo = (ImageView) findViewById(R.id.poke_fundo);
        fundo.setAlpha(127);

        perfilID = getIntent().getExtras().getInt("perfilID");

        if (getIntent() != null
                && getIntent().getExtras() != null
                && getIntent().getExtras().getString("id") != null
                && !getIntent().getExtras().getString("id").equals("")) {
            nota = db_notas.getNota(getIntent().getExtras().getString("id"));

            titulo.setText(nota.getTitulo());
            texto.setText(nota.getTexto());

            Random gerador = new Random();
            fundo_aux = nota.getFundo();
            if (Evoluir(nota.getFundo()))//Se o pokemon pode Evoluir
                if (gerador.nextInt(2) == 1) {//20==19
                    Toast.makeText(this, "Parabéns, sua PokeNota Evoluiu!!!", Toast.LENGTH_SHORT).show();
                    if (fundo_aux == 133) {//Eve -> Fogo|Agua|Eletr
                        fundo_aux += gerador.nextInt(3) + 1;
                    } else {
                        fundo_aux++;
                    }
                }
            new Pokemon().Fundo(fundo_aux, fundo);

        } else {
            GeraFundo();
        }
    }

    public void btnNotaVoltar(View v) {
        btn = (Button) findViewById(v.getId());

        if (btn.getText().toString().equals("Salvar")) {
            if (titulo.getText().toString().equals("")) {
                Toast.makeText(this, "Insira um Título para Salvar", Toast.LENGTH_SHORT).show();
            } else if (texto.getText().toString().equals("")) {
                Toast.makeText(this, "Insira uma Anotação para Salvar", Toast.LENGTH_SHORT).show();
            } else if (!titulo.getText().toString().equals("") && !texto.getText().toString().equals("")) {

                if (getIntent() != null
                        && getIntent().getExtras() != null
                        && getIntent().getExtras().getString("id") != null
                        && !getIntent().getExtras().getString("id").equals("")) {
                    Toast.makeText(this,
                            db_notas.alterar(nota.getCodigo(),
                                    titulo.getText().toString(),
                                    texto.getText().toString(),
                                    fundo_aux),
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,
                            db_notas.inserir(titulo.getText().toString(), texto.getText().toString(), fundo_aux, perfilID),
                            Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent();
                setResult(salvar, i);
                finish();
            }
        } else if (btn.getText().toString().equals("Cancelar")) {
            Intent i = new Intent();
            setResult(cancelar, i);
            finish();
        }
    }

    public void GeraFundo() {
        Random gerador = new Random();
        fundo_aux = gerador.nextInt(152) + 1;

        fundo_aux = ValidaFundoInicial(fundo_aux);
        if (fundo_aux > 143) {
            fundo_aux = gerador.nextInt(152);
        }

        new Pokemon().Fundo(fundo_aux, fundo);
    }

    public boolean Evoluir(int fundo_aux) {
        if (fundo_aux == 3
                || fundo_aux == 6
                || fundo_aux == 9
                || fundo_aux == 12
                || fundo_aux == 15
                || fundo_aux == 18
                || fundo_aux == 20
                || fundo_aux == 22
                || fundo_aux == 24
                || fundo_aux == 26
                || fundo_aux == 28
                || fundo_aux == 31
                || fundo_aux == 34
                || fundo_aux == 36
                || fundo_aux == 38
                || fundo_aux == 40
                || fundo_aux == 42
                || fundo_aux == 45
                || fundo_aux == 47
                || fundo_aux == 49
                || fundo_aux == 51
                || fundo_aux == 53
                || fundo_aux == 55
                || fundo_aux == 57
                || fundo_aux == 59
                || fundo_aux == 62
                || fundo_aux == 65
                || fundo_aux == 68
                || fundo_aux == 71
                || fundo_aux == 73
                || fundo_aux == 76
                || fundo_aux == 78
                || fundo_aux == 80
                || fundo_aux == 82
                || fundo_aux == 83
                || fundo_aux == 85
                || fundo_aux == 87
                || fundo_aux == 89
                || fundo_aux == 91
                || fundo_aux == 94
                || fundo_aux == 95
                || fundo_aux == 97
                || fundo_aux == 99
                || fundo_aux == 101
                || fundo_aux == 103
                || fundo_aux == 105
                || fundo_aux == 106
                || fundo_aux == 107
                || fundo_aux == 108
                || fundo_aux == 110
                || fundo_aux == 112
                || fundo_aux == 113
                || fundo_aux == 114
                || fundo_aux == 115
                || fundo_aux == 117
                || fundo_aux == 119
                || fundo_aux == 121
                || fundo_aux == 122
                || fundo_aux == 123
                || fundo_aux == 124
                || fundo_aux == 125
                || fundo_aux == 126
                || fundo_aux == 127
                || fundo_aux == 128
                || fundo_aux == 130
                || fundo_aux == 132
                || fundo_aux == 134
                || fundo_aux == 135
                || fundo_aux == 136
                || fundo_aux == 137
                || fundo_aux == 139
                || fundo_aux == 141
                || fundo_aux == 142
                || fundo_aux == 143
                || fundo_aux == 144
                || fundo_aux == 145
                || fundo_aux == 146
                || fundo_aux == 149
                || fundo_aux == 150
                || fundo_aux == 151
                || fundo_aux == 152) {
            return false;
        } else {
            return true;
        }
    }

    public int ValidaFundoInicial(int fundo_aux) {
        if (fundo_aux == 2 || fundo_aux == 3) {
            fundo_aux = 1;
        } else if (fundo_aux == 5 || fundo_aux == 6) {
            fundo_aux = 4;
        } else if (fundo_aux == 8 || fundo_aux == 9) {
            fundo_aux = 7;
        } else if (fundo_aux == 11 || fundo_aux == 12) {
            fundo_aux = 10;
        } else if (fundo_aux == 14 || fundo_aux == 15) {
            fundo_aux = 13;
        } else if (fundo_aux == 17 || fundo_aux == 18) {
            fundo_aux = 16;
        } else if (fundo_aux == 20) {
            fundo_aux = 19;
        } else if (fundo_aux == 22) {
            fundo_aux = 21;
        } else if (fundo_aux == 24) {
            fundo_aux = 23;
        } else if (fundo_aux == 26) {
            fundo_aux = 25;
        } else if (fundo_aux == 28) {
            fundo_aux = 27;
        } else if (fundo_aux == 30 || fundo_aux == 31) {
            fundo_aux = 29;
        } else if (fundo_aux == 33 || fundo_aux == 34) {
            fundo_aux = 32;
        } else if (fundo_aux == 36) {
            fundo_aux = 35;
        } else if (fundo_aux == 38) {
            fundo_aux = 37;
        } else if (fundo_aux == 40) {
            fundo_aux = 39;
        } else if (fundo_aux == 42) {
            fundo_aux = 41;
        } else if (fundo_aux == 44 || fundo_aux == 45) {
            fundo_aux = 43;
        } else if (fundo_aux == 47) {
            fundo_aux = 46;
        } else if (fundo_aux == 49) {
            fundo_aux = 48;
        } else if (fundo_aux == 51) {
            fundo_aux = 50;
        } else if (fundo_aux == 53) {
            fundo_aux = 52;
        } else if (fundo_aux == 55) {
            fundo_aux = 54;
        } else if (fundo_aux == 57) {
            fundo_aux = 56;
        } else if (fundo_aux == 59) {
            fundo_aux = 58;
        } else if (fundo_aux == 61 || fundo_aux == 62) {
            fundo_aux = 60;
        } else if (fundo_aux == 64 || fundo_aux == 65) {
            fundo_aux = 63;
        } else if (fundo_aux == 67 || fundo_aux == 68) {
            fundo_aux = 66;
        } else if (fundo_aux == 70 || fundo_aux == 71) {
            fundo_aux = 69;
        } else if (fundo_aux == 73) {
            fundo_aux = 72;
        } else if (fundo_aux == 75 || fundo_aux == 76) {
            fundo_aux = 74;
        } else if (fundo_aux == 78) {
            fundo_aux = 77;
        } else if (fundo_aux == 80) {
            fundo_aux = 79;
        } else if (fundo_aux == 82) {
            fundo_aux = 81;
        } else if (fundo_aux == 85) {
            fundo_aux = 84;
        } else if (fundo_aux == 87) {
            fundo_aux = 86;
        } else if (fundo_aux == 89) {
            fundo_aux = 88;
        } else if (fundo_aux == 91) {
            fundo_aux = 90;
        } else if (fundo_aux == 93 || fundo_aux == 94) {
            fundo_aux = 92;
        } else if (fundo_aux == 97) {
            fundo_aux = 96;
        } else if (fundo_aux == 99) {
            fundo_aux = 98;
        } else if (fundo_aux == 101) {
            fundo_aux = 100;
        } else if (fundo_aux == 103) {
            fundo_aux = 102;
        } else if (fundo_aux == 105) {
            fundo_aux = 104;
        } else if (fundo_aux == 110) {
            fundo_aux = 109;
        } else if (fundo_aux == 112) {
            fundo_aux = 111;
        } else if (fundo_aux == 117) {
            fundo_aux = 116;
        } else if (fundo_aux == 119) {
            fundo_aux = 118;
        } else if (fundo_aux == 121) {
            fundo_aux = 120;
        } else if (fundo_aux == 130) {
            fundo_aux = 129;
        } else if (fundo_aux == 134 || fundo_aux == 135 || fundo_aux == 136) {
            fundo_aux = 133;
        } else if (fundo_aux == 139) {
            fundo_aux = 138;
        } else if (fundo_aux == 141) {
            fundo_aux = 140;
        } else if (fundo_aux == 148 || fundo_aux == 149) {
            fundo_aux = 147;
        }
        return fundo_aux;
    }
}
