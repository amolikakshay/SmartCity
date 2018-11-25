package ai.neuronet.com.palavasmartcity.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import ai.neuronet.com.palavasmartcity.R;
import ai.neuronet.com.palavasmartcity.callback.UpdateListener;

public class ButtonViewHolder extends RecyclerView.ViewHolder {

    private android.widget.Button button;
    public ButtonViewHolder(View itemView) {
        super(itemView);
        button=itemView.findViewById(R.id.button);
    }

    public void bindData(final String data, final UpdateListener updateListener)
    {
       button.setText(data);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               updateListener.update(data);
           }
       });
    }
}
