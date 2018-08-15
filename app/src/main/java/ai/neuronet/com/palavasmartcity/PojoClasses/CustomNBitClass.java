package ai.neuronet.com.palavasmartcity.PojoClasses;

import com.ai.web.defnition.Nbit;

import java.util.ArrayList;

/**
 * Created by ${Shailendra} on 10-02-2018.
 */

public class CustomNBitClass {

    Nbit nbit;
    private boolean isError;

    public Nbit getNbit() {
        return nbit;
    }

    public void setNbit(Nbit nbit) {
        this.nbit = nbit;
    }

    public ArrayList<ChatData> getChatDataArrayList() {
        return chatDataArrayList;
    }

    public void setChatDataArrayList(ArrayList<ChatData> chatDataArrayList) {
        this.chatDataArrayList = chatDataArrayList;
    }

    ArrayList<ChatData> chatDataArrayList;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
