package popovvad.findme;

import android.content.Context;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Вадим on 21.03.2018.
 */

public class ServerInteraction {

    private String url;
    private String json;
    private Context context;

    ServerInteraction(String url, String json, Context context){
        this.context = context;
        setUrl(this.url);
        setJson(this.json);
    }

    private void setJson(String json) {
        this.json = json;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public String getJson(){
        return json;
    }

    public String PostQuery() throws IOException {

        OkHttpClient client = new OkHttpClient();
        String json = getJson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(getUrl())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return "123";

    }


}
