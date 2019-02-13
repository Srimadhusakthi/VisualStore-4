package com.visualstore.Validation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.visualstore.R;

public class Validation1 implements TextView.OnEditorActionListener {
    private Context context;
    private EditText edit1;
    private EditText edit2;
    private EditText edit3;
    private Double min;
    private Double max;
    private TextView errorView;
    private int msg;

    //   final EditText edit2, final EditText axis1, final EditText addition1,
//    final EditText edit3, final EditText cylind2, final EditText axis2, final EditText addition2
    public Validation1(Context context, EditText edit1, EditText edit2, EditText edit3, Double min, Double max, TextView errorView, int msg) {

        this.context = context;
        this.edit1 = edit1;
        this.edit2 = edit2;
        this.edit3 = edit3;
        this.min=min;
        this.max=max;
        this.errorView=errorView;
        this.msg=msg;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if ( !edit1.getText().toString().isEmpty()  ) {


            Double i = Double.valueOf(edit1.getText().toString());

            if (i > max) {
                edit1.setBackground(context.getResources().getDrawable(R.drawable.error_bg));
                errorView.setText(msg);
            } else if (i < min) {
                edit1.setBackground(context.getResources().getDrawable(R.drawable.error_bg));
                errorView.setText(msg);
            } else {
                edit2.requestFocus();
                errorView.setText("");
                edit3.setText(edit1.getText().toString().trim());
                edit1.setBackground(context.getResources().getDrawable(R.drawable.rectangle));
            }


            return true;


        }
        // Return true if you have consumed the action, else false.
        return false;
    }
}