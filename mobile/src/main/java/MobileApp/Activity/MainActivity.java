package MobileApp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tp2.R;

import java.util.List;

import CommonApp.Listener.Interface.IOnSyncWear;
import CommonApp.Listener.SyncWearListener;
import CommonApp.Entity.MessageGetEntity;
import CommonApp.Listener.Interface.IOnMessageRefresh;
import CommonApp.Listener.Interface.IOnMessageSended;
import CommonApp.Listener.RefreshMessageListener;
import CommonApp.Listener.SendMessageListener;
import CommonApp.ServiceUtil.Interface.IConnectionCallback;
import CommonApp.ServiceUtil.ServiceConnection;

import MobileApp.API.AbstractQueryHandler;
import MobileApp.API.Callback.MessageGetCallback;
import MobileApp.API.Callback.MessagePostCallback;
import MobileApp.Service.MessageService;
import MobileApp.Service.WearService;

public class MainActivity extends AppCompatActivity implements
        IOnMessageRefresh, IOnMessageSended, IOnSyncWear, IConnectionCallback<MessageService>
{
    private ServiceConnection<MessageService> wear_service_connection = new ServiceConnection<>(this);
    private MessageService message_service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MessageService.binding(this, wear_service_connection);

        SwipeRefreshLayout swipe_refresh = findViewById(R.id.mobile_refresh_swipe);
        RefreshMessageListener message_refresh_listener = new RefreshMessageListener(this);
        swipe_refresh.setOnRefreshListener(message_refresh_listener);
        swipe_refresh.setRefreshing(true);

        Button send_button = findViewById(R.id.mobile_send_button);
        SendMessageListener message_send_listener = new SendMessageListener(this);
        send_button.setOnClickListener(message_send_listener);

        Button sync_button = findViewById(R.id.mobile_sync_wear_button);
        sync_button.setOnClickListener(new SyncWearListener(this));
        updateSyncButtonText(WearService.isRunning());
    }

    public void error()
    {
        String error_message = getResources().getString(R.string.mobile_error);
        Toast.makeText(this, error_message, Toast.LENGTH_LONG).show();
    }

    public void updateSyncButtonText(boolean is_started)
    {
        Resources resources = getResources();

        Button sync_button = findViewById(R.id.mobile_sync_wear_button);

        String sync_button_text = (!is_started)
                ? resources.getString(R.string.mobile_sync_button)
                : resources.getString(R.string.mobile_unsync_button);

        sync_button.setText(sync_button_text);
    }

    @Override
    public void onMessageRefresh()
    {
        if (message_service == null)
        {
            error();
            return;
        }

        ListView messages_view = findViewById(R.id.mobile_message_listview);
        AbstractQueryHandler<List<MessageGetEntity>> callback =  new MessageGetCallback(this, messages_view);
        message_service.getMessages(callback);

        SwipeRefreshLayout swipe_refresh = findViewById(R.id.mobile_refresh_swipe);
        swipe_refresh.setRefreshing(false);
    }

    @Override
    public void onMessageSended(View v)
    {
        if (message_service == null)
        {
            error();
            return;
        }

        SwipeRefreshLayout swipe_refresh = findViewById(R.id.mobile_refresh_swipe);
        swipe_refresh.setRefreshing(true);

        EditText message_view = findViewById(R.id.mobile_message_input);
        String message_str = message_view.getText().toString();
        message_view.getText().clear();

        MessagePostCallback callback = new MessagePostCallback(this, this);

        message_service.sendMessage(message_str, callback);
    }

    @Override
    public void onSyncWear(View v)
    {
        boolean is_started = WearService.isRunning();

        if (is_started)
        {
            WearService.stop(this);
        }
        else
        {
            WearService.start(this);
        }

        updateSyncButtonText(!is_started);
    }

    @Override
    public void onConnectedCallback(MessageService service)
    {
        message_service = service;
        onMessageRefresh();
    }
}
