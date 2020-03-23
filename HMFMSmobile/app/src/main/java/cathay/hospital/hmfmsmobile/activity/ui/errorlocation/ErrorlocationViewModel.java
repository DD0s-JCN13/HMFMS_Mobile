package cathay.hospital.hmfmsmobile.activity.ui.errorlocation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ErrorlocationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ErrorlocationViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Nothing to show cuz' no data currently");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
