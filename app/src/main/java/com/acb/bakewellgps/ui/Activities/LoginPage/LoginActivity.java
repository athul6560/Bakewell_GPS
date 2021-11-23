package com.acb.bakewellgps.ui.Activities.LoginPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.acb.bakewellgps.R;
import com.acb.bakewellgps.SharedPref.SharedData;
import com.acb.bakewellgps.Utils.Dialogues;
import com.acb.bakewellgps.databinding.ActivityLoginBinding;
import com.acb.bakewellgps.ui.Activities.DashboardPage.Dashboard;

import java.util.List;


import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class LoginActivity extends AppCompatActivity implements ILoginLogic.view, EasyPermissions.PermissionCallbacks {
    private ActivityLoginBinding binding;
    private static final int LOCATION_PERMISSION_CODE = 100;
    private LoginLogic loginLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SharedData.LoginCheck(this)){
            startActivity(new Intent(this,Dashboard.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        requestPermissions();

        initComponents();
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginLogic.loginFunction(binding.usernameEdittext.getText().toString(), binding.passwordEdittext.getText().toString());

            }
        });
    }

    private void initComponents() {
        loginLogic = new LoginLogic(this, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        requestPermissions();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void loginResponse(String message, Boolean status) {
        if (status) {
            SharedData.LogedStatusUpdate(LoginActivity.this,true);
            startActivity(new Intent(LoginActivity.this, Dashboard.class));
            finish();
        } else
            Dialogues.showWarning(LoginActivity.this, "Login Failed", message);
    }

    @Override
    public void requestPermissions() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing

            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    LOCATION_PERMISSION_CODE, perms);
        }
    }
}