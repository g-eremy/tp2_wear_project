package MobileApp.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.tp2.R;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import CommonApp.ServiceUtil.Interface.IConnectionCallback;
import CommonApp.ServiceUtil.ServiceConnection;
import MobileApp.Activity.MainActivity;
import CommonApp.Task.WearMessageTask;

import static CommonApp.Constant.MessageAPIConstant.*;

public class WearService extends Service implements MessageClient.OnMessageReceivedListener, IConnectionCallback<MessageService> {
    public static final String CHANNEL_ID = "MobileApp.Service.WearService.ForegroundService";
    public static final int NOTIFICATION_ID = 1;

    private ServiceConnection<MessageService> message_service_connection = new ServiceConnection<>(this);
    private MessageService message_service = null;

    private static boolean is_running = false;

    @Override
    public void onCreate()
    {
        super.onCreate();

        is_running = true;
        Wearable.getMessageClient(this).addListener(this);
        MessageService.binding(this, message_service_connection);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startServiceWithNotification();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        is_running = false;
        Wearable.getMessageClient(this).removeListener(this);
        MessageService.unbinding(this, message_service_connection);
    }

    @Override
    public IBinder onBind(Intent intent) // no bind this service
    {
        throw new UnsupportedOperationException("No bind this service");
    }

    @Override
    public void onConnectedCallback(MessageService service)
    {
        message_service = service;
    }

    public void sendMessage(String message)
    {
        WearMessageTask wmr = new WearMessageTask(this, message);
        Thread thread = new Thread(wmr);

        thread.start();
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent e)
    {
        if (!e.getPath().equals(MESSAGE_MOBILE_API_PATH))
        {
            return;
        }

        String message = new String(e.getData());

        switch(message)
        {
            case MESSAGE_GET_REQUEST:
                //message_service.getMessages();
                break;
        }
    }

    private void startServiceWithNotification()
    {
        createNotificationChannel();

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

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
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
