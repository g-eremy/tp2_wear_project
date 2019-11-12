package MobileApp.API.Callback;

import android.content.Context;

import java.util.List;

import CommonApp.Entity.MessageGetEntity;
import MobileApp.API.AbstractQueryHandler;

public class MessageWearGetCallback extends AbstractQueryHandler<List<MessageGetEntity>>
{
    public MessageWearGetCallback(Context context)
    {
        super(context);
    }

    @Override
    public void callback(List<MessageGetEntity> response, Context context)
    {

    }
}
