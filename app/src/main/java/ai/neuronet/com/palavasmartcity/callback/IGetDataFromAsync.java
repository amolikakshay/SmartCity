package ai.neuronet.com.palavasmartcity.callback;
import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;

/**
 * Created by ${Shailendra} on 10-02-2018.
 */

public interface IGetDataFromAsync {
    void onDataReceiveFromAsync(CustomNBitClass customNBitClass);
    void OnDataDoInBackground();
    void isLogedIn(boolean isLogedIn);
}
