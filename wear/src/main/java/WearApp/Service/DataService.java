package WearApp.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import static CommonApp.Constant.MessageAPIConstant.*;
import CommonApp.ServiceUtil.ServiceBinder;

public class DataService extends Service implements MessageClient.OnMessageReceivedListener
{
    public static final String BROADCAST_NAME = "WearApp.Service.DataService";
    public static final String EXTRA_MESSAGE_NAME = BROADCAST_NAME + "." + "message";

    private IBinder binder = new ServiceBinder<DataService>(this);

    private Intent broadcast = new Intent(BROADCAST_NAME);

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    @Override
    public void onCreate()
    {
        Wearable.getMessageClient(this).addListener(this);
    }

    @Override
    public void onDestroy()
    {
        Wearable.getMessageClient(this).removeListener(this);
    }

    @Override
    public void onMessageReceived(MessageEvent e)
    {
        if (!e.getPath().equals(MESSAGE_WEAR_API_PATH))
        {
            return;
        }

        String message = new String(e.getData());
        broadcast.putExtra(EXTRA_MESSAGE_NAME, message);
        sendBroadcast(broadcast);
    }

    public static void bindReceiver(Context context, BroadcastReceiver receiver)
    {
        context.registerReceiver(receiver, new IntentFilter(BROADCAST_NAME));
    }

    public static void unbindReceiver(Context context, BroadcastReceiver receiver)
    {
        context.unregisterReceiver(receiver);
    }

    public static void binding(Context context, ServiceConnection sc)
    {
        Intent e = new Intent(context, DataService.class);
        context.bindService(e, sc, Context.BIND_AUTO_CREATE);
    }

    public static void unbinding(Context context, ServiceConnection sc)
    {
        context.unbindService(sc);
    }
}
