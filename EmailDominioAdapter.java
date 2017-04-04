package br.edu.ufcspa.voltaparacasa.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Esta classe representa um adapter de um AutoCompleteTextView, componente do android que será
 * utilizado para sugerir domínios de email para o usuário em seu cadastro/login na aplicação assim
 * que o usuário digitar o char '@'
 * <p>
 * Os domínios de email sugeridos são os domínios institucionais até então aceitos pelo sistema.
 * <p>
 * Created by edupooch on 12/03/2017.
 */

public class EmailDominioAdapter extends ArrayAdapter<String> {

    private static final int LAYOUT_DO_BOX = android.R.layout.simple_list_item_1;

    private ArrayList<String> itens;
    private Filter emailFilter; //para filtrar os resultados conforme o usuário digita as letras

    public EmailDominioAdapter(Context context, ArrayList<String> dominiosDisponiveis) {
        super(context, LAYOUT_DO_BOX, dominiosDisponiveis);
        this.itens = dominiosDisponiveis;
        this.emailFilter = new EmailFilter(this,itens);
    }

    /**
     * Método que cria a view de cada sugestão de domínio
     *
     * @param position    posição no ArrayList de domínios
     * @param convertView recurso do adapter para otimizar o carregamento com reutilização de views
     * @param parent      edit text que terá o combo box
     * @return view da caixa com a sugestão
     */
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (convertView == null)
            view = inflater.inflate(LAYOUT_DO_BOX, parent, false);

        String dominio = itens.get(position);
        TextView textDominio = (TextView) view;
        textDominio.setText(dominio);

        return view;
    }


    @NonNull
    public Filter getFilter() {
        return emailFilter;
    }
}