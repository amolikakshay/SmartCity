package ai.neuronet.com.palavasmartcity.networkCall;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import com.ai.web.client.HandleChat;

import com.ai.web.client.Utill;
import com.ai.web.defnition.Format;
import com.ai.web.defnition.Mime;
import com.ai.web.defnition.Nbit;
import com.ai.web.defnition.Type;
import com.ai.web.workflow.components.Text;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.neuronet.com.palavasmartcity.PojoClasses.ChatData;
import ai.neuronet.com.palavasmartcity.PojoClasses.ChatType;
import ai.neuronet.com.palavasmartcity.PojoClasses.CustomNBitClass;
import ai.neuronet.com.palavasmartcity.callback.IGetDataFromAsync;
import ai.neuronet.com.palavasmartcity.utils.Utils;

public class DataCallAsync extends AsyncTask<String, Void, Nbit> {

    private String jsonresponse;
    private IGetDataFromAsync iGetDataFromAsync;

    public DataCallAsync(IGetDataFromAsync iGetDataFromAsync) {
        this.iGetDataFromAsync = iGetDataFromAsync;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Nbit doInBackground(String... urls) {
        try {
            String message = "Error";
            Nbit nbit = new Nbit();

            try {
                Nbit bit = new Nbit();
                bit.sessionid = "343f6d4e-b5a1-486f-8d44-d899b1d0e2a6";
                bit.context = urls[2];
                bit.type = Type.REQUEST;
                bit.prevcontext = urls[3];//"NA";
                bit.timestamp = String.valueOf(System.currentTimeMillis());
                bit.user = "68e5358b-a154-4cfd-8646-61e8d5887a18";
                bit.password = "pwd";
                bit.bytes = null;
                bit.followQuestion = null;
                bit.followAnswer = null;
                bit.followType = null;
                bit.format = Format.TEXT;
                bit.mimetype = Mime.string;
                ArrayList<Text> text = new ArrayList<Text>();
                Text t = new Text();
                t.setId(urls[1]);
                t.setTitle(urls[0]);
                text.add(t);
                bit.setText(text);
                bit.button = null;
                bit.card = null;
                bit.metadata = null;

                HandleChat chat = new HandleChat();
                jsonresponse = chat.handlechatTextRequest(bit);
                nbit = nbit.GetNbit(jsonresponse);
                System.out.println("bits resp: " + jsonresponse);
                iGetDataFromAsync.OnDataDoInBackground();
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
            }
            Log.e("Nucleus Message :-- ", message);
            return nbit;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(Nbit message) {
        Log.e("Login", "" + message);
        Log.e("Response ", message + "");
        if (message != null) {
            ArrayList<Text> messageList = message.getText();
            if (messageList != null && !messageList.isEmpty()) {
                String response = messageList.get(messageList.size() - 1).getTitle();
                String[] stringPipe = new String[0];
                ArrayList<ChatData> chatDataArrayList = new ArrayList<>();

                if (response != null && response.contains("|")) {
                    stringPipe = response.split("\\|");
                }

                if (stringPipe.length > 0) {
                    for (String aStringPipe : stringPipe) {
                        ChatData chatData = new ChatData();
                        String[] stringArr = aStringPipe.split("\\^");
                        chatData.setChatId(stringArr[0]);

                        if (stringArr[1].contains("~")) {
                            stringArr[1] = showHighlightedText(chatData, stringArr[1]);
                            chatData.setChatMessage(stripHtml(stringArr[1]));
                        } else if (stringArr[1].contains("[")) {
                            stringArr[1] = showHighlightedTextWithBractOnly(chatData, stringArr[1]);
                            chatData.setChatMessage(stringArr[1]);
                        } else {
                            chatData.setChatMessage(stripHtml(stringArr[1]));
                        }
                        chatData.setChatType(stringArr[3]);
                        chatData.setChatTypeEnum(ChatType.getItemType(stringArr[3]));
                        chatData.setLoopRequired(stringArr[4]);
                        chatData.setChatContainerText(stringArr[5]);
                        chatDataArrayList.add(chatData);
                    }
                } else {

                    if (response != null) {

                        String[] stringArr = response.split("\\^");
                        ChatData chatData = new ChatData();
                        chatData.setChatId(stringArr[0]);

                        if (stringArr[1].contains("~")) {
                            stringArr[1] = showHighlightedText(chatData, stringArr[1]);
                            chatData.setChatMessage(stripHtml(stringArr[1]));
                        } else if (stringArr[1].contains("[")) {
                            stringArr[1] = showHighlightedTextWithBractOnly(chatData, stringArr[1]);
                            chatData.setChatMessage(stringArr[1]);
                        } else {
                            chatData.setChatMessage(stripHtml(stringArr[1]));
                        }

//                        ArrayList<String> metaDataList = message.getMetadata();
//                        for (String textValues : metaDataList) {
//                            if (textValues.equalsIgnoreCase("ENTITY:logincheck:0")) {
//                                iGetDataFromAsync.isLogedIn(true);
//                            }
//                        }
                        chatData.setChatType(stringArr[3]);
                        chatData.setChatTypeEnum(ChatType.getItemType(stringArr[3]));
                        chatData.setLoopRequired(stringArr[4]);
                        chatData.setChatContainerText(stringArr[5]);
                        chatDataArrayList.add(chatData);
                    }
                }
                CustomNBitClass customNBitClass = new CustomNBitClass();
                customNBitClass.setChatDataArrayList(chatDataArrayList);
                customNBitClass.setNbit(message);
                iGetDataFromAsync.onDataReceiveFromAsync(customNBitClass);
            } else {
                showErrorMessage();
            }
        } else {
            showErrorMessage();
        }

    }

    private void showErrorMessage() {

        ChatData dummyChatData = new ChatData();
        dummyChatData.setChatId("0");
        dummyChatData.setChatMessage("Sorry, Something Went Wrong, Please Try again Later");
        dummyChatData.setChatType("text");
        dummyChatData.setChatTypeEnum(ChatType.getItemType("text"));
        dummyChatData.setLoopRequired("0");
        CustomNBitClass customNBitClass = new CustomNBitClass();
        ArrayList<ChatData> chatDataList = new ArrayList<>();
        chatDataList.add(dummyChatData);
        customNBitClass.setChatDataArrayList(chatDataList);
        customNBitClass.setNbit(new Nbit());
        iGetDataFromAsync.onDataReceiveFromAsync(customNBitClass);
    }

    public String stripHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
            return html;
        } else {
            return Html.fromHtml(html).toString();
        }
    }


    private String showHighlightedText(ChatData chatData, String jsonresponse) {

        if (jsonresponse.contains("~")) {
            String[] droupDownString = jsonresponse.split("~");
            String[] dp = droupDownString[0].split("\\[");

            if (dp.length > 1) {
                String firstDropdownValue = dp[1];
                String secondDropdownValue = droupDownString[1].replace("]", "");
                ArrayList<String> strings = new ArrayList<>();
                strings.add(firstDropdownValue);
                strings.add(secondDropdownValue);
                chatData.set_dropDownList(strings);
            }

            String[] lastArray = dp[0].split(" ");
            chatData.setTextHighlighted(lastArray[lastArray.length - 1]);
            return dp[0];
        }
        return jsonresponse;
    }


    private String showHighlightedTextWithBractOnly(ChatData chatData, String jsonresponse) {
        if (jsonresponse.contains("[")) {
            Pattern p = Pattern.compile("\\[(.*?)\\]");
            Matcher m = p.matcher(jsonresponse);

            List<String> arrayList = new LinkedList<>();
            while (m.find()) {
                System.out.println(m.group(1));
                if (m.group(1).contains(".")) {
                    String s = m.group(1);
                    s = s.replace("-", " ");
                    s = s.substring(0, s.indexOf("."));
                    arrayList.add(s);
                    jsonresponse = jsonresponse.replaceAll(m.group(1), s);

                } else {
                    arrayList.add(m.group(1));
                }
            }

            chatData.set_dropDownList(arrayList);
            chatData.set_multipleHighLightedList(arrayList);
            chatData.setChatMessageOriginal(jsonresponse);

            Utils.getInstance().clearList();
            jsonresponse = jsonresponse.replaceAll("\\[", " ").replaceAll("\\]", "");
            String[] stringBuilder = jsonresponse.split(" ");

            for (String value : arrayList) {
                for (int i = 0; i < stringBuilder.length; i++) {
                    if (value.equalsIgnoreCase(stringBuilder[i])) {
                        if (stringBuilder[i - 1] != null) {

                            Utils.getInstance().set_categoryList(stringBuilder[i - 1]);
                            stringBuilder[i - 1] = "";

//                            if (value.contains( "email" ))
//                            {
//                                chatData.setTypeEmail( true );
//                            }
//                            else if(value.contains( "password" ))
//                            {
//                                chatData.setTypePassword( true );
//                            }
                        }
                        break;
                    }
                }
            }

            jsonresponse = convertStringArrayToString(stringBuilder, " ").trim().replaceAll("\\s+", " ");
            ;

            return jsonresponse;
        }
        return jsonresponse;
    }


    private String convertStringArrayToString(String[] strings, String delimiter) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            stringBuilder.append(s).append(delimiter);
        }

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}