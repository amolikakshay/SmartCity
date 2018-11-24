package ai.neuronet.com.palavasmartcity.Fragments;

//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.widget.CardView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.ai.web.client.ClientDialogs;
//import com.ai.web.defnition.Nbit;
//
//import ai.neuronet.com.palavasmartcity.PojoClasses.ChatData;
//import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
//import ai.neuronet.com.palavasmartcity.R;
//import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
//import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;
//
//public class LoginUsingEmailOrOtp extends Fragment implements IGetDataFromAsync {
//
//
//    private Button loginUsingEmail;
//    private Button loginUsingOtp;
//
//    private String id;
//    private ProgressBar progressBar;
//    private ViewGroup loginUsingEmailCardView;
//    private ViewGroup loginUsingOtpViewGroup;
//    private TextView errorMessageToLoginUsingEmailOrOtp;
//    private String title;
//    private Nbit nbit;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_login_or_otp, container
//                , false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        id = getArguments().getString("ID");
//
//        title = (getArguments().getString("title"));
//        callWebService();
//        initView(view);
//    }
//
//    private void initView(View view) {
//
//        errorMessageToLoginUsingEmailOrOtp = view.findViewById(R.id.errorMessageToLoginUsingEmailOrOtp);
//        View loginusingEmailConatiner = view.findViewById(R.id.loginusingEmail);
//        loginUsingEmailCardView = (ViewGroup) loginusingEmailConatiner;
//        loginUsingEmail = (Button) loginUsingEmailCardView.getChildAt(0);
//        loginUsingEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginUsingEmail();
//            }
//        });
//        View loginUsingOtpContainer = view.findViewById(R.id.loginUsingOtp);
//        loginUsingOtpViewGroup = (ViewGroup) loginUsingOtpContainer;
//        loginUsingOtp = (Button) loginUsingOtpViewGroup.getChildAt(0);
//        loginUsingOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                loginUsingOTP();
//            }
//        });
//        progressBar = view.findViewById(R.id.loginProgressbar);
//
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    private void callWebService() {
//        DataCallAsync dataCallAsync = new DataCallAsync(this);
//        dataCallAsync.execute();
//    }
//
//    @Override
//    public void onDataReceiveFromAsync(ClientDialogs clientDialogs) {
//
//        Log.e("", "");
//        nbit = customNBitClass.getNbit();
//        progressBar.setVisibility(View.GONE);
//
//
//        DataCallAsync dataCallAsync = new DataCallAsync(new IGetDataFromAsync() {
//            @Override
//            public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {
//
//                ChatData chatData = customNBitClass.getChatDataArrayList().get(0);
//                errorMessageToLoginUsingEmailOrOtp.setText(chatData.getChatMessage());
//                loginUsingEmailCardView.setVisibility(View.VISIBLE);
//                loginUsingEmail.setText(customNBitClass.getChatDataArrayList().get(1).getChatMessage());
//                loginUsingEmail.setTag(loginUsingEmail.getId(), customNBitClass.getChatDataArrayList().get(1));
//
//                loginUsingOtpViewGroup.setVisibility(View.VISIBLE);
//                loginUsingOtp.setText(customNBitClass.getChatDataArrayList().get(2).getChatMessage());
//                loginUsingOtp.setTag(loginUsingOtp.getId(), customNBitClass.getChatDataArrayList().get(2));
//
//            }
//
//            @Override
//            public void OnDataDoInBackground() {
//
//            }
//
//            @Override
//            public void isLogedIn(boolean isLogedIn) {
//
//            }
//        }, customNBitClass.getNbit());
//        dataCallAsync.execute("FaceRecognition:error", customNBitClass.getChatDataArrayList().get(0).getChatId(), "complaints:new", "search:new");
//
//    }
//
//    @Override
//    public void OnDataDoInBackground() {
//
//    }
//
//    @Override
//    public void isLogedIn(boolean isLogedIn) {
//
//    }
//
//    private void loginUsingOTP() {
//
//        if (getActivity() == null) {
//            return;
//        }
//        if (!getActivity().isFinishing()) {
//            OtpFragment loginFragment = new OtpFragment();
//
//            FragmentManager fragmentManager = getFragmentManager();
//            if (fragmentManager != null) {
//                if (getActivity() == null || getActivity().isFinishing()) {
//                    return;
//                }
//
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                ChatData chatData = (ChatData) loginUsingOtp.getTag(loginUsingOtp.getId());
//                Bundle bundle = new Bundle();
//                bundle.putString("ID", chatData.getChatId());
//                Nbit nbit = new Nbit();
//                bundle.putString("nBit", nbit.GetJson(this.nbit));
//                bundle.putString("title", chatData.getChatContainerText());
//                loginFragment.setArguments(bundle);
//                transaction.replace(R.id.contentPanel, loginFragment, loginFragment.getClass().getCanonicalName()).addToBackStack(loginFragment.getClass().getCanonicalName()).commit();
//            }
//        }
//
//    }
//
//    private void loginUsingEmail() {
//
//        if (getActivity() == null) {
//            return;
//        }
//        if (!getActivity().isFinishing()) {
//            EmailAndPasswordFragment emailAndPasswordFragment = new EmailAndPasswordFragment();
//
//            FragmentManager fragmentManager = getFragmentManager();
//            if (fragmentManager != null) {
//                if (getActivity() == null || getActivity().isFinishing()) {
//                    return;
//                }
//
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                ChatData chatData = (ChatData) loginUsingEmail.getTag(loginUsingEmail.getId());
//                Bundle bundle = new Bundle();
//                bundle.putString("ID", chatData.getChatId());
//                bundle.putString("title", chatData.getChatContainerText());
//                emailAndPasswordFragment.setArguments(bundle);
//                transaction.replace(R.id.contentPanel, emailAndPasswordFragment, emailAndPasswordFragment.getClass().getCanonicalName()).addToBackStack(emailAndPasswordFragment.getClass().getCanonicalName()).commit();
//            }
//        }
//    }
//}
