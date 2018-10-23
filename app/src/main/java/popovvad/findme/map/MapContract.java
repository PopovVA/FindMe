package popovvad.findme.map;

import android.content.Context;
import android.content.Intent;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

public interface MapContract {

    interface View {

        void showToast(String message);

        void showProgressDialog();

        void hideProgressDialog();

        Context getContextView();

        String getMainUsername();

        void setMainUsername(String main_user);

        void setTittleUsername(String tittle_user);

        String getTittleUsername();

        void setTittleToolbar(String tittleToolbar);

        void setMapPoint();

        void moveCamera();

        void startSomeActivity(Intent intent);

    }

    interface Presenter {

        void onButtonMenuWasClicked();

        void onButtonSearchWasClicked();

        void onButtonContactsWasClicked();

        void onButtonSettingsWasClicked();

        void onButtonExitWasClicked();

        void onButtonSendWasClicked();

        void onButtonFaqWasClicked();

        void onButtonFabWasClicked();

        Point getMapPoint();

        CameraPosition getCameraPosition();

        Animation getAnimation();
    }

}
