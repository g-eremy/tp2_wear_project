package WearApp.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tp2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static CommonApp.Constant.MessageAPIConstant.*;

import CommonApp.Adapter.MessageAdapter;
import CommonApp.Entity.MessageGetEntity;
import CommonApp.Listener.Interface.IOnMessageRefresh;
import CommonApp.Listener.Interface.IOnMessageSending;
import CommonApp.Listener.RefreshMessageListener;
import CommonApp.Listener.SendingMessageListener;
import CommonApp.ServiceUtil.Interface.IConnectionCallback;
import CommonApp.ServiceUtil.ServiceConnection;
import WearApp.Receiver.Interface.IReceiver;
import WearApp.Receiver.MessageReceiver;
import WearApp.Service.DataService;

import static CommonApp.Constant.MessageAPIConstant.GPS_ERROR;

public class MainActivity extends WearableActivity implements
        IConnectionCallback<DataService>, IReceiver, IOnMessageRefresh, IOnMessageSending
{
    private ServiceConnection<DataService> data_service_connection = new ServiceConnection<>(this);
    private DataService data_service = null;
    private MessageReceiver receiver = new MessageReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataService.binding(this, data_service_connection);

        SendingMessageListener sending_listener = new SendingMessageListener(this);
        Button send_message_button = findViewById(R.id.wear_button_default_message_send);
        send_message_button.setOnClickListener(sending_listener);

        RefreshMessageListener message_refresh_listener = new RefreshMessageListener(this);
        SwipeRefreshLayout swipe_refresh = findViewById(R.id.wear_refresh_layout);
        swipe_refresh.setOnRefreshListener(message_refresh_listener);
        swipe_refresh.setRefreshing(true);

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        DataService.bindReceiver(this, receiver);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        DataService.unbindReceiver(this, receiver);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        DataService.unbinding(this, data_service_connection);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);

        Button button = findViewById(R.id.wear_button_default_message_send);
        button.setVisibility(View.GONE);
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();

        Button button = findViewById(R.id.wear_button_default_message_send);
        button.setVisibility(View.VISIBLE);

        SwipeRefreshLayout swipe_refresh = findViewById(R.id.wear_refresh_layout);
        swipe_refresh.setRefreshing(true);
        onMessageRefresh();
    }

    @Override
    public void receive(Context context, String message)
    {
        SwipeRefreshLayout swipe_refresh = findViewById(R.id.wear_refresh_layout);

        switch(message)
        {
            case GPS_ERROR:
                String error_message = getResources().getString(R.string.wear_gps_error);
                Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();

                break;

            default:
                Gson gson = new GsonBuilder()
                        .create();

                Type list_type = new TypeToken<ArrayList<MessageGetEntity>>(){}.getType();
                List<MessageGetEntity> o = gson.fromJson(message, list_type);

                MessageAdapter message_adapter = MessageAdapter.create(this, o, FullMessageActivity.class);
                ListView list_view = findViewById(R.id.wear_message_listview);
                list_view.setAdapter(message_adapter);

                break;
        }

        swipe_refresh.setRefreshing(false);
    }

    @Override
    public void onMessageRefresh()
    {
        if (data_service == null)
        {
            error();
            return;
        }

        data_service.sendMessage(MESSAGE_GET_REQUEST);
    }

    @Override
    public void onMessageSending(View v)
    {
        data_service.sendMessage(MESSAGE_DEFAULT_POST_REQUEST);

        SwipeRefreshLayout swipe_refresh = findViewById(R.id.wear_refresh_layout);
        swipe_refresh.setRefreshing(true);
    }

    @Override
    public void onConnectedCallback(DataService service)
    {
        data_service = service;
        onMessageRefresh();
    }

    private void error()
    {
        String error_message = getResources().getString(R.string.wear_error);
        Toast.makeText(this, error_message, Toast.LENGTH_LONG).show();
    }
}
