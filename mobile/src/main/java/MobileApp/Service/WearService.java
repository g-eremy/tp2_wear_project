package MobileApp.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.tp2.R;

import MobileApp.Activity.MainActivity;
import MobileApp.Task.WearMessageTask;

public class WearService extends Service
{
    public static final String CHANNEL_ID = "MobileApp.Service.WearService.ForegroundService";
    public static final int NOTIFICATION_ID = 1;

    private static boolean is_running = false;

    @Override
    public void onCreate()
    {
        super.onCreate();

        is_running = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startServiceWithNotification();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) // no bind this service
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        is_running = false;
    }

    public void sendMessage(String message)
    {
        WearMessageTask wmr = new WearMessageTask(this, message);
        Thread thread = new Thread(wmr);

        thread.start();
    }

    private void startServiceWithNotification()
    {
        Intent notification_intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent content_pending_intent = PendingIntent.getActivity(this, 0, notification_intent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.mobile_foreground_text))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(content_pending_intent)
                .build();

        startForeground(NOTIFICATION_ID, notification);
    }

    /* BEGIN STATIC METHODS */

    public static boolean isRunning()
    {
        return is_running;
    }

    private static Intent createIntent(Context context)
    {
        Intent e = new Intent(context, WearService.class);

        return e;
    }

    public static void start(Context context)
    {
        Intent e = createIntent(context);
        context.startService(e);
    }

    public static void stop(Context context)
    {
        Intent e = createIntent(context);
        context.stopService(e);
    }

    /* END STATIC METHODS */
}
