package com.gooner10.urlbookmarkmanager.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Realm Object Pojo Model
 */
public class BookMarkModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String urlName;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
