package ai.neuronet.com.palavasmartcity.PojoClasses;

public enum ChatType {

    ITEM_TYPE_TEXT(0), ITEM_TYPE_BUTTON(1), ITEM_TYPE_IMAGE(2), ITEM_TYPE_CARD(3), ITEM_TYPE_GRID(4),ITEM_TYPE_INPUT(5),ITEM_TYPE_URL(5),ITEM_TYPE_LOGO(7),ITEM_TYPE_VOICE(8), ITEM_TYPE_UNKNOWN(100);

    private int _type;

    ChatType(int type)
    {
        _type = type;
    }

    public static ChatType getItemType(String type)
    {
        switch (type.toLowerCase())
        {
            case "text":
            case "voicetext":
                return ITEM_TYPE_TEXT;
            case "voice":
                return ITEM_TYPE_VOICE;
            case "button":
                return ITEM_TYPE_BUTTON;
            case "image":
                return ITEM_TYPE_IMAGE;
            case "card":
                return ITEM_TYPE_CARD;
            case "grid":
                return ITEM_TYPE_GRID;
            case "input":
                return ITEM_TYPE_INPUT;
            case "url":
                return ITEM_TYPE_URL;
            case "logo":
                return ITEM_TYPE_LOGO;
        }
        return ITEM_TYPE_UNKNOWN;
    }

    public int getType()
    {
        return _type;
    }

}
