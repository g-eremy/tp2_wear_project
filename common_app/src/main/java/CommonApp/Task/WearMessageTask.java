package CommonApp.Task;

import android.content.Context;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeClient;
import com.google.android.gms.wearable.Wearable;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static CommonApp.Constant.MessageAPIConstant.MESSAGE_WEAR_API_PATH;

public class WearMessageTask implements Runnable
{
    private Context context;
    private String message;

    public WearMessageTask(Context context, String message)
    {
        this.context = context;
        this.message = message;
    }

    private Collection<String> getNodes()
    {
        HashSet<String> results = new HashSet<>();

        try
        {
            NodeClient node_client = Wearable.getNodeClient(context);
            List<Node> nodes = Tasks.await(node_client.getConnectedNodes());

            for (Node node : nodes)
            {
                results.add(node.getId());
            }
        }
        catch(Exception e)
        {
            results.clear();
        }

        return results;
    }

    @Override
    public void run()
    {
        Collection<String> nodes = getNodes();

        for (String n : nodes)
        {
            Wearable.getMessageClient(context).sendMessage(n, MESSAGE_WEAR_API_PATH, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
