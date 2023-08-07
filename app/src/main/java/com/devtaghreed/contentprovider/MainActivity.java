package com.devtaghreed.contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.devtaghreed.contentprovider.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    public static final int READ_CONTACTS_REQUEST_CODE = 111;
    ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_REQUEST_CODE);
        }else {
            getAllContacts();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_CONTACTS_REQUEST_CODE && grantResults.length > 0) {

            getAllContacts();

        }
    }

    void getAllContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String contactName;
        String contentNumber;

        Cursor cursor = getContentResolver().
                query(uri, null, null, null, ContactsContract.Contacts.DISPLAY_NAME+" ASC");

        if(cursor.moveToFirst()){
            do{
                long contentId = cursor.getLong(cursor.getColumnIndexOrThrow("_ID"));
                Uri uri1 = ContactsContract.Data.CONTENT_URI;
                Cursor cursor1 = getContentResolver().
                        query(uri1, null, ContactsContract.Data.CONTACT_ID+"=?",
                                new String[]{String.valueOf(contentId)}, null);

                if (cursor1.moveToFirst()){
                    contactName = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME));
                  do {
                      if (cursor1.getString(cursor1.getColumnIndexOrThrow("mimeType"))
                              .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)){
                          if (cursor1.getInt(cursor1.getColumnIndexOrThrow("data2"))
                                  == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE){
                              contentNumber = cursor1.getString(cursor1.getColumnIndexOrThrow("data1"));
                              Contacts contacts = new Contacts(contactName,contentNumber);
                             contactsArrayList.add(contacts);

                              Adapter adapter = new Adapter(contactsArrayList, new onClickListener() {
                                  @Override
                                  public void onClick(int pos) {
                                      Contacts contacts = contactsArrayList.get(pos);
                                      Intent intent = new Intent();
                                      intent.putExtra("name", contacts.getName());
                                      intent.putExtra("num", contacts.getNumber());
                                      setResult(RESULT_OK,intent);
                                      finish();
                                  }
                              });
                              binding.rv.setAdapter(adapter);
                              binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext() ,
                                      RecyclerView.VERTICAL , false));

                         }
                     }

                  }while (cursor1.moveToNext());
                }
                cursor1.close();
            }while(cursor.moveToNext());
            cursor.close();

        }
    }

}




