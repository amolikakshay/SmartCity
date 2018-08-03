package ai.neuronet.com.palavasmartcity.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ai.neuronet.com.neuronetai.R;

/**
 * Created by ${Shailendra} on 18-02-2018.
 */

public class NeuronetButton extends AppCompatButton {
    public NeuronetButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public NeuronetButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NeuronetButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface(context.getString(R.string.font_normal), context);
        setTypeface(customFont);
    }
}
