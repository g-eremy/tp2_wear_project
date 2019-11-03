package WearApp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tp2.R;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends WearableActivity implements DataClient.OnDataChangedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button test_button = findViewById(R.id.wear_test_button);
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(MainActivity.this, TestActivity.class);
                startActivity(e);
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        for (DataEvent event : dataEvents)
        {
            if (event.getType() != DataEvent.TYPE_CHANGED)
            {
                continue;
            }

            // DataItem changed
            DataItem item = event.getDataItem();
            if (item.getUri().getPath().compareTo("/mobile") != 0)
            {
                continue;
            }

            DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
            String e = dataMap.getString("mobile_message");
            TextView t = findViewById(R.id.wear_message);
            t.setText(e);
        }
    }

}
