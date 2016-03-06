package com.gooner10.urlbookmarkmanager;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Providing the Realm Configuration to the Application
 */
public class BookMarkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Configuring the Realm for the application
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("BookMark")
                .build();

//        Realm.deleteRealm(realmConfiguration);
        // Make this realm default
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
