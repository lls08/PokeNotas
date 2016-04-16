package com.saunitti.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String NOME_DB = "pokebanco.db";

    public static final String TABELA_NOTAS = "notas";
    public static final String COD_NOTAS = "notaId";
    public static final String TITULO = "titulo";
    public static final String TEXTO = "texto";
    public static final String FUNDO = "fundo";

    public static final String TABELA_PERFIS = "perfis";
    public static final String COD_PERFIL = "perfilId";
    public static final String NOME = "nome";
    public static final String SENHA = "senha";
    public static final String MANTER_LOGADO = "manter";
    public static final String ULTIMO_LOGADO = "ultimo";

    public static final int VERSAO = 2;

    private static DbHelper instancia = null;

    public static DbHelper getInstance(Context context) {
        if (instancia == null)
            instancia = new DbHelper(context);
        return instancia;
    }

    private DbHelper(Context context) {
        super(context, NOME_DB, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA_NOTAS + " (" +
                COD_NOTAS + " integer primary key autoincrement, " +
                TITULO + " text, " +
                TEXTO + "  text, " +
                FUNDO + " integer, " +
                COD_PERFIL + " integer " +
                ")";

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABELA_PERFIS + " (" +
                COD_PERFIL + " integer primary key autoincrement, " +
                NOME + " text , " +
                SENHA + " text , " +
                MANTER_LOGADO + " text , " +
                ULTIMO_LOGADO + " text " +
                ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
