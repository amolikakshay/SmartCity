package ai.neuronet.com.palavasmartcity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.ai.web.client.ClientDialogs;
import com.ai.web.client.Dialog;
import com.ai.web.client.Dialogs;
import com.ai.web.client.HandleChat;
import com.ai.web.client.Utill;
import com.ai.web.defnition.Nbit;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ai.neuronet.com.palavasmartcity.Adapters.SmartCityResultAdapter;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.GetSysnonmsSuccessListener;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.callback.UpdateListener;
import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;
import ai.neuronet.com.palavasmartcity.networkCall.GetSysnonosTask;
import ai.neuronet.com.palavasmartcity.utils.SharePreferenceManager;

public class MainFragment extends Fragment implements IGetDataFromAsync, SwipeRevealLayout.SwipeListener, GetSysnonmsSuccessListener, View.OnClickListener,UpdateListener {


    private DataCallAsync _dataCallAsync;
    private RecyclerView _recyclerView;
    private SmartCityResultAdapter _smartCityResultAdapter;
    private ArrayList<Dialog> _dialogsList;
    private AVLoadingIndicatorView _avLoadingIndicatorView;
    private AutoCompleteTextView _editText;
    private RelativeLayout bottomLayout;
    private ImageView _sendChat;
    private SwipeRevealLayout swipeRevealLayout;
    private static String mainDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_main_fragment, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callWebSynonymsWebService();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        callWebService("0", "hello", null, "0", SharePreferenceManager.getInstance().getMobile(getContext()), null, "en-US",
                getString(R.string.app_name_type));
    }

    private void initView(View view) {

        _avLoadingIndicatorView = view.findViewById(R.id.avi);
        swipeRevealLayout = view.findViewById(R.id.swipeRevealLyout);
        swipeRevealLayout.setSwipeListener(this);
        bottomLayout = view.findViewById(R.id.bottomLayout);
        _editText = view.findViewById(R.id.editText);
        _editText.setThreshold(2);
        swipeRevealLayout.open(true);
        _editText.requestFocus();
        showSoftInputPanel(_editText);


        _recyclerView = view.findViewById(R.id.recyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        _dialogsList = new ArrayList<>();
        _smartCityResultAdapter = new SmartCityResultAdapter(getActivity(), _dialogsList,this);
        _recyclerView.setAdapter(_smartCityResultAdapter);
        _sendChat = view.findViewById(R.id.sendChat);
        _sendChat.setOnClickListener(this);
    }


    private void callWebService(String nodeId, String dialog, String nbitJson, String session, String user, String context, String lang, String appType) {

        _avLoadingIndicatorView.setVisibility(View.VISIBLE);
        _recyclerView.setVisibility(View.GONE);
        _dataCallAsync = new DataCallAsync(this);
        _dataCallAsync.setDialog(nodeId, dialog, nbitJson, session, user, context, lang, getString(R.string.app_name_type));
        _dataCallAsync.execute();
    }

    @Override
    public void onDataReceiveFromAsync(ClientDialogs customNBitClass) {

        _avLoadingIndicatorView.setVisibility(View.GONE);
        _recyclerView.setVisibility(View.VISIBLE);
        if(customNBitClass.getDialogs()==null)
        {
            return;
        }

        Dialogs dialogs = customNBitClass.getDialogs();
        ArrayList<Dialog> dialogArrayList = dialogs.getDialogs();
        _dialogsList.clear();


        if(dialogArrayList.size() >2 && dialogArrayList.get(2).displaytype.equalsIgnoreCase("FORM")) {
            org.jsoup.nodes.Document doc = Jsoup.parse(dialogArrayList.get(2).dialog);


            Dialog dialog = new Dialog();
            mainDialog = dialogArrayList.get(2).dialog;

            Elements keyElements = doc.select("label");

            List<String> keyStrings = new LinkedList<>();
            for (int i = 0 ;i<=keyElements.size()-1;i++)
            {
                keyStrings.add(keyElements.get(i).text());
            }

            Elements valueElements = doc.select("input");
            List<String> valueStrings = new LinkedList<>();

            for (int i = 0 ;i<=valueElements.size()-1;i++)
            {
                valueStrings.add(valueElements.get(i).attr("value"));
            }


            if(doc.select("select ")!=null && doc.select("select ").select("option")!=null) {
                Elements optionsElements =doc.select("select ").select("option");
                StringBuilder stringBuilder =new StringBuilder();
                for (int k=0;k<optionsElements.size()-1;k++) {

                    stringBuilder.append(optionsElements.get(k).text()+"%");
                }

                valueStrings.add(stringBuilder.toString());
            }
            JSONObject obj = new JSONObject();

            for (int j=0;j<keyStrings.size();j++) {
                try {
                    obj.put(keyStrings.get(j), valueStrings.get(j));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.e("Json Object",""+obj);

            dialogArrayList.get(2).dialog=obj.toString();
        }
        _dialogsList.addAll(dialogArrayList);
        _smartCityResultAdapter.notifyDataSetChanged();
        if (dialogArrayList.get(dialogArrayList.size() - 1).getForward().equalsIgnoreCase("1")) {
           sendRequest();
        }


    }

    private void callWebSynonymsWebService() {
        GetSysnonosTask getSysnonosTask = new GetSysnonosTask(this);
        getSysnonosTask.execute();
    }


    @Override
    public void OnDataDoInBackground() {

    }

    @Override
    public void isLogedIn(boolean isLogedIn) {

    }

    @Override
    public void onClosed(SwipeRevealLayout view) {
        android.util.Log.e("Close","2222");
    }

    @Override
    public void onOpened(SwipeRevealLayout view) {

        android.util.Log.e("Open","1111");

    }

    @Override
    public void onSlide(SwipeRevealLayout view, float slideOffset) {

        android.util.Log.e("OnSwaipe","00000");
    }

    @Override
    public void synonyms(ArrayList<String> strings) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _editText.setDropDownVerticalOffset(10);
        _editText.setAdapter(arrayAdapter);
    }

    private void hideSoftInputPanel() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(_editText.getWindowToken(), 0);
        }
        swipeRevealLayout.close(true);
    }

    private void showSoftInputPanel(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sendChat:
                sendRequest();
                break;
        }
    }

    private void sendRequest() {
        if (_dataCallAsync.getJsonresponse() != null) {
            Dialog dialog = _dialogsList.get(_dialogsList.size() - 1);
            Nbit nbit = new Nbit();
            nbit = nbit.GetNbit(_dataCallAsync.getJsonresponse());
            callWebService(dialog.getNodeid(), _editText.getText().toString(), _dataCallAsync.getJsonresponse(), nbit.getSessionid(), nbit.getUser(), nbit.getContext(), nbit.getLanguage(), getString(R.string.app_name_type));
            swipeRevealLayout.close(true);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (Fragment fragment:getActivity().getSupportFragmentManager().getFragments()) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        if(getActivity()!=null)
        getActivity().finish();
    }

    @Override
    public void update(String query) {

        if (_dataCallAsync.getJsonresponse() != null) {
            Dialog dialog = _dialogsList.get(_dialogsList.size() - 1);
            Nbit nbit = new Nbit();
            nbit = nbit.GetNbit(_dataCallAsync.getJsonresponse());
            callWebService(dialog.getNodeid(), query, _dataCallAsync.getJsonresponse(), nbit.getSessionid(), nbit.getUser(), nbit.getContext(), nbit.getLanguage(), getString(R.string.app_name_type));
            swipeRevealLayout.close(true);
        }
    }

    @Override
    public void logComplaint(Dialog dialog) {

        if (_dataCallAsync.getJsonresponse() != null) {
            Nbit nbit = new Nbit();
            nbit = nbit.GetNbit(_dataCallAsync.getJsonresponse());
            callWebService(dialog.getNodeid(), dialog.dialog, _dataCallAsync.getJsonresponse(), nbit.getSessionid(), nbit.getUser(), nbit.getContext(), nbit.getLanguage(), getString(R.string.app_name_type));
            swipeRevealLayout.close(true);
        }
    }
}