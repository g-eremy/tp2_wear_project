package MobileApp.API.Callback;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import MobileApp.API.AbstractQueryHandler;
import CommonApp.Adapter.MessageAdapter;
import CommonApp.Entity.MessageGetEntity;
import MobileApp.Activity.FullMessageActivity;

public class MessageGetCallback extends AbstractQueryHandler<List<MessageGetEntity>>
{
    private ListView list_view;
    private SwipeRefreshLayout refresh_view;

    public MessageGetCallback(Context context, ListView list_view, SwipeRefreshLayout refresh_view)
    {
        super(context);

        this.list_view = list_view;
        this.refresh_view = refresh_view;
    }

    @Override
    public void callback(List<MessageGetEntity> response, Context context)
    {
        ListAdapter messages_adapter = MessageAdapter.create(context, response, FullMessageActivity.class);
        list_view.setAdapter(messages_adapter);
        refresh_view.setRefreshing(false);
    }
}
