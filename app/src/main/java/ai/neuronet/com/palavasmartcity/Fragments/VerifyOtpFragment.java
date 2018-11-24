package ai.neuronet.com.palavasmartcity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.ai.web.client.Utill;
import java.io.IOException;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.utils.SharePreferenceManager;

public class VerifyOtpFragment extends android.support.v4.app.Fragment {


    private ViewGroup btnSubmitViewGroup;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private EditText mPinEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verify_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.loginProgressbarMPin);
        initView(view);
    }



    private void initView(View view) {

        View loginusingEmailConatiner = view.findViewById(R.id.btnSubmit);
        btnSubmitViewGroup = (ViewGroup) loginusingEmailConatiner;
        btnSubmit = (Button) btnSubmitViewGroup.getChildAt(0);
        btnSubmitViewGroup.setVisibility(View.VISIBLE);
        btnSubmit.setText("Submit");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subMitOtp();
            }
        });
        mPinEditText = (EditText) view.findViewById(R.id.mPinEditText);
        mPinEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private void subMitOtp() {

        progressBar.setVisibility(View.VISIBLE);
        VerifyOtpFragment.SubmitOtpTask submitOtpTask =new SubmitOtpTask();
        submitOtpTask.execute();
    }

    public class SubmitOtpTask extends AsyncTask<Void, Void, String> {

        private String mobileNumber;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mobileNumber = SharePreferenceManager.getInstance().getMobile(getActivity());
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return  new Utill().validateOTP(mobileNumber,mPinEditText.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);

            mPinEditText.setText("");
            if(string.equalsIgnoreCase("true"))
            {
                if (getActivity() == null) {
                    return;
                }
                if (!getActivity().isFinishing()) {
                    MainFragment mainFragment = new MainFragment();

                    FragmentManager fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }

                        SharePreferenceManager.getInstance().setLoginTrue(getContext());
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.contentPanel, mainFragment, mainFragment.getClass().getCanonicalName()).addToBackStack(mainFragment.getClass().getCanonicalName()).commitAllowingStateLoss();
                    }
                }
            }

            progressBar.setVisibility(View.GONE);
        }
    }
}
