package com.specialtopics.flost.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> mItemsList;
    private boolean matchFlag; // flags true if adapter is used for the match view (formpage8)

    public ItemAdapter(Context context, List<Item> itemList, boolean match){
        this.context = context;
        this.mItemsList = itemList;
        this.matchFlag = match;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, user, location, price, type, match;
        ImageView thumbnail;


        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_title);
            //description = view.findViewById(R.id.itemDescription);
            //price = view.findViewById(R.id.itemPrice);
            //type = view.findViewById(R.id.itemCategory);
            //user = view.findViewById(R.id.userSellingItem);
            thumbnail = view.findViewById(R.id.item_img);
            match = view.findViewById(R.id.tv_match);
            //location = view.findViewById(R.id.itemLocation);
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mItemsList.get(position);
        holder.name.setText(item.getName());
        holder.thumbnail.setImageResource(R.drawable.bottle);
        if(matchFlag)
            holder.match.setText("MATCH: "); //TODO: create util function that finds match percentage
        //holder.type.setText(item.getType());
        //holder.user.setText(item.getUserID());
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }
}
