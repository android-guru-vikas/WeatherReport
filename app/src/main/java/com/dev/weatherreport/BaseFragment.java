package com.dev.weatherreport;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.weatherreport.network.controllers.WebServiceExecutor;
import com.dev.weatherreport.utils.AppLogs;
import com.dev.weatherreport.utils.AppToast;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseFragment extends Fragment implements WebServiceExecutor.APIResponseListener {
    private OnFragmentInteractionListener mListener;
    public Context pContext;
    public WebServiceExecutor pWebServiceExecutor;
    public AppToast pAppToast;
    public AppLogs pAppLogs;
    public String pTAG = BaseFragment.class.getName();
    private ProgressDialog pd;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObjects();

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading...");
        pd.setMessage("Please wait...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mListener != null) {
            mListener.onFragmentInteraction("Custom Title");
        }
        return inflater.inflate(R.layout.fragment_base, container, false);
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void initObjects() {
        pWebServiceExecutor = WebServiceExecutor.getInstance();
        pAppToast = AppToast.getInstance();
        pAppLogs = AppLogs.getInstance();
    }

    public void showLoading() {
        if (pContext != null && pd != null) {
            pd.show();
        }
    }

    public void hideLoading() {
        if (pd != null) {
            pd.hide();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            pContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onLoading(boolean isNetworkEnabled, boolean isLoading, Bundle bundle) {
        if (getActivity() != null) {
            if (!isNetworkEnabled) {
                pAppToast.showToast(pContext, "Network not found. Please check your network settings.");
            }
        }
    }

    @Override
    public void onNetworkFailure(Call call, Response response, Throwable throwable, Bundle bundle) {
        showErrorAndLogs(response, throwable);
        hideLoading();
    }

    @Override
    public void onNetworkSuccess(Call call, Response response, Bundle bundle) {
        hideLoading();
    }

    private void showErrorAndLogs(Response response, Throwable throwable) {
        if (response != null) {
            if (response.isSuccessful())
                showErrorToast(pContext, response.code(), response.message());
            else
                showErrorToast(pContext, response.code(), response.message());
        } else {
            apiErrorToast(pContext, throwable);
        }
    }

    protected void showErrorToast(Context context, int code, String message) {
        if (getActivity() != null) {
            if (context != null) {
                pAppLogs.e("ERROR", "Code : " + code + " " + message);
                if (code == 400) {
                    pAppToast.showToast(context, getString(R.string.bad_Request) + "\n" + code);
                } else if (code == 401) {
                    pAppToast.showToast(context, getString(R.string.unauthorized_user) + "\n" + code);
                } else if (code == 404) {
                    pAppToast.showToast(context, getString(R.string.something_went_wrong_on_server) + "\n" + code);
                } else if (code >= 500) {
                    pAppToast.showToast(context, getString(R.string.something_went_wrong_on_server) + "\n" + code);
                } else {
                    pAppToast.showToast(context, getString(R.string.something_went_wrong) + "\n" + code);
                }
            }
        }
    }

    protected void apiErrorToast(final Context context, final Throwable t) {
        if (getActivity() != null) {
            if (t instanceof UnknownHostException) {
                pAppToast.showToast(context, getString(R.string.UNKNOWN_HOST));
            } else if (t instanceof ConnectException) {
                pAppToast.showToast(context, getString(R.string.UNKNOWN_HOST));
            } else if (t instanceof SocketTimeoutException) {
                pAppToast.showToast(context, getString(R.string.SOCKET_TIME_OUT));
            } else if (t instanceof SocketException) {
                pAppToast.showToast(context, getString(R.string.UNABLE_TO_CONNECT));
            } else if (t instanceof JsonSyntaxException) {
                pAppToast.showToast(context, getString(R.string.JSON_SYNTAX));
            } else {
                pAppToast.showToast(context, getString(R.string.UNKNOWN_ERROR));
            }
        }

    }

}
