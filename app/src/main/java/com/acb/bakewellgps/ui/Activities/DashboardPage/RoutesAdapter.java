package com.acb.bakewellgps.ui.Activities.DashboardPage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.acb.bakewellgps.R;
import com.acb.bakewellgps.modell.RoutesData;

import java.util.ArrayList;
import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RoutesData> items = new ArrayList<>();

    private Context ctx;
    private RoutesAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, RoutesData obj, int position);
    }

    public void setOnItemClickListener(final RoutesAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public RoutesAdapter(Context context, List<RoutesData> items) {
        this.items = items;
        ctx = context;

    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);

            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.routes_inflator, parent, false);
        vh = new RoutesAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RoutesAdapter.OriginalViewHolder) {
            RoutesAdapter.OriginalViewHolder view = (RoutesAdapter.OriginalViewHolder) holder;

            RoutesData p = items.get(position);


            if(position==1)
            view.name.setText("Trivandrum - Ayoor");
            else if(position==2)
                view.name.setText("Trivandrum - Kottarakara");
            else
                view.name.setText(p.getRoute_name());
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
