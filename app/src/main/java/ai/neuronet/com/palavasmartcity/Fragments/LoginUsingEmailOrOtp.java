package ai.neuronet.com.palavasmartcity.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import ai.neuronet.com.palavasmartcity.PojoClasses.ChatData;
import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;

public class LoginUsingEmailOrOtp extends Fragment implements IGetDataFromAsync {


    private Button loginUsingEmail;
    private Button loginUsingOtp;

    String id;
    private ProgressBar progressBar;
    private ViewGroup loginUsingEmailCardView;
    private ViewGroup loginUsingOtpViewGroup;
    private TextView errorMessageToLoginUsingEmailOrOtp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_or_otp, container
                , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getString("ID");
        callWebService();
        initView(view);
    }

    private void initView(View view) {

        errorMessageToLoginUsingEmailOrOtp = view.findViewById(R.id.errorMessageToLoginUsingEmailOrOtp);
        View loginusingEmailConatiner = view.findViewById(R.id.loginusingEmail);
        loginUsingEmailCardView = (ViewGroup) loginusingEmailConatiner;
        loginUsingEmail = (Button) loginUsingEmailCardView.getChildAt(0);

        View loginUsingOtpContainer = view.findViewById(R.id.loginUsingOtp);
        loginUsingOtpViewGroup = (ViewGroup) loginUsingOtpContainer;
        loginUsingOtp = (Button) loginUsingOtpViewGroup.getChildAt(0);

        progressBar = view.findViewById(R.id.loginProgressbar);

        progressBar.setVisibility(View.VISIBLE);
    }

    private void callWebService() {

        DataCallAsync dataCallAsync = new DataCallAsync(this);
        dataCallAsync.execute("FaceRecognition:error", "d4b23baa-756f-4f2b-9279-e52261cd918c", "complaints:new", "search:new");
    }

    @Override
    public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {

        Log.e("", "");
        progressBar.setVisibility(View.GONE);
        ChatData  chatData =customNBitClass.getChatDataArrayList().get(0);
        errorMessageToLoginUsingEmailOrOtp.setText(chatData.getChatMessage());
        loginUsingEmailCardView.setVisibility(View.VISIBLE);
        loginUsingEmail.setText(customNBitClass.getChatDataArrayList().get(1).getChatMessage());

        loginUsingOtpViewGroup.setVisibility(View.VISIBLE);
        loginUsingOtp.setText(customNBitClass.getChatDataArrayList().get(2).getChatMessage());



    }

    @Override
    public void OnDataDoInBackground() {

    }

    @Override
    public void isLogedIn(boolean isLogedIn) {

    }
}