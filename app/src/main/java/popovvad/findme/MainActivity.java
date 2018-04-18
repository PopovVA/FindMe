package popovvad.findme;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        reg_button = (Button) findViewById(R.id.reg_button);
        reg_button.setOnClickListener(this);

        login_user = (EditText) findViewById(R.id.login_user);
        login_password = (EditText) findViewById(R.id.login_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                User user = new User(login_user.getText().toString(),login_password.getText().toString(),getApplicationContext());
                try {
                    if(user.login()){
                        Intent intentLog = new Intent(this, MapActivity.class);
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
