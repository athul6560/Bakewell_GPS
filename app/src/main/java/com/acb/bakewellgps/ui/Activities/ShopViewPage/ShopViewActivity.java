package com.acb.bakewellgps.ui.Activities.ShopViewPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.acb.bakewellgps.R;

import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.databinding.ActivityShopViewBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ShopViewActivity extends AppCompatActivity implements IShopViewLogic.view {
    private ActivityShopViewBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Address addressGlobal;
    private ShopViewLogic shopViewLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initComponents();

        initToolbar();
        setLocation();
        binding.updateGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressGlobal == null) {
                    Dialogues.showWarning(ShopViewActivity.this, "No Location Fount", "Please try to refresh te location.");
                } else {
                    shopViewLogic.updateGpsApi(addressGlobal.getLongitude(), addressGlobal.getLatitude(), getShopId());
                }
            }
        });
        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });


    }

    private int getShopId() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("shopId");
            return value;
        }
        return 0;
    }

    private String getShopName() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("shopName");
            return value;
        }
        return "";
    }

    private void setLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                try {
                    if (location != null) {

                        Geocoder geocoder = new Geocoder(ShopViewActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        setTextAddress(addresses.get(0));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml(getShopName()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setTextAddress(Address address) {
        addressGlobal = address;
        binding.address.setText(address.getFeatureName() + "\n" + "Pin: " + address.getPostalCode() + ", " + address.getLocality() + ", " + address.getCountryName());
        binding.location.setText(address.getLatitude() + "," + address.getLongitude());
    }


    private void initComponents() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        shopViewLogic = new ShopViewLogic(this, this);

    }

    @Override
    public void updateGpsCallback(boolean status) {
        if (status)
            Dialogues.showSuccessDialogue(ShopViewActivity.this, "Success", "Location Updated Successfully");
        else
            Dialogues.showWarning(ShopViewActivity.this,"Failed","Updation Failed");
    }
}