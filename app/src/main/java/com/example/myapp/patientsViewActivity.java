package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class patientsViewActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "patientPressedKey";
    private static final String TAG = "patientsViewActivity";
    private Map<String, String> keysNames = new HashMap<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<MyAdapter.MyViewHolder> mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_view);


        //Read database for all patient keys belonging to the user logged in
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mUserKeys = FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/" + "keys");

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keysNames = (Map<String, String>) dataSnapshot.getValue();
                layoutManager = new LinearLayoutManager(patientsViewActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                mAdapter = new MyAdapter(keysNames);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(patientsViewActivity.this, "Failed to load post.",Toast.LENGTH_SHORT).show();

            }
        };
        mUserKeys.addValueEventListener(userListener);


        //Show all patients here in a RecyclerView using the keys in Arraylist
        recyclerView = (RecyclerView) findViewById(R.id.allPatientsRecyclerView);


//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private final Map<String, String> mDataset;

        //Storing keys and values separately for later use;
        private ArrayList<String> values;
        private ArrayList<String> keys;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case

            //TextView showing the patient name
            public TextView textView;
            public View layout;

            public MyViewHolder(View v) {
                super(v);
                layout = v;
                textView = (TextView) v.findViewById(R.id.word);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                //sending the patient key for the next activity using the intent object
                int mPosition = getLayoutPosition();
                String element = keys.get(mPosition);
                Intent intent = new Intent(patientsViewActivity.this, patientProfileActivity.class);
                intent.putExtra(EXTRA_MESSAGE, element);
                startActivity(intent);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Map<String, String> myDataset) {
            mDataset = myDataset;
            values = new ArrayList<String>(mDataset.values());
            keys = new ArrayList<String>(mDataset.keySet());
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_textview_layout, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.setText(values.get(position));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return values.size();
        }
    }
}
