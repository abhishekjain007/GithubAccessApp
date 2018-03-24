package com.example.abhij.githubnetworking;

/**
 * Created by abhij on 18-03-2018.
 */

public class Repo {

    String name;
    String html_url;

    public Repo(String name, String html_url) {
        this.name = name;
        this.html_url = html_url;
    }

    public String getName() {
        return name;
    }

    public String getHtml_url() {
        return html_url;
    }

}
