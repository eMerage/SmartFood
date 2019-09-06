package emerge.project.onmeal.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by attract on 3/12/15.
 */

public class RobotoRegularEditText extends EditText {
    public RobotoRegularEditText(Context context) {
        super(context);
        init();
    }

    public RobotoRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        setTypeface(tf);
    }
}
