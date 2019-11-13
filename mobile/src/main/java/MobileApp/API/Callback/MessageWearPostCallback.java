package MobileApp.API.Callback;

import android.content.Context;

import MobileApp.API.AbstractQueryHandler;
import MobileApp.Service.WearService;
import okhttp3.ResponseBody;

public class MessageWearPostCallback extends AbstractQueryHandler<ResponseBody>
{
    private WearService service;

    public MessageWearPostCallback(Context context, WearService service)
    {
        super(context);

        this.service = service;
    }

    @Override
    public void callback(ResponseBody response, Context context)
    {
        service.sendMessagesFromServer();
    }
}
