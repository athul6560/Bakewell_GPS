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
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.acb.bakewellgps.R;
import com.acb.bakewellgps.Tools.IntentConstants;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.Utils.Tools;
import com.acb.bakewellgps.databinding.ActivityEditBinding;
import com.acb.bakewellgps.modell.allCurrencies;
import com.acb.bakewellgps.modell.areaList;
import com.acb.bakewellgps.modell.categoryName;
import com.acb.bakewellgps.modell.countryList;
import com.acb.bakewellgps.modell.customerGroup;
import com.acb.bakewellgps.modell.parentCompany.parentCompany;
import com.acb.bakewellgps.modell.responseSimple;
import com.acb.bakewellgps.modell.sentShopUpdateDetails;
import com.acb.bakewellgps.modell.shopCategories;
import com.acb.bakewellgps.modell.shopDetails;
import com.acb.bakewellgps.ui.Activities.addNewShopPage.AddLogic;
import com.acb.bakewellgps.ui.Activities.addNewShopPage.AddNewShopActivity;
import com.acb.bakewellgps.ui.Activities.addNewShopPage.IAddLogic;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity implements IEditLogic.view, IAddLogic.view {
    private ActivityEditBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_REQUEST_LOGO = 108;
    private AddLogic addLogic;
    private EditLogic editLogic;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap ImageBitmap;
    List<areaList> areaLists = new ArrayList<>();
    List<parentCompany> parentCompany = new ArrayList<>();
    List<categoryName> shopCategories;
    private Bitmap LogoBitmap;
    private List<com.acb.bakewellgps.modell.customerGroup> customerGroup = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initToolsbar();
        initComponents();
        setExistingData(IntentConstants.SHOP_DETAILS);
        Dialogues.show(this);
        addLogic.getAllArea();
        addLogic.getShopCategory();
        addLogic.getallParentCompanies();
        addLogic.getCustomerGroup();
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
        binding.mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.whatsappNumber.setText(editable.toString());
            }
        });
        binding.addlogoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_LOGO);
                }
            }
        });
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation().isStatus())
                    if (Tools.isNetworkConnected(EditActivity.this))
                        editLogic.updateShopDetails(getUpdateData());
                    else
                        Toast.makeText(EditActivity.this, "No Network", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(EditActivity.this, "" + validation().getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private responseSimple validation() {
        responseSimple responseSimple = new responseSimple();
        if (binding.company.getSelectedItem().toString().equals("Select Parent Company")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Select Parent Company");
            return responseSimple;
        }
        if (binding.area.getSelectedItem().toString().equals("Select Area")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Select Area");
            return responseSimple;
        }
        if (binding.shopCategoryId.getSelectedItem().equals("Select Shop Category")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Select Shop Category");
            return responseSimple;
        }
        if (binding.organisationName.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Customer  Name");
            return responseSimple;
        }
        if (!binding.organisationName.getText().toString().matches("^[a-zA-Z\\s]*$")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Valid Customer Name");
            return responseSimple;
        }


        if (!binding.email.getText().toString().equals("") && !Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Valid Email ID");
            return responseSimple;
        }

        if (binding.taxNumber.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Tax Reg Number");
            return responseSimple;
        }


        if (binding.area.getSelectedItem().equals("Select Area")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Select Area");
            return responseSimple;
        }
        if (binding.addressOne.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Address Line 1");
            return responseSimple;
        }
        if (binding.mobileNumber.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Mobile Number");
            return responseSimple;
        }
        if (binding.contactName.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Contact Name");
            return responseSimple;
        }
        if (binding.contactNumber.getText().toString().equals("")) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Contact Number");
            return responseSimple;
        }

        if (!binding.website.getText().toString().equals("") &&  !Patterns.WEB_URL.matcher(binding.website.getText().toString()).matches()) {
            responseSimple.setStatus(false);
            responseSimple.setMessage("Please Enter Valid Website");
            return responseSimple;
        }

        responseSimple.setStatus(true);
        responseSimple.setMessage("Success");
        return responseSimple;
    }

    private void setExistingData(shopDetails shopDetails) {
        binding.shopLogo.setImageBitmap(Tools.getImageBitmapFromBase(shopDetails.getShop_logo()));
        binding.shopImage.setImageBitmap(Tools.getImageBitmapFromBase(shopDetails.getShop_image()));
        binding.organisationName.setText(shopDetails.getOrganisation_name());
        binding.taxNumber.setText(shopDetails.getTax_number());
        binding.addressOne.setText(shopDetails.getAddress_line1());
        binding.addressTwo.setText(shopDetails.getAddress_line2());
        binding.postBoxNumber.setText(shopDetails.getPost_box_number());
        binding.website.setText(shopDetails.getWebsite());
        binding.telNumber.setText(shopDetails.getPhone_no());
        binding.mobileNumber.setText(shopDetails.getMobile_no1());
        binding.whatsappNumber.setText(shopDetails.getWhatsapp_no());
        binding.email.setText(shopDetails.getEmail());
        binding.secondNumber.setText(shopDetails.getMobile_no2());
        binding.ownerName.setText(shopDetails.getOwner_name());
        binding.ownerNumber.setText(shopDetails.getOwner_mobile_no());
        binding.contactName.setText(shopDetails.getShop_contact_name());
        binding.contactNumber.setText(shopDetails.getShop_contact_mobile_no());


    }

    private sentShopUpdateDetails getUpdateData() {
        sentShopUpdateDetails shopUpdateDetails = new sentShopUpdateDetails();
        shopUpdateDetails.setId(getShopId());
        shopUpdateDetails.setShop_logo(Tools.getStringfromBitmap(LogoBitmap));
        shopUpdateDetails.setShop_image(Tools.getStringfromBitmap(ImageBitmap));
        shopUpdateDetails.setOrganisation_name(binding.organisationName.getText().toString());
        shopUpdateDetails.setTax_number(binding.taxNumber.getText().toString());
        shopUpdateDetails.setParent_id(getparentId(binding.company.getSelectedItem().toString()));
        shopUpdateDetails.setProvince_id(getProvinceId(binding.area.getSelectedItem().toString()));
        shopUpdateDetails.setAddress_line1(binding.addressOne.getText().toString());
        shopUpdateDetails.setAddress_line2(binding.addressTwo.getText().toString());
        shopUpdateDetails.setPost_box_number(binding.postBoxNumber.getText().toString());
        shopUpdateDetails.setWebsite(binding.website.getText().toString());
        shopUpdateDetails.setPhone_no(binding.telNumber.getText().toString());
        shopUpdateDetails.setMobile_no1(binding.mobileNumber.getText().toString());
        shopUpdateDetails.setWhatsapp_no(binding.whatsappNumber.getText().toString());
        shopUpdateDetails.setEmail(binding.email.getText().toString());
        shopUpdateDetails.setMobile_no2(binding.secondNumber.getText().toString());
        shopUpdateDetails.setOwner_name(binding.ownerName.getText().toString());
        shopUpdateDetails.setOwner_mobile_no(binding.ownerNumber.getText().toString());
        shopUpdateDetails.setShop_contact_name(binding.contactName.getText().toString());
        shopUpdateDetails.setShop_contact_mobile_no(binding.contactNumber.getText().toString());
        shopUpdateDetails.setShop_category_id(getShopCategoryId(binding.shopCategoryId.getSelectedItem().toString()));
        shopUpdateDetails.setProvince_area_id(getProvinceId(binding.area.getSelectedItem().toString()));
        shopUpdateDetails.setCustomer_group_id(getCustomerGroupId(binding.customerGroup.getSelectedItem().toString()));

        return shopUpdateDetails;
    }

    private int getCustomerGroupId(String toString) {
        for (int i = 0; i < customerGroup.size(); i++) {
            if (customerGroup.get(i).getGroup_name().equals(toString)) {
                return customerGroup.get(i).getId();
            }
        }
        return 0;
    }

    private int getShopCategoryId(String categoryName) {
        for (int i = 0; i < shopCategories.size(); i++) {
            if (shopCategories.get(i).getCategory_name().equals(categoryName)) {
                return shopCategories.get(i).getId();
            }
        }
        return 0;
    }

    private int getProvinceId(String provinceName) {
        for (int i = 0; i < areaLists.size(); i++) {
            if (areaLists.get(i).getArea_name().equals(provinceName)) {
                return areaLists.get(i).getId();
            }
        }
        return 0;
    }

    private int getparentId(String toString) {
        for (int i = 0; i < parentCompany.size(); i++) {
            if (parentCompany.get(i).getShort_name().equals(toString)) {
                return parentCompany.get(i).getId();
            }
        }
        return 0;
    }

    private void initComponents() {
        addLogic = new AddLogic(this, this);
        editLogic = new EditLogic(this, this);
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
            ImageBitmap = photo;
            binding.shopImage.setImageBitmap(photo);
        } else if (requestCode == CAMERA_REQUEST_LOGO && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            LogoBitmap = photo;
            binding.shopLogo.setImageBitmap(photo);
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

    @Override
    public void addSuccessCallback(Boolean status, String Message) {

    }

    @Override
    public void countryCallback(Boolean status, String Message, List<countryList> countryList) {

    }

    @Override
    public void areaCallback(Boolean status, String Message, List<areaList> areaLists) {
        if (status) {
            this.areaLists = areaLists;
            this.areaLists.add(0, new areaList(0, "Select Area"));
            setAreaSpinner();
            Dialogues.dismiss();
            // addLogic.getShopCategory();
        } else {
            Toast.makeText(EditActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void currencyCallback(Boolean status, String Message, List<allCurrencies> allCurrencies) {

    }

    @Override
    public void shopCategoryCallBack(Boolean status, String Message, shopCategories shopCategories) {
        if (status) {
            this.shopCategories = shopCategories.getData();
            this.shopCategories.add(0, new categoryName(0, "Select Shop Category"));
            //   addLogic.getallParentCompanies();
            setShopCategorySpinner();

        } else {

            Toast.makeText(EditActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            Dialogues.dismiss();
        }
    }

    @Override
    public void parentCompanyCallBack(Boolean status, String Message, List<parentCompany> parentCompany) {
        if (status) {
            this.parentCompany = parentCompany;

            //    setAreaSpinner();
            //  setShopCategorySpinner();
            setCompanySpinner();
            //  addLogic.getAllArea();


        } else {
            addLogic.getAllArea();
            Toast.makeText(EditActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            Dialogues.dismiss();
        }
    }

    @Override
    public void customerGroupCallback(Boolean status, String Message, List<customerGroup> customerGroups) {
        if (status) {
            this.customerGroup = customerGroups;

            this.customerGroup.add(0, new customerGroup(4, "Shop Individuals"));
            setCustomerGroupSpinner();
            addLogic.getAllArea();


        } else {
            addLogic.getAllArea();
            Toast.makeText(EditActivity.this, "" + Message, Toast.LENGTH_SHORT).show();
            Dialogues.dismiss();
        }
    }

    private void setCustomerGroupSpinner() {
        Spinner spin = (Spinner) findViewById(R.id.customer_group);
        ArrayAdapter<customerGroup> adapter =
                new ArrayAdapter<customerGroup>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, customerGroup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);

        spin.setSelection(getCustomerGroupIndex());
    }

    private int getCustomerGroupIndex() {
        int result = 0;
        int data = IntentConstants.SHOP_DETAILS.getCustomer_group_id();
        try {
            for (int i = 0; i < customerGroup.size(); i++) {


                if (customerGroup.get(i).getId() == data) {

                    result = i;
                }
            }
        } catch (Exception e) {
        }


        return result;
    }


    private void setAreaSpinner() {

        Spinner spin = (Spinner) findViewById(R.id.area);
        ArrayAdapter<areaList> adapter =
                new ArrayAdapter<areaList>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, areaLists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);

        spin.setSelection(getAreaIndex());

    }

    private int getAreaIndex() {

        int result = 0;
        String area = IntentConstants.SHOP_DETAILS.getProvince_area_name();

        for (int i = 0; i < areaLists.size(); i++) {

            if (areaLists.get(i).getArea_name() != null)
                if (areaLists.get(i).getArea_name().equals(area)) {

                    result = i;
                }
        }
        return result;
    }

    private void setShopCategorySpinner() {

        Spinner spin = (Spinner) findViewById(R.id.shop_category_id);
        ArrayAdapter<categoryName> adapter =
                new ArrayAdapter<categoryName>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, shopCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);
        spin.setSelection(getShopCategoryIndex(IntentConstants.SHOP_DETAILS.shop_category_name));
    }

    private int getShopCategoryIndex(String shop_category_name) {
        int result = 0;


        for (int i = 0; i < shopCategories.size(); i++) {

            if (shopCategories.get(i).getCategory_name() != null)
                if (shopCategories.get(i).getCategory_name().equals(shop_category_name)) {

                    result = i;
                }
        }
        return result;
    }

    private void setCompanySpinner() {

        Spinner spin = (Spinner) findViewById(R.id.company);
        ArrayAdapter<parentCompany> adapter =
                new ArrayAdapter<parentCompany>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        parentCompany);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);

        spin.setSelection(getparentIndex());
    }

    private int getparentIndex() {
        int result = 0;
        int data = IntentConstants.SHOP_DETAILS.getParent_id();
        try {
            for (int i = 0; i < parentCompany.size(); i++) {


                if (parentCompany.get(i).getId() == data) {

                    result = i;
                }
            }
        } catch (Exception e) {
        }


        return result;
    }
}