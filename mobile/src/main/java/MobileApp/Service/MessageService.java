package MobileApp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import CommonApp.API.AbstractQueryHandler;
import CommonApp.API.Interface.IMessageAPI;
import CommonApp.API.RequestHandler;
import CommonApp.Entity.MessageGetEntity;
import CommonApp.Entity.MessagePostEntity;
import CommonApp.ServiceUtil.ServiceBinder;

public class MessageService extends Service
{
    public static int STUDENT_ID = 20140477;

    private IBinder binder = new ServiceBinder<MessageService>(this);
    private RequestHandler<IMessageAPI> request_manager;
    private IMessageAPI message_api;

    @Override
    public void onCreate()
    {
        request_manager = new RequestHandler<>(IMessageAPI.class, this);
        message_api = request_manager.getService();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    public void getMessages(AbstractQueryHandler<List<MessageGetEntity>> callback)
    {
        Call<List<MessageGetEntity>> call = message_api.getMessages();
        request_manager.request(call, callback);
    }

    public void sendMessage(String message_str, AbstractQueryHandler<ResponseBody> callback)
    {
        MessagePostEntity message_entity = new MessagePostEntity(
                STUDENT_ID,
                34.001,
                3.235,
                message_str
        );

        Call<ResponseBody> call = message_api.sendMessage(message_entity);
        request_manager.request(call, callback);
    }

    public void sendMessage(String message_str)
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
