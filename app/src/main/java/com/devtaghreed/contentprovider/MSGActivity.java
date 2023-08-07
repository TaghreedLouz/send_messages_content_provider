package com.devtaghreed.contentprovider;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.devtaghreed.contentprovider.databinding.ActivityMsgBinding;

public class MSGActivity extends AppCompatActivity {

    ActivityMsgBinding binding;
    public static final int SEND_SMS_REQUEST_CODE = 111;
    ActivityResultLauncher<Intent> arl;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMsgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
//                        if (result == null || result.equals("") || result.equals(null)){
                            Intent intent=result.getData();
                            String Names = intent.getStringExtra("name");
                            num= intent.getStringExtra("num");
                            binding.phoneEt.setText(Names);
//                        }else {
//                            Toast.makeText(MSGActivity.this, "Select contact", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
        binding.phoneEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                arl.launch(intent);
            }
        });


        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MSGActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendMSG();
                } else {
                    ActivityCompat.requestPermissions(MSGActivity.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUEST_CODE);
                }
                binding.phoneEt.setText("");
                binding.msgEt.setText("");
            }

        });


    }

    private void sendMSG() {
        String msg = binding.msgEt.getText().toString().trim();

        if (!num.equals("") && !msg.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num, null, msg, null, null);
            Toast.makeText(MSGActivity.this, "SMS send successfully!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(MSGActivity.this, "Enter Value", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SEND_SMS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendMSG();
        } else {
            Toast.makeText(MSGActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }
}










