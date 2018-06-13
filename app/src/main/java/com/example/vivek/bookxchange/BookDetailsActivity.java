package com.example.vivek.bookxchange;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class BookDetailsActivity extends AppCompatActivity {

    private ImageView bookdImageView;
    private TextView bookdNameTV;
    private TextView authordTV;
    private TextView pricedTV;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Toolbar toolbard = (Toolbar) findViewById(R.id.toolbard);
        setSupportActionBar(toolbard);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        bookdImageView = (ImageView) findViewById(R.id.bookFullImage);
        bookdNameTV = (TextView) findViewById(R.id.bookdNameTV);
        authordTV = (TextView) findViewById(R.id.authordNameTV);
        pricedTV = (TextView) findViewById(R.id.pricedTV);

        Books book = (Books) getIntent().getSerializableExtra("BookObject");

        try{
            Picasso.get()
                    .load(book.getBookImageUrl())
                    .fit()
                    .centerInside()
                    .into(bookdImageView);

            bookdNameTV.setText(book.getBookName());
            authordTV.setText(book.getBookAuthor());
            pricedTV.setText("\u20B9 "+String.valueOf(book.getBookPrice()));

        }catch (Exception e){
            Log.e("Details Exception:",e.getMessage());
        }

    }
}
