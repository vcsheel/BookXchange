package com.example.vivek.bookxchange;

import java.util.List;

public class Users {
    private String userId;
    private String name;
    private String emailId;

    public List<Books> getMyRentedBooks() {
        return myRentedBooks;
    }

    public void setMyRentedBooks(List<Books> myRentedBooks) {
        this.myRentedBooks = myRentedBooks;
    }

    public List<Books> getMyAddedBooks() {
        return myAddedBooks;
    }

    public void setMyAddedBooks(List<Books> myAddedBooks) {
        this.myAddedBooks = myAddedBooks;
    }

    public List<Books> getMyBoughtBooks() {
        return myBoughtBooks;
    }

    public void setMyBoughtBooks(List<Books> myBoughtBooks) {
        this.myBoughtBooks = myBoughtBooks;
    }

    private List<Books> myRentedBooks;
    private List<Books> myAddedBooks;
    private List<Books> myBoughtBooks;

    public Users(String userId, String name, String emailId) {
        this.userId = userId;
        this.name = name;
        this.emailId = emailId;
    }

    public Users(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}