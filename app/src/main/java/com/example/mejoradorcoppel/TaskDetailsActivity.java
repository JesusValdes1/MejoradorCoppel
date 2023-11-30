package com.example.mejoradorcoppel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Objects;

public class TaskDetailsActivity extends AppCompatActivity {

    EditText titleEditText, descEditText, horaInicioEditText, horaFinEditText;
    ImageButton guardarTareaBtn, backBtn;
    TextView pageTitleTextView;
    String titulo,descripcion,hInicio,hFin,docId;
    Button borrarTareaBtn;
    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        backBtn = findViewById(R.id.back_btn);
        titleEditText = findViewById(R.id.titulo_text);
        descEditText = findViewById(R.id.desc_text);
        horaInicioEditText = findViewById(R.id.editTextHoraInicio);
        horaFinEditText = findViewById(R.id.editTextHoraFin);
        guardarTareaBtn = findViewById(R.id.save_task_btn);
        pageTitleTextView = findViewById(R.id.page_title);
        borrarTareaBtn = findViewById(R.id.delete_task_btn);

        backBtn.setOnClickListener((v)-> startActivity(new Intent(TaskDetailsActivity.this,MainActivity.class)));

        //receive data
        titulo = getIntent().getStringExtra("titulo");
        descripcion = getIntent().getStringExtra("descripcion");
        hInicio = getIntent().getStringExtra("hora inicio");
        hFin = getIntent().getStringExtra("hora fin");
        docId = getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()){
            editMode = true;
        }

        titleEditText.setText(titulo);
        descEditText.setText(descripcion);
        horaInicioEditText.setText(hInicio);
        horaFinEditText.setText(hFin);
        if(editMode){
            pageTitleTextView.setText("Edita tu tarea");
            borrarTareaBtn.setVisibility(View.VISIBLE);
        }

        guardarTareaBtn.setOnClickListener((v)-> guardarTarea());

        borrarTareaBtn.setOnClickListener((v)-> borrarTareaDeFirebase());

    }
    void guardarTarea(){
        String taskTitulo = titleEditText.getText().toString();
        String descTask = descEditText.getText().toString();
        String hInicioTask = horaInicioEditText.getText().toString();
        String hFinTask = horaFinEditText.getText().toString();

        if (taskTitulo == null || taskTitulo.isEmpty()){
            titleEditText.setError("Titulo requerido");
            return;
        }
        Task task = new Task();
        task.setTituloTarea(taskTitulo);
        task.setDescTarea(descTask);
        task.setHorainicio(hInicioTask);
        task.setHorafin(hFinTask);
        task.setTimestamp(Timestamp.now());

        guardarTareaFirebase(task);

    }
    void guardarTareaFirebase(Task task){
        DocumentReference documentReference;
        if(editMode){
            documentReference = Utility.getCollectionReferenceForTask().document(docId);
        }else {
            documentReference = Utility.getCollectionReferenceForTask().document();
        }
        documentReference.set(task).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(TaskDetailsActivity.this,"Tarea agregada con éxito");
                    finish();
                }else{
                    Utility.showToast(TaskDetailsActivity.this,"No se pudo agregar la tarea");
                }
            }
        });
    }
    void borrarTareaDeFirebase(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForTask().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(TaskDetailsActivity.this,"Tarea borrada");
                    finish();
                }else{
                    Utility.showToast(TaskDetailsActivity.this,"No se pudo borrar la tarea");
                }
            }
        });
    }
}