package MobileApp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tp2.R;

import CommonApp.ServiceUtil.Interface.IConnectionCallback;
import CommonApp.ServiceUtil.ServiceConnection;
import MobileApp.Service.WearService;

public class TestActivity extends AppCompatActivity implements IConnectionCallback<WearService>
{
    private ServiceConnection<WearService> wear_service_connection = new ServiceConnection<>(this);
    private WearService wear_service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        WearService.binding(this, wear_service_connection);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        WearService.unbinding(this, wear_service_connection);
    }

    @Override
    public void onConnectedCallback(WearService service)
    {
        wear_service = service;

        TextView t = findViewById(R.id.mobile_on_connected);
        t.setText("Service prêt");

        Button b = findViewById(R.id.mobile_test_message_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                wear_service.sendMessage("Message de test evnoyé depuis l'appli mobile...");
            }
        });
    }

}
