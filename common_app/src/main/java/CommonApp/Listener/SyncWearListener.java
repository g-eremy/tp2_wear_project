package CommonApp.Listener;

import android.view.View;

import CommonApp.Listener.Interface.IOnSyncWear;

public class SyncWearListener implements View.OnClickListener
{
    private IOnSyncWear listener;

    public SyncWearListener(IOnSyncWear listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v)
    {
        listener.onSyncWear(v);
    }
}
