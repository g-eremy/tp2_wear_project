package MobileApp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tp2.R;

import java.util.List;

import MobileApp.API.AbstractQueryHandler;
import MobileApp.API.Callback.MessageGetCallback;
import MobileApp.API.Callback.MessagePostCallback;
import CommonApp.Entity.MessageGetEntity;
import CommonApp.Listener.Interface.IOnMessageRefresh;
import CommonApp.Listener.Interface.IOnMessageSended;
import CommonApp.Listener.RefreshMessageListener;
import CommonApp.Listener.SendMessageListener;
import CommonApp.ServiceUtil.Interface.IConnectionCallback;
import CommonApp.ServiceUtil.ServiceConnection;

import MobileApp.Service.MessageService;

public class MainActivity extends AppCompatActivity implements IOnMessageRefresh, IOnMessageSended, IConnectionCallback<MessageService>
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
    }

    public void error()
    {
        String error_message = getResources().getString(R.string.mobile_error);
        Toast.makeText(this, error_message, Toast.LENGTH_LONG).show();
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
    public void onConnectedCallback(MessageService service)
    {
        message_service = service;
        onMessageRefresh();
    }

}
