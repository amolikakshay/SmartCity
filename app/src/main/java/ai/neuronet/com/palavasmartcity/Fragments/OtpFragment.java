package ai.neuronet.com.palavasmartcity.Fragments;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;

public class OtpFragment extends Fragment implements IGetDataFromAsync
    {


        private String id;
        private String title;
        private TextView welComeMessageText;
        private ViewGroup sendOtpViewGroup;
        private Button btnSendOTP;
        private EditText mOtpEditText;
        private ProgressBar progressBar;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
            {
                return inflater.inflate(R.layout.fragment_otp, container, false);
            }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
            {
                super.onViewCreated(view, savedInstanceState);
                id = getArguments().getString("ID");
                title = getArguments().getString("title");
                initViews(view);
                callWebService();
            }

        private void initViews(View view)
            {

                progressBar = view.findViewById(R.id.loginProgressbarOtp);
                progressBar.setVisibility(View.VISIBLE);

                welComeMessageText = view.findViewById(R.id.welComeMessageText);
                mOtpEditText = view.findViewById(R.id.mOtpEditText);

                View sendOtpConatiner = view.findViewById(R.id.btnSendOTP);
                sendOtpViewGroup = (ViewGroup) sendOtpConatiner;
                btnSendOTP = (Button) sendOtpViewGroup.getChildAt(0);
                sendOtpViewGroup.setVisibility(View.VISIBLE);
                btnSendOTP.setText(getResources().getString(R.string.sendOTP));
                btnSendOTP.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                            {
                                sendOtp();
                            }
                    });
            }

        private void sendOtp()
            {


                progressBar.setVisibility(View.VISIBLE);
                DataCallAsync dataCallAsync = new DataCallAsync(new IGetDataFromAsync()
                    {
                        @Override
                        public void onDataReceiveFromAsync(CustomNBitClass customNBitClass)
                            {

                                progressBar.setVisibility(View.GONE);
                                if (customNBitClass.getChatDataArrayList().get(0).getChatMessage().toLowerCase().contains("Enter your OTP".toLowerCase()))
                                    {
                                        verifyOTP();
                                    }
                            }

                        @Override
                        public void OnDataDoInBackground()
                            {

                            }

                        @Override
                        public void isLogedIn(boolean isLogedIn)
                            {

                            }
                    });

                String id = (String) btnSendOTP.getTag(btnSendOTP.getId());

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("mobileno", "9420951562");
                dataCallAsync.execute("{\\\"mobileno\\\":\\\"9420951562\\\"}", id, "complaints:new", "complaints:new");
                //"9fd12e7f-2fc9-4377-85ad-7eec224b777a"
            }

        private void verifyOTP()
            {

            }

        private void callWebService()
            {

                DataCallAsync dataCallAsync = new DataCallAsync(this);
                dataCallAsync.execute(title, id, "complaints:new", "search:new");

            }

        @Override
        public void onDataReceiveFromAsync(CustomNBitClass customNBitClass)
            {

                Log.e("", "" + customNBitClass);
                progressBar.setVisibility(View.GONE);
                welComeMessageText.setText(Html.fromHtml(customNBitClass.getChatDataArrayList().get(0).getChatMessage()));
                mOtpEditText.setHint(customNBitClass.getChatDataArrayList().get(2).getChatMessage());
                btnSendOTP.setTag(btnSendOTP.getId(), customNBitClass.getChatDataArrayList().get(2).getChatId());
            }

        @Override
        public void OnDataDoInBackground()
            {

            }

        @Override
        public void isLogedIn(boolean isLogedIn)
            {

            }
    }
