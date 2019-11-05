package MobileApp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.tp2.R;

import CommonApp.Fragment.FullMessageFragment;

import static CommonApp.Adapter.MessageAdapter.*;

public class FullMessageActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_message);
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
