package popovvad.findme;

import android.content.Context;
import android.widget.Toast;

public abstract class Message {

    public static void showMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

}
