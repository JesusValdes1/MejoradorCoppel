package com.example.mejoradorcoppel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton agregarTareabtn;
    RecyclerView recyclerView;
    ImageButton menubtn, backbtn;

    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backbtn = findViewById(R.id.back_btn);
        agregarTareabtn = findViewById(R.id.add_task_btn);
        recyclerView = findViewById(R.id.recyler_view);
        menubtn = findViewById(R.id.menu_btn);

        backbtn.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
        agregarTareabtn.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this,TaskDetailsActivity.class)));
        menubtn.setOnClickListener((v)->showMenu());
        setupRecyclerView();
    }
    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,menubtn);
        popupMenu.getMenu().add("Salir");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Salir"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
    void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForTask().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Task> options = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query,Task.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(options,this);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        taskAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        taskAdapter.stopListening();
    }
    @Override
    protected void onResume(){
        super.onResume();
        taskAdapter.notifyDataSetChanged();
    }
}