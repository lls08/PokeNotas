package com.saunitti.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saunitti.objetos.PokeNota;

import java.util.ArrayList;

public class DbNotas {

    private DbHelper banco;

    public DbNotas(Context context) {
        banco = DbHelper.getInstance(context);
    }

    public String inserir(String titulo, String texto, int fundo, int cod_perfil) {
        ContentValues valores = new ContentValues();
        long resultado;

        SQLiteDatabase db = banco.getWritableDatabase();

        valores.put(banco.TITULO, titulo);
        valores.put(banco.TEXTO, texto);
        valores.put(banco.FUNDO, fundo);
        valores.put(banco.COD_PERFIL, cod_perfil);

        resultado = db.insert(banco.TABELA_NOTAS, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir a PokeNota";
        else
            return "PokeNota inserida com sucesso";
    }

    public String alterar(int id, String titulo, String texto, int fundo) {
        ContentValues valores = new ContentValues();
        long resultado;

        SQLiteDatabase db = banco.getWritableDatabase();

        valores.put(banco.TITULO, titulo);
        valores.put(banco.TEXTO, texto);
        valores.put(banco.FUNDO, fundo);

        String condicaoWhere = banco.COD_NOTAS+" = ?";
        String[] valoresWhere = {Integer.toString(id)};

        resultado = db.update(banco.TABELA_NOTAS, valores, condicaoWhere, valoresWhere);
        db.close();

        if (resultado == -1)
            return "Erro ao alterar a PokeNota";
        else
            return "PokeNota alterada com sucesso";
    }

    public String excluir(int id) {
        long resultado;

        SQLiteDatabase db = banco.getWritableDatabase();

        String condicaoWhere = banco.COD_NOTAS + " = ?";
        String[] valoresWhere = {Integer.toString(id)};

        resultado = db.delete(DbHelper.TABELA_NOTAS, condicaoWhere, valoresWhere);
        db.close();

        if (resultado == -1)
            return "Erro ao excluir a PokeNota";
        else
            return "PokeNota excluída com sucesso";
    }

    public PokeNota getNota(String id) {
        Cursor cursor;
        String[] campos = {banco.COD_NOTAS, banco.TITULO, banco.TEXTO, banco.FUNDO, banco.COD_PERFIL};

        String condicaoWhere = banco.COD_NOTAS + " = ?";
        String[] valoresWhere = {id};

        SQLiteDatabase db = banco.getWritableDatabase();
        cursor = db.query(banco.TABELA_NOTAS, campos, condicaoWhere, valoresWhere, null, null, null, null);

        PokeNota nota = new PokeNota();

        if (cursor != null) {
            cursor.moveToFirst();
            nota.setCodigo(Integer.parseInt(id));
            nota.setTitulo(cursor.getString(cursor.getColumnIndex(DbHelper.TITULO)));
            nota.setTexto(cursor.getString(cursor.getColumnIndex(DbHelper.TEXTO)));
            nota.setFundo(cursor.getInt(cursor.getColumnIndex(DbHelper.FUNDO)));
        }

        db.close();
        return nota;
    }

    public ArrayList<PokeNota> getLista(String perfil) {
        Cursor cursor;
        String[] campos = {banco.COD_NOTAS, banco.TITULO,banco.TEXTO,banco.FUNDO};

        String condicaoWhere = banco.COD_PERFIL+ " = ?";
        String[] valoresWhere = {perfil};

        SQLiteDatabase db = banco.getWritableDatabase();
        cursor = db.query(banco.TABELA_NOTAS, campos, condicaoWhere, valoresWhere, null, null, null, null);

        ArrayList<PokeNota> notas = new ArrayList<PokeNota>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                PokeNota nota = new PokeNota();
                nota.setCodigo(cursor.getInt(cursor.getColumnIndex(banco.COD_NOTAS)));
                nota.setTitulo(cursor.getString(cursor.getColumnIndex(banco.TITULO)));
                nota.setTexto(cursor.getString(cursor.getColumnIndex(banco.TEXTO)));
                nota.setFundo(cursor.getInt(cursor.getColumnIndex(banco.FUNDO)));

                notas.add(nota);
                cursor.moveToNext();
            }
        }

        db.close();
        return notas;
    }
}
