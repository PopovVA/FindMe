package popovvad.findme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import popovvad.findme.mySupportLibrary.UniversalMechanisms;


public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MapView mapView;

    private Point mainPoint;
    private PlacemarkMapObject mainPlacemarkMapObject;

    private double longitude;
    private double latitude;

    private String main_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        //Определение местоположения
        setCurrentGeo();
    }


    private void initUI() {
        Intent intent = getIntent();
        main_user = intent.getStringExtra("main_user");
        MapKitFactory.setApiKey("0f8d1d87-de3b-4c52-823d-94463b58dae7");//Инициализация АПИ яндекса
        MapKitFactory.initialize(this);

        setContentView(R.layout.map_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Местоположение " + intent.getStringExtra("tittle_user"));
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UniversalMechanisms.showMessage(view.getContext(), "Определяю ваше местоположение...");
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Местоположение " + main_user);
                longitude = 0.00;
                latitude = 0.00;
                setCurrentGeo();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        longitude = intent.getDoubleExtra("longitude", 0.00);
        latitude = intent.getDoubleExtra("latitude", 0.00);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("main_user", main_user);
            startActivity(intent);
        } else if (id == R.id.nav_contacts) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_faq) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void refreshUserCoordinates(final Context contextThread) {
        Intent intent = getIntent();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!UniversalMechanisms.isOnline(contextThread)) {
                    return;
                }
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                GeoPosition geoPosition = new GeoPosition();
                geoPosition.SetUpLocationListener(contextThread);
                ServerInteraction serverInteraction = new ServerInteraction("http://popovvad.ru/refreshCoordinates.php",
                        "{\"username\" " + ":\"" + main_user + "\", \"latitude\" " + ":\"" + geoPosition.getLatitude() + "\", \"longitude\" :" + "\"" + geoPosition.getLongitude() + "\"" + "}", "put");
                serverInteraction.execute();
                try {
                    String response = serverInteraction.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, 0L, 50L * 1000);
    }

    public void setCurrentGeo() {
        GeoPosition geoPosition = new GeoPosition();
        if (latitude == 0.00 & longitude == 0.00) {
            geoPosition.SetUpLocationListener(this);
        } else {
            geoPosition.setLatitude(this.latitude);
            geoPosition.setLongitude(this.longitude);
        }

        mapView = findViewById(R.id.mapview);
        if (mainPoint == null) {
            mainPoint = new Point(geoPosition.getLatitude(), geoPosition.getLongitude());
            mainPlacemarkMapObject = mapView.getMap().getMapObjects().addPlacemark(mainPoint, ImageProvider.fromResource(this, R.drawable.mygeo_light_icon));
            ;
        } else {
            mainPoint = new Point(geoPosition.getLatitude(), geoPosition.getLongitude());
            mainPlacemarkMapObject.setGeometry(mainPoint);
        }
        try {
            mapView.getMap().move(
                    new CameraPosition(mainPoint, 15.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);
            //mapView.getMap().getMapObjects().addPlacemark(mainPoint,ImageProvider.fromResource(this, R.drawable.mygeo_light_icon));
            refreshUserCoordinates(this); // фоновая отправка текущих координат на сервер
        } catch (Exception t) {
            UniversalMechanisms.showMessage(this, "Ошибка определения местоположения");
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
