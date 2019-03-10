package com.dev.weatherreport.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.dev.weatherreport.network.controllers.WebServiceExecutor;
import com.dev.weatherreport.utils.AppLogs;
import com.dev.weatherreport.utils.AppToast;

public abstract class BaseActivity extends AppCompatActivity implements WebServiceExecutor.APIResponseListener {
    public Context pContext;
    public WebServiceExecutor pWebServiceExecutor;
    public AppToast pAppToast;
    public AppLogs pAppLogs;
    public String pTAG = BaseActivity.class.getName();
    private ProgressDialog pd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pd = new ProgressDialog(this);
        pd.setTitle("Loading...");
        pd.setMessage("Please wait...");
        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        setContentView(layout);
        initObjects();
    }

    public void showLoading() {
        if (pContext != null && pd != null && !isFinishing()) {
            pd.show();
        }
    }

    public void hideLoading() {
        if (pd != null && !isFinishing()) {
            pd.hide();
        }
    }

    private void initObjects() {
        pContext = this;
        pWebServiceExecutor = WebServiceExecutor.getInstance();
        pAppToast = AppToast.getInstance();
        pAppLogs = AppLogs.getInstance();
    }
}
