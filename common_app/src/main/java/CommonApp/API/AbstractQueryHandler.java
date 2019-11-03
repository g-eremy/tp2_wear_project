package CommonApp.API;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.common_app.R;

public abstract class AbstractQueryHandler<T> implements Callback<T>
{
    private Context context;

    public AbstractQueryHandler(Context context)
    {
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response)
    {
        callback(response.body(), context);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t)
    {
        String message = context.getResources().getString(R.string.query_error);
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }

    public abstract void callback(T response, Context context);
}
