package com.appsbylyon.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class TipCalculator extends Activity
{
    private static final String BILL_TOTAL = "BILL_TOTAL";
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    private double currentBillTotal;

    private int currentCustomPercent;

    private EditText tip10EditText;
    private EditText total10EditText;
    private EditText tip15EditText;
    private EditText total15EditText;
    private EditText tip20EditText;
    private EditText total20EditText;
    private EditText billEditText;
    private EditText tipCustomEditText;
    private EditText totalCustomEditText;

    private TextView customTipTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

        if (savedInstanceState == null)
        {
            currentBillTotal = 0.0;
            currentCustomPercent = 18;
            Log.i("Tip Calc", "Bundle is NULL");
        }
        else
        {
            currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
            currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
            Log.i("Tip Calc", "Bundle is NOT null");
        }

        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditText);
        tip20EditText = (EditText) findViewById(R.id.tip20EditText);
        total20EditText = (EditText) findViewById(R.id.total20EditText);
        billEditText = (EditText) findViewById(R.id.billEditText);
        tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

        customTipTextView = (TextView) findViewById(R.id.customTipTextView);

        billEditText.addTextChangedListener(billEditTextWatcher);

        SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

        customSeekBar.setProgress(currentCustomPercent);
        if (currentBillTotal != 0.0)
        {
            billEditText.setText(Double.toString(currentBillTotal));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.i("Tip Calc", "Save Instance State Called");
        super.onSaveInstanceState(outState);
        outState.putDouble(BILL_TOTAL, currentBillTotal);
        outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
    }

    private void updateStandard()
    {
        double PercentTip10 = currentBillTotal * .1;
        double PercentTotal10 = currentBillTotal + PercentTip10;

        double PercentTip15 = currentBillTotal * .15;
        double PercentTotal15 = currentBillTotal + PercentTip15;

        double PercentTip20 = currentBillTotal * .2;
        double PercentTotal20 = currentBillTotal + PercentTip20;

        tip10EditText.setText(String.format("%.02f", PercentTip10));
        tip15EditText.setText(String.format("%.02f", PercentTip15));
        tip20EditText.setText(String.format("%.02f", PercentTip20));

        total10EditText.setText(String.format("%.02f", PercentTotal10));
        total15EditText.setText(String.format("%.02f", PercentTotal15));
        total20EditText.setText(String.format("%.02f", PercentTotal20));
    }

    private void updateCustom()
    {
        customTipTextView.setText(currentCustomPercent+"%");

        double customTipAmount = currentBillTotal * currentCustomPercent * .01;
        double customTotalAmount = currentBillTotal + customTipAmount;

        tipCustomEditText.setText(String.format("%.02f", customTipAmount));
        totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
    }

    private SeekBar.OnSeekBarChangeListener customSeekBarListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            currentCustomPercent = seekBar.getProgress();
            updateCustom();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar){}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar){}
    };

    private TextWatcher billEditTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3){}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {
            try
            {
                currentBillTotal = Double.parseDouble(charSequence.toString());

            }
            catch (NumberFormatException NFE)
            {
                Toast.makeText(TipCalculator.this, "That is an invalid character", Toast.LENGTH_SHORT).show();
                if (charSequence.length() > 0)
                {
                    String value = charSequence.toString();
                    value = value.substring(0, (value.length()-1));
                    billEditText.setText(value);
                }
            }
            updateStandard();
            updateCustom();
        }
        @Override
        public void afterTextChanged(Editable editable){}
    };
}
