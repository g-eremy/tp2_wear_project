package CommonApp.Listener;

import android.view.View;

import CommonApp.Listener.Interface.IOnMessageSended;

public class SendMessageListener implements View.OnClickListener
{
    private IOnMessageSended listener;

    public SendMessageListener(IOnMessageSended listener)
    {
        this.listener = listener;
    }


    @Override
    public void onClick(View v)
    {
        this.listener.onMessageSended(v);
    }
}
