package com.acb.bakewellgps.ui.Activities.ShopViewPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.acb.bakewellgps.R;

import com.acb.bakewellgps.SharedPref.SharedData;
import com.acb.bakewellgps.Tools.IntentConstants;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.Utils.Tools;
import com.acb.bakewellgps.databinding.ActivityDashboardBinding;
import com.acb.bakewellgps.databinding.ActivityShopViewBinding;
import com.acb.bakewellgps.modell.shopDetails;
import com.acb.bakewellgps.ui.Activities.DashboardPage.Dashboard;
import com.acb.bakewellgps.ui.Activities.EditPage.EditActivity;
import com.acb.bakewellgps.ui.Activities.ShopListPage.ShopListActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ShopViewActivity extends AppCompatActivity implements IShopViewLogic.view {

    private ActivityShopViewBinding binding;

    private ShopViewLogic shopViewLogic;
    private LocationRequest locationRequest;
    private double globallong, globalLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_view);
        binding = ActivityShopViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initComponents();
        getCurrentLocation();

        setLocation();

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShopViewActivity.this, EditActivity.class);


                i.putExtra("shopId", getShopId());
                startActivity(i);

            }
        });
        binding.updateGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                shopViewLogic.updateGpsApi(globallong, globalLang, getShopId());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ShopViewActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        shopViewLogic.getShopDetailsAPI(getShopId());
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


    }


    private void setTextAddress(double lang, double longitude) {
        globalLang = lang;
        globallong = longitude;
        //  binding.address.setText(address.getFeatureName() + "\n" + "Pin: " + address.getPostalCode() + ", " + address.getLocality() + ", " + address.getCountryName());
        //  binding.location.setText(lang + "," + longitude);
    }

    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(ShopViewActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(ShopViewActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(ShopViewActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        setTextAddress(latitude, longitude);
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    Toast.makeText(ShopViewActivity.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void initComponents() {

        shopViewLogic = new ShopViewLogic(this, this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    @Override
    public void updateGpsCallback(boolean status) {
        if (status)
            Dialogues.showSuccessDialogue(ShopViewActivity.this, "Success", "Location Updated Successfully");
        else
            Dialogues.showWarning(ShopViewActivity.this, "Failed", "Updation Failed");
    }

    @Override
    public void shopDetailsCallback(boolean status, shopDetails shopDetails) {
        if (status) {
            setShopData(shopDetails);
            IntentConstants.SHOP_DETAILS = shopDetails;
        }
    }

    private void setShopData(shopDetails shopDetails) {
        binding.shopName.setText(shopDetails.getOrganisation_name());
        binding.subTitle.setText(shopDetails.getShop_category_name());
        binding.address.setText(shopDetails.getAddress_line1() + "\n" + shopDetails.getAddress_line2() + " " + shopDetails.getAddress_line3() + " " + shopDetails.getProvince_name());
        binding.taxId.setText("Tax : " + shopDetails.getTax_number());
        binding.ownerName.setText(shopDetails.getOwner_name());
        binding.ownerNumber.setText(shopDetails.getOwner_mobile_no());
        binding.ownerEmail.setText(shopDetails.getOwner_email_id());
        binding.contactName.setText(shopDetails.getShop_contact_name());
        binding.location.setText(shopDetails.getLatitude() + "," + shopDetails.getLongitude());
        binding.contactNumber.setText(shopDetails.getShop_contact_mobile_no());
        binding.website.setText(shopDetails.getWebsite()+"");
        binding.parentCompany.setText("Parent Company : " + shopDetails.getParent_name());
        binding.tradeLicenseNumber.setText("Trade License Number : "+shopDetails.getLicence_no());
        binding.customerGroup.setText("Customer Group : "+shopDetails.getCustomer_group_name());
        if (shopDetails.getShop_image() != null)
            binding.imageShop.setImageBitmap(Tools.getImageBitmapFromBase(shopDetails.getShop_image()));
        if (shopDetails.is_approved) {
            binding.editBtn.setVisibility(View.GONE);
        }

    }
}