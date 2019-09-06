package emerge.project.onmeal.ui.activity.landingaddressmenual;


import emerge.project.onmeal.ui.activity.landingaddressadditianal.AddressAddingInteractor;
import emerge.project.onmeal.ui.activity.landingaddressadditianal.AddressAddingInteractorImpil;
import emerge.project.onmeal.ui.activity.landingaddressadditianal.AddressAddingPresenter;
import emerge.project.onmeal.ui.activity.landingaddressadditianal.AddressAddingView;
import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class AddressMenaulPresenterImpli implements AddressMenualPresenter,AddressMeunalInteractor.OnAddNewAddressFinishedListener{


    private AddressMenualView addressMenualView;
    AddressMeunalInteractor meunalInteractor;


    public AddressMenaulPresenterImpli(AddressMenualView addressMenualView) {
        this.addressMenualView = addressMenualView;
        this.meunalInteractor = new AddressMeunalInteractorImpil();

    }



    @Override
    public void addNewAddress(AddressItems addressItems) {
        meunalInteractor.addNewAddress(addressItems,this);
    }
    @Override
    public void addNewAddressSuccessful() {
        addressMenualView.addNewAddressSuccessful();
    }

    @Override
    public void addNewAddressFail(String msg) {
        addressMenualView.addNewAddressFail(msg);
    }


}
