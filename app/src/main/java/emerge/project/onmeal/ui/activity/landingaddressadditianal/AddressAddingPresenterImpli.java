package emerge.project.onmeal.ui.activity.landingaddressadditianal;


import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class AddressAddingPresenterImpli implements AddressAddingPresenter,AddressAddingInteractor.OnAddNewAddressFinishedListener{


    private AddressAddingView addressAddingView;
    AddressAddingInteractor addressAddingInteractor;


    public AddressAddingPresenterImpli(AddressAddingView addressAddingView) {
        this.addressAddingView = addressAddingView;
        this.addressAddingInteractor = new AddressAddingInteractorImpil();

    }



    @Override
    public void addNewAddress(AddressItems addressItems) {
        addressAddingInteractor.addNewAddress(addressItems,this);
    }
    @Override
    public void addNewAddressSuccessful() {
        addressAddingView.addNewAddressSuccessful();
    }

    @Override
    public void addNewAddressFail(String msg) {
        addressAddingView.addNewAddressFail(msg);
    }


}
