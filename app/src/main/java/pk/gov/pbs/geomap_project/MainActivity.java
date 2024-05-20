package pk.gov.pbs.geomap_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pk.gov.pbs.geomap.SampleGeoMapActivity;
import pk.gov.pbs.geomap_project.activities.GeoMapActivity;
import pk.gov.pbs.utils.CustomActivity;
import pk.gov.pbs.utils.location.LocationService;

public class MainActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        checkAllPermissions();

        findViewById(R.id.btnGpsToolkit).setOnClickListener(view -> {
            startActivity(new Intent(this, GeoMapActivity.class));
        });

        findViewById(R.id.btnActivityOne).setOnClickListener(view -> {
            startActivity(new Intent(this, SampleGeoMapActivity.class));
        });

        findViewById(R.id.btnActivityTwo).setOnClickListener(view -> {
            Toast.makeText(this, "Activity Two", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        startService(new Intent(this, LocationService.class));
    }
}