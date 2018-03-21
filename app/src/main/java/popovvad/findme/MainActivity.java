package popovvad.findme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_button;
    Button reg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        reg_button = (Button) findViewById(R.id.reg_button);
        reg_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                User user = new User("Popov","");
                user.login();

                Intent intentLog = new Intent(this, MapActivity.class);
                startActivity(intentLog);
                break;
            case R.id.reg_button:
                Intent intentReg = new Intent(this, MapActivity.class);
                startActivity(intentReg);
                break;
            default:
                break;
        }
    }
}
