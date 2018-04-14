package com.example.vivek.bookxchange;

public class Books {

    private String bookName;
    private String bookImageUrl;
    private String bookAuthor;
    private String bookDesc;
    private float bookPrice;

    public Books() {
    }

    public Books(String bookName, String bookImageUrl, String bookAuthor, String bookDesc, float bookPrice) {
        this.bookName = bookName;
        this.bookImageUrl = bookImageUrl;
        this.bookAuthor = bookAuthor;
        this.bookDesc = bookDesc;
        this.bookPrice = bookPrice;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public float getBookPrice() {
        return bookPrice;
    }


    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }
}
