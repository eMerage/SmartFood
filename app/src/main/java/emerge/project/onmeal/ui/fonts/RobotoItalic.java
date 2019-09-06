package emerge.project.onmeal.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by attract on 3/12/15.
 */

public class RobotoItalic extends TextView {
    public RobotoItalic(Context context) {
        super(context);
        init();
    }

    public RobotoItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoItalic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Italic.ttf");
        setTypeface(tf);
    }
}
