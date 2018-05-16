package popovvad.findme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mapView;


    ImageButton imageButton;

    private static final int PERMISSION_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Инициализация компонентов UI
        initUI();
        //Инициализация АПИ яндекса
        MapKitFactory.setApiKey("0f8d1d87-de3b-4c52-823d-94463b58dae7");
        MapKitFactory.initialize(this);
        //Проверка разрешения на определение местоположения

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
        }else{
            setCurrentGeo();
        }
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
        //imageButton = (ImageButton)findViewById(R.id.button_refresh);
        //imageButton.setOnClickListener(this);
    }

    private void refreshUserCoordinates(final Context contextThread) {
        Intent intent = getIntent();
        final String user = intent.getStringExtra("user");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
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
