package com.mms.datacalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogDataVolume extends DialogFragment {

    private static final String TAG = "DialogDataVolume";

    private Button ok;
    private Button cancel;

    private NumberPicker numberPickerDataVolume;
    private int dataVolume;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_datavolume, container, false);

        cancel = view.findViewById(R.id.cancel);
        ok = view.findViewById(R.id.ok);
        numberPickerDataVolume = view.findViewById(R.id.numberPickerDataVolume);

        //NumberPicker
        numberPickerDataVolume.setMinValue(500);
        numberPickerDataVolume.setMaxValue(10000);
        dataVolume = 500;

        numberPickerDataVolume.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            numberPickerDataVolume.setValue((newVal < oldVal)? oldVal - 100:oldVal + 100);

            if(oldVal < newVal){
                dataVolume = oldVal + 100;
            } else if (oldVal > newVal){
                dataVolume = oldVal - 100;
            }

            }
        });

        //Cancel Button to dismiss
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
                ((Account)getActivity()).pickDataVolume.setText(dataVolume + " MB");
                getDialog().dismiss();


            }
        });

        return view;
    }
}
