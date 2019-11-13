package CommonApp.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageGetEntity implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("student_id")
    private int student_id;

    @SerializedName("gps_lat")
    private double gps_lat;

    @SerializedName("gps_long")
    private double gps_long;

    @SerializedName("student_message")
    private String student_message;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getStudentId()
    {
        return student_id;
    }

    public void setStudentId(int student_id)
    {
        this.student_id = student_id;
    }

    public double getGpsLat()
    {
        return gps_lat;
    }

    public void setGpsLat(double gps_lat)
    {
        this.gps_lat = gps_lat;
    }

    public double getGpsLong()
    {
        return gps_long;
    }

    public void setGpsLong(double gps_long)
    {
        this.gps_long = gps_long;
    }

    public String getStudentMessage()
    {
        return student_message;
    }

    public void setStudentMessage(String student_message)
    {
        this.student_message = student_message;
    }
}
