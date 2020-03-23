package cathay.hospital.hmfmsmobile.activity.ui.locationmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationmapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LocationmapViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("I will tell you where, but not now");
    }

    public LiveData<String> getText(){
        return mText;
    }
}
