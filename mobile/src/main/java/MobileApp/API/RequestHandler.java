package MobileApp.API;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHandler<T>
{
    public static final String base_url = "https://hmin309-embedded-systems.herokuapp.com/message-exchange/messages/";

    private static Retrofit retrofit_singleton = null;

    private T service;
    private Context context;

    public RequestHandler(Class<T> service, Context context)
    {
        if(retrofit_singleton == null)
        {
            OkHttpClient.Builder httpClient = new OkHttpClient
                    .Builder();

            Gson gson = new GsonBuilder()
                    .create();

            retrofit_singleton = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }

        this.service = retrofit_singleton.create(service);
        this.context = context;
    }

    public T getService()
    {
        return service;
    }

    public <U> void request (final Call<U> call, final AbstractQueryHandler<U> callback)
    {
        if (callback != null)
        {
            call.enqueue(callback);
        }
        else
        {
            call.enqueue(new Callback<U>() {
                @Override
                public void onResponse(Call<U> call, Response<U> response)
                {

                }

                @Override
                public void onFailure(Call<U> call, Throwable t)
                {

                }
            });
        }

    }
}
