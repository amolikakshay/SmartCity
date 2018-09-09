package ai.neuronet.com.palavasmartcity.PojoClasses;

public enum CardType {

    SINGLE_TEXT_BUTTON(0), DOUBLE_TEXT_BUTTON(1),

    TRIPLE_TEXT_BUTTON(2), THREE_TEXT_ONE_DROPDOWN_Button(3),

    TWO_TEXT_THREE_DROPDOWN_ONE_DATEPICKER_BUTTON(4), THREE_TEXT_ONE_DROPDOWN_ONE_DATEPICKER_BUTTON_ONE_AUTOCOMPLTEVIEW_BUTTON(5);
    private int _type;

    CardType(int type) {
        _type = type;
    }

    public static CardType getItemType(String type) {
        switch (type.toLowerCase()) {
            case "text":
                return SINGLE_TEXT_BUTTON;
            case "voicetext":
                return DOUBLE_TEXT_BUTTON;
            case "voice":
                return TRIPLE_TEXT_BUTTON;
            case "button":
                return THREE_TEXT_ONE_DROPDOWN_Button;
            case "image":
                return TWO_TEXT_THREE_DROPDOWN_ONE_DATEPICKER_BUTTON;
            case "card":
        }
        return SINGLE_TEXT_BUTTON;
    }

    public int getType() {
        return _type;
    }


}
