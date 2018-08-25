package ai.neuronet.com.palavasmartcity.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;

import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;

public class EmailAndPasswordFragment extends Fragment implements IGetDataFromAsync
    {


        private ProgressBar loginProgressbarEmail;
        private TextView welComeMessageText;
        private EditText mEmailEditText;
        private EditText mPasswordEditText;
        private ViewGroup sendLoginViewGroup;
        private Button btnLoginUsingEmail;
        private String id;
        private String title;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
            {
                return inflater.inflate(R.layout.emaild_and_password_fragment, container, false);
            }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
            {
                super.onViewCreated(view, savedInstanceState);
                initView(view);
                callWebService();
            }

        private void initView(View view)
            {

                id = getArguments().getString("ID");
                title = getArguments().getString("title");

                loginProgressbarEmail = view.findViewById(R.id.loginProgressbarEmail);
                welComeMessageText = view.findViewById(R.id.welComeMessageText);
                mEmailEditText = view.findViewById(R.id.mEmailEditText);
                mPasswordEditText = view.findViewById(R.id.mPasswordEditText);

                View sendLoginConatiner = view.findViewById(R.id.btnLoginUsingEmail);
                sendLoginViewGroup = (ViewGroup) sendLoginConatiner;
                btnLoginUsingEmail = (Button) sendLoginViewGroup.getChildAt(0);
                sendLoginViewGroup.setVisibility(View.VISIBLE);
                btnLoginUsingEmail.setText(getResources().getString(R.string.login));
            }

        @Override
        public void onDataReceiveFromAsync(CustomNBitClass customNBitClass)
            {
                Log.e("", "" + customNBitClass.getChatDataArrayList().get(0));
                welComeMessageText.setText(Html.fromHtml(customNBitClass.getChatDataArrayList().get(0).getChatMessage()));
                btnLoginUsingEmail.setTag(btnLoginUsingEmail.getId(), customNBitClass.getChatDataArrayList().get(2).getChatId());

                btnLoginUsingEmail.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                            {
                                sendloginService();
                            }
                    });
            }

        private void sendloginService()
            {
                DataCallAsync dataCallAsync = new DataCallAsync(new IGetDataFromAsync()
                    {
                        @Override
                        public void onDataReceiveFromAsync(CustomNBitClass customNBitClass)
                            {

                                Log.e("", "" + customNBitClass.getChatDataArrayList().get(0));
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

                String id = (String) btnLoginUsingEmail.getTag(btnLoginUsingEmail.getId());

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email", "testdesireepaco@hotmail.com");
                jsonObject.addProperty("password", "india@123");
                dataCallAsync.execute(jsonObject.toString(), id, "complaints:new", "complaints:new");
            }

        @Override
        public void OnDataDoInBackground()
            {

            }

        @Override
        public void isLogedIn(boolean isLogedIn)
            {

            }

        private void callWebService()
            {
                DataCallAsync dataCallAsync = new DataCallAsync(this);
                dataCallAsync.execute(title, id, "complaints:new", "search:new");
            }

    }
