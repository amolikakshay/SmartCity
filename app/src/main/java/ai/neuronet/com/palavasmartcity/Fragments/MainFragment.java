package ai.neuronet.com.palavasmartcity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ai.web.client.ClientDialogs;
import com.ai.web.client.Dialog;
import com.ai.web.client.Dialogs;
import com.ai.web.defnition.Nbit;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.reginald.editspinner.EditSpinner;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import ai.neuronet.com.palavasmartcity.Adapters.SmartCityResultAdapter;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.GetSysnonmsSuccessListener;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.networkCall.DataCallAsync;
import ai.neuronet.com.palavasmartcity.networkCall.GetSysnonosTask;
import ai.neuronet.com.palavasmartcity.views.CustomAutoCompleteTextvIew;

public class MainFragment extends Fragment implements IGetDataFromAsync, SwipeRevealLayout.SwipeListener, GetSysnonmsSuccessListener, View.OnClickListener {


    private DataCallAsync _dataCallAsync;
    private RecyclerView _recyclerView;
    private SmartCityResultAdapter _smartCityResultAdapter;
    private ArrayList<Dialog> _dialogsList;
    private AVLoadingIndicatorView _avLoadingIndicatorView;
    private AutoCompleteTextView _editText;
    private RelativeLayout bottomLayout;
    private ImageView _sendChat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        callWebSynonymsWebService();
        callWebService("0", "login", null, "0", null, "search:new", "en-US",
                getString(R.string.app_name_type));

    }

    private void initView(View view) {

        _avLoadingIndicatorView = view.findViewById(R.id.avi);
        SwipeRevealLayout swipeRevealLayout = view.findViewById(R.id.swipeRevealLyout);
        swipeRevealLayout.setSwipeListener(this);
        bottomLayout = view.findViewById(R.id.bottomLayout);
        _editText = view.findViewById(R.id.editText);
        _editText.setThreshold(1);
        _recyclerView = view.findViewById(R.id.recyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        _dialogsList = new ArrayList<>();
        _smartCityResultAdapter = new SmartCityResultAdapter(getActivity(), _dialogsList);
        _recyclerView.setAdapter(_smartCityResultAdapter);
        _sendChat = view.findViewById(R.id.sendChat);
        _sendChat.setOnClickListener(this);
    }


    private void callWebService(String nodeId, String dialog, String nbitJson, String session, String user, String context, String lang, String appType) {

        _avLoadingIndicatorView.setVisibility(View.VISIBLE);
        _dataCallAsync = new DataCallAsync(this);
        _dataCallAsync.setDialog(nodeId, dialog, nbitJson, session, user, context, lang, getString(R.string.app_name_type));
        _dataCallAsync.execute();
    }

    @Override
    public void onDataReceiveFromAsync(ClientDialogs customNBitClass) {

        _avLoadingIndicatorView.setVisibility(View.GONE);
        Dialogs dialogs = customNBitClass.getDialogs();
        ArrayList<Dialog> dialogArrayList = dialogs.getDialogs();
        _dialogsList.clear();
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

    }

    @Override
    public void onOpened(SwipeRevealLayout view) {

    }

    @Override
    public void onSlide(SwipeRevealLayout view, float slideOffset) {

    }

    @Override
    public void synonyms(ArrayList<String> strings) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _editText.setDropDownVerticalOffset(10);
        _editText.setAdapter(arrayAdapter);
    }

    private void hideSoftInputPanel() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(_editText.getWindowToken(), 0);
        }
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
        }
    }
}