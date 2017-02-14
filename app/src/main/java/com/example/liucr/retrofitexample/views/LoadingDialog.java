package com.example.liucr.retrofitexample.views;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.liucr.retrofitexample.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

/**
 * Created by liucr on 2017/2/5.
 */

public class LoadingDialog extends Dialog {

    private TextView tipsView;

    private CircularProgressView circularProgressView;

    public LoadingDialog(Context context) {
        super(context, R.style.WaitingDialogStyle);
        setCustomDialog();
        this.setCancelable(false);
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_loading, null);
        circularProgressView = (CircularProgressView) mView.findViewById(R.id.progress_view);
        circularProgressView.setColor(getContext().getResources().getColor(R.color.colorAccent));

        tipsView = (TextView) mView.findViewById(R.id.waiting_tips);
        super.setContentView(mView);
    }

    public void setWaitingText(String waitingText) {
        if (!TextUtils.isEmpty(waitingText)) {
            tipsView.setText(waitingText);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }
}
