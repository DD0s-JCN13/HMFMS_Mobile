package cathay.hospital.hmfmsmobile.activity.ui.lostitem;

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

public class LostitemFragment extends Fragment {

    private LostitemViewModel lostitemViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle saveInstanceState) {
        lostitemViewModel =
                ViewModelProviders.of(this).get(LostitemViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lostitem, container, false);
        final TextView textView = root.findViewById(R.id.text_lostitem);
        lostitemViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s);}
        });
        return root;
    }
}
