package MobileApp.API.Callback;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import CommonApp.Entity.MessageGetEntity;
import MobileApp.API.AbstractQueryHandler;
import MobileApp.Service.WearService;

public class MessageWearGetCallback extends AbstractQueryHandler<List<MessageGetEntity>>
{
    private WearService service;

    public MessageWearGetCallback(WearService service)
    {
        super(service);

        this.service = service;
    }

    @Override
    public void callback(List<MessageGetEntity> response, Context context)
    {
        Gson gson = new GsonBuilder()
                .create();

        String message = gson.toJson(response);

        service.sendMessage(message);
    }
}
