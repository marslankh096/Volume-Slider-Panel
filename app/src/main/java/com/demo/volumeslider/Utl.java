package com.demo.volumeslider;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.demo.volumeslider.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Utl {
    public static SharedPreferences.Editor editor;
    static Context f8cn;
    public static SharedPreferences pref;

    
    public interface OnRefreshComplete {
        void Complete();
    }

    public static void setLLayout(Context context, View view, int i, int i2) {
    }

    public static void setLSquare(Context context, View view, int i) {
    }

    public Utl(Context context) {
        f8cn = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        pref = sharedPreferences;
        editor = sharedPreferences.edit();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
    }

    public static Bitmap getBitmapwithAlpha(Bitmap bitmap, int i) {
        if (!bitmap.isMutable()) {
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        new Canvas(bitmap).drawColor((i & 255) << 24, PorterDuff.Mode.DST_IN);
        return bitmap;
    }

    public static Bitmap createThumb(Context context, Bitmap bitmap) {
        int height = (context.getResources().getDisplayMetrics().widthPixels * bitmap.getHeight()) / 1080;
        return Bitmap.createScaledBitmap(bitmap, height, height, true);
    }

    public static void SetUIRelative(Context context, View view, int i, int i2) {
        view.setLayoutParams(new RelativeLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().heightPixels * i2) / 1920));
    }

    public static void SetUILinear(Context context, View view, int i, int i2) {
        view.setLayoutParams(new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().heightPixels * i2) / 1920));
    }

    public static void SetUILinearVivo(Context context, View view, int i, int i2) {
        view.setLayoutParams(new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().widthPixels * i2) / 1080));
    }

    public static void SetUIRelativeVivo(Context context, View view, int i, int i2) {
        view.setLayoutParams(new RelativeLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().widthPixels * i2) / 1080));
    }

    public static void SetUIRelativeTopMargin(Context context, View view, int i, int i2, int i3) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().heightPixels * i2) / 1920);
        layoutParams.topMargin = (context.getResources().getDisplayMetrics().widthPixels * i3) / 1920;
        view.setLayoutParams(layoutParams);
    }

    public static Bitmap gettextbitmap(String str, int i, Typeface typeface, int i2, int i3, int i4) {
        TextPaint textPaint = new TextPaint(65);
        textPaint.setStyle(Paint.Style.FILL);
        if (typeface != null) {
            textPaint.setTypeface(typeface);
        }
        textPaint.setColor(i);
        textPaint.setTextSize(80.0f);
        if (i2 >= 0 && i3 >= 0) {
            textPaint.setShadowLayer(i4, i2, i3, Color.parseColor("#000000"));
        }
        int measureText = (int) textPaint.measureText(str);
        StaticLayout staticLayout = new StaticLayout(str, textPaint, measureText, Layout.Alignment.ALIGN_CENTER, 1.0f, 4.0f, true);
        Bitmap createBitmap = Bitmap.createBitmap(measureText, staticLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(65);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0);
        canvas.drawPaint(paint);
        canvas.save();
        canvas.translate(0.0f, 0.0f);
        staticLayout.draw(canvas);
        canvas.restore();
        return createBitmap;
    }

    public static void openVideo(Context context, String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(str), "video/*");
            context.startActivity(intent);
        } catch (Exception unused) {
            Toast(context, "can't play from here");
        }
    }


    public static Bitmap getBitmapFromText(String str, float f, int i, Typeface typeface) {
        Paint paint = new Paint(1);
        paint.setTextSize(f);
        paint.setColor(i);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        float f2 = -paint.ascent();
        Bitmap createBitmap = Bitmap.createBitmap((int) (paint.measureText(str) + 0.5f), (int) (paint.descent() + f2 + 0.5f), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawText(str, 0.0f, f2, paint);
        return createBitmap;
    }

    public static Bitmap getBitmapFromText(String str, float f, int i, Typeface typeface, int i2) {
        Paint paint = new Paint(1);
        paint.setTextSize(f);
        paint.setColor(i);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setShadowLayer(12.0f, 0.0f, 0.0f, i2);
        float f2 = -paint.ascent();
        Bitmap createBitmap = Bitmap.createBitmap((int) (paint.measureText(str) + 0.5f), (int) (paint.descent() + f2 + 0.5f), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawText(str, 0.0f, f2, paint);
        return createBitmap;
    }

    public static String getmilliSecondsToTimer(long j) {
        String str;
        String str2;
        int i = (int) (j / 3600000);
        long j2 = j % 3600000;
        int i2 = ((int) j2) / 60000;
        int i3 = (int) ((j2 % 60000) / 1000);
        if (i > 0) {
            str = i + ":";
            if (i < 10) {
                str = "0" + i + ":";
            }
        } else {
            str = "";
        }
        if (i3 < 10) {
            str2 = "0" + i3;
        } else {
            str2 = "" + i3;
        }
        String str3 = "" + i2;
        if (i2 < 10) {
            str3 = "0" + i2;
        }
        return str + str3 + ":" + str2;
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        int i;
        String string;
        try {
            i = Settings.Secure.getInt(context.getContentResolver(), "accessibility_enabled");
        } catch (Settings.SettingNotFoundException unused) {
            i = 0;
        }
        if (i != 1 || (string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services")) == null) {
            return false;
        }
        return string.toLowerCase().contains(context.getPackageName().toLowerCase());
    }

    public static void getPermissionAccessibilty(Activity activity, int i) {
        new Intent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(new Intent("android.settings.ACCESSIBILITY_SETTINGS"), i);
    }



    public static void getOverlayPermission(Activity activity, int i) {
        Boolean.valueOf(true);
        Context applicationContext = activity.getApplicationContext();
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (!Settings.canDrawOverlays(applicationContext)) {
            Boolean.valueOf(false);
            activity.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + applicationContext.getPackageName())), i);
            return;
        }
        Boolean.valueOf(true);
    }

    public static Boolean checkoverlay(Activity activity) {
        return Boolean.valueOf(Build.VERSION.SDK_INT >= 23 ? Settings.canDrawOverlays(activity.getApplicationContext()) : true);
    }

    public static boolean checkSystemWritePermission(Context context, int i) {
        boolean canWrite = Build.VERSION.SDK_INT >= 23 ? Settings.System.canWrite(context) : true;
        if (i >= 0 && !canWrite) {
            permissionSystemSettings((Activity) context, i);
        }
        return canWrite;
    }

    public static void permissionSystemSettings(Activity activity, int i) {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, i);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static void addContact(Context context, String str, String str2, Uri uri) {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
        arrayList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue("account_type", null).withValue("account_name", null).build());
        if (str != null) {
            arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/name").withValue("data1", str).build());
        }
        if (str2 != null) {
            arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", str2).withValue("data2", 2).build());
        }
        if (uri != null) {
            try {
                arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/photo").withValue("data15", getBytes(context.getContentResolver().openInputStream(uri))).build());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            context.getContentResolver().applyBatch("com.android.contacts", arrayList);
        } catch (Exception e3) {
            e3.printStackTrace();
            Toast.makeText(context, "Exception: " + e3.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }












    public static void Toast(Context context, String str) {
        Toast(context, str, 0);
    }

    public static void Toast(Context context, String str, int i) {
        Toast.makeText(context, str, i).show();
    }


    public static boolean hasWriteSettingsPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.System.canWrite(context);
        }
        return ContextCompat.checkSelfPermission(context, "android.permission.WRITE_SETTINGS") == 0;
    }

    public static void getWriteSettingsPermission(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            ((Activity) context).startActivityForResult(intent, i);
            return;
        }
        ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.WRITE_SETTINGS"}, i);
    }





    public static void getDndPermission(Context context, int i) {
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT < 23 || notificationManager.isNotificationPolicyAccessGranted()) {
            return;
        }
        ((Activity) context).startActivityForResult(new Intent("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"), i);
    }

    public static void setonoff(boolean z) {
        editor.putBoolean("onoff", z);
        editor.commit();
    }

    public static Boolean getonoff() {
        return Boolean.valueOf(pref.getBoolean("onoff", false));
    }

    public static void setRingMode(String str, String str2) {
        editor.putString(str, str2);
        editor.commit();
    }
}
