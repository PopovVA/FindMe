package popovvad.findme.authorization;


import android.content.Intent;
import android.content.res.Resources;

import popovvad.findme.MapActivity;
import popovvad.findme.R;
import popovvad.findme.dataBase.onlineBase.ModelDB;
import popovvad.findme.dataBase.onlineBase.OnlineRepository;

public class AuthorizationPresenter implements AuthorizationContract.Presenter {

    //Компоненты MVP приложения
    private AuthorizationContract.View mView;
    private OnlineRepository mRepository;
    private Resources resources;

    //Ответ сервера
    private String response;

    //Обрати внимание на аргументы конструктора - мы передаем экземпляр View, а Repository просто создаём конструктором.
    public AuthorizationPresenter(AuthorizationContract.View mView) {
        this.mView = mView;
        this.mRepository = new ModelDB();
        this.resources = mView.getContextView().getResources();
    }

    @Override
    public void onButtonLoginWasClicked() {
        mView.showProgressDialog();
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_authorization));
        mRepository.setJson(getJsonQuery());
        mRepository.setRequest("post");
        mRepository.loadResponse(new ModelDB.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                mView.hideProgressDialog();
                if (response.equals("successful")) {
                    //авторизация пройдена успешна, перехожу к карте
                    Intent intentReg = new Intent(mView.getContextView(), MapActivity.class);
                    intentReg.putExtra("main_user", mView.getUsername());
                    intentReg.putExtra("tittle_user", mView.getUsername());
                } else {
                    mView.showToast(response);
                }
            }
        });

    }

    @Override
    public void onButtonRegWasClicked() {
        mView.showProgressDialog();
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_registration));
        mRepository.setJson(getJsonQuery());
        mRepository.setRequest("post");
        mRepository.loadResponse(new ModelDB.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                mView.hideProgressDialog();
                if (response.equals("successful")) {
                    //авторизация пройдена успешна, перехожу к карте
                    Intent intentReg = new Intent(mView.getContextView(), MapActivity.class);
                    intentReg.putExtra("main_user", mView.getUsername());
                    intentReg.putExtra("tittle_user", mView.getUsername());
                } else {
                    mView.showToast(response);
                }
            }
        });
    }

    private String getJsonQuery() {
        return "{\"username\" " + ":\"" + mView.getUsername() + "\", \"password\" :" + "\"" + mView.getPassword() + "\"" + "}";
    }
}
