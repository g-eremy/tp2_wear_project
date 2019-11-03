package CommonApp.ServiceUtil.Interface;

import android.app.Service;

public interface IConnectionCallback<T extends Service>
{
    public void onConnectedCallback(T service);
}
