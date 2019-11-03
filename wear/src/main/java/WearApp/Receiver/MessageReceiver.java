package WearApp.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import WearApp.Receiver.Interface.IReceiver;

import static WearApp.Service.DataService.*;

public class MessageReceiver extends BroadcastReceiver
{
    IReceiver callback;

    public MessageReceiver(IReceiver callback)
    {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String message = intent.getStringExtra(EXTRA_MESSAGE_NAME);

        this.callback.receive(context, message);
    }
}
