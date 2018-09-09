//package ai.neuronet.com.palavasmartcity.Fragments;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ProgressBar;
//
//import com.ai.web.defnition.Nbit;
//
//import ai.neuronet.com.palavasmartcity.PojoClasses.ChatData;
//import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
//
//import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
//import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;
//
//public class VerifyOtpFragment extends android.support.v4.app.Fragment implements IGetDataFromAsync {
//
//
//    private String id;
//    private String title;
//    private ViewGroup btnSubmitViewGroup;
//    private Button btnSubmit;
//    private ProgressBar progressBar;
//    private Nbit nbit;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_verify_otp, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        id = getArguments().getString("ID");
//        title = getArguments().getString("title");
//        progressBar = view.findViewById(R.id.loginProgressbarMPin);
//        progressBar.setVisibility(View.VISIBLE);
//        callWebService();
//        initView(view);
//    }
//
//    private void callWebService() {
//
//        Nbit nbit = new Nbit();
//        DataCallAsync dataCallAsync = new DataCallAsync(this, nbit.GetNbit(getArguments().getString("nBit")));
//        dataCallAsync.execute(title, id, "search:new", "search:new");
//    }
//
//
//    private void initView(View view) {
//
//        View loginusingEmailConatiner = view.findViewById(R.id.btnSubmit);
//        btnSubmitViewGroup = (ViewGroup) loginusingEmailConatiner;
//        btnSubmit = (Button) btnSubmitViewGroup.getChildAt(0);
//        btnSubmitViewGroup.setVisibility(View.VISIBLE);
//        btnSubmit.setText("Submit");
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                subMitOtp();
//            }
//        });
//    }
//
//    private void subMitOtp() {
//
//        final DataCallAsync dataCallAsync = new DataCallAsync(new IGetDataFromAsync() {
//            @Override
//            public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {
//                if (customNBitClass.getChatDataArrayList() != null && customNBitClass.getChatDataArrayList().get(0) != null) {
//
//                    if (getActivity() == null) {
//                        return;
//                    }
//                    if (!getActivity().isFinishing()) {
//                        MainFragment mainFragment = new MainFragment();
//
//                        FragmentManager fragmentManager = getFragmentManager();
//                        if (fragmentManager != null) {
//                            if (getActivity() == null || getActivity().isFinishing()) {
//                                return;
//                            }
//
//                            FragmentTransaction transaction = fragmentManager.beginTransaction();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("ID", id);
//                            bundle.putString("title", title);
//                            bundle.putString("nBit", new Nbit().GetJson(nbit));
//                            mainFragment.setArguments(bundle);
//                            transaction.replace(R.id.contentPanel, mainFragment, mainFragment.getClass().getCanonicalName()).addToBackStack(mainFragment.getClass().getCanonicalName()).commit();
//                        }
//
//                    }
//                }
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
//        }, nbit);
//
//        dataCallAsync.execute(title, id, "search:new", "search:new");
//    }
//
//    @Override
//    public void onDataReceiveFromAsync(CustomNBitClass customNBitClass) {
//
//        progressBar.setVisibility(View.GONE);
//        if (customNBitClass.getChatDataArrayList() != null && customNBitClass.getChatDataArrayList().get(0) != null) {
//
//            ChatData chatData = customNBitClass.getChatDataArrayList().get(0);
//            title = chatData.getChatMessage();
//            id = chatData.getChatId();
//            nbit = customNBitClass.getNbit();
//        }
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
