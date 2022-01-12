package com.acb.bakewellgps.ui.Activities.DashboardPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.acb.bakewellgps.R;

import com.acb.bakewellgps.modell.allCustomerModel.allCustomers;

import java.util.ArrayList;
import java.util.List;

public class AllCustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<allCustomers> items = new ArrayList<>();

    private Context ctx;
    private AllCustomerAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, allCustomers obj, int position);
    }

    public void setOnItemClickListener(final AllCustomerAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AllCustomerAdapter(Context context, List<allCustomers> items) {
        this.items = items;
        ctx = context;

    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name, subtitle, routeId;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            subtitle = (TextView) v.findViewById(R.id.subtitle);
            routeId = (TextView) v.findViewById(R.id.route_id);

            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_customer_inflator, parent, false);
        vh = new AllCustomerAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof AllCustomerAdapter.OriginalViewHolder) {
            AllCustomerAdapter.OriginalViewHolder view = (AllCustomerAdapter.OriginalViewHolder) holder;

            allCustomers p = items.get(position);
            view.routeId.setText(p.getId() + "");
            view.name.setText(p.getName());
          //  view.subtitle.setText("" + p.getEmail() + " | " + p.getAddress_line1());

          //  view.name.setText(p.getOwner_name());
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
