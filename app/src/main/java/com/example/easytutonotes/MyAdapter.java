package com.example.easytutonotes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    RealmResults<Note> notesList;


    public MyAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.moodView.setText(note.getMood());
        holder.rateView.setText(note.getRate()+"/10");
        holder.peopleView.setText(note.getPeople());
        String formattedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formattedTime);
        String color = newTextColor(note.getMood());
        holder.moodView.setTextColor(Color.parseColor(color));
//        holder.item_layout.setBackgroundColor(Color.parseColor(color));


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            //delete the note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public String newTextColor(String mood){
        String color;
        switch (mood){
            case "Excited":
                color = "#3c88df";
                break;
            case "Happy":
                color = "#49cae2";
                break;
            case "Worried":
                color = "#f2c525";
                break;
            case "Sad":
                color = "#ff813d";
                break;
            case "Angry":
                color = "#ff1d32";
                break;
            default:
                color = "#Black";
                break;
        }
        return color;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout item_layout;
        TextView moodView;
        TextView rateView;
        TextView peopleView;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_layout=(RelativeLayout) itemView.findViewById(R.id.item_view);
            moodView = itemView.findViewById(R.id.current_mood);
            rateView = itemView.findViewById(R.id.current_rate);
            peopleView = itemView.findViewById(R.id.current_people);
            timeOutput = itemView.findViewById(R.id.current_time);
        }
    }
}
