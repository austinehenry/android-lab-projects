package com.example.gps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    Button b1;

    LocationManager lm;
    LocationListener ll;

    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.t1);
        b1 = findViewById(R.id.b1);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        ll = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                t1.append("\nLatitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(getApplicationContext(), "Please enable GPS", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndStartLocation();
            }
        });
    }

    private void checkPermissionAndStartLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        } else {
            // Permission granted
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, ll);
        }
    }

    // Handle user's response to permission request
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermissionAndStartLocation(); // Retry now
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
