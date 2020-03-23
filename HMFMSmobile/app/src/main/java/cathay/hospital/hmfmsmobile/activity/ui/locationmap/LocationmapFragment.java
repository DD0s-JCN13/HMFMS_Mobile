package cathay.hospital.hmfmsmobile.activity.ui.locationmap;

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

public class LocationmapFragment extends Fragment {

    private LocationmapViewModel locationmapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationmapViewModel =
                ViewModelProviders.of(this).get(LocationmapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_locationmap, container, false);
        final TextView textView = root.findViewById(R.id.text_locationmap);
        locationmapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s);}
        });
        return root;
    }
}
