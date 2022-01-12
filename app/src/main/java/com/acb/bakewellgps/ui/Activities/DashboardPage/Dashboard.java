package com.acb.bakewellgps.ui.Activities.DashboardPage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.acb.bakewellgps.R;
import com.acb.bakewellgps.SharedPref.SharedData;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.databinding.ActivityDashboardBinding;

import com.acb.bakewellgps.modell.RoutesData;
import com.acb.bakewellgps.modell.allCustomerModel.allCustomers;
import com.acb.bakewellgps.ui.Activities.LoginPage.LoginActivity;
import com.acb.bakewellgps.ui.Activities.ShopListPage.ShopListActivity;
import com.acb.bakewellgps.ui.Activities.addNewShopPage.AddNewShopActivity;
import com.google.gson.Gson;

import java.util.List;

public class Dashboard extends AppCompatActivity implements IDashboardLogic.view {
    private DashboardLogic logic;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initComponents();
        initToolbar();
        logic.callAllCustomerApi(SharedData.getId(Dashboard.this));
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, AddNewShopActivity.class));
            }
        });
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialogue();

            }
        });
    }

    private void showLogoutDialogue() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                      logic.callLogoutApi(SharedData.getUsername(Dashboard.this));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("Route - "+SharedData.getRouteNumber(Dashboard.this)));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar.setTitle(Html.fromHtml("<small>YOUR TITLE</small>"));
    }

    private void initComponents() {
        logic = new DashboardLogic(this, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
    }

    @Override
    public void routeListApiCompleted(boolean status, List<RoutesData> routeList, String errorMessage) {


    }

    @Override
    public void allCustomerApiCallBack(boolean status, List<allCustomers> customersList, String errorMessage) {
        if (status)
            setAdapter(customersList);
        else {
            Dialogues.showWarning(this, "Error", errorMessage);
        }
    }

    @Override
    public void logoutCallback(boolean status, String errorMessage) {
        if(status){
            SharedData.LogedStatusUpdate(Dashboard.this,false);
            startActivity(new Intent(Dashboard.this,LoginActivity.class));
            finish();
        }
    }

    private void setAdapter(List<allCustomers> routeList) {
        AllCustomerAdapter mAdapter = new AllCustomerAdapter(this, routeList);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AllCustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, allCustomers obj, int position) {

                Intent i = new Intent(Dashboard.this, ShopListActivity.class);
                Gson gson = new Gson();
                String Routes = gson.toJson(obj);
                i.putExtra("Routes", Routes);
                startActivity(i);
            }
        });
    }
}