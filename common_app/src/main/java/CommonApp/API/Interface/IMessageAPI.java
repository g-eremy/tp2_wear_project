package CommonApp.API.Interface;

import CommonApp.Entity.MessageGetEntity;
import CommonApp.Entity.MessagePostEntity;
import CommonApp.Entity.VoidEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IMessageAPI
{
    @POST("")
    Call<VoidEntity> sendMessage(@Body MessagePostEntity body);

    @GET("")
    Call<MessageGetEntity> getMessages();
}
