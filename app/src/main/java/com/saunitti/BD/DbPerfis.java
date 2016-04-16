package com.saunitti.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbPerfis {
    private DbHelper banco;

    public DbPerfis(Context context) {
        banco = DbHelper.getInstance(context);
    }

    public String inserir(String nome, String senha, String manter_logado) {
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(DbHelper.NOME, nome);
        valores.put(DbHelper.SENHA, senha);
        valores.put(DbHelper.MANTER_LOGADO, manter_logado);

        SQLiteDatabase db = banco.getWritableDatabase();
        resultado = db.insert(DbHelper.TABELA_PERFIS, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro inserido com sucesso";
    }

    public String alterar(int id, String nome, int senha, String manter_logado) {
        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(DbHelper.NOME, nome);
        valores.put(DbHelper.SENHA, senha);
        valores.put(DbHelper.MANTER_LOGADO, manter_logado);

        SQLiteDatabase db = banco.getWritableDatabase();
        String condicaoWhere = banco.COD_PERFIL + " = ?";
        String[] valoresWhere = {Integer.toString(id)};

        resultado = db.update(DbHelper.TABELA_PERFIS, valores, condicaoWhere, valoresWhere);
        db.close();

        if (resultado == -1)
            return "Erro ao alterar registro";
        else
            return "Registro alterado com sucesso";
    }

    public String excluir(int id) {
        long resultado;

        SQLiteDatabase db = banco.getWritableDatabase();

        String condicaoWhere = banco.COD_PERFIL + " = ?";
        String[] valoresWhere = {Integer.toString(id)};

        resultado = db.delete(DbHelper.TABELA_PERFIS, condicaoWhere, valoresWhere);
        db.close();

        if (resultado == -1)
            return "Erro ao excluir registro";
        else
            return "Registro excluído com sucesso";
    }

    public boolean verificaPerfis() {
        Cursor cursor;
        String[] campos = {banco.COD_PERFIL};

        SQLiteDatabase db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PERFIS, campos, null, null, null, null, null, "1");

        boolean retorno;
        if (cursor != null & cursor.getCount() > 0) {
            retorno = true;
        } else {
            retorno = false;
        }
        db.close();
        return retorno;
    }

    public int logar(String nome, String senha) {
        Cursor cursor;
        String[] campos = {banco.COD_PERFIL};

        String condicaoWhere = banco.NOME + " = ? AND " + banco.SENHA + " = ? ";
        String[] valoresWhere = {nome, senha};

        SQLiteDatabase db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PERFIS, campos, condicaoWhere, valoresWhere, null, null, null, null);

        int retorno = 0;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            retorno = cursor.getInt(cursor.getColumnIndex(banco.COD_PERFIL));
            ultimoLogado(retorno,"S");
        }

        db.close();
        return retorno;
    }

    public Cursor getPerfil(String perfil) {
        Cursor cursor;
        String[] campos = {banco.COD_PERFIL, banco.NOME, banco.SENHA, banco.MANTER_LOGADO};
        String condicaoWhere = banco.COD_PERFIL + " = ?";
        String[] valoresWhere = {perfil};

        SQLiteDatabase db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PERFIS, campos, condicaoWhere, valoresWhere, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    public void ultimoLogado(int id, String valor) {
        ContentValues valores = new ContentValues();
        valores.put(DbHelper.ULTIMO_LOGADO, valor);

        String condicaoWhere = banco.COD_PERFIL + " = ? ";
        String[] valoresWhere = {Integer.toString(id)};

        SQLiteDatabase db = banco.getWritableDatabase();
        db.update(DbHelper.TABELA_PERFIS, valores, condicaoWhere, valoresWhere);
    }

    public int getLogado() {
        Cursor cursor;
        String[] campos = {banco.COD_PERFIL};
        String condicaoWhere = banco.ULTIMO_LOGADO + " = ? AND " + banco.MANTER_LOGADO + " = ?";
        String[] valoresWhere = {"S", "S"};

        SQLiteDatabase db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PERFIS, campos, condicaoWhere, valoresWhere, null, null, null, null);

        int perfilId = 0;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            perfilId = cursor.getInt(cursor.getColumnIndex(banco.COD_PERFIL));
        }

        db.close();
        return perfilId;
    }
}
