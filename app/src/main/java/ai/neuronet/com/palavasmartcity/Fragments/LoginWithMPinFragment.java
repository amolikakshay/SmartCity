package ai.neuronet.com.palavasmartcity.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;

public class LoginWithMPinFragment extends android.support.v4.app.Fragment implements IGetDataFromAsync {


    private String id;
    private String title;
    private ViewGroup btnSubmitViewGroup;
    private Button btnSubmit;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mpin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getString("ID");
        title = getArguments().getString("title");
        progressBar = view.findViewById(R.id.loginProgressbarMPin);
        progressBar.setVisibility(View.VISIBLE);
        callWebService();
        initView(view);
    }

    private void callWebService() {
        DataCallAsync dataCallAsync = new DataCallAsync(this);
        dataCallAsync.execute(title, id, "search:new", "search:new");
    }


    private void initView(View view) {


        View loginusingEmailConatiner = view.findViewById(R.id.btnSubmit);
        btnSubmitViewGroup = (ViewGroup) loginusingEmailConatiner;
        btnSubmit = (Button) btnSubmitViewGroup.getChildAt(0);
        btnSubmitViewGroup.setVisibility(View.VISIBLE);
        btnSubmit.setText("Submit");
    }

    @Override
    public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {

        progressBar.setVisibility(View.GONE);
        Log.e("Data Received", "" + customNBitClass.getChatDataArrayList().get(0));

    }

    @Override
    public void OnDataDoInBackground() {

    }

    @Override
    public void isLogedIn(boolean isLogedIn) {

    }
}
