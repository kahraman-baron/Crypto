package com.semihbaser.crypto.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.semihbaser.crypto.R;
import com.semihbaser.crypto.adapter.BelongingsAdapter;
import com.semihbaser.crypto.databinding.ActivityDashboardBinding;
import com.semihbaser.crypto.model.Belongings;

import java.util.ArrayList;
import java.util.Map;

public class dashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Belongings> belongingsArrayList;
    BelongingsAdapter belongingsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboardActivity.this, UploadActivity.class);
                startActivity(intent);

            }
        });

        belongingsArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getDataFromFirestore();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        belongingsAdapter = new BelongingsAdapter(belongingsArrayList);
        binding.recyclerView.setAdapter(belongingsAdapter);


    }
    private void getDataFromFirestore() {

        firebaseFirestore.collection("Belongings").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(dashboardActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {

                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> getData = snapshot.getData();

                        String userEmail = (String) getData.get("useremail");
                        String name = (String) getData.get("name");
                        String price = (String) getData.get("price");
                        String downloadUrl = (String) getData.get("downloadurl");
                        String info = (String) getData.get("info");

                        Belongings belongings = new Belongings(userEmail, name, price, downloadUrl, info);

                        belongingsArrayList.add(belongings);

                    }
                    belongingsAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {

            auth.signOut();

            Intent intentToMain = new Intent(dashboardActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}