package ai.neuronet.com.palavasmartcity.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

@SuppressLint("AppCompatCustomView")
public class CustomAutoCompleteTextvIew  extends AutoCompleteTextView
{

    public CustomAutoCompleteTextvIew(Context context) {
        super(context);

    }

    public CustomAutoCompleteTextvIew(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);

    }

    public CustomAutoCompleteTextvIew(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);


    }

    @Override
    public boolean enoughToFilter() {
        return false;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(getFilter()==null)
        {
            return;
        }

        if (focused) {
            performFiltering(getText(), 0);
        }
    }
}
