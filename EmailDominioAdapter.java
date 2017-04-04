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
 *
 * Os domínios de email sugeridos são os domínios institucionais até então aceitos pelo sistema.
 *
 * Created by edupooch on 12/03/2017.
 */

public class EmailDominioAdapter extends ArrayAdapter<String> {

    private static final int LAYOUT_DO_BOX = android.R.layout.simple_list_item_1;

    private ArrayList<String> items;
    private ArrayList<String> itemsClone;
    private ArrayList<String> suggestions;

    public EmailDominioAdapter(Context context, ArrayList<String> dominiosDisponiveis) {
        super(context, LAYOUT_DO_BOX, dominiosDisponiveis);
        this.items = dominiosDisponiveis;
        this.itemsClone = new ArrayList<>(dominiosDisponiveis);
        this.suggestions = new ArrayList<>();
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(LAYOUT_DO_BOX, null);
        }
        String customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView)v;
            if (customerNameLabel != null) {
                customerNameLabel.setText(customer);
            }
        }
        return v;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            return (String)resultValue;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null){
                String palabra = constraint.toString();
                if(palabra.contains("@")) {
                    String palabra2 = palabra.substring(palabra.indexOf("@"));
                    String antesArroba;
                    try{
                        antesArroba = palabra.substring(0, palabra.indexOf("@"));
                    }catch (Exception ex)
                    {
                        antesArroba ="";
                    }
                    suggestions.clear();
                    for (String customer : itemsClone) {
                        if(customer.toLowerCase().startsWith(palabra2.toLowerCase())){
                            suggestions.add(antesArroba+customer);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> filteredList = (ArrayList<String>) results.values;
            if(results.count > 0) {
                clear();
                for (String c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}