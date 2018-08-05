package ai.neuronet.com.palavasmartcity.PojoClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ${Shailendra} on 10-02-2018.
 */

public class ChatData {
    String chatId;
    String chatMessage;
    String chatType;
    String loopRequired;
    ChatType chatTypeEnum;
    String _textHighlighted="";
    String chatMessageOriginal;
    private List<String> _dropDownList=new ArrayList<>();
    private List<String> _multipleHighLightedList=new LinkedList<>();
    private boolean isTypeEmail;
    private boolean isTypePassword;
    private boolean _isLogin;


    public ChatType getChatTypeEnum() {
        return chatTypeEnum;
    }

    public void setChatTypeEnum(ChatType chatTypeEnum) {
        this.chatTypeEnum = chatTypeEnum;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getLoopRequired() {
        return loopRequired;
    }

    public void setLoopRequired(String loopRequired) {
        this.loopRequired = loopRequired;
    }

    public String getChatContainerText() {
        return chatContainerText;
    }

    public void setChatContainerText(String chatContainerText) {
        this.chatContainerText = chatContainerText;
    }

    String chatContainerText;

    public String getTextHighlighted()
    {
        return _textHighlighted;
    }

    public void setTextHighlighted(String textHighlighted)
    {
        _textHighlighted = textHighlighted;
    }

    public List<String> get_multipleHighLightedList()
    {
        return _multipleHighLightedList;
    }

    public void set_multipleHighLightedList(List<String> _multipleHighLightedList)
    {
        this._multipleHighLightedList = _multipleHighLightedList;
    }

    public List<String> get_dropDownList()
    {
        return _dropDownList;
    }

    public void set_dropDownList(List<String> dropDownList)
    {
        this._dropDownList.addAll(dropDownList);
    }

    public String get_textHighlighted()
    {
        return _textHighlighted;
    }

    public String getChatMessageOriginal()
    {
        return chatMessageOriginal;
    }

    public void setChatMessageOriginal(String chatMessageOriginal)
    {
        this.chatMessageOriginal = chatMessageOriginal;
    }

    public boolean isTypeEmail()
    {
        return isTypeEmail;
    }

    public void setTypeEmail(boolean typeEmail)
    {
        isTypeEmail = typeEmail;
    }

    public void set_textHighlighted(String _textHighlighted)

    {
        this._textHighlighted = _textHighlighted;

    }

    public boolean isTypePassword()
    {
        return isTypePassword;
    }

    public void setTypePassword(boolean typePassword)
    {
        isTypePassword = typePassword;
    }

    public boolean isLogin() {
        return _isLogin;
    }

    public void setIsLogin(boolean _isLogin) {
        this._isLogin = _isLogin;
    }
}
