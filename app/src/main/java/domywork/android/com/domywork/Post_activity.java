package domywork.android.com.domywork;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.auth.FirebaseAuth.*;

public class Post_activity extends AppCompatActivity {

    private static final String TAG = "Post_Activity";

    private static final String REQUIRED = "Required";



    // [START declare_database_ref]

    private DatabaseReference mDatabase;

    // [END declare_database_ref]



    private EditText mWork,mName,mNo,mPlace,mAmount;

    private EditText mBodyDesc;

    private FloatingActionButton mSubmitButton;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_activity);



        // [START initialize_database_ref]

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // [END initialize_database_ref]



        mWork = findViewById(R.id.fieldWork);
        mName = findViewById(R.id.fieldName);
        mNo = findViewById(R.id.fieldNo);
        mPlace = findViewById(R.id.fieldPlace);
        mAmount = findViewById(R.id.fieldAmount);
        mBodyDesc = findViewById(R.id.fieldBody);

        mSubmitButton = findViewById(R.id.fabSubmitPost);



        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                submitPost();

            }

        });

    }



    private void submitPost() {

        final String Wo = mWork.getText().toString();
        final String Na = mName.getText().toString();
        final String Number= mNo.getText().toString();
        final String Pla= mPlace.getText().toString();
        final String Amo= mAmount.getText().toString();
        final String Des = mBodyDesc.getText().toString();



        if (TextUtils.isEmpty(Wo)) {

            mWork.setError(REQUIRED);

            return;

        }
        if (TextUtils.isEmpty(Na)) {

            mName.setError(REQUIRED);

            return;

        }
        if (TextUtils.isEmpty(Number)) {

            mNo.setError(REQUIRED);

            return;

        }
        if (TextUtils.isEmpty(Pla)) {

            mPlace.setError(REQUIRED);

            return;

        }
        if (TextUtils.isEmpty(Amo)) {

            mAmount.setError(REQUIRED);

            return;

        }

        if (TextUtils.isEmpty(Des)) {

            mBodyDesc.setError(REQUIRED);

            return;

        }



        // Disable button so there are no multi-posts

        setEditingEnabled(false);

        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();



        // [START single_value_read]

        final String userId = getInstance().getCurrentUser().getUid();

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(

                new ValueEventListener() {

                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Get user value

                        User user = dataSnapshot.getValue(User.class);



                        // [START_EXCLUDE]

                        if (user == null) {

                            // User is null, error out

                            Log.e(TAG, "User " + userId + " is unexpectedly null");

                            Toast.makeText(Post_activity.this,

                                    "Error: could not fetch user.",

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            // Write new post

                            writeNewPost(userId, user.username,Wo,Na,Number,Pla,Amo,Des );

                        }



                        // Finish this Activity, back to the stream

                        setEditingEnabled(true);

                        finish();

                        // [END_EXCLUDE]

                    }



                    @Override

                    public void onCancelled(DatabaseError databaseError) {

                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());

                        // [START_EXCLUDE]

                        setEditingEnabled(true);

                        // [END_EXCLUDE]

                    }

                });

        // [END single_value_read]

    }



    private void setEditingEnabled(boolean enabled) {

        mWork.setEnabled(enabled);
        mName.setEnabled(enabled);
        mNo.setEnabled(enabled);
        mPlace.setEnabled(enabled);
        mAmount.setEnabled(enabled);
        mBodyDesc.setEnabled(enabled);

        if (enabled) {

            mSubmitButton.show();

        } else {

            mSubmitButton.hide();

        }

    }



    // [START write_fan_out]

    private void  writeNewPost(String userId, String username, String Wo, String Na,String No,String Pla,String Amo,String BodyDesc) {

        // Create new post at /user-posts/$userid/$postid and at

        // /posts/$postid simultaneously

        String key = mDatabase.child("posts").push().getKey();

        Post post = new Post(userId, username,Wo,Na,No,Pla,Amo,BodyDesc);

        Map<String, Object> postValues = post.toMap();



        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/posts/" + key, postValues);

        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);



        mDatabase.updateChildren(childUpdates);


    // [END write_fan_out]

}}



