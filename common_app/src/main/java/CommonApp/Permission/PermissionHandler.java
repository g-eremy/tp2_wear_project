package CommonApp.Permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionHandler
{
    private Activity activity;
    private int permission_request;

    public PermissionHandler(Activity activity, int permission_request)
    {
        this.activity = activity;
        this.permission_request = permission_request;
    }

    public void askPermissions() throws PackageManager.NameNotFoundException
    {
        String[] permissions = getPermissions();
        List<String> checking = new ArrayList<>();

        for (String p : permissions)
        {
            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            {
                continue;
            }

            checking.add(p);
        }

        String[] array = new String[checking.size()];
        ActivityCompat.requestPermissions(activity, checking.toArray(array), permission_request);
    }

    public boolean checkAllPermissions() throws PackageManager.NameNotFoundException
    {
        String[] permissions = getPermissions();

        for (String p : permissions)
        {
            if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            {
                return false;
            }
        }

        return true;
    }

    private boolean checkPermission(String permission)
    {
        return (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED);
    }

    private String[] getPermissions() throws PackageManager.NameNotFoundException
    {
        PackageManager package_manager = activity.getPackageManager();
        String package_name = activity.getPackageName();
        PackageInfo package_info = package_manager.getPackageInfo(package_name, PackageManager.GET_PERMISSIONS);

        return package_info.requestedPermissions;
    }
}
