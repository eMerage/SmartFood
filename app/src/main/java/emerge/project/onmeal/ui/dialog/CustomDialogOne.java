package emerge.project.onmeal.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import emerge.project.onmeal.R;


/**
 * Created by Himanshu on 11/17/2016.
 */

public class CustomDialogOne extends Dialog implements View.OnClickListener {

    private View mDialogView;
    TextView txtTitle, txtMessage;


    public static final int SUCCESS_TYPE =0;
    public static final int ERROR_TYPE = 1;
    public static final int WARNING_TYPE = 2;


    private String mTitleText, mMessageText, bConfirmBtnText = "OK";
    private int msgStatus;

    Boolean cancelableStatus = false;



    Context mContext;


    private OnClickListener mConfirmClickListener;


    private Button mOKButton;


    public CustomDialogOne(Context context, int alertType) {
        super(context);
        mContext = context;
        msgStatus = alertType;
    }

    public static interface OnClickListener {
        public void onClick(CustomDialogOne customdialogone);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        setContentView(R.layout.dialog_message_one);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        txtTitle = (TextView) findViewById(R.id.textView_message_title);
        txtMessage = (TextView) findViewById(R.id.textView_message_message);



        mOKButton = (Button) findViewById(R.id.button_ok);
        mOKButton.setOnClickListener(this);


        setErrorTypeGif(msgStatus);
        setTitleText(mTitleText);
        setMessageText(mMessageText);
        setButtonText(bConfirmBtnText);
        setDialogCancelable(cancelableStatus);


    }

    private void setErrorTypeGif(int msgStatus) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            if (msgStatus == 0) {
                mOKButton.setBackgroundDrawable(mDialogView.getResources().getDrawable(R.drawable.selector_button_green));
            } else if (msgStatus == 1) {
                mOKButton.setBackgroundDrawable(mDialogView.getResources().getDrawable(R.drawable.selector_button_red));
            } else if (msgStatus == 2) {
                mOKButton.setBackgroundDrawable(mDialogView.getResources().getDrawable(R.drawable.selector_button_yellow));
            } else {
                mOKButton.setBackgroundDrawable(mDialogView.getResources().getDrawable(R.drawable.selector_button_green));
            }

        } else {
            if (msgStatus == 0) {
                mOKButton.setBackground(mDialogView.getResources().getDrawable(R.drawable.selector_button_green));
            } else if (msgStatus == 1) {
                mOKButton.setBackground(mDialogView.getResources().getDrawable(R.drawable.selector_button_red));
            } else if (msgStatus == 2) {
                mOKButton.setBackground(mDialogView.getResources().getDrawable(R.drawable.selector_button_yellow));
            } else {
                mOKButton.setBackground(mDialogView.getResources().getDrawable(R.drawable.selector_button_green));
            }


        }




    }


    public CustomDialogOne setConfirmClickListener(OnClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }


    public CustomDialogOne setButtonText(String text) {
        bConfirmBtnText = text;
        if (mOKButton != null && mOKButton != null) {
            mOKButton.setText(bConfirmBtnText);
        }

        return this;
    }


    public CustomDialogOne setDialogCancelable(Boolean cancelable) {

        cancelableStatus = cancelable;
        setCancelable(cancelableStatus);
        return this;
    }


    public CustomDialogOne setTitleText(String text) {
        mTitleText = text;
        if (txtTitle != null && mTitleText != null) {
            txtTitle.setText(mTitleText);

        }
        return this;
    }


    public CustomDialogOne setMessageText(String text) {
        mMessageText = text;
        if (txtMessage != null && mMessageText != null) {
            txtMessage.setText(mMessageText);
        }
        return this;
    }


    @Override
    public void onClick(View v) {
        if (mConfirmClickListener != null) {
            mConfirmClickListener.onClick(CustomDialogOne.this);
        } else {

        }


    }


    protected void onStart() {
        mDialogView.showContextMenu();

    }


}
