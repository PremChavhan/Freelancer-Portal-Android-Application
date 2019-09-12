package domywork.android.com.domywork;



import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;




// [START post_class]

@IgnoreExtraProperties

public class Post {


    public String uid;

    public String author;

    public String Wo;

    public String Na;

     public String No;

     public String Pla;

     public String Amo;

     public String BodyDesc;

    public int starCount = 0;

   public int stars;

    public Post() {

        // Default constructor required for calls to DataSnapshot.getValue(Post.class)

    }



    public Post(String userId, String username, String wo, String na, String no, String pla, String amo, String bodyDesc) {



        this.uid = uid;

        this.author = author;
        this.Wo= Wo;
        this.Na= Na;
        this.No=No;
        this.Pla=Pla;
        this.Amo=Amo;
        this.BodyDesc=BodyDesc;
    // [START post_to_map]

        }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("uid", uid);

        result.put("author", author);

        result.put("Work", Wo);
        result.put("Help Seeker Name", Na);
        result.put("Phone No", No);
        result.put("Place of Work", Pla);
        result.put("Amount", Amo);
        result.put("Description", BodyDesc);
        result.put("starCount", starCount);
        result.put("stars", stars);


        return result;
    }


    // [END post_to_map]



}


// [END post_class]