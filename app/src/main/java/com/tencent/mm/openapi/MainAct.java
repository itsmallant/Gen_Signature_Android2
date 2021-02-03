package com.tencent.mm.openapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainAct extends Activity {
    /* access modifiers changed from: private */
    public Button copyBtn;
    /* access modifiers changed from: private */
    public TextView errorTv;
    private Button getBtn;
    /* access modifiers changed from: private */
    public EditText pkgNameEt;
    /* access modifiers changed from: private */
    public TextView resultTv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.pkgNameEt = (EditText) findViewById(R.id.pkg_name_et);
        this.resultTv = (TextView) findViewById(R.id.result_tv);
        this.errorTv = (TextView) findViewById(R.id.error_tv);
        this.getBtn = (Button) findViewById(R.id.get_sign_btn);
        this.getBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainAct.this.resultTv.setText("");
                MainAct.this.errorTv.setText("");
                MainAct.this.copyBtn.setVisibility(View.GONE);
                String packageName = MainAct.this.pkgNameEt.getText().toString();
                if (packageName != null && packageName.length() > 0) {
                    MainAct.this.getSign(packageName);
                }
            }
        });
        this.copyBtn = (Button) findViewById(R.id.copy_btn);
        this.copyBtn.setVisibility(View.GONE);
        this.copyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ClipboardManager) MainAct.this.getSystemService(Context.CLIPBOARD_SERVICE)).setText(MainAct.this.resultTv.getText().toString().trim());
                Toast.makeText(MainAct.this, R.string.copy_done, Toast.LENGTH_LONG).show();
            }
        });
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.alert_title).setMessage(R.string.alert_msg);
        builder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainAct.this.finish();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public void getSign(String packageName) {
        Signature[] signs = getRawSignature(this, packageName);
        if (signs == null || signs.length == 0) {
            errout("signs is null");
            return;
        }
        for (Signature sign : signs) {
            stdout(MD5.getMessageDigest(sign.toByteArray()));
        }
    }

    private Signature[] getRawSignature(Context context, String packageName) {
        if (packageName == null || packageName.length() == 0) {
            errout("getSignature, packageName is null");
            return null;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (info != null) {
                return info.signatures;
            }
            errout("info is null, packageName = " + packageName);
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            errout("NameNotFoundException");
            return null;
        }
    }

    private void stdout(String content) {
        this.resultTv.append(String.valueOf(content) + "\n");
        this.copyBtn.setVisibility(View.VISIBLE);
    }

    private void errout(String content) {
        this.errorTv.append(String.valueOf(content) + "\n");
    }
}