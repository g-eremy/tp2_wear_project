package MobileApp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import retrofit2.Call;

import CommonApp.API.AbstractQueryHandler;
import CommonApp.API.Interface.IMessageAPI;
import CommonApp.API.RequestHandler;
import CommonApp.Entity.MessageGetEntity;
import CommonApp.Entity.MessagePostEntity;
import CommonApp.Entity.VoidEntity;
import CommonApp.ServiceUtil.ServiceBinder;

public class MessageService extends Service
{
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

    public void getMessages(AbstractQueryHandler<MessageGetEntity> callback)
    {
        Call<MessageGetEntity> call = message_api.getMessages();
        request_manager.request(call, callback);
    }

    public void sendMessage(MessagePostEntity message, AbstractQueryHandler<VoidEntity> callback)
    {
        Call<VoidEntity> call = message_api.sendMessage(message);
        request_manager.request(call, callback);
    }

    public void sendMessage(MessagePostEntity message)
    {
        sendMessage(message, null);
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
