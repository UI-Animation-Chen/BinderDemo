package com.czf.binderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.czf.binderdemo.service.MyBinder;
import com.czf.binderdemo.service.MyService;

import static com.czf.binderdemo.service.MyBinder.MY_BINDER_1;
import static com.czf.binderdemo.service.MyBinder.MY_BINDER_2;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;

    private IBinder service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        bindMyService();
    }

    private void initViews() {
        btn1 = findViewById(R.id.binder_1);
        btn2 = findViewById(R.id.binder_2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (service == null) return;

                try {
                    Parcel reply = Parcel.obtain();
                    service.transact(MY_BINDER_1, Parcel.obtain(), reply, 0); // 0 for normal rpc
                    Log.d("--==--", reply.readString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (service == null) return;

                try {
                    Parcel reply = Parcel.obtain();
                    service.transact(MY_BINDER_2, Parcel.obtain(), reply, 0); // 0 for normal rpc
                    Log.d("--==--", reply.readString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindMyService() {
        bindService(new Intent(getApplicationContext(), MyService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("--==-- connected", name.toShortString() + ", process: " + Process.myPid());
                MainActivity.this.service = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("--==-- connected", name.toShortString());
                MainActivity.this.service = null;

            }
        }, Service.BIND_AUTO_CREATE);
    }

}
