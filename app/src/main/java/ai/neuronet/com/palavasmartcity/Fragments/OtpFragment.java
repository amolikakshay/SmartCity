//package ai.neuronet.com.palavasmartcity.Fragments;
//
//import android.icu.util.ValueIterator;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.ai.web.defnition.Nbit;
//import com.google.gson.JsonObject;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
//import ai.neuronet.com.palavasmartcity.R;
//import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
//import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;
//
//public class OtpFragment extends Fragment implements IGetDataFromAsync {
//
//
//    private String id;
//    private String title;
//    private TextView welComeMessageText;
//    private ViewGroup sendOtpViewGroup;
//    private Button btnSendOTP;
//    private EditText mOtpEditText;
//    private ProgressBar progressBar;
//    private Nbit nbit;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_otp, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        id = getArguments().getString("ID");
//        title = getArguments().getString("title");
//        initViews(view);
//        callWebService();
//    }
//
//    private void initViews(View view) {
//
//        progressBar = view.findViewById(R.id.loginProgressbarOtp);
//        progressBar.setVisibility(View.VISIBLE);
//
//        welComeMessageText = view.findViewById(R.id.welComeMessageText);
//        mOtpEditText = view.findViewById(R.id.mOtpEditText);
//
//        View sendOtpConatiner = view.findViewById(R.id.btnSendOTP);
//        sendOtpViewGroup = (ViewGroup) sendOtpConatiner;
//        btnSendOTP = (Button) sendOtpViewGroup.getChildAt(0);
//        sendOtpViewGroup.setVisibility(View.VISIBLE);
//        btnSendOTP.setText(getResources().getString(R.string.sendOTP));
//        btnSendOTP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendOtp();
//            }
//        });
//    }
//
//    private void sendOtp() {
//
//        final Nbit nbit = new Nbit();
//        progressBar.setVisibility(View.VISIBLE);
//        DataCallAsync dataCallAsync = new DataCallAsync(new IGetDataFromAsync() {
//            @Override
//            public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {
//
//                progressBar.setVisibility(View.GONE);
//                if (customNBitClass.getChatDataArrayList().get(0) != null) {
//                    verifyOTP(nbit, customNBitClass.getChatDataArrayList().get(0).getChatMessage(), customNBitClass.getChatDataArrayList().get(0).getChatId());
//                }
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
//        }, this.nbit);
//
//        String id = (String) btnSendOTP.getTag(btnSendOTP.getId());
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("mobileno", mOtpEditText.getText().toString());
//        dataCallAsync.execute(jsonObject.toString(), id, "complaints:new", "complaints:new");
//    }
//
//    private void verifyOTP(Nbit nbit, String chatMessage, String chatId) {
//
//        if (getActivity() == null) {
//            return;
//        }
//        if (!getActivity().isFinishing()) {
//            VerifyOtpFragment loginFragment = new VerifyOtpFragment();
//
//            FragmentManager fragmentManager = getFragmentManager();
//            if (fragmentManager != null) {
//                if (getActivity() == null || getActivity().isFinishing()) {
//                    return;
//                }
//
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//                Bundle bundle = new Bundle();
//                bundle.putString("ID", chatId);
//                bundle.putString("title", chatMessage);
//                bundle.putString("nBit", new Nbit().GetJson(nbit));
//                loginFragment.setArguments(bundle);
//                transaction.replace(R.id.contentPanel, loginFragment, loginFragment.getClass().getCanonicalName()).addToBackStack(loginFragment.getClass().getCanonicalName()).commit();
//            }
//
//        }
//    }
//
//    private void callWebService() {
//        Nbit nbit = new Nbit();
//        Nbit nbit1 = nbit.GetNbit(getArguments().getString("nBit"));
//        DataCallAsync dataCallAsync = new DataCallAsync(this, nbit1);
//        dataCallAsync.execute(title, id, "complaints:new", "search:new");
//
//    }
//
//    @Override
//    public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {
//
//        Log.e("", "" + customNBitClass);
//        nbit = customNBitClass.getNbit();
//        progressBar.setVisibility(View.GONE);
//        welComeMessageText.setText(Html.fromHtml(customNBitClass.getChatDataArrayList().get(0).getChatMessage()));
//        mOtpEditText.setHint(customNBitClass.getChatDataArrayList().get(2).getChatMessage());
//        btnSendOTP.setTag(btnSendOTP.getId(), customNBitClass.getChatDataArrayList().get(2).getChatId());
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
//}
