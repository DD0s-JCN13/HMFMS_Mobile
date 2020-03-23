package cathay.hospital.hmfmsmobile.activity.ui.errorlocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cathay.hospital.hmfmsmobile.R;

public class ErrorlocationFragment extends Fragment {

    private ErrorlocationViewModel errorlocationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        errorlocationViewModel =
                ViewModelProviders.of(this).get(ErrorlocationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_errorlocation, container, false);
        final TextView textView = root.findViewById(R.id.text_errorlocation);
        errorlocationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {textView.setText(s); }
        });
        return root;
    }
}
