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
                if (!getActivity().isFinishing()) {
                    HomeFragment homeFragment = new HomeFragment();

                    FragmentManager fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.contentPanel, homeFragment, homeFragment.getClass().getCanonicalName()).addToBackStack(homeFragment.getClass().getCanonicalName()).commit();
                    }
                }
            }
        }.start();

    }
}
