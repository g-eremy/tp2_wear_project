package CommonApp.Listener;

import android.view.View;

import CommonApp.Entity.MessageGetEntity;
import CommonApp.Listener.Interface.IOnMessageView;

public class ViewMessageListener implements View.OnClickListener
{
    private IOnMessageView listener;
    private MessageGetEntity message;

    public ViewMessageListener(IOnMessageView listener, MessageGetEntity message)
    {
        this.listener = listener;
        this.message = message;
    }

    @Override
    public void onClick(View v)
    {
        listener.onMessageView(v, message);
    }
}
