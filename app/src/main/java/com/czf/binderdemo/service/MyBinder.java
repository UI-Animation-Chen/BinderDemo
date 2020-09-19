package com.czf.binderdemo.service;

import android.os.Binder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyBinder extends Binder {

    public static final int MY_BINDER_1 = 0x101;
    public static final int MY_BINDER_2 = 0x102;

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case MY_BINDER_1:
                StringBuilder sb = new StringBuilder(1024 * 1024);
                // 1M - 8K, java char occupy 2B.
                for (int i = 0; i < (1024 * 1016) / 2 - 19; i ++) {
                    sb.append((char)(97 + (i % 26)));
                }
                reply.writeString(sb.toString());
                reply.recycle();
                return true;
            case MY_BINDER_2:
                reply.writeString("form binder 2, thread: " + Thread.currentThread().getName());
                reply.recycle();
                return true;
            default:
                return super.onTransact(code, data, reply, flags);
        }
    }
}
