package ai.neuronet.com.palavasmartcity.ViewHolders.CardHolders;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import ai.neuronet.com.palavasmartcity.PojoClasses.CardType;
import ai.neuronet.com.palavasmartcity.R;

public class SingleTextCardViewHolder extends RecyclerView.ViewHolder {

    private EditText _editText;

    public SingleTextCardViewHolder(View itemView, CardType cardType) {
        super(itemView);
        _editText = itemView.findViewById(R.id.mOtpEditText);

    }

    public void bindData(com.ai.web.client.Dialog dialog) {


        _editText.setText(dialog.getDialog());
    }

}
