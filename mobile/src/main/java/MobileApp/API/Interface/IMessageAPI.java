package MobileApp.API.Interface;

import java.util.List;

import CommonApp.Entity.MessageGetEntity;
import CommonApp.Entity.MessagePostEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IMessageAPI
{
    @POST(".")
    Call<ResponseBody> sendMessage(@Body MessagePostEntity body);

    @GET(".")
    Call<List<MessageGetEntity>> getMessages();
}
