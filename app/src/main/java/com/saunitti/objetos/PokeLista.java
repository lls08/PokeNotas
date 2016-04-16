package com.saunitti.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.saunitti.pokenotas.R;

import java.util.ArrayList;

/**
 * Created by Lemke on 09/04/2016.
 */
public class PokeLista extends ArrayAdapter<PokeNota> {
    public PokeLista(Context context, ArrayList<PokeNota> notas) {
        super(context, 0, notas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PokeNota notas = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_poke_notas_item, parent, false);
        }

        TextView titulo = (TextView) convertView.findViewById(R.id.poke_nota_titulo);
        TextView texto = (TextView) convertView.findViewById(R.id.poke_nota_texto);
        ImageView fundo = (ImageView) convertView.findViewById(R.id.poke_nota_fundo);

        titulo.setText(notas.getTitulo());
        texto.setText(notas.getTexto());
        new Pokemon().Fundo(notas.getFundo(), fundo);

        return convertView;
    }
}