package popovvad.findme.authorization;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import popovvad.findme.R;
import popovvad.findme.supportLibrary.UniversalMechanisms;

public class AuthorizationView extends AppCompatActivity implements AuthorizationContract.View, View.OnClickListener {

    private AuthorizationContract.Presenter mPresenter;

    Button login_button;
    Button reg_button;

    EditText login_user;
    EditText login_password;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        init();
    }

    public void init() {
        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс MainContract.View
        mPresenter = new AuthorizationPresenter(this);

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
                mPresenter.onButtonLoginWasClicked();
                break;
            case R.id.reg_button:
                mPresenter.onButtonRegWasClicked();
                break;
            default:
                break;
        }
    }

    @Override
    public void showToast(String message) {
        UniversalMechanisms.showMessage(getContextView(), message);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "Загрузка", getString(R.string.please_wait));
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public Context getContextView() {
        return this.getApplicationContext();
    }

    @Override
    public String getUsername() {
        return login_user.getText().toString();
    }

    @Override
    public String getPassword() {
        return login_password.getText().toString();
    }
}
