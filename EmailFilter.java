package br.edu.ufcspa.voltaparacasa.adapters;

import android.support.annotation.NonNull;
import android.widget.Filter;

import java.util.ArrayList;

/**Classe responsável pela filtragem dos resultados das sugestões do Campo de Email conforme o
 * usuário digita os caracteres
 *
 * Created by edupooch on 04/04/2017.
 */

class EmailFilter extends Filter {
    private final EmailDominioAdapter adapter;
    private ArrayList<String> sugestoes;
    private ArrayList<String> itensClone;

    EmailFilter(EmailDominioAdapter adapter, ArrayList<String> itens) {
        this.itensClone = new ArrayList<>(itens);
        this.sugestoes = new ArrayList<>();
        this.adapter = adapter;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence palavra) {
        if (palavra != null && palavra.toString().contains("@")) {
            return getFilterResults(palavra.toString());
        }
        return new Filter.FilterResults(); //não há nenhuma sugestão
    }
    /**Método para verificar as opções disponíveis de sugestão baseadas no texto 
    * já digitado pelo user
     *
     */
    @NonNull
    private Filter.FilterResults getFilterResults(String palavra) {
        String depoisArroba = palavra.substring(palavra.indexOf("@"));
        String antesArroba = getStringAntesDoArroba(palavra);
        sugestoes.clear();

        for (String dominio : itensClone) {
            if (dominio.toLowerCase().startsWith(depoisArroba.toLowerCase())) {
                sugestoes.add(antesArroba + dominio);
            }
        }

        Filter.FilterResults filterResults = new Filter.FilterResults();
        filterResults.values = sugestoes;
        filterResults.count = sugestoes.size();
        return filterResults;
    }

    @NonNull
    private String getStringAntesDoArroba(String palavra) {
        try {
            return palavra.substring(0, palavra.indexOf("@"));
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**Método que envia sugestões filtradas e atualiza o adapter, pode não retornar nada
     * caso o texto digitado pelo usuário não seja compatível com nenhum domínio cadastrado
     *
     */
    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        ArrayList<String> listaComFiltro = (ArrayList<String>) results.values;

        if (results.count > 0) {
            adapter.clear();
            for (String dominio : listaComFiltro) {
                adapter.add(dominio);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
