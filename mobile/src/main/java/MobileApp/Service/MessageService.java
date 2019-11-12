package MobileApp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.example.tp2.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import MobileApp.API.AbstractQueryHandler;
import MobileApp.API.Interface.IMessageAPI;
import MobileApp.API.RequestHandler;

import CommonApp.Entity.MessageGetEntity;
import CommonApp.Entity.MessagePostEntity;
import CommonApp.ServiceUtil.Interface.IConnectionCallback;
import CommonApp.ServiceUtil.ServiceBinder;
import CommonApp.ServiceUtil.ServiceConnection;

public class MessageService extends Service implements IConnectionCallback<GPSService>
{
    public static int STUDENT_ID = 20140477;

    private IBinder binder = new ServiceBinder<MessageService>(this);
    private RequestHandler<IMessageAPI> request_manager;
    private IMessageAPI message_api;

    private ServiceConnection<GPSService> gps_service_connection = new ServiceConnection<>(this);
    private GPSService gps_service = null;

    @Override
    public void onCreate()
    {
        request_manager = new RequestHandler<>(IMessageAPI.class, this);
        message_api = request_manager.getService();

        GPSService.binding(this, gps_service_connection);
    }

    @Override
    public void onDestroy()
    {
        GPSService.unbinding(this, gps_service_connection);
        gps_service = null;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    @Override
    public void onConnectedCallback(GPSService service)
    {
        this.gps_service = service;
    }

    public void getMessages(AbstractQueryHandler<List<MessageGetEntity>> callback)
    {
        Call<List<MessageGetEntity>> call = message_api.getMessages();
        request_manager.request(call, callback);
    }

    public void sendMessage(String message_str, AbstractQueryHandler<ResponseBody> callback) throws Exception
    {
        if (gps_service == null)
        {
            String error_message = getResources().getString(R.string.mobile_not_gps_service);
            throw new Exception(error_message);
        }

        Location location = gps_service.getLastLocation();

        MessagePostEntity message_entity = new MessagePostEntity(
                STUDENT_ID,
                location.getLatitude(),
                location.getLongitude(),
                message_str
        );

        Call<ResponseBody> call = message_api.sendMessage(message_entity);
        request_manager.request(call, callback);
    }

    public void sendMessage(String message_str) throws Exception
    {
        sendMessage(message_str, null);
    }

    public static void binding(Context context, ServiceConnection sc)
    {
        Intent e = new Intent(context, MessageService.class);
        context.bindService(e, sc, Context.BIND_AUTO_CREATE);
    }

    public static void unbinding(Context context, ServiceConnection sc)
    {
        context.unbindService(sc);
    }

}
