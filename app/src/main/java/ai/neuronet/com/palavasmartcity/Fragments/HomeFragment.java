package ai.neuronet.com.palavasmartcity.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ai.neuronet.com.palavasmartcity.Fragments.faceLogin.FaceLoginFragment;
import ai.neuronet.com.palavasmartcity.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {


        Button button = view.findViewById(R.id.startConversionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginProcess();
            }
        });

    }

    private void startLoginProcess() {


        if (getActivity() == null) {
            return;
        }
        if (!getActivity().isFinishing()) {
//            LoginFragment loginFragment = new LoginFragment();
            FaceLoginFragment loginFragment = new FaceLoginFragment();

            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentPanel, loginFragment, loginFragment.getClass().getCanonicalName()).addToBackStack(loginFragment.getClass().getCanonicalName()).commit();
            }
        }
    }
}
