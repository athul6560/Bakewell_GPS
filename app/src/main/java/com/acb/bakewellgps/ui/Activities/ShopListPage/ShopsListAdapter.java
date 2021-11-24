package com.acb.bakewellgps.ui.Activities.ShopListPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.acb.bakewellgps.R;
import com.acb.bakewellgps.modell.RoutesData;
import com.acb.bakewellgps.modell.Shop;
import com.acb.bakewellgps.ui.Activities.DashboardPage.RoutesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Shop> items = new ArrayList<>();

    private Context ctx;
    private ShopsListAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, Shop obj, int position);
    }

    public void setOnItemClickListener(final ShopsListAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public ShopsListAdapter(Context context, List<Shop> items) {
        this.items = items;
        ctx = context;

    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView locImage;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            locImage = (ImageView) v.findViewById(R.id.location_image);

            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops_inflator, parent, false);
        vh = new ShopsListAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ShopsListAdapter.OriginalViewHolder) {
            ShopsListAdapter.OriginalViewHolder view = (ShopsListAdapter.OriginalViewHolder) holder;

            Shop p = items.get(position);
            if (position == 0) {
                view.locImage.setColorFilter(ActivityCompat.getColor(ctx, android.R.color.holo_green_light));
            }
            view.name.setText(p.getShop_name());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {

                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


}
