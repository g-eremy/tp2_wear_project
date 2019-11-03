package WearApp.Activity;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.example.tp2.R;

import CommonApp.ServiceUtil.Interface.IConnectionCallback;
import CommonApp.ServiceUtil.ServiceConnection;
import WearApp.Receiver.Interface.IReceiver;
import WearApp.Receiver.MessageReceiver;
import WearApp.Service.DataService;

public class TestActivity extends WearableActivity implements IConnectionCallback<DataService>, IReceiver
{
    private ServiceConnection<DataService> data_service_connection = new ServiceConnection<>(this);
    private DataService data_service = null;
    private MessageReceiver receiver = new MessageReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DataService.binding(this, data_service_connection);

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void receive(Context context, String message)
    {
        TextView t = findViewById(R.id.wear_test_message);
        t.setText(message);
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
    public void onConnectedCallback(DataService service)
    {
        TextView t = findViewById(R.id.wear_service_text);
        t.setText("Service prêt");
        
        data_service = service;
    }
}
