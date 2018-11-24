package ai.neuronet.com.palavasmartcity.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.utils.SharePreferenceManager;

public class SpalashFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spalsh, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

        new CountDownTimer(5000, 100) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {


                if (getActivity() == null) {
                    return;
                }

                if(SharePreferenceManager.getInstance().isLogin(getContext()))
                {
                    if (!getActivity().isFinishing()) {
                        MainFragment mainFragment = new MainFragment();

                        FragmentManager fragmentManager = getFragmentManager();
                        if (fragmentManager != null) {
                            if (getActivity() == null || getActivity().isFinishing()) {
                                return;
                            }
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.contentPanel, mainFragment, mainFragment.getClass().getCanonicalName()).addToBackStack(mainFragment.getClass().getCanonicalName()).commitAllowingStateLoss();
                        }
                    }
                }
                else {
                    if (!getActivity().isFinishing()) {
                        OtpFragment otpFragment = new OtpFragment();

                        FragmentManager fragmentManager = getFragmentManager();
                        if (fragmentManager != null) {
                            if (getActivity() == null || getActivity().isFinishing()) {
                                return;
                            }
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.contentPanel, otpFragment, otpFragment.getClass().getCanonicalName()).addToBackStack(otpFragment.getClass().getCanonicalName()).commitAllowingStateLoss();
                        }
                    }
                }
            }
        }.start();

    }
}
