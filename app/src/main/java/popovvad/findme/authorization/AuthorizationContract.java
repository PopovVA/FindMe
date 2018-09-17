package popovvad.findme.authorization;

import android.content.Context;

public interface AuthorizationContract {

    interface View {
        void showToast(String message);

        void showProgressDialog();

        void hideProgressDialog();

        Context getContextView();

        String getUsername();

        String getPassword();
    }

    interface Presenter {
        void onButtonLoginWasClicked();

        void onButtonRegWasClicked();

    }

}
