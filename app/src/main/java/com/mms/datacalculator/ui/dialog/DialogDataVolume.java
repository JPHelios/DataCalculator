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

public class DialogDataVolume extends DialogFragment implements View.OnClickListener {

    private DataVolumeListener dataVolumeListener;
    private NumberPicker numberPickerDataVolume;
    private int dataVolume  = 500;

    public DialogDataVolume(DataVolumeListener dataVolumeListener){
        this.dataVolumeListener = dataVolumeListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_datavolume, container, false);

        Button cancel = view.findViewById(R.id.cancelDurationTimeDialogButton);
        Button ok = view.findViewById(R.id.okDurationTimeDialogButton);

        numberPickerDataVolume = view.findViewById(R.id.numberPickerDataVolume);
        numberPickerDataVolume.setMinValue(500);
        numberPickerDataVolume.setMaxValue(10000);

        numberPickerDataVolume.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerDataVolume.setValue((newVal < oldVal) ? oldVal - 100 : oldVal + 100);

                if (oldVal < newVal) {
                    dataVolume = oldVal + 100;
                } else if (oldVal > newVal) {
                    dataVolume = oldVal - 100;
                }
            }
        });

        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelDurationTimeDialogButton:
                Objects.requireNonNull(getDialog()).dismiss();
                break;

            case R.id.okDurationTimeDialogButton:
                dataVolumeListener.onVolumeSelected(dataVolume);
                Objects.requireNonNull(getDialog()).dismiss();
                break;
        }
    }

    public interface DataVolumeListener {
        void onVolumeSelected(int dataVolume);
    }
}
