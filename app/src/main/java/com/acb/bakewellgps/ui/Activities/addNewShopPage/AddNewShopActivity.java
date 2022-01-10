package com.acb.bakewellgps.ui.Activities.addNewShopPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.acb.bakewellgps.R;
import com.acb.bakewellgps.SharedPref.SharedData;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.Utils.Tools;
import com.acb.bakewellgps.databinding.ActivityAddNewShopBinding;
import com.acb.bakewellgps.modell.allCurrencies;
import com.acb.bakewellgps.modell.areaList;
import com.acb.bakewellgps.modell.categoryName;
import com.acb.bakewellgps.modell.countryList;
import com.acb.bakewellgps.modell.responseSimple;
import com.acb.bakewellgps.modell.sentShopAddDetails;
import com.acb.bakewellgps.modell.shopCategories;
import com.acb.bakewellgps.ui.Activities.ShopViewPage.ShopViewActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class AddNewShopActivity extends AppCompatActivity implements IAddLogic.view {
    private ActivityAddNewShopBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private AddLogic logic;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap ImageBitmap;
    List<countryList> countryList = new ArrayList<>();
    List<allCurrencies> allCurrencies = new ArrayList<>();
    List<areaList> areaLists = new ArrayList<>();
    List<categoryName> shopCategories;
    private LocationRequest locationRequest;
    private double globallong, globalLang;
    String[] transactionTypes = {"Cash", "Credit",
            "Cheque", "Transfer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewShopBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initToolsbar();
        initComponents();
        Dialogues.show(this);
        logic.getAllCountries();
        getCurrentLocation();

        setLocation();
        binding.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();

                setLocation();
            }
        });
        binding.addimageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validation().isStatus())
                    logic.addNewShop(getnewShopDetails());
                else
                    Toast.makeText(AddNewShopActivity.this, "" + validation().getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

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

    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(AddNewShopActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(AddNewShopActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(AddNewShopActivity.this)
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
                    Toast.makeText(AddNewShopActivity.this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void setTextAddress(double lang, double longitude) {
        globalLang = lang;
        globallong = longitude;
        //  binding.address.setText(address.getFeatureName() + "\n" + "Pin: " + address.getPostalCode() + ", " + address.getLocality() + ", " + address.getCountryName());
        binding.latlong.setText(lang + "," + longitude);
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

    private responseSimple validation() {
        responseSimple responseSimple = new responseSimple();
        if (binding.shortName.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Short Name");
            return responseSimple;
        }
        if (binding.organisationName.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Organisation Name");
            return responseSimple;
        }
        if (binding.taxNumber.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Tax Number");
            return responseSimple;
        }
        if ( binding.postBoxNumber.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Post Box Number");
            return responseSimple;
        }

        responseSimple.setStatus(true);
        responseSimple.setMessage("Success");
        return responseSimple;
             /*   ,
               ,
                23,
               ,
                binding.addressOne.getText().toString(),
                binding.addressTwo.getText().toString(),
                binding.addressThree.getText().toString(),
                binding.email.getText().toString(),
                binding.website.getText().toString(),
                binding.mobileNumber.getText().toString(),
                binding.secondNumber.getText().toString(),
                binding.whatsappNumber.getText().toString(),
                binding.telNumber.getText().toString(),
                34,
                "cash",
                binding.licenseNumber.getText().toString(),
                binding.ownerName.getText().toString(),
                binding.ownerNumber.getText().toString(),
                binding.ownerEmail.getText().toString(),
                binding.contactName.getText().toString(),
                binding.contactNumber.getText().toString(),
                binding.contactEmail.getText().toString(),
                4,
                "",
                "",
                Tools.getStringfromBitmap(ImageBitmap),
                Tools.getStringfromBitmap(ImageBitmap),
                1997,
                binding.landMark.getText().toString()*/
    }

    private void setTransactionTypeSpinner() {

        Spinner spin = (Spinner) findViewById(R.id.transaction_type);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                transactionTypes);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);
    }

    private void setCountrySpinner() {

        Spinner spin = (Spinner) findViewById(R.id.country);
        ArrayAdapter<countryList> adapter =
                new ArrayAdapter<countryList>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
    }

    private void setAreaSpinner() {

        Spinner spin = (Spinner) findViewById(R.id.area);
        ArrayAdapter<areaList> adapter =
                new ArrayAdapter<areaList>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, areaLists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);

    }

    private void setShopCategorySpinner() {

        Spinner spin = (Spinner) findViewById(R.id.shop_category_id);
        ArrayAdapter<categoryName> adapter =
                new ArrayAdapter<categoryName>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, shopCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
    }

    private sentShopAddDetails getnewShopDetails() {

            int creditDays = Integer.parseInt(binding.creditDays.getText().toString());
            int year = Integer.parseInt(binding.establishedYear.getText().toString());

        sentShopAddDetails addDetails = new sentShopAddDetails(
                binding.shortName.getText().toString(),
                binding.organisationName.getText().toString(),
                binding.taxNumber.getText().toString(),
                getProvinceId(binding.area.getSelectedItem().toString() ),
                binding.postBoxNumber.getText().toString(),
                binding.addressOne.getText().toString(),
                binding.addressTwo.getText().toString(),
                binding.addressThree.getText().toString(),
                binding.email.getText().toString(),
                binding.website.getText().toString(),
                binding.mobileNumber.getText().toString(),
                binding.secondNumber.getText().toString(),
                binding.whatsappNumber.getText().toString(),
                binding.telNumber.getText().toString(),
                getShopCategoryId(binding.shopCategoryId.getSelectedItem().toString()),
                binding.transactionType.getSelectedItem().toString(),
                binding.licenseNumber.getText().toString(),
                binding.ownerName.getText().toString(),
                binding.ownerNumber.getText().toString(),
                binding.ownerEmail.getText().toString(),
                binding.contactName.getText().toString(),
                binding.contactNumber.getText().toString(),
                binding.contactEmail.getText().toString(),
                creditDays,

                globalLang+"",
                globallong+"",
                Tools.getStringfromBitmap(ImageBitmap),
                Tools.getStringfromBitmap(ImageBitmap),
                year,
                binding.landMark.getText().toString(),
                SharedData.getId(AddNewShopActivity.this),
                SharedData.getRouteId(AddNewShopActivity.this)


        );
        return addDetails;
    }

    private int getShopCategoryId(String categoryName) {
        for(int i=0;i<shopCategories.size();i++){
            if (shopCategories.get(i).getCategory_name().equals(categoryName)){
                return shopCategories.get(i).getId();
            }
        }
        return 0;
    }

    private int getProvinceId(String provinceName) {
        for(int i=0;i<areaLists.size();i++){
            if (areaLists.get(i).getArea_name().equals(provinceName)){
                return areaLists.get(i).getProvince_id();
            }
        }
        return 0;
    }

    private void initComponents() {
        logic = new AddLogic(this, this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageBitmap = photo;
            binding.shopImage.setImageBitmap(photo);
        }
    }

    private void initToolsbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("Add Shop"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void addSuccessCallback(Boolean status, String Message) {

    }

    @Override
    public void countryCallback(Boolean status, String Message, List<countryList> countryList) {
        if (status) {
            this.countryList = countryList;
            logic.getAllArea();
        } else {
            Toast.makeText(AddNewShopActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void areaCallback(Boolean status, String Message, List<areaList> areaLists) {
        if (status) {
            this.areaLists = areaLists;
            logic.getAllCurrencies();
        } else {
            Toast.makeText(AddNewShopActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void currencyCallback(Boolean status, String Message, List<allCurrencies> allCurrencies) {
        if (status) {
            this.allCurrencies = allCurrencies;
            logic.getShopCategory();
        } else {
            Toast.makeText(AddNewShopActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void shopCategoryCallBack(Boolean status, String Message, shopCategories shopCategories) {
        if (status) {
            this.shopCategories = shopCategories.getData();
            setCountrySpinner();
            setAreaSpinner();
            setShopCategorySpinner();
            setTransactionTypeSpinner();
            Dialogues.dismiss();
        } else {
            Toast.makeText(AddNewShopActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            Dialogues.dismiss();
        }
    }
}