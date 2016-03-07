package com.gooner10.urlbookmarkmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.gooner10.urlbookmarkmanager.models.BookMarkModel;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A fragment that is to be attached to the main activity.
 * This fragment display all of the bookmark added by the user
 * in a listview.
 */
public class URLBookMarkFragment extends Fragment {

    private ListView mListView;
    private Realm realm;
    private BookMarkAdapter bookMarkAdapter;
    private int position;

    public URLBookMarkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_url_bookmark, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mListView = (ListView) view.findViewById(R.id.listView);
        SearchView searchView = (SearchView) view.findViewById(R.id.search_view);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.user_input, null);
                final EditText urlName = (EditText) dialogView.findViewById(R.id.urlName);
                final EditText urlLink = (EditText) dialogView.findViewById(R.id.urlLink);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(dialogView);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setTitle(R.string.dialog_title);

                // Add action buttons
                builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        realm.beginTransaction();
                        BookMarkModel bookMarkModel = realm.createObject(BookMarkModel.class);
                        bookMarkModel.setId(UUID.randomUUID().toString());
                        bookMarkModel.setUrlName(urlName.getText().toString());
                        bookMarkModel.setUrl(urlLink.getText().toString());
                        realm.commitTransaction();
                        bookMarkAdapter.notifyDataSetChanged();
                    }
                })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {

                TextView urlLink = (TextView) view.findViewById(R.id.url);
                String url = String.valueOf(urlLink.getText());

                if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
                    Uri uri = Uri.parse(url);
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(uri);
//                    startActivity(i);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Couldnot resolve URL ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Invalid URL " + url, Toast.LENGTH_SHORT).show();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookMarkAdapter.getFilter().filter(newText);
//                Toast.makeText(getActivity(), "Text Change ", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realm = Realm.getDefaultInstance();

        RealmResults<BookMarkModel> realmResults = realm.allObjects(BookMarkModel.class);
        ArrayList<BookMarkModel> bookMarkModels = new ArrayList<>();
        Log.d("TAG", "class " + realmResults.getClass());
        for (BookMarkModel model : realmResults) {
            Log.d("TAG", " urlName " + model.getUrlName());
            Log.d("TAG", " url " + model.getUrl());
            bookMarkModels.add(model);
        }
        bookMarkAdapter = new BookMarkAdapter(getActivity(), bookMarkModels);
        Log.d("TAG", " url " + bookMarkModels.size());
        mListView.setAdapter(bookMarkAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

//    @Override
//    public void positionSelected(int newPosition) {
//        position = newPosition;
//    }
}
