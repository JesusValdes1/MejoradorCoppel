package com.example.mejoradorcoppel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import javax.annotation.Nonnull;

public class TaskAdapter extends FirestoreRecyclerAdapter<Task,TaskAdapter.TaskViewHolder> {
    Context context;
    public TaskAdapter(@NonNull FirestoreRecyclerOptions<Task> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull Task task) {
        holder.titleTextView.setText(task.tituloTarea);
        holder.descTextView.setText(task.descTarea);
        holder.horaInicioTextView.setText(task.horainicio);
        holder.horaFinTextView.setText(task.horafin);
        holder.timestampTextView.setText(Utility.timestampToString(task.timestamp));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,TaskDetailsActivity.class);
            intent.putExtra("titulo",task.tituloTarea);
            intent.putExtra("descripcion",task.descTarea);
            intent.putExtra("horaI",task.horainicio);
            intent.putExtra("horaF",task.horafin);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_task_item,parent,false);
        return new TaskViewHolder(view);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView,descTextView, timestampTextView, horaInicioTextView, horaFinTextView;

        public TaskViewHolder(@Nonnull View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tarea_titulo_text_view);
            descTextView = itemView.findViewById(R.id.tarea_desc_text_view);
            timestampTextView = itemView.findViewById(R.id.tarea_timestamp_text_view);
            horaInicioTextView = itemView.findViewById(R.id.hora_inicio_text_view);
            horaFinTextView = itemView.findViewById(R.id.hora_fin_text_view);
        }
    }
}
