package com.dev.weatherreport.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.dev.weatherreport.BaseFragment;
import com.dev.weatherreport.R;
import com.dev.weatherreport.fragment.BangloreFragment;
import com.dev.weatherreport.fragment.ChennaiFragment;
import com.dev.weatherreport.fragment.DelhiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    private static String CURRENT_TAG = "delhi";
    private static final String DEL_TAG = "delhi";
    private static final String BLR_TAG = "banglore";
    private static final String CHE_TAG = "Chennai";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        Fragment fragment = null;
        FragmentManager fm = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.navigation_delhi:
                CURRENT_TAG = DEL_TAG;
                fragment = fm.findFragmentByTag(DEL_TAG);
                if (fragment == null) {
                    fragment = new DelhiFragment();
                }
                break;
            case R.id.navigation_blr:
                CURRENT_TAG = BLR_TAG;
                fragment = fm.findFragmentByTag(BLR_TAG);
                if (fragment == null) {
                    fragment = new BangloreFragment();
                }
                break;
            case R.id.navigation_chennai:
                CURRENT_TAG = CHE_TAG;
                fragment = fm.findFragmentByTag(CHE_TAG);
                if (fragment == null) {
                    fragment = new ChennaiFragment();
                }
                break;
        }
        return loadFragment(fragment, fm);
    };

    private boolean loadFragment(Fragment fragment, FragmentManager fm) {
        if (fragment != null) {
            fm.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        loadFragment(new DelhiFragment(), getSupportFragmentManager());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onLoading(boolean isNetworkEnabled, boolean isLoading, Bundle bundle) {

    }

    @Override
    public void onNetworkSuccess(Call call, Response response, Bundle bundle) {

    }

    @Override
    public void onNetworkFailure(Call call, Response response, Throwable throwable, Bundle bundle) {

    }
}
