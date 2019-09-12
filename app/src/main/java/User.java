import com.google.firebase.database.IgnoreExtraProperties;



// [START blog_user_class]

@IgnoreExtraProperties

public class User {



    public String HSname;

    public String Work;



    public User() {

        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }



    public User(String Hsname, String Work) {

        this.HSname = HSname;

        this.Work = Work;

    }



}