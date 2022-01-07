package com.acb.bakewellgps.ui.Activities.addNewShopPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.acb.bakewellgps.R;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.databinding.ActivityAddNewShopBinding;
import com.acb.bakewellgps.modell.allCurrencies;
import com.acb.bakewellgps.modell.areaList;
import com.acb.bakewellgps.modell.countryList;
import com.acb.bakewellgps.modell.sentShopAddDetails;
import com.acb.bakewellgps.modell.sentShopUpdateDetails;
import com.acb.bakewellgps.modell.shopCategories;
import com.acb.bakewellgps.ui.Activities.EditPage.EditLogic;
import com.acb.bakewellgps.ui.Activities.EditPage.IEditLogic;

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
    shopCategories shopCategories;

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
                logic.addNewShop(getnewShopDetails());

            }
        });

    }

    private sentShopAddDetails getnewShopDetails() {
        //      sentShopAddDetails addDetails = new sentShopAddDetails();
        return null;
    }

    private void initComponents() {
        logic = new AddLogic(this, this);
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

        getSupportActionBar().setTitle(Html.fromHtml("Edit Shop"));

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
            this.shopCategories = shopCategories;
            Dialogues.dismiss();
        } else {
            Toast.makeText(AddNewShopActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            Dialogues.dismiss();
        }
    }
}