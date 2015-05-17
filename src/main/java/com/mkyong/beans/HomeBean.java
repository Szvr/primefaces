package com.mkyong.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "home")
@ViewScoped
public class HomeBean{

    private final String title = "Home";

    public String getTitle() {
        return title;
    }

}