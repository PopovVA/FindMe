package popovvad.findme.contact;

import android.content.Context;
import android.content.Intent;

public interface ContactContract {

    interface View {

        void showToast(String message);

        void showProgressDialog();

        void hideProgressDialog();

        Context getContextView();

        String getMainUser();

        double getLatitude();

        double getLongitude();

        void setContactName(String string);

        void setCity(String string);

        void setCounty(String string);

        void setCoordinates(String string);

        void startSomeActivity(Intent intent);

    }

    interface Presenter {

        void onButtonFindWasClicked();

        void onButtonNewContactWasClicked();

    }

}
