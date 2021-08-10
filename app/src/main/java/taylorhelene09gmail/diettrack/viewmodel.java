package taylorhelene09gmail.diettrack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class viewmodel extends ViewModel {
    public MutableLiveData listitem = new MutableLiveData();
    public void setListitem(String message) {
        listitem.setValue(message);
    }
    public MutableLiveData getListitem(){
        return listitem;
    }
}
