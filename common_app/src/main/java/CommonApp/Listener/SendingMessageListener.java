package CommonApp.Listener;

import android.view.View;

import CommonApp.Listener.Interface.IOnMessageSending;

public class SendingMessageListener implements View.OnClickListener
{
    private IOnMessageSending listener;

    public SendingMessageListener(IOnMessageSending listener)
    {
        this.listener = listener;
    }


    @Override
    public void onClick(View v)
    {
        this.listener.onMessageSending(v);
    }
}
