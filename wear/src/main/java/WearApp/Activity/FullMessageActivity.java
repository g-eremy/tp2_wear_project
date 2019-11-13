package WearApp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import android.app.Fragment;

import com.example.tp2.R;

import CommonApp.Fragment.FullMessageFragment;

import static CommonApp.Adapter.MessageAdapter.MESSAGE_BUNDLE_NAME;

public class FullMessageActivity extends WearableActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_message);


        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if (!(fragment instanceof FullMessageFragment))
        {
            return;
        }

        Intent e = getIntent();
        Bundle bundle = e.getBundleExtra(MESSAGE_BUNDLE_NAME);

        fragment.setArguments(bundle);
    }
}
