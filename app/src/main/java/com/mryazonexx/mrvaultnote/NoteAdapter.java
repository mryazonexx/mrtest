package com.mryazonexx.mrvaultnote;

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

public class NoteAdapter extends FirestoreRecyclerAdapter<NoteTools, NoteAdapter.NoteViewHolder> {
    final Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<NoteTools> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull NoteTools noteTools) {
        holder.noteTitleBoxView.setText(noteTools.title);
        holder.noteContentBoxView.setText(noteTools.content);
        holder.noteTimestampBoxView.setText(Tools.timestampToString(noteTools.timestamp));

        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, NoteFormDetails.class);
            intent.putExtra("title", noteTools.title);
            intent.putExtra("content", noteTools.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });


    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_recycler_view_item, parent, false);
        return new NoteViewHolder(view);

    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        final TextView noteTitleBoxView;
        final TextView noteContentBoxView;
        final TextView noteTimestampBoxView;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleBoxView = itemView.findViewById(R.id.note_title_box_view);
            noteContentBoxView = itemView.findViewById(R.id.note_content_box_view);
            noteTimestampBoxView = itemView.findViewById(R.id.note_timestamp_box_view);
        }

    }
}
