package popovvad.findme.map;

import android.content.Intent;
import android.content.res.Resources;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import popovvad.findme.R;
import popovvad.findme.authorization.AuthorizationView;
import popovvad.findme.dataBase.onlineBase.ModelDB;
import popovvad.findme.dataBase.onlineBase.OnlineRepository;
import popovvad.findme.search.SearchMainView;
import popovvad.findme.supportLibrary.GeoPosition;

public class MapPresenter implements MapContract.Presenter {

    //Компоненты MVP приложения
    private MapContract.View mView;
    private OnlineRepository mRepository;
    private Resources resources;

    //Ответ сервера
    private String response;
    private GeoPosition geoPosition;

    MapPresenter(MapMainView mView) {
        this.mView = mView;
        this.mRepository = new ModelDB();
        this.resources = mView.getContextView().getResources();
    }

    @Override
    public void onButtonMenuWasClicked() {

    }

    @Override
    public void onButtonSearchWasClicked() {
        Intent intent = new Intent(mView.getContextView(), SearchMainView.class);
        mView.startActivity(intent);
    }

    @Override
    public void onButtonContactsWasClicked() {
        Intent intent = new Intent(mView.getContextView(), SearchMainView.class);
        mView.startActivity(intent);
    }

    @Override
    public void onButtonSettingsWasClicked() {

    }

    @Override
    public void onButtonExitWasClicked() {
        Intent intent = new Intent(mView.getContextView(), AuthorizationView.class);
        mView.showProgressDialog();
        mView.startActivity(intent);
        mView.hideProgressDialog();
    }

    @Override
    public void onButtonSendWasClicked() {

    }

    @Override
    public void onButtonFaqWasClicked() {

    }

    @Override
    public void onButtonFabWasClicked() {

        //Создаю точку на карте и перемещаю камеру
        mView.showProgressDialog();
        mView.setTittleUsername(resources.getString(R.string.toolbar_tittle) + mView.getMainUsername());
        mView.setMapPoint();
        mView.moveCamera();

        //Обновляю координаты пользователя
        mRepository.setJson(getJsonQuery());
        mRepository.setRequest("put");
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_refreshCoordinates));
        mRepository.loadResponse(new ModelDB.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                mView.hideProgressDialog();
                if (response.equals("successful")) {
                    mView.showToast("Определил ваше местоположение");
                } else {
                    mView.showToast(response);
                }
            }
        });
    }

    private GeoPosition getGeoPosition() {
        if (geoPosition == null) {
            geoPosition = new GeoPosition();
        }
        geoPosition.SetUpLocationListener(mView.getContextView());
        return geoPosition;
    }

    @Override
    public Point getMapPoint() {
        return new Point(getGeoPosition().getLatitude(), getGeoPosition().getLongitude());
    }

    @Override
    public CameraPosition getCameraPosition() {
        return new CameraPosition(getMapPoint(), 15.0f, 0.0f, 0.0f);
    }

    @Override
    public Animation getAnimation() {
        return new Animation(Animation.Type.SMOOTH, 0);
    }

    private String getJsonQuery() {
        return "{\"username\" " + ":\"" + mView.getMainUsername() + "\", \"latitude\" " + ":\"" + geoPosition.getLatitude() + "\", \"longitude\" :" + "\"" + geoPosition.getLongitude() + "\"" + "}";
    }

}
