package ai.neuronet.com.palavasmartcity.PojoClasses.faceRecognition;

public class User
{
    private String email;

    private String score;

    private String avg_score;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getScore ()
    {
        return score;
    }

    public void setScore (String score)
    {
        this.score = score;
    }

    public String getAvg_score ()
    {
        return avg_score;
    }

    public void setAvg_score (String avg_score)
    {
        this.avg_score = avg_score;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+", score = "+score+", avg_score = "+avg_score+"]";
    }
}