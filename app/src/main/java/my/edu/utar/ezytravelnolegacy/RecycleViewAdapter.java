package my.edu.utar.ezytravelnolegacy;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    Context context;
    ArrayList<Activity> activityList;

    public RecycleViewAdapter(Context context, ArrayList<Activity> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_activitylist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Activity activity = activityList.get(position);
        holder.activityType.setText(activity.getActivityType());
        holder.description.setText(activity.getDescription());
        holder.date.setText(activity.getDate());
        holder.time.setText(activity.getTime());

    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView activityType;
        TextView description;
        TextView date;
        TextView time;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            activityType = itemView.findViewById(R.id.tvType);
            description = itemView.findViewById(R.id.tvDesc);
            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 121, 0, "Update");
            contextMenu.add(this.getAdapterPosition(), 122, 0, "Delete");
        }
    }
}
