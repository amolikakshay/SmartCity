package ai.neuronet.com.palavasmartcity.networkCall;

import android.os.AsyncTask;

import com.ai.web.client.ClientDialogs;
import com.ai.web.client.HandleChat;


import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;

public class DataCallAsync extends AsyncTask<Void, Void, ClientDialogs> {


    private String jsonresponse;
    private IGetDataFromAsync iGetDataFromAsync;
    private String nodeId;
    private String dialog;
    private String nbitjson;
    private String session;
    private String user;
    private String context;
    private String lang;
    private String app;

    public DataCallAsync(IGetDataFromAsync iGetDataFromAsync) {
        this.iGetDataFromAsync = iGetDataFromAsync;
    }

    public void setDialog(String nodeid, String dialog, String nbitjson, String session, String user, String context, String lang, String app) {

        this.nodeId = nodeid;
        this.dialog = dialog;
        this.nbitjson = nbitjson;
        this.session = session;
        this.user = user;
        this.context = context;
        this.lang = lang;
        this.app = app;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected ClientDialogs doInBackground(Void... urls) {
        try {
            ClientDialogs clientDialogs = null;
            try {

                HandleChat chat = new HandleChat();
                jsonresponse = chat.setDialogs(nodeId, dialog, nbitjson, session, user, context, lang, app);
                iGetDataFromAsync.OnDataDoInBackground();
                if(jsonresponse!=null)
                clientDialogs = chat.getDialogs(jsonresponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return clientDialogs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(ClientDialogs message) {

        if (message == null) {
            showErrorMessage();
        } else {
            iGetDataFromAsync.onDataReceiveFromAsync(message);
        }
    }

    private void showErrorMessage() {

        iGetDataFromAsync.onDataReceiveFromAsync(null);
    }

    public String getJsonresponse() {
        return jsonresponse;
    }
}