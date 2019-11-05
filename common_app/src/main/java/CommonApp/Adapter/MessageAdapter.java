package CommonApp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.common_app.R;

import java.util.List;

import CommonApp.Entity.MessageGetEntity;
import CommonApp.Listener.Interface.IOnMessageView;
import CommonApp.Listener.ViewMessageListener;

public class MessageAdapter extends ArrayAdapter<MessageGetEntity> implements IOnMessageView
{
    public static final int MESSAGE_MAX_LEN = 50;
    public static final String SUBSTITUTE_STR = "...";

    public static final String MESSAGE_BUNDLE_NAME = "bundle_message";
    public static final String MESSAGE_STUDENT_ID_NAME = "student_id";
    public static final String MESSAGE_GPS_LAT_NAME = "gps_lat";
    public static final String MESSAGE_GPS_LNG_NAME = "gps_lng";
    public static final String MESSAGE_FULL_NAME = "full_message";

    private Class<?> activity_link;

    public MessageAdapter(Context context, List<MessageGetEntity> data, Class<?> activity_link)
    {
        super(context, 0, data);

        this.activity_link = activity_link;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        if (v == null)
        {
            v = LayoutInflater.from(getContext()).inflate(R.layout.message_adapter, parent, false);
        }

        Context context = getContext();
        MessageGetEntity e = getItem(position);

        String message = e.getStudentMessage();

        int message_len = message.length();
        int substitute_len = SUBSTITUTE_STR.length();

        if ((message_len + substitute_len) > MESSAGE_MAX_LEN)
        {
            message = message.substring(0, MESSAGE_MAX_LEN);
            message += SUBSTITUTE_STR;
        }

        TextView t = v.findViewById(R.id.common_message);
        t.setText(message);



        v.findViewById(R.id.common_message_adapter_element).setOnClickListener(new ViewMessageListener(this, e));

        return v;
    }

    @Override
    public void onMessageView(View v, MessageGetEntity message)
    {
        Context context = getContext();

        Bundle args = new Bundle();
        args.putInt(MESSAGE_STUDENT_ID_NAME, message.getStudentId());
        args.putDouble(MESSAGE_GPS_LAT_NAME, message.getGpsLat());
        args.putDouble(MESSAGE_GPS_LNG_NAME, message.getGpsLong());
        args.putString(MESSAGE_FULL_NAME, message.getStudentMessage());

        Intent e = new Intent(context, activity_link);
        e.putExtra(MESSAGE_BUNDLE_NAME, args);
        context.startActivity(e);
    }
}
