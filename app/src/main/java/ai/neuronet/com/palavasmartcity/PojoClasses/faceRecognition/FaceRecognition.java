package ai.neuronet.com.palavasmartcity.PojoClasses.faceRecognition;

public class FaceRecognition
{
    private User[] user;

    public User[] getUser ()
    {
        return user;
    }

    public void setUser (User[] user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user = "+user+"]";
    }
}