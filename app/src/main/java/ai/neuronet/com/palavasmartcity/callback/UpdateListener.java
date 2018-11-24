package ai.neuronet.com.palavasmartcity.callback;

import android.app.Dialog;

public interface UpdateListener {
    void update(String query);
    void logComplaint(com.ai.web.client.Dialog dialog);
}
