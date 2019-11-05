package MobileApp.API.Callback;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import MobileApp.API.AbstractQueryHandler;
import CommonApp.Adapter.MessageAdapter;
import CommonApp.Entity.MessageGetEntity;
import MobileApp.Activity.FullMessageActivity;

public class MessageGetCallback extends AbstractQueryHandler<List<MessageGetEntity>>
{
    private ListView view;

    public MessageGetCallback(Context context, ListView view)
    {
        super(context);

        this.view = view;
    }

    @Override
    public void callback(List<MessageGetEntity> response, Context context)
    {
        ListAdapter messages_adapter = new MessageAdapter(context, response, FullMessageActivity.class);
        view.setAdapter(messages_adapter);
    }
}
