package ai.neuronet.com.palavasmartcity.ViewHolders.CardHolders;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import ai.neuronet.com.palavasmartcity.PojoClasses.CardType;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.UpdateListener;
import ai.neuronet.com.palavasmartcity.font.NeuroNetTextView;
import ai.neuronet.com.palavasmartcity.views.CustomAutoCompleteTextvIew;


public class TextDropdownCardViewHolder extends RecyclerView.ViewHolder {

     private NeuroNetTextView complaintTextView;
     private CustomAutoCompleteTextvIew customAutoCompleteTextView_WithButton;
     private Button customAutoCompleteTextview_Button;
     private ConstraintLayout reportType,location,subArea;
     private TextView textView_with_spinner;
     private Spinner spinner;
     private View complaint_picker;
     private Button loginToSmartHubButton;
     private Context context;
     private UpdateListener _updateListener;



    public TextDropdownCardViewHolder(View itemView, CardType cardType, final UpdateListener updateListener) {
        super(itemView);

         context=itemView.getContext();

        complaintTextView=itemView.findViewById(R.id.textView_CustomAutoCompleteTextvIew);

        customAutoCompleteTextView_WithButton = itemView.findViewById(R.id.customAutoCompleteTextView_WithButton);
        customAutoCompleteTextView_WithButton.setInputType(InputType.TYPE_CLASS_TEXT);
        customAutoCompleteTextview_Button  =  itemView.findViewById(R.id.customAutoCompleteTextview_Button);

        reportType                          = itemView.findViewById(R.id.reportType);

        location                            = itemView.findViewById( R.id.location);

        subArea                            = itemView.findViewById(R.id.subArea);

        textView_with_spinner              = itemView.findViewById(R.id.textView_with_spinner);
        spinner                             =itemView.findViewById(R.id.spinner);

        complaint_picker                         =itemView.findViewById(R.id.complaint_picker);
        complaint_picker.setVisibility(View.GONE);

        CardView sendComplaintButtonContainer = itemView.findViewById(R.id.sendComplaintButton);

        sendComplaintButtonContainer.setVisibility(View.VISIBLE);
        loginToSmartHubButton = (Button) sendComplaintButtonContainer.getChildAt(0);

        customAutoCompleteTextview_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListener.update(customAutoCompleteTextView_WithButton.getText().toString());
            }
        });


        _updateListener = updateListener;
    }

    public void bindData(final com.ai.web.client.Dialog dialog) {
        String json = dialog.GetJson(dialog);

        JSONObject jObject  = null;
        try {
            jObject = new JSONObject(json);
            String  valueString = (String) jObject.optString("dialog");
            JSONObject menu = new JSONObject(valueString);

            Iterator iter = menu.keys();
            String[] keyArr = new String[10];
            String[] valArr = new String[10];
            int count = 0;
            while(iter.hasNext()){
                keyArr[count] = (String)iter.next();
                valArr[count] = menu.getString(keyArr[count]);
                count +=1;

            }
            complaintTextView.setText(keyArr[0]);
            customAutoCompleteTextView_WithButton.setText(valArr[0]);

            LinearLayout reportTypeLinerLayout = (LinearLayout) reportType.getChildAt(0);

            TextView reportTypeKey= (TextView) reportTypeLinerLayout.getChildAt(0);
            reportTypeKey.setText(keyArr[1]);

            TextView reportTypeValue= (TextView) reportTypeLinerLayout.getChildAt(1);
            reportTypeValue.setText(valArr[1]);


            LinearLayout locationLinerLayout = (LinearLayout) location.getChildAt(0);

            TextView locationKey= (TextView) locationLinerLayout.getChildAt(0);
            locationKey.setText(keyArr[2]);

            TextView locationValue= (TextView) locationLinerLayout.getChildAt(1);
            locationValue.setText(valArr[2]);


            LinearLayout subAreaLinerLayout = (LinearLayout) subArea.getChildAt(0);

            TextView subAreaKey= (TextView) subAreaLinerLayout.getChildAt(0);
            subAreaKey.setText(keyArr[3]);

            TextView subAreaKeyValue= (TextView) subAreaLinerLayout.getChildAt(1);
            subAreaKeyValue.setText(valArr[3]);

            textView_with_spinner.setText(keyArr[4]);

            String value= valArr[4];
            String [] spinnerArray = value.split("%");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (context, android.R.layout.simple_spinner_dropdown_item,
                            spinnerArray);

            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setSelection(0);
            loginToSmartHubButton.setText(context.getString(R.string.sendTheComplaint));


            menu.put(keyArr[4],spinner.getSelectedItem());
            jObject.put("dialog",menu);
            dialog.dialog = jObject.toString();
            loginToSmartHubButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _updateListener.logComplaint(dialog);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
