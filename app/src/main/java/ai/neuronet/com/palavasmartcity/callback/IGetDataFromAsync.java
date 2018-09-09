package ai.neuronet.com.palavasmartcity.callback;
import com.ai.web.client.ClientDialogs;

/**
 * Created by ${Shailendra} on 10-02-2018.
 */

public interface IGetDataFromAsync {
    void onDataReceiveFromAsync(ClientDialogs customNBitClass);
    void OnDataDoInBackground();
    void isLogedIn(boolean isLogedIn);
}
