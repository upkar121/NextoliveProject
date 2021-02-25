package com.example.nextoliveproject.Helper;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nextoliveproject.R;

import java.util.Objects;

public class LogoProgressDialog {

    Dialog dialog;

    Context context;

    public LogoProgressDialog(Context context) {

        this.context = context;
    }

    public void setProgress(String message) {

        dialog = new Dialog(context, android.R.style.Theme_Black);
        View view = LayoutInflater.from(context).inflate(R.layout.loader_show_loader, null);
        TextView msg = view.findViewById(R.id.msg);
        msg.setText("");
        ImageView logo = view.findViewById(R.id.logo);
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        logo.startAnimation(rotate);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    public Dialog getDialog() {

        return dialog;
    }
}
