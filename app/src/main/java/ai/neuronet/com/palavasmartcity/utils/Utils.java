package ai.neuronet.com.palavasmartcity.utils;

import java.util.ArrayList;

public class Utils
{
    private static Utils _instance;
    private ArrayList <String> _categoryList=new ArrayList<>(  );
    public static Utils getInstance()
    {
        if (_instance == null)
        {
            _instance = new Utils();
        }

        return _instance;
    }

    public void set_categoryList(String categoryList)
    {
       // _categoryList.clear();
        this._categoryList.add(categoryList);
    }

    public ArrayList<String> get_categoryList()
    {
        return _categoryList;
    }

    public void clearList()
    {
        this._categoryList.clear();
    }
}
