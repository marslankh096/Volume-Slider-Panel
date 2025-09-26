package com.demo.volumeslider;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.demo.volumeslider.service.AdminReceiver;
import com.demo.volumeslider.service.MyAccessibilityService;


public class MainActivity extends AppCompatActivity {
    ComponentName compName;
    DevicePolicyManager deviceManger;
    ImageView setting;
    ImageView start;
    ImageView theme;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);



        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);


        final BottomSheetService bottomSheetService = new BottomSheetService();
        this.start = (ImageView) findViewById(R.id.btn_start);
        this.theme = (ImageView) findViewById(R.id.btn_theme);
        this.setting = (ImageView) findViewById(R.id.btn_settings);
        this.start.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                MainActivity.this.compName = new ComponentName(MainActivity.this, AdminReceiver.class);
                MainActivity mainActivity = MainActivity.this;
                mainActivity.deviceManger = (DevicePolicyManager) mainActivity.getSystemService(Context.DEVICE_POLICY_SERVICE);
                BottomSheetService.isaccess = Utl.isAccessibilitySettingsOn(MainActivity.this);
                BottomSheetService.iswritesetting = Utl.checkSystemWritePermission(MainActivity.this, -1);
                BottomSheetService.isoverlay = Utl.checkoverlay(MainActivity.this).booleanValue();
                if (!BottomSheetService.isaccess || !BottomSheetService.iswritesetting || !BottomSheetService.isoverlay) {
                    bottomSheetService.show(MainActivity.this.getSupportFragmentManager(), "exampleBottomSheet");
                    return;
                }
                if (Utl.getonoff().booleanValue()) {
                    Utl.setonoff(false);
                    MyAccessibilityService.destroyView();
                } else {
                    Utl.setonoff(true);
                    MyAccessibilityService.viewCrewate();
                }
                MainActivity.this.setonoff();
            }
        });
        this.theme.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ThemePacks.class));
            }
        });
        this.setting.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (!BottomSheetService.isaccess || !BottomSheetService.iswritesetting || !BottomSheetService.isoverlay) {
                    Toast.makeText(MainActivity.this, "Service not yet started", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }
            }
        });
        setonoff();
        DrawableCompat.setTint(DrawableCompat.wrap(AppCompatResources.getDrawable(this, R.drawable.fill)), 0xffff0000);
    }

    
    public static class BottomSheetService extends BottomSheetDialogFragment {
        public static boolean isaccess;
        public static boolean isoverlay;
        public static boolean iswritesetting;
        ImageView btn_close;
        ImageView btn_next;
        ImageView btnaccess;
        ImageView btnoverlay;
        ImageView btnwrite;
        ComponentName compName;
        DevicePolicyManager deviceManger;
        LinearLayout layaccess;
        LinearLayout layadmin;
        LinearLayout laydnd;
        LinearLayout layoverlay;
        LinearLayout laywrite;

        
        public interface BottomSheetListener {
            void onButtonClicked(String str);
        }

        public void onSuccess() {
        }

        @Override 
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View inflate = layoutInflater.inflate(R.layout.bottom_sheet_service, viewGroup, false);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MyTransparentBottomSheetDialogTheme);
            this.btnaccess = (ImageView) inflate.findViewById(R.id.btnaccess);
            this.btnwrite = (ImageView) inflate.findViewById(R.id.btnwrite);
            this.btnoverlay = (ImageView) inflate.findViewById(R.id.btnoverlay);
            this.layaccess = (LinearLayout) inflate.findViewById(R.id.layaccess);
            this.laydnd = (LinearLayout) inflate.findViewById(R.id.laydnd);
            this.laywrite = (LinearLayout) inflate.findViewById(R.id.laywrite);
            this.layoverlay = (LinearLayout) inflate.findViewById(R.id.layoverlay);
            this.layadmin = (LinearLayout) inflate.findViewById(R.id.layadmin);
            this.btn_close = (ImageView) inflate.findViewById(R.id.close);
            this.btn_next = (ImageView) inflate.findViewById(R.id.next);
            this.compName = new ComponentName(getActivity(), AdminReceiver.class);
            this.deviceManger = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
            isaccess = Utl.isAccessibilitySettingsOn(getActivity());
            iswritesetting = Utl.checkSystemWritePermission(getActivity(), -1);
            isoverlay = Utl.checkoverlay(getActivity()).booleanValue();
            if (isaccess) {
                this.layaccess.setVisibility(View.GONE);
            }
            if (iswritesetting) {
                this.laywrite.setVisibility(View.GONE);
            }
            if (isoverlay) {
                this.layoverlay.setVisibility(View.GONE);
            }
            this.layaccess.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Utl.getPermissionAccessibilty(BottomSheetService.this.getActivity(), 10);
                    BottomSheetService.this.dismiss();
                }
            });
            this.laydnd.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Utl.getDndPermission(BottomSheetService.this.getActivity(), 12);
                }
            });
            this.laywrite.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Utl.getWriteSettingsPermission(BottomSheetService.this.getActivity(), 11);
                    BottomSheetService.this.dismiss();
                }
            });
            this.layoverlay.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Utl.getOverlayPermission(BottomSheetService.this.getActivity(), 13);
                    if (BottomSheetService.isoverlay) {
                        BottomSheetService.this.layoverlay.setVisibility(View.GONE);
                        BottomSheetService.this.dismiss();
                    }
                }
            });
            this.layadmin.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
                    intent.putExtra("android.app.extra.DEVICE_ADMIN", BottomSheetService.this.compName);
                    intent.putExtra("android.app.extra.ADD_EXPLANATION", "You should enable the app!");
                    BottomSheetService.this.startActivityForResult(intent, 1);
                }
            });
            this.btn_close.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    BottomSheetService.this.dismiss();
                }
            });
            this.btn_next.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    if (!BottomSheetService.isaccess || !BottomSheetService.iswritesetting || !BottomSheetService.isoverlay) {
                        Toast.makeText(BottomSheetService.this.getActivity(), "Give all permission first", Toast.LENGTH_SHORT).show();
                    } else {
                        BottomSheetService.this.dismiss();
                    }
                }
            });
            return inflate;
        }

        @Override 
        public void onAttach(Context context) {
            super.onAttach(context);
        }

        @Override 
        public void onActivityResult(int i, int i2, Intent intent) {
            super.onActivityResult(i, i2, intent);
            if (i == 10) {
                boolean isAccessibilitySettingsOn = Utl.isAccessibilitySettingsOn(getActivity());
                isaccess = isAccessibilitySettingsOn;
                if (isAccessibilitySettingsOn) {
                    this.layaccess.setVisibility(View.GONE);
                }
            } else if (i == 11) {
                boolean hasWriteSettingsPermission = Utl.hasWriteSettingsPermission(getActivity());
                iswritesetting = hasWriteSettingsPermission;
                if (hasWriteSettingsPermission) {
                    this.laywrite.setVisibility(View.GONE);
                }
            }
            if (i == 13) {
                new Handler().postDelayed(new Runnable() { 
                    @Override 
                    public void run() {
                        BottomSheetService.isoverlay = Utl.checkoverlay(BottomSheetService.this.getActivity()).booleanValue();
                        if (BottomSheetService.isoverlay) {
                            BottomSheetService.this.layoverlay.setVisibility(View.GONE);
                        }
                        if (BottomSheetService.isaccess && BottomSheetService.iswritesetting && BottomSheetService.isoverlay) {
                            BottomSheetService.this.getActivity().setResult(-1);
                            new Utl(BottomSheetService.this.getActivity());
                            BottomSheetService.this.checkPermissions();
                        }
                    }
                }, 500L);
            }
            if (isaccess && iswritesetting && isoverlay) {
                getActivity().setResult(-1);
                new Utl(getActivity());
            }
        }

        public void checkPermissions() {
            String[] strArr = {"android.permission.MODIFY_AUDIO_SETTINGS"};
            if (Build.VERSION.SDK_INT > 22) {
                requestPermissions(strArr, PointerIconCompat.TYPE_CONTEXT_MENU);
            } else {
                onSuccess();
            }
        }

        @Override 
        public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
            super.onRequestPermissionsResult(i, strArr, iArr);
            if (i != 1001) {
                return;
            }
            if (iArr.length > 0) {
                for (int i2 : iArr) {
                    if (i2 != 0) {
                        getActivity().finish();
                        return;
                    }
                }
                onSuccess();
                return;
            }
            getActivity().finish();
        }

        @Override 
        public void onResume() {
            super.onResume();
            if (isaccess) {
                this.layaccess.setVisibility(View.GONE);
            }
            if (iswritesetting) {
                this.laywrite.setVisibility(View.GONE);
            }
            if (isoverlay) {
                this.layoverlay.setVisibility(View.GONE);
            }
        }
    }

    public void setonoff() {
        if (!BottomSheetService.iswritesetting || !BottomSheetService.isaccess || !BottomSheetService.isoverlay) {
            this.start.setImageDrawable(getResources().getDrawable(R.drawable.start_1));
        } else if (Utl.getonoff().booleanValue()) {
            this.start.setImageDrawable(getResources().getDrawable(R.drawable.stop_1));
        } else {
            Toast.makeText(this, "service off", Toast.LENGTH_SHORT).show();
            this.start.setImageDrawable(getResources().getDrawable(R.drawable.start_1));
        }
    }
}
