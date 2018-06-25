package popovvad.findme;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.Timer;
import java.util.TimerTask;


public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Инициализация компонентов UI
        initUI();
        //Проверка разрешения на определение местоположения
        setCurrentGeo();
    }

    @Override
    public void onClick(View v) {
        Message.showMessage(this,"test");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Опции");
        menu.add("FAQ");
        menu.add("Написать разработчику");
        menu.add("Выход");

        return super.onCreateOptionsMenu(menu);
    }

    private void initUI(){
        //Инициализация АПИ яндекса
        MapKitFactory.setApiKey("0f8d1d87-de3b-4c52-823d-94463b58dae7");
        MapKitFactory.initialize(this);

        setContentView(R.layout.map_activity);


    }

    private void refreshUserCoordinates(final Context contextThread) {
        Intent intent = getIntent();
        final String user = intent.getStringExtra("user");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Looper.myLooper() == null)
                {
                    Looper.prepare();
                }
                GeoPosition geoPosition = new GeoPosition();
                geoPosition.SetUpLocationListener(contextThread);
                ServerInteraction serverInteraction = new ServerInteraction("http://popovvad.ru/refreshCoordinates.php",
                        "{\"user\" " + ":\"" + user + "\", \"latitude\" " + ":\"" + geoPosition.getLatitude() + "\", \"longitude\" :" + "\"" + geoPosition.getLongitude() + "\"" + "}", "put");
                serverInteraction.execute();
            }
        }, 0L, 50L * 1000);
    }

    public void setCurrentGeo(){
        GeoPosition geoPosition = new GeoPosition();
        geoPosition.SetUpLocationListener(this);
        setContentView(R.layout.map_activity);
        mapView = findViewById(R.id.mapview);
        try {
            mapView.getMap().move(
                    new CameraPosition(new Point(geoPosition.getLatitude(), geoPosition.getLongitude()), 15.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);
            mapView.getMap().getMapObjects().addPlacemark(new Point(geoPosition.getLatitude(), geoPosition.getLongitude()), ImageProvider.fromResource(this, R.drawable.mygeo_light_icon));
            refreshUserCoordinates(this); // фоновая отправка текущих координат на сервер
        } catch (Exception t){
            Message.showMessage(this,"Ошибка определения местоположения");
        }
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

}
