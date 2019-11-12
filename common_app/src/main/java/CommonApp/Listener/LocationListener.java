package CommonApp.Listener;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import CommonApp.Listener.Interface.IOnLocated;

public class LocationListener extends LocationCallback
{
    private IOnLocated listener;

    public LocationListener(IOnLocated listener)
    {
        this.listener = listener;
    }

    @Override
    public void onLocationResult(LocationResult result)
    {
        listener.onLocated(result.getLastLocation());
    }
}
