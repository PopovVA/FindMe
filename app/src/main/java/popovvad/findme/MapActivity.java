package popovvad.findme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;

    private static final int PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("0f8d1d87-de3b-4c52-823d-94463b58dae7");
        MapKitFactory.initialize(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
        }else{
            GeoPosition geoPosition = new GeoPosition();
            geoPosition.SetUpLocationListener(this);
            setContentView(R.layout.map_activity);
            mapView = (MapView)findViewById(R.id.mapview);
            try {
                mapView.getMap().move(
                        new CameraPosition(new Point(geoPosition.getLatitude(), geoPosition.getLongitude()), 15.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0),
                        null);
                mapView.getMap().getMapObjects().addPlacemark(new Point(geoPosition.getLatitude(), geoPosition.getLongitude()), ImageProvider.fromResource(this, R.drawable.mygeo_icon));
            } catch (Exception t){
             Message.showMessage(this,"Ошибка определения местоположения");
            }

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Опции");
        menu.add("FAQ");
        menu.add("Написать разработчику");
        menu.add("Выход");

        return super.onCreateOptionsMenu(menu);
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
