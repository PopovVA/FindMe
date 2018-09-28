package popovvad.findme.authorization;

import android.content.Context;
import android.content.Intent;

public interface AuthorizationContract {

    interface View {
        void showToast(String message);

        void showProgressDialog();

        void hideProgressDialog();

        Context getContextView();

        String getUsername();

        String getPassword();

        void startSomeActivity(Intent intent);
    }

    interface Presenter {
        void onButtonLoginWasClicked();

        void onButtonRegWasClicked();

    }

}
