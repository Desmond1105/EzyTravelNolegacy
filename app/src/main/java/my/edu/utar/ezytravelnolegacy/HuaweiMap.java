package my.edu.utar.ezytravelnolegacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;

public class HuaweiMap extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapViewDemoActivity";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int REQUEST_CODE = 100;
    private static final LatLng LAT_LNG = new LatLng(31.2304,121.4737);
    private HuaweiMap hmap;
    private MapView mMapView;
    private Marker mMarker;
    private Circle mCircle;
    private static final String[] RUNTIME_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
    private android.content.pm.PackageManager PackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huawei_map);

        //check for permission
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i("Nearby", "sdk < 28 Q");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, strings, 1); }
        }
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, "android.permission.ACCESS_BACKGROUND_LOCATION"};
                ActivityCompat.requestPermissions(this, strings, 2); }
        }

        //get mapView by layout view
        mMapView =findViewById(R.id.mapView);
        Bundle mapViewBundle =null;
        if(savedInstanceState!=null){
            mapViewBundle =savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);

        //get map by async
        mMapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(com.huawei.hms.maps.HuaweiMap huaweiMap) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}