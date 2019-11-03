package MobileApp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import com.example.tp2.R;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
{
    private DataClient dataClient;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView t = findViewById(R.id.mobile_message);
        final Button b = findViewById(R.id.mobile_send);

        dataClient = Wearable.getDataClient(this);

        Button test_button = findViewById(R.id.mobile_test_button);
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(MainActivity.this, TestActivity.class);
                startActivity(e);
            }
        });

        /*
        GoogleApiClient googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();
         */

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                msg = t.getText().toString();

                PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/mobile");
                putDataMapReq.getDataMap().putString("mobile_message", msg);
                PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                Task<DataItem> putDataTask = dataClient.putDataItem(putDataReq);
            }
        });
    }

    @Override
    public void onConnectionSuspended(int cause) { }

    @Override
    public void onConnected(Bundle connectionHint)
    {
    }
}
