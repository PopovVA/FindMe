package popovvad.findme.authorization;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import popovvad.findme.R;
import popovvad.findme.dataBase.onlineBase.ModelDB;
import popovvad.findme.dataBase.onlineBase.OnlineRepository;
import popovvad.findme.map.MapMainView;
import popovvad.findme.supportLibrary.UniversalMechanisms;


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
        if (!checkPermission()) {
            mView.hideProgressDialog();
            mView.showToast("Для работы приложения необходимо разрешение на работу с геолокацией");
            return;
        }
        if (!checkInternet()) {
            mView.hideProgressDialog();
            mView.showToast("Для работы приложения необходимо интернет соединение");
            return;
        }
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_authorization));
        mRepository.setJson(getJsonQuery());
        mRepository.setRequest("post");
        mRepository.loadResponse(new ModelDB.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                mView.hideProgressDialog();
                if (response.equals("successful")) {
                    //авторизация пройдена успешна, перехожу к карте
                    Intent intent = new Intent(mView.getContextView(), MapMainView.class);
                    intent.putExtra("main_user", mView.getUsername());
                    intent.putExtra("tittle_user", mView.getUsername());
                    mView.startSomeActivity(intent);
                } else {
                    mView.showToast(response);
                }
            }
        });

    }

    @Override
    public void onButtonRegWasClicked() {
        mView.showProgressDialog();
        if (!checkPermission()) {
            mView.hideProgressDialog();
            mView.showToast("Для работы приложения необходимо разрешение на работу с геолокацией");
            return;
        }
        if (!checkInternet()) {
            mView.hideProgressDialog();
            mView.showToast("Для работы приложения необходимо интернет соединение");
            return;
        }
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_registration));
        mRepository.setJson(getJsonQuery());
        mRepository.setRequest("post");
        mRepository.loadResponse(new ModelDB.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                mView.hideProgressDialog();
                if (response.equals("successful")) {
                    //авторизация пройдена успешна, перехожу к карте
                    Intent intent = new Intent(mView.getContextView(), MapMainView.class);
                    intent.putExtra("main_user", mView.getUsername());
                    intent.putExtra("tittle_user", mView.getUsername());
                    mView.startSomeActivity(intent);
                } else {
                    mView.showToast(response);
                }
            }
        });
    }

    private Boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(mView.getContextView(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private Boolean checkInternet() {
        if (!UniversalMechanisms.isOnline(mView.getContextView())) {
            return false;
        }
        return true;
    }

    private String getJsonQuery() {
        return "{\"username\" " + ":\"" + mView.getUsername() + "\", \"password\" :" + "\"" + mView.getPassword() + "\"" + "}";
    }
}
