package cathay.hospital.hmfmsmobile.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cathay.hospital.hmfmsmobile.activity.R;

public class ItemAdaptor extends RecyclerView.Adapter<ItemAdaptor.ItemViewHolder> {
    private Context context;
    private List<String> itemIDLister, itemTypeLister, itemStateLister;
    public ItemAdaptor(Context context, List<String> itemIDLister,
                       List<String> itemTypeLister, List<String> itemStateLister){
        this.context = context;
        this.itemIDLister = itemIDLister;
        this.itemTypeLister = itemTypeLister;
        this.itemStateLister = itemStateLister;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_role, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.itemID.setText(itemIDLister.get(position));
        holder.itemTypeName.setText(itemTypeLister.get(position));
        switch (itemStateLister.get(position)){
            case "wait":
                holder.item_wait.setVisibility(View.VISIBLE);
                holder.item_alert.setVisibility(View.INVISIBLE);
                holder.item_check.setVisibility(View.INVISIBLE);
                break;
            case "alert":
                holder.item_wait.setVisibility(View.INVISIBLE);
                holder.item_alert.setVisibility(View.VISIBLE);
                holder.item_check.setVisibility(View.INVISIBLE);
                break;
            case "pass":
                holder.item_wait.setVisibility(View.INVISIBLE);
                holder.item_alert.setVisibility(View.INVISIBLE);
                holder.item_check.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemIDLister.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemID, itemTypeName;
        ImageView item_check, item_wait, item_alert;
        public ItemViewHolder(View itemView){
            super(itemView);
            itemID = itemView.findViewById(R.id.item_propNum);
            itemTypeName = itemView.findViewById(R.id.item_type);
            item_check = itemView.findViewById(R.id.scan_item_checked);
            item_wait = itemView.findViewById(R.id.scan_item_wait);
            item_alert = itemView.findViewById(R.id.scan_item_alert);
        }

    }
}
