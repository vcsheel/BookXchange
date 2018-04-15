package com.example.vivek.bookxchange;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {
    private Map<String,String> addedBooks;
    private String email;

    public Users() {
        addedBooks = new HashMap<String, String>();
    }

    public Map<String, String> getAddedBooks() {
        return addedBooks;
    }

    public void setAddedBooks(Map<String, String> addedBooks) {
        this.addedBooks = addedBooks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}