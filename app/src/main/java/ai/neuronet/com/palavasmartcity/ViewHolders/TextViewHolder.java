package ai.neuronet.com.palavasmartcity.ViewHolders;

import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import ai.neuronet.com.palavasmartcity.R;

public class TextViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public TextViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }

    public void bindData(String value) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            textView.setText(Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT));
        else
            textView.setText(Html.fromHtml(value));
    }
}
