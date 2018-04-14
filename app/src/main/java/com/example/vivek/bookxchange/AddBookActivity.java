package com.example.vivek.bookxchange;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddBookActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseImageButton;
    private Button uploadButton;
    private Button browsebooksButton;
    private ImageView bookImageView;
    private ProgressBar uploadProgress;
    private EditText etBookName;
    private EditText etAuthorName;
    private EditText etPrice;
    private EditText etDesc;

    private Uri bookImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_activity);

        chooseImageButton = findViewById(R.id.chooseImageButton);
        uploadButton = findViewById(R.id.uploadButton);
        browsebooksButton = findViewById(R.id.showAllButton);
        bookImageView = findViewById(R.id.bookImageView);
        uploadProgress = findViewById(R.id.uploadProgressBar);
        etBookName = findViewById(R.id.etBookName);
        etAuthorName = findViewById(R.id.etAuthorName);
        etPrice = findViewById(R.id.etPrice);
        etDesc = findViewById(R.id.etDesc);


        mStorageRef = FirebaseStorage.getInstance().getReference("Books");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Books");

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(AddBookActivity.this,"Upload is Progress",Toast.LENGTH_SHORT).show();
                }else {
                    uploadBookDetails();
                }
            }
        });

        browsebooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, BrowseBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            bookImageUri = data.getData();
            Picasso.get().load(bookImageUri).into(bookImageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadBookDetails(){
        if(bookImageUri == null){
            Toast.makeText(this,"No image selected",Toast.LENGTH_SHORT).show();
        }else {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(bookImageUri));

            mUploadTask = fileReference.putFile(bookImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgress.setProgress(0);
                                }
                            },1000);

                            Toast.makeText(AddBookActivity.this,"Book Details Saved Successfully",Toast.LENGTH_SHORT).show();
                            Books book = new Books(etBookName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString(),
                                    etAuthorName.getText().toString().trim(),etDesc.getText().toString().trim(),
                                    Float.parseFloat(etPrice.getText().toString().trim()));

                            String bookId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(bookId).setValue(book);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBookActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgress.setProgress((int) progress);

                        }
                    });
        }

    }
}
