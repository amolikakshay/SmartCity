package ai.neuronet.com.palavasmartcity.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import ai.neuronet.com.neuronetai.R;

public class NeuroNetTextView  extends AppCompatTextView
{


    public NeuroNetTextView(Context context)
    {
        super(context);
        applyCustomFont(context);
    }

    public NeuroNetTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NeuroNetTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context)
    {
        Typeface customFont = FontCache.getTypeface(context.getString(R.string.font_normal), context);
        setTypeface(customFont);
    }
}
