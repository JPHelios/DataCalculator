package com.mms.datacalculator.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mms.datacalculator.R;

import java.util.Objects;

public class DialogDurationTime extends DialogFragment implements View.OnClickListener {

    private int days;
    private DurationTimeListener durationTimeListener;

    public DialogDurationTime(DurationTimeListener durationTimeListener) {
        this.durationTimeListener = durationTimeListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_durationtime, container, false);

        Button cancel = view.findViewById(R.id.cancelDurationTimeDialogButton);
        Button ok = view.findViewById(R.id.okDurationTimeDialogButton);
        NumberPicker numberPickerDataVolume = view.findViewById(R.id.numberPickerDataVolume);

        numberPickerDataVolume.setMinValue(1);
        numberPickerDataVolume.setMaxValue(31);
        numberPickerDataVolume.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                days = newVal;
            }
        });

        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelDurationTimeDialogButton:
                Objects.requireNonNull(getDialog()).dismiss();
                break;

            case R.id.okDurationTimeDialogButton:
                durationTimeListener.onDurationTimeSelected(days);
                Objects.requireNonNull(getDialog()).dismiss();
                break;
        }
    }

    public interface DurationTimeListener {
        void onDurationTimeSelected(int days);
    }
}
