package CommonApp.Listener;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import CommonApp.Listener.Interface.IOnMessageRefresh;

public class RefreshMessageListener implements SwipeRefreshLayout.OnRefreshListener
{
    private IOnMessageRefresh listener;

    public RefreshMessageListener(IOnMessageRefresh listener)
    {
        this.listener = listener;
    }

    @Override
    public void onRefresh()
    {
        listener.onMessageRefresh();
    }
}
