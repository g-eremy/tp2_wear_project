package CommonApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.common_app.R;

import java.util.List;

import CommonApp.Entity.MessageGetEntity;

public class MessageAdapter extends ArrayAdapter<MessageGetEntity>
{
    public static int MESSAGE_MAX_LEN = 50;
    public static String SUBSTITUTE_STR = "...";

    public MessageAdapter(Context context, List<MessageGetEntity> data)
    {
        super(context, 0, data);
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

        return v;
    }
}
