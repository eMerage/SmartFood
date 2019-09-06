package emerge.project.onmeal.ui.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by attract on 3/12/15.
 */

public class RobotoBold extends TextView {
    public RobotoBold(Context context) {
        super(context);
        init();
    }

    public RobotoBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        setTypeface(tf);
    }
}
