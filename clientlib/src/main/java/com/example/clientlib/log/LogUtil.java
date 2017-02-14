package com.example.clientlib.log;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

public class LogUtil {

    public final static String TAG = "TAG_Liucr";

    public static void d(String msg) {
        i(msg, null);
    }

    public static void i(String msg, Throwable tr) {
        Log.i(getLogTag(), msg, tr);
    }

    public static void e(String msg) {
        e(msg, null);
    }

    public static void e(String msg, Throwable tr) {
        Log.e(getLogTag(), msg, tr);
    }

    public static void exception(Exception ex) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] sts = ex.getStackTrace();
        if (sts != null && sts.length > 0) {
            for (StackTraceElement st : sts) {
                if (st != null) {
                    sb.append("[ ")
                            .append(st.getFileName()).append(":").append(st.getLineNumber())
                            .append(" ]\r\n");
                }
            }
        }
        Log.e(getLogTag(), sb.toString());
    }

    @NonNull
    private static String getLogTag() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return TAG;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(LogUtil.class.getName())) {
                continue;
            }
            return TAG + getCallName(st);
        }
        return TAG;
    }

    @NonNull
    private static String getCallName(StackTraceElement st) {
        StringBuilder buf = new StringBuilder();

        if (st == null) {
            buf.append("(Null Stack)");
        } else {
            String fName = st.getFileName();

            if (TextUtils.isEmpty(fName)) {
                buf.append("(Unknown Source)");
            } else {
                int lineNum = st.getLineNumber();
                buf.append('(');
                buf.append(fName);
                if (lineNum >= 0) {
                    buf.append(':');
                    buf.append(lineNum);
                }
                buf.append(')');
                buf.append('.');
                buf.append(st.getMethodName());
                buf.append('(');
                buf.append(')');
            }

        }
        return buf.toString();
    }

}
