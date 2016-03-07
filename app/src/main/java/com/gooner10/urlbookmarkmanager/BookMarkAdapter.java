package com.gooner10.urlbookmarkmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gooner10.urlbookmarkmanager.models.BookMarkModel;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Gooner10 on 3/6/16.
 */
public class BookMarkAdapter extends BaseAdapter implements Filterable {
    private final Context context;
    RealmResults<BookMarkModel> realmResults;
    CallBack callBack;
    private MyFilter filter;
    private ArrayList<BookMarkModel> bookMarkModels;
    private ArrayList<BookMarkModel> filterdBookMarkModels;

//    public BookMarkAdapter(Context context, RealmResults<BookMarkModel> realmResults) {
//        this.realmResults = realmResults;
//        this.context = context;
//    }

    public BookMarkAdapter(Context context, ArrayList<BookMarkModel> bookMarkModels) {
        this.context = context;
        this.bookMarkModels = bookMarkModels;
        this.filterdBookMarkModels = bookMarkModels;
    }

    @Override
    public int getCount() {
//        return realmResults.size();
        return bookMarkModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.single_row, parent, false);
        TextView urlName = (TextView) rowView.findViewById(R.id.urlName);
        TextView urlText = (TextView) rowView.findViewById(R.id.url);
//        urlName.setText(realmResults.get(position).getUrlName());
//        urlText.setText(realmResults.get(position).getUrl());
        urlName.setText(bookMarkModels.get(position).getUrlName());
        urlText.setText(bookMarkModels.get(position).getUrl());
//        urlName.setText(realmResults.get(position));
//        callBack.positionSelected(position);
        return rowView;
    }

    public interface CallBack {
        void positionSelected(int position);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter();
        }
        return filter;
    }

    public class MyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<BookMarkModel> arrayList = new ArrayList<>();
//            Log.d("GAG", "perfromfiltering constraint " + constraint);
//            Toast.makeText(context, "Text Change " + constraint, Toast.LENGTH_SHORT).show();
            Log.d("BAG", "performFiltering realmResults " + bookMarkModels.size());
//            Log.d("BAG", "performFiltering realmResults " + constraint.length());
//            Log.d("BAG", "performFiltering realmResults " + (constraint != null));
            if (constraint != null && constraint.length() > 0) {
                Log.d("BAG", "inside IF ");
                for (int i = 0; i < bookMarkModels.size(); i++) {
                    Log.d("BAG", "name ");
                    String name = bookMarkModels.get(i).getUrlName();
                    Log.d("BAG", "name " + name);
                    if (name.contains(constraint.toString())) {
                        Log.d("GAG", "perfromfiltering constraint " + constraint);
                        arrayList.add(bookMarkModels.get(i));
                    }
                }
                filterResults.count = arrayList.size();
                filterResults.values = arrayList;
            } else {
                filterResults.count = filterdBookMarkModels.size();
                filterResults.values = filterdBookMarkModels;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.d("GAG", "publish result constraint " + results.values);
//            bookMarkModels = (ArrayList<BookMarkModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
