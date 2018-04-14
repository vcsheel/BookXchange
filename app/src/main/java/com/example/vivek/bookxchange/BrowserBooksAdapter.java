package com.example.vivek.bookxchange;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BrowserBooksAdapter extends RecyclerView.Adapter<BrowserBooksAdapter.BookViewHolder> {

    private Context mContext;
    private List<Books> mBooks;

    public BrowserBooksAdapter(Context mContext, List<Books> mBooks) {
        this.mContext = mContext;
        this.mBooks = mBooks;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Books currentBook = mBooks.get(position);
        holder.bookName.setText(currentBook.getBookName());
        holder.authorName.setText(currentBook.getBookAuthor());
        holder.bookPrice.setText("\u20B9 "+String.valueOf(currentBook.getBookPrice()));
        Picasso.get()
                .load(currentBook.getBookImageUrl())
                .placeholder(R.drawable.bload)
                .fit()
                .centerInside()
                .into(holder.bookImageView);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookViewHolder extends  RecyclerView.ViewHolder{

        public TextView bookName;
        public TextView authorName;
        public TextView bookPrice;
        public ImageView bookImageView;

        public BookViewHolder(View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.bookNameTV);
            authorName = itemView.findViewById(R.id.authorNameTV);
            bookPrice = itemView.findViewById(R.id.priceTV);
            bookImageView = itemView.findViewById(R.id.bookImageView);
        }
    }

}
