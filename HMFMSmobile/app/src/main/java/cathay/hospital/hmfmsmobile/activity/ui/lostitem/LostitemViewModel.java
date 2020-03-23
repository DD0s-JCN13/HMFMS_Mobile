package cathay.hospital.hmfmsmobile.activity.ui.lostitem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LostitemViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LostitemViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Lost and Found...?");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
