package MobileApp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;

import com.example.tp2.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import CommonApp.Listener.Interface.IOnLocated;
import CommonApp.Listener.LocationListener;
import CommonApp.ServiceUtil.ServiceBinder;
import CommonApp.ServiceUtil.ServiceConnection;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class GPSService extends Service implements IOnLocated
{
    public static final int GPS_INTERVAL = 2000;

    private IBinder binder = new ServiceBinder<GPSService>(this);
    private FusedLocationProviderClient location_client;

    private Location last_location = null;
    private LocationRequest location_request;
    private LocationListener location_listener;

    @Override
    public void onCreate()
    {
        location_client = LocationServices.getFusedLocationProviderClient(this);

        location_request = LocationRequest.create()
                .setInterval(GPS_INTERVAL)
                .setFastestInterval(GPS_INTERVAL)
                .setPriority(PRIORITY_HIGH_ACCURACY);

        location_listener = new LocationListener(this);

        location_client.requestLocationUpdates(location_request, location_listener, Looper.getMainLooper());
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    public Location getLastLocation() throws Exception
    {
        if (last_location == null)
        {
            String message = getResources().getString(R.string.mobile_gps_error);
            throw new Exception(message);
        }

        return last_location;
    }

    @Override
    public void onLocated(Location result)
    {
        this.last_location = result;
    }

    public static void binding(Context context, ServiceConnection sc)
    {
        Intent e = new Intent(context, GPSService.class);
        context.bindService(e, sc, Context.BIND_AUTO_CREATE);
    }

    public static void unbinding(Context context, ServiceConnection sc)
    {
        context.unbindService(sc);
    }

}
