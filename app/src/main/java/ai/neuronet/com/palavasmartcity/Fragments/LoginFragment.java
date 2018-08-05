package ai.neuronet.com.palavasmartcity.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ai.web.defnition.Nbit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ai.neuronet.com.palavasmartcity.PojoClasses.ChatData;
import ai.neuronet.com.palavasmartcity.PojoClasses.ChatType;
import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;

public class LoginFragment extends android.support.v4.app.Fragment implements IGetDataFromAsync {


    private ImageView palavaLogo;
    private TextView palavaLoginMessage;
    private TextView loginToSmartHubButton;
    private TextView palavaLogin_Subtitle;
    private ProgressBar loginProgressbar;
    private CardView buttonLoginCard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        palavaLogo = view.findViewById(R.id.palavaLogo);
        palavaLoginMessage = view.findViewById(R.id.palavaLoginMessage);
        loginToSmartHubButton = view.findViewById(R.id.loginToSmartHubButton);
        loginToSmartHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getActivity() == null) {
                    return;
                }
                if (!getActivity().isFinishing()) {
                    LoginUsingEmailOrOtp loginFragment = new LoginUsingEmailOrOtp();

                    FragmentManager fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        if (getActivity() == null || getActivity().isFinishing()) {
                            return;
                        }

                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        Bundle bundle =new Bundle();
                        bundle.putString("ID", (String) loginToSmartHubButton.getTag());
                        loginFragment.setArguments(bundle);
                        transaction.replace(R.id.contentPanel, loginFragment, loginFragment.getClass().getCanonicalName()).commit();
                    }
                }
            }
        });
        palavaLogin_Subtitle = view.findViewById(R.id.palavaLogin_Subtitle);
        loginProgressbar = view.findViewById(R.id.loginProgressbar);
        buttonLoginCard = view.findViewById(R.id.buttonLoginCard);
        loginProgressbar.setVisibility(View.VISIBLE);
        callWebService();
    }


    private void callWebService() {
        DataCallAsync dataCallAsync = new DataCallAsync(this);
        dataCallAsync.execute("login", "0", "search:new","NA");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {


        loginProgressbar.setVisibility(View.GONE);
        ArrayList<ChatData> chatDataArrayList = customNBitClass.getChatDataArrayList();

        for (ChatData chatData : chatDataArrayList) {
            if (chatData.getChatTypeEnum() == ChatType.ITEM_TYPE_IMAGE) {

                Glide.with(this)
                        .load("http://" + chatData.getChatMessage())
                        .into(palavaLogo);

            }
            palavaLoginMessage.setText(Html.fromHtml(chatDataArrayList.get(2).getChatMessage(), Html.FROM_HTML_MODE_LEGACY));
            loginToSmartHubButton.setText(chatDataArrayList.get(3).getChatContainerText());
            buttonLoginCard.setVisibility(View.VISIBLE);
            loginToSmartHubButton.setTag(chatDataArrayList.get(3).getChatId());
        }
    }

    @Override
    public void OnDataDoInBackground() {

    }

    @Override
    public void isLogedIn(boolean isLogedIn) {

    }
}
