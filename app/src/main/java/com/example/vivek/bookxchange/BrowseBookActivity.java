package com.example.vivek.bookxchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BrowseBookActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView mBrowseBookRV;
    private BrowserBooksAdapter mBrowseBookAdapter;
    private ProgressBar progressCircle;

    private DatabaseReference mDatabaseRef;
    private List<Books> mbooks;

    private DrawerLayout mDrawableLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_book);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawableLayout = (DrawerLayout) findViewById(R.id.browse_book_DL);
        mToggle = new ActionBarDrawerToggle(this,mDrawableLayout,R.string.open,R.string.close);
        mDrawableLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.browse_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressCircle = findViewById(R.id.progress_circle);
        mBrowseBookRV = findViewById(R.id.browse_book_RV);
        mBrowseBookRV.setHasFixedSize(true);
        mBrowseBookRV.setLayoutManager(new LinearLayoutManager(this));

        mbooks = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Books");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Books book = postSnapshot.getValue(Books.class);
                    mbooks.add(book);
                }

                mBrowseBookAdapter = new BrowserBooksAdapter(BrowseBookActivity.this,mbooks);

                mBrowseBookRV.setAdapter(mBrowseBookAdapter);
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override


            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BrowseBookActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                progressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings :
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_logout :
                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
                return true;
        }
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_myaccount :
                Toast.makeText(this,"Account",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting :
                Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_addbook:
                Log.e("Nav Action: ","Add book Selected");
                Intent intent = new Intent(getApplicationContext(),AddBookActivity.class);
                startActivity(intent);
                break;

        }

        mDrawableLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawableLayout.isDrawerOpen(GravityCompat.START)){
            mDrawableLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
