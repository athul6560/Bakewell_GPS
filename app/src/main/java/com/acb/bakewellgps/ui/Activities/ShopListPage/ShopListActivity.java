package com.acb.bakewellgps.ui.Activities.ShopListPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.acb.bakewellgps.R;
import com.acb.bakewellgps.databinding.ActivityDashboardBinding;
import com.acb.bakewellgps.databinding.ActivityShopListBinding;
import com.acb.bakewellgps.modell.RoutesData;
import com.acb.bakewellgps.modell.Shop;
import com.acb.bakewellgps.ui.Activities.DashboardPage.Dashboard;
import com.acb.bakewellgps.ui.Activities.DashboardPage.DashboardLogic;
import com.acb.bakewellgps.ui.Activities.DashboardPage.RoutesAdapter;
import com.acb.bakewellgps.ui.Activities.ShopViewPage.ShopViewActivity;
import com.acb.bakewellgps.ui.Activities.addNewShopPage.AddNewShopActivity;
import com.google.gson.Gson;

import java.util.List;

public class ShopListActivity extends AppCompatActivity {
    private ActivityShopListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initComponents();
        initToolbar();
        onClicks();
        setAdapter(getIntentRoutes());
    }

    private void onClicks() {

    }

    private List<Shop> getIntentRoutes() {
        Gson gson = new Gson();
        RoutesData ob = gson.fromJson(getIntent().getStringExtra("Routes"), RoutesData.class);
        return ob.getShops();
    }

    private void initComponents() {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
    }

    private void setAdapter(List<Shop> shops) {
        ShopsListAdapter mAdapter = new ShopsListAdapter(this, shops);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ShopsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Shop obj, int position) {
                Intent i = new Intent(ShopListActivity.this, ShopViewActivity.class);

                i.putExtra("shopName", obj.getShop_name());
                i.putExtra("shopId", obj.getShop_id());
                startActivity(i);

            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("Shops List"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }
}