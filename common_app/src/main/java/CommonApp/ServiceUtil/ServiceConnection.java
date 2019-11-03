package CommonApp.ServiceUtil;

import android.app.Service;
import android.content.ComponentName;
import android.os.IBinder;

import CommonApp.ServiceUtil.Interface.IConnectionCallback;

public class ServiceConnection<T extends Service> implements android.content.ServiceConnection
{
    private IConnectionCallback<T> callback;

    public ServiceConnection()
    {
        this.callback = null;
    }

    public ServiceConnection(IConnectionCallback<T> callback)
    {
        this.callback = callback;
    }

    public void onServiceConnected(ComponentName className, IBinder rawBinder)
    {
        ServiceBinder<T> binder = (ServiceBinder<T>) rawBinder;

        if (callback != null)
        {
            callback.onConnectedCallback(binder.getService());
        }
    }

    public void onServiceDisconnected(ComponentName className)
    {
    }
}
