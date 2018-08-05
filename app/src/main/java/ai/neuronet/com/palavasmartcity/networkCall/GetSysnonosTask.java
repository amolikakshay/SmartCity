package ai.neuronet.com.palavasmartcity.networkCall;

import android.os.AsyncTask;
import android.text.style.ClickableSpan;

import com.ai.web.client.Entity;

import java.util.ArrayList;
import ai.neuronet.com.palavasmartcity.callback.GetSysnonmsSuccessListener;


public class GetSysnonosTask extends AsyncTask<String, Void, ArrayList<String>>
{

    private ArrayList _sysnomsList;
    private GetSysnonmsSuccessListener _getSysnonmsSuccessListener ;

    public GetSysnonosTask(GetSysnonmsSuccessListener getSysnonmsSuccessListener)
    {
        _getSysnonmsSuccessListener = getSysnonmsSuccessListener;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings)
    {
        Entity entity =new Entity();
        return entity.getSynonymByName( strings[0]);
    }



    @Override
    protected void onPostExecute(ArrayList<String> strings)
    {
        super.onPostExecute( strings );
        _sysnomsList = new ArrayList(  );
        _sysnomsList.clear();
        _getSysnonmsSuccessListener.synonyms( strings );
        _sysnomsList.addAll( strings );
    }

    public ArrayList<String> getSysnomsList()
    {
        return _sysnomsList;
    }
}
