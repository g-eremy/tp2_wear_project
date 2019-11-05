package MobileApp.API.Callback;


import android.content.Context;

import MobileApp.API.AbstractQueryHandler;
import CommonApp.Listener.Interface.IOnMessageRefresh;
import okhttp3.ResponseBody;

public class MessagePostCallback extends AbstractQueryHandler<ResponseBody>
{
    private IOnMessageRefresh listener;

    public MessagePostCallback(Context context, IOnMessageRefresh listener)
    {
        super(context);

        this.listener = listener;
    }

    @Override
    public void callback(ResponseBody response, Context context)
    {
        listener.onMessageRefresh();
    }
}
