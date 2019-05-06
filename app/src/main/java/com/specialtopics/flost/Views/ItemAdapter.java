package com.specialtopics.flost.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Item> mItemsList;
    private List<Item> filteredList;
    private boolean matchFlag; // flags true if adapter is used for the match view (formpage8)

    public ItemAdapter(Context context, List<Item> itemList, boolean match){
        this.context = context;
        this.mItemsList = itemList;
        this.filteredList = itemList;
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
        Item item = filteredList.get(position);
        byte[] imageByte = item.getImage();
        holder.name.setText(item.getName());

        if (imageByte != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            Log.d("bmp_check", String.valueOf(bmp));
            holder.thumbnail.setImageBitmap(bmp);
        }
        //holder.thumbnail.setImageResource(item.getImage());
        if (matchFlag)
            holder.match.setText("MATCH: "); //TODO: create util function that finds match percentage
        //holder.type.setText(item.getType());
        //holder.user.setText(item.getUserID());
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<Item>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Item> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mItemsList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<Item> getFilteredResults(String constraint) {
        List<Item> results = new ArrayList<>();

        for (Item item : mItemsList) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
                Log.d("HomeFragmentFound", item.getName());
            }
        }
        return results;
    }
}
