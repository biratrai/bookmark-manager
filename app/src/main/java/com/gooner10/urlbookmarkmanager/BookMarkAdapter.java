package com.gooner10.urlbookmarkmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gooner10.urlbookmarkmanager.models.BookMarkModel;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A custom Adapter for the URLBookMarkFragment
 */
public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.BookMarkViewHolder> {
    private final Context context;
    private final RealmResults<BookMarkModel> realmResults;
    private final Realm realm;

    public BookMarkAdapter(Context context, RealmResults<BookMarkModel> realmResults) {
        this.realmResults = realmResults;
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public BookMarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new BookMarkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookMarkViewHolder holder, final int position) {
        holder.urlName.setText(realmResults.get(position).getUrlName());
        holder.urlText.setText(realmResults.get(position).getUrl());
        holder.urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = realmResults.get(position).getUrl();

                if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Could not resolve URL ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Invalid URL " + url, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // All changes to data must happen in a transaction
                realm.beginTransaction();
                realmResults.remove(position);
                notifyDataSetChanged();
                realm.commitTransaction();

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class BookMarkViewHolder extends RecyclerView.ViewHolder {
        final TextView urlName;
        final TextView urlText;
        final ImageView imageView;

        public BookMarkViewHolder(View itemView) {
            super(itemView);
            urlName = (TextView) itemView.findViewById(R.id.urlName);
            urlText = (TextView) itemView.findViewById(R.id.url);
            imageView = (ImageView) itemView.findViewById(R.id.trash);
        }
    }
}
