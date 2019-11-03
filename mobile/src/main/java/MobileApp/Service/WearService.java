package MobileApp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import CommonApp.ServiceUtil.ServiceBinder;
import MobileApp.Task.WearMessageTask;

public class WearService extends Service
{
    private IBinder binder = new ServiceBinder<WearService>(this);

    @Override
    public void onCreate()
    {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    public void sendMessage(String message)
    {
        WearMessageTask wmr = new WearMessageTask(this, message);
        Thread thread = new Thread(wmr);

        thread.start();
    }

    public static void binding(Context context, ServiceConnection sc)
    {
        Intent e = new Intent(context, WearService.class);
        context.bindService(e, sc, Context.BIND_AUTO_CREATE);
    }

    public static void unbinding(Context context, ServiceConnection sc)
    {
        context.unbindService(sc);
    }
}
