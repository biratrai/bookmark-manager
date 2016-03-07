package com.gooner10.urlbookmarkmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gooner10.urlbookmarkmanager.models.BookMarkModel;

import io.realm.RealmResults;

/**
 * A custom Adapter for the URLBookMarkFragment
 */
public class BookMarkAdapter extends BaseAdapter {
    private final Context context;
    private final RealmResults<BookMarkModel> realmResults;

    public BookMarkAdapter(Context context, RealmResults<BookMarkModel> realmResults) {
        this.realmResults = realmResults;
        this.context = context;
    }

    @Override
    public int getCount() {
        return realmResults.size();
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
        urlName.setText(realmResults.get(position).getUrlName());
        urlText.setText(realmResults.get(position).getUrl());
        return rowView;
    }
}
