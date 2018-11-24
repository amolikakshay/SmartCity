package ai.neuronet.com.palavasmartcity.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


import com.ai.web.client.ClientDialogs;
import com.ai.web.client.Utill;
import com.ai.web.defnition.Nbit;


import java.io.IOException;

import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.utils.SharePreferenceManager;

public class OtpFragment extends Fragment implements IGetDataFromAsync {


    private ViewGroup sendOtpViewGroup;
    private Button btnSendOTP;
    private EditText mOtpEditText;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {

        progressBar = view.findViewById(R.id.loginProgressbarOtp);


        mOtpEditText = view.findViewById(R.id.mOtpEditText);
        mOtpEditText.setInputType(InputType.TYPE_CLASS_PHONE);

        View sendOtpConatiner = view.findViewById(R.id.btnSendOTP);
        sendOtpViewGroup = (ViewGroup) sendOtpConatiner;
        btnSendOTP = (Button) sendOtpViewGroup.getChildAt(0);
        sendOtpViewGroup.setVisibility(View.VISIBLE);
        btnSendOTP.setText(getResources().getString(R.string.sendOTP));
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendOtp();
            }
        });
    }

    private void sendOtp() {

          GenerateOTPTask generateOTPTask =new GenerateOTPTask();
          generateOTPTask.execute();
    }

    private void verifyOTP() {

        if (getActivity() == null) {
            return;
        }
        if (!getActivity().isFinishing()) {
            VerifyOtpFragment verifyOtpFragment = new VerifyOtpFragment();

            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }

                SharePreferenceManager.getInstance().saveLoginDetails(mOtpEditText.getText().toString(),getContext());
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentPanel, verifyOtpFragment, verifyOtpFragment.getClass().getCanonicalName()).addToBackStack(verifyOtpFragment.getClass().getCanonicalName()).commitAllowingStateLoss();
            }

        }
    }

    @Override
    public void onDataReceiveFromAsync(ClientDialogs customNBitClass) {

//        Log.e("", "" + customNBitClass);
//        nbit = customNBitClass.getNbit();
//        progressBar.setVisibility(View.GONE);
//        welComeMessageText.setText(Html.fromHtml(customNBitClass.getChatDataArrayList().get(0).getChatMessage()));
//        mOtpEditText.setHint(customNBitClass.getChatDataArrayList().get(2).getChatMessage());
//        btnSendOTP.setTag(btnSendOTP.getId(), customNBitClass.getChatDataArrayList().get(2).getChatId());
    }

    @Override
    public void OnDataDoInBackground() {

    }

    @Override
    public void isLogedIn(boolean isLogedIn) {

    }

      public class GenerateOTPTask extends AsyncTask<Void, Void, String> {

          @Override
          protected void onPreExecute() {
              super.onPreExecute();
              progressBar.setVisibility(View.VISIBLE);
          }

          @Override
          protected String doInBackground(Void... voids) {
              try {
                  return  new Utill().generateOTP(mOtpEditText.getText().toString());
              } catch (IOException e) {
                e.printStackTrace();
              }
              return "";
          }

          @Override
          protected void onPostExecute(String string) {
              super.onPostExecute(string);
              progressBar.setVisibility(View.GONE);
              if(string.equalsIgnoreCase("true"))
              {
                  verifyOTP();
                  mOtpEditText.setText("");
              }
          }
      }
}
