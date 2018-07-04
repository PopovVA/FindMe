package popovvad.findme;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_button;
    Button reg_button;

    EditText login_user;
    EditText login_password;
    private static final int PERMISSION_REQUEST = 1;

    private ProgressDialog progressDialog = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
        }

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        reg_button = (Button) findViewById(R.id.reg_button);
        reg_button.setOnClickListener(this);

        login_user = (EditText) findViewById(R.id.login_user);
        login_password = (EditText) findViewById(R.id.login_password);


    }


    @Override
    public void onClick(View v) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Message.showMessage(this,"Для работы приложения необходимо разрешение на работу с геолокацией");
            return;
        }
        progressDialog = new ProgressDialog(v.getContext(),R.style.MyTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
        switch (v.getId()) {
            case R.id.login_button:

                User user = new User(login_user.getText().toString(),login_password.getText().toString(),getApplicationContext());
                try {
                    if(user.login()){
                        Intent intentLog = new Intent(this, MapActivity.class);
                        intentLog.putExtra("user", user.getUsername());
                        progressDialog.hide();
                        startActivity(intentLog);
                        break;
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.reg_button:
                User userReg = new User(login_user.getText().toString(),login_password.getText().toString(),getApplicationContext());
                try {
                    if(userReg.registration()){
                        Intent intentReg = new Intent(this, MapActivity.class);
                        intentReg.putExtra("user", userReg.getUsername());
                        startActivity(intentReg);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}