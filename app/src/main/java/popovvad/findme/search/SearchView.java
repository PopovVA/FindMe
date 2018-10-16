package popovvad.findme.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import popovvad.findme.R;
import popovvad.findme.supportLibrary.UniversalMechanisms;


public class SearchView extends AppCompatActivity implements SearchContract.View {

    private ProgressDialog progressDialog;

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
}
