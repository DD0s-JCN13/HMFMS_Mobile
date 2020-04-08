package cathay.hospital.hmfmsmobile.activity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> Usrname;
    private MutableLiveData<String> divNo;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        Usrname = new MutableLiveData<>();
        divNo = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        Usrname.setValue("Username");
        divNo.setValue("divNo");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getUsrname() { return Usrname;}
    public LiveData<String> getdivNo() { return divNo;}
}