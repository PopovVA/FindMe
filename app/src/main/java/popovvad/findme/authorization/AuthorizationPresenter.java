package popovvad.findme.authorization;


import android.content.res.Resources;

import popovvad.findme.R;
import popovvad.findme.mySupportLibrary.InputControl;

public class AuthorizationPresenter implements AuthorizationContract.Presenter {

    //Компоненты MVP приложения
    private AuthorizationContract.View mView;
    private AuthorizationContract.Repository mRepository;
    private Resources resources;

    //Ответ сервера
    private String response;

    //Обрати внимание на аргументы конструктора - мы передаем экземпляр View, а Repository просто создаём конструктором.
    public AuthorizationPresenter(AuthorizationContract.View mView) {
        this.mView = mView;
        this.mRepository = new AuthorizationRepository();
        this.resources = mView.getContextView().getResources();
    }

    @Override
    public void onButtonLoginWasClicked() {
        mView.showProgressDialog();
        response = InputControl.regControl(mView.getUsername(), mView.getPassword());
        if (!response.equals("successful")) {
            mView.hideProgressDialog();
            mView.showToast(response);
        }
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_authorization));
        mRepository.setJson(getJsonQuery());
        mRepository.setRequest("post");
        mRepository.loadResponse(new AuthorizationRepository.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                mView.hideProgressDialog();
                if (response.equals("successful")) {
                    //авторизация пройдена успешна, перехожу к карте
                    mView.showToast(response);
                } else {
                    mView.showToast(response);
                }
            }
        });

    }

    @Override
    public void onButtonRegWasClicked() {
        mView.showProgressDialog();
        response = InputControl.loginControl(mView.getUsername(), mView.getPassword());
        if (!response.equals("successful")) {
            mView.hideProgressDialog();
            mView.showToast(response);
        }
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_registration));
        mRepository.setJson(getJsonQuery());
        mRepository.setRequest("post");
        mRepository.loadResponse(new AuthorizationRepository.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                mView.hideProgressDialog();
                if (response.equals("successful")) {
                    //авторизация пройдена успешна, перехожу к карте
                    mView.showToast(response);
                } else {
                    mView.showToast(response);
                }
            }
        });
    }

    @Override
    public void onDestroy() {

    }

    private String getJsonQuery() {
        return "{\"username\" " + ":\"" + mView.getUsername() + "\", \"password\" :" + "\"" + mView.getPassword() + "\"" + "}";
    }
}
