package taylorhelene09gmail.diettrack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    public MutableLiveData item = new MutableLiveData();

    //public String getItem() {
    //    return item.getValue();
   // }

    public void setItem(String msg) {
       item.setValue(msg);
    }
    public MutableLiveData getMessage(){
      return item;
    }
    public MutableLiveData listitem = new MutableLiveData();

    //for sliders int
    public MutableLiveData slideitem = new MutableLiveData();
    public void setSlideitem(String num) {
        slideitem.setValue(num);
    }
    public MutableLiveData geSlideitem(){
        return slideitem;
    }
    //for sliders int
    public MutableLiveData slideitem2 = new MutableLiveData();
    public void setSlideitem2(String num2) {
        slideitem2.setValue(num2);
    }
    public MutableLiveData geSlideitem2(){
        return slideitem2;
    }
    //for sliders int
    public MutableLiveData slideitem3 = new MutableLiveData();
    public void setSlideitem3(String num3) {
        slideitem3.setValue(num3);
    }
    public MutableLiveData geSlideitem3(){
        return slideitem3;
    }
    //injection
    public MutableLiveData bpitem = new MutableLiveData();
    public void setbpitem(String bp) {
       bpitem.setValue(bp);
    }
    public MutableLiveData getbpitem(){
        return bpitem;
    }
    public MutableLiveData meditem = new MutableLiveData();
    public void setmeditem(String med) {
        meditem.setValue(med);
    }
    public MutableLiveData getmeditem(){
        return meditem;
    }
    public MutableLiveData list = new MutableLiveData();
    public void setList(String lst) {
        list.setValue(lst);
    }
    public MutableLiveData getList(){
        return list;
    }
}
