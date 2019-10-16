package com.mms.datacalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogDurationTime extends DialogFragment {

    private static final String TAG = "DialogDataVolume";

    private Button ok;
    private Button cancel;

    private NumberPicker numberPickerDataVolume;
    private int dataVolume;
    private int days;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_durationtime, container, false);

        cancel = view.findViewById(R.id.cancel);
        ok = view.findViewById(R.id.ok);
        numberPickerDataVolume = view.findViewById(R.id.numberPickerDataVolume);

        //NumberPicker
        numberPickerDataVolume.setMinValue(1);
        numberPickerDataVolume.setMaxValue(31);



        numberPickerDataVolume.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                days = newVal;
            }
        });

        //Buttons1
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set value --->

                ((Account)getActivity()).durationTime.setText(days + "");
                getDialog().dismiss();
            }
        });

        return view;
    }
}
