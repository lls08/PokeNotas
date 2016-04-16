package com.saunitti.objetos;

/**
 * Created by Lemke on 09/04/2016.
 */
public class PokeNota {
    int codigo;
    String titulo;
    String texto;
    int fundo;

    public void poke_nota(int codigo, String titulo, int fundo) {
        setCodigo(codigo);
        setTitulo(titulo);
        setTexto(texto);
        setFundo(fundo);
    }

    //Get
    public int getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public int getFundo() {
        return fundo;
    }

    //Set
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFundo(int fundo) {
        this.fundo = fundo;
    }
}

