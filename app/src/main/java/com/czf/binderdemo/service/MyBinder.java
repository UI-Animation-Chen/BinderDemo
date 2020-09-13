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
                reply.writeString("form binder 1, process: " + Process.myPid());
                return true;
            case MY_BINDER_2:
                reply.writeString("form binder 2, process: " + Process.myPid());
                return true;
            default:
                return super.onTransact(code, data, reply, flags);
        }
    }
}
