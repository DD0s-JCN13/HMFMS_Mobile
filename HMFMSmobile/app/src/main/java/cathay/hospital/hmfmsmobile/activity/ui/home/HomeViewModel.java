package cathay.hospital.hmfmsmobile.activity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> Usrname;
    private MutableLiveData<String> divNo;

    public HomeViewModel() {
        Usrname = new MutableLiveData<>();
        divNo = new MutableLiveData<>();
        Usrname.setValue("Username");
        divNo.setValue("divNo");
    }

    public LiveData<String> getUsrname() { return Usrname;}
    public LiveData<String> getdivNo() { return divNo;}
}