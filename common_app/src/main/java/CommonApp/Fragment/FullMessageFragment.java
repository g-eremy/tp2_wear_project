package CommonApp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common_app.R;

import static CommonApp.Adapter.MessageAdapter.*;

public class FullMessageFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_full_message, container, false);

        Bundle bundle = this.getArguments();

        TextView student_id_view = v.findViewById(R.id.common_student_id);
        TextView gps_lat_view = v.findViewById(R.id.common_gps_lat);
        TextView gps_lng_view = v.findViewById(R.id.common_gps_lng);
        TextView full_message_view = v.findViewById(R.id.common_full_message);

        Integer student_id = bundle.getInt(MESSAGE_STUDENT_ID_NAME);
        Double gps_lat = bundle.getDouble(MESSAGE_GPS_LAT_NAME);
        Double gps_lng = bundle.getDouble(MESSAGE_GPS_LNG_NAME);
        String full_message = bundle.getString(MESSAGE_FULL_NAME);

        student_id_view.setText(student_id.toString());
        gps_lat_view.setText(gps_lat.toString());
        gps_lng_view.setText(gps_lng.toString());
        full_message_view.setText(full_message);

        return v;
    }

}
