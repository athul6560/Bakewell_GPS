package com.acb.bakewellgps.ui.Activities.EditPage;

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
import com.acb.bakewellgps.Utils.Tools;
import com.acb.bakewellgps.databinding.ActivityDashboardBinding;
import com.acb.bakewellgps.databinding.ActivityEditBinding;
import com.acb.bakewellgps.modell.sentShopUpdateDetails;

public class EditActivity extends AppCompatActivity implements IEditLogic.view {
    private ActivityEditBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private EditLogic logic;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap ImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initToolsbar();
        initComponents();
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
                logic.updateShopDetails(getUpdateData());

            }
        });
    }

    private sentShopUpdateDetails getUpdateData() {
        sentShopUpdateDetails shopUpdateDetails = new sentShopUpdateDetails();
        shopUpdateDetails.setId(getShopId());
        shopUpdateDetails.setOrganisation_name(binding.organizationName.getText().toString());
        shopUpdateDetails.setShop_image(Tools.getStringfromBitmap(ImageBitmap));
        shopUpdateDetails.setAddress_line1(binding.addressOne.getText().toString());
        shopUpdateDetails.setAddress_line2(binding.addressTwo.getText().toString());
        shopUpdateDetails.setEmail(binding.email.getText().toString());
        shopUpdateDetails.setAddress_line3(binding.addressThree.getText().toString());
        shopUpdateDetails.setOwner_email_id(binding.ownerEmail.getText().toString());
        shopUpdateDetails.setOwner_mobile_no(binding.ownerNumber.getText().toString());
        shopUpdateDetails.setOwner_name(binding.ownerName.getText().toString());
        shopUpdateDetails.setShop_contact_email_id(binding.contactEmail.getText().toString());
        shopUpdateDetails.setShop_contact_name(binding.contactName.getText().toString());
        shopUpdateDetails.setShop_contact_mobile_no(binding.contactNumber.getText().toString());


        return shopUpdateDetails;
    }

    private void initComponents() {
        logic = new EditLogic(this, this);
    }

    private int getShopId() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("shopId");
            return value;
        }
        return 0;
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
            ImageBitmap=photo;
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
    public void updateSuccessCallback(Boolean status, String Message) {
        if (status) {
            Toast.makeText(EditActivity.this, Message, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(EditActivity.this, Message, Toast.LENGTH_SHORT).show();
        }
    }
}