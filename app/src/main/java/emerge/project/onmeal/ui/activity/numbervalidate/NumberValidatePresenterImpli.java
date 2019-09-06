package emerge.project.onmeal.ui.activity.numbervalidate;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class NumberValidatePresenterImpli implements NumberValidatePresenter,
        NumberValidateInteractor.OnOTPCodeValidationFinishedListener,
        NumberValidateInteractor.OnNewOTPCodeFinishedListener{


    private NumberValidateView numberValidateView;
    NumberValidateInteractor numberValidateInteractor;


    public NumberValidatePresenterImpli(NumberValidateView numberValidateView) {

        this.numberValidateView = numberValidateView;
        this.numberValidateInteractor = new NumberValidateInteractorImpil();

    }


    @Override
    public void validateOTPCode(String codeFromUserReg, String codeFromSMS, String userId) {
        numberValidateInteractor.validateOTPCode(codeFromUserReg,codeFromSMS,userId,this);
    }



    @Override
    public void oTPCodeMissing() {
        numberValidateView.showOTPCodeMissing();
    }

    @Override
    public void oTPCodeInvalid() {
        numberValidateView.showOTPCodeInvalid();
    }

    @Override
    public void oTPCodeValid() {
        numberValidateView.showOTPCodeValid();
    }

    @Override
    public void oTPCodeExpired(String code) {
        numberValidateView.showOTPCodeExpired(code);
    }

    @Override
    public void oTPCodeServerError(String error) {
        numberValidateView.showOTPCodeServerError(error);
    }




    @Override
    public void getNewOTPCode(String userId) {
        numberValidateInteractor.getNewOTPCode(userId,this);
    }


    @Override
    public void newOTPCode(String code) {
        numberValidateView.showNewOTPCode(code);
    }

    @Override
    public void newOTPCodeServerError(String error) {
        numberValidateView.showNewOTPCodeServerError(error);
    }
}
