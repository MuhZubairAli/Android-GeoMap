package pk.gov.pbs.geomap_project.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import egolabsapps.basicodemine.offlinemap.Interfaces.GeoPointListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import pk.gov.pbs.database.DatabaseUtils;
import pk.gov.pbs.geomap.LocationUtils;
import pk.gov.pbs.geomap.utils.CustomMapListener;
import pk.gov.pbs.geomap.utils.CustomMapUtils;
import pk.gov.pbs.geomap.views.CustomOfflineMapView;
import pk.gov.pbs.geomap_project.R;
import pk.gov.pbs.geomap_project.db.Repository;
import pk.gov.pbs.geomap_project.models.BlockAssignment;
import pk.gov.pbs.geomap_project.models.Locations;
import pk.gov.pbs.utils.CustomActivity;
import pk.gov.pbs.utils.DateTimeUtils;
import pk.gov.pbs.utils.FileManager;
import pk.gov.pbs.utils.SystemUtils;
import pk.gov.pbs.utils.location.ILocationChangeCallback;
import pk.gov.pbs.utils.location.LocationService;

public class GeoMapActivity extends CustomActivity implements CustomMapListener, GeoPointListener {
    private static final String TAG = "GeoMapActivity";
    private static boolean mAutoLocation = true;
    private final double mDefaultZoom = 16.;
    private int backClickCount = 0;

    private Repository mRepository;

    private AlertDialog mAddNoteDialogue, mAddUnitDialogue;
    private LinearLayout mAddNoteLayout, mAddUnitLayout;

    private ILocationChangeCallback mOnLocationChangedListener;
    private Runnable backButtonListener;

    private BlockAssignment mBlockAssignment;
    private CustomOfflineMapView mOfflineMapView;
    private IMapController mMapController;
    private CustomMapUtils mMapUtils;

    private ArrayList<Marker> mHouseholdMarkers;

    private Marker mMarkerCurrentLocation;
    private Location mCurrentLocation = new Location(LocationManager.GPS_PROVIDER);

    private Polygon mAccuracyCircle;
    private KmlDocument mKmlBlockBoundary;

    private FloatingActionButton btnLocate, btnAddUnit, btnAddNote, btnViewUnits, btnCompleteListing, btnExit;
    private TextView tvLat, tvLon, tvAlt, tvAccuracy, tvSRC, tvLocationTime, tvEB, tvAddress;

    private BroadcastReceiver GPS_PROVIDER_ACCESS;

    private String mBlockCode;
    private int blockBoundaryOverlayIndex = 0;
    private FileManager mFileManager;

    private static final String geoJsonDirectory = "GeoJson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_map);
        checkAllPermissions();
        initActivityElements();
        startLocationService();

        initialize();
    }

    private void initialize(){
        mFileManager = new FileManager(this);
        backButtonListener = ()-> {
            backClickCount = 0;
            mUXToolkit.showToast("Double press back button to go back");
        };

        mOfflineMapView = findViewById(R.id.map);
        mOfflineMapView.init(this, this);

        mRepository = new Repository(getApplication());

        File gj = mFileManager.getExternalPublicDirectory(geoJsonDirectory);
        if (gj != null && !gj.exists())
            if(!gj.mkdir())
                Log.d(TAG, "initialize: failed to create geo json directory'");
            else
                Log.d(TAG, "initialize: GeoJson direct created for block boundaries");

        GPS_PROVIDER_ACCESS = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase(LocationService.BROADCAST_RECEIVER_ACTION_LOCATION_CHANGED)) {
                    Log.d(TAG, "onReceive: Broadcast received! " + intent.getAction());
                    Location location = intent.getParcelableExtra(LocationService.BROADCAST_EXTRA_LOCATION_DATA);
                    mRepository.insert(new pk.gov.pbs.geomap_project.models.Location(location));

                    if (mAutoLocation) {
                        mCurrentLocation.set(location);
                        updateDisplayData();
                    }

                    if (
                            LocationUtils.isValidLocation(location)
                                    && !isFinishing()
                                    && !isDestroyed()
                                    && mOnLocationChangedListener != null
                    )
                        mOnLocationChangedListener.onLocationChange(location);

                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationService.BROADCAST_RECEIVER_ACTION_LOCATION_CHANGED);
        registerReceiver(GPS_PROVIDER_ACCESS, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapUtils != null) {
            mMapUtils.getMap().onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapUtils != null) {
            mMapUtils.getMap().onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(GPS_PROVIDER_ACCESS);
        stopLocationService();
    }

    @Override
    public void mapLoadSuccess(MapView mapView, CustomMapUtils mapUtils) {
        mMapUtils = mapUtils;
        mMapController = mapView.getController();
        mapUtils.setInitialZoom(mDefaultZoom);

        //Initializing map elements like, current location marker, accuracy circle, block boundary etc
        initMapElements(mapView);

        //Defining onLocationChangedListener
        //---------------------------------------------------------
        mOnLocationChangedListener = (Location mLocation) -> {
            if (!LocationUtils.isValidLocation(mLocation))
                return;

            GeoPoint gp = new GeoPoint(mLocation.getLatitude(), mLocation.getLongitude());
            if (mAutoLocation) {
                if (!mapView.getOverlays().contains(mMarkerCurrentLocation))
                    mapView.getOverlays().add(mMarkerCurrentLocation);

                if (!mapView.getOverlays().contains(mAccuracyCircle))
                    mapView.getOverlays().add(mAccuracyCircle);

                mMarkerCurrentLocation.setPosition(gp);
                mAccuracyCircle.setPoints(
                        Polygon.pointsAsCircle(gp, mLocation.getAccuracy())
                );

                mMapController.animateTo(gp);
                mapView.invalidate();
            }
        };

        verifyCurrentLocation(mOnLocationChangedListener);
    }

    @Override
    public void mapLoadFailed(String ex) {
        mUXToolkit.showAlertDialogue("Failed to load map: " + ex);
        Log.e("ex:", ex);
    }

    @Override
    public void onGeoPointRecieved(GeoPoint geoPoint) {
        if (mCurrentLocation == null)
            mCurrentLocation = new Location(LocationManager.GPS_PROVIDER);

        mCurrentLocation.setLatitude(geoPoint.getLatitude());
        mCurrentLocation.setLongitude(geoPoint.getLongitude());
        mCurrentLocation.setTime(SystemUtils.getUnixTs());
        //Todo: this should be based on current zoom level
        mCurrentLocation.setAccuracy(1);
        mCurrentLocation.setProvider(CustomOfflineMapView.LOCATION_PROVIDER_GEO_PICKER);
        updateDisplayData();
    }

    private void updateDisplayData(){
        tvLat.setText(
                Html.fromHtml(
                        getString(R.string.td_labeled_latitude, String.valueOf(mCurrentLocation.getLatitude()))
                )
        );
        tvLon.setText(
                Html.fromHtml(
                        getString(R.string.td_labeled_longitude, String.valueOf(mCurrentLocation.getLongitude()))
                )
        );
        tvAlt.setText(
                Html.fromHtml(
                        getString(R.string.td_labeled_altitude, String.valueOf(mCurrentLocation.getAltitude()))
                )
        );
        tvAccuracy.setText(
                Html.fromHtml(
                        getString(R.string.td_labeled_accuracy, String.valueOf(mCurrentLocation.getAccuracy()))
                )
        );
        tvSRC.setText(
                Html.fromHtml(
                        getString(
                                R.string.td_labeled_src,
                                String.valueOf(mCurrentLocation.getProvider())
                        )
                )
        );

        tvLocationTime.setText(
                Html.fromHtml(
                        getString(
                                R.string.td_labeled_time,
                                DateTimeUtils.formatDateTime("hh:mm:ss", (mCurrentLocation.getTime()/1000))
                        )
                )
        );

        Boolean in = isPointInsideBlockBoundary(new GeoPoint(mCurrentLocation));
        String sin = in == null ? "..." :
                in ? "Inside Boundary" : "Outside Boundary";

        tvAddress.setText(sin);
    }

    private void initMapElements(MapView mapView){
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mMarkerCurrentLocation = new Marker(mapView);
        mMarkerCurrentLocation.setIcon(AppCompatResources.getDrawable(this,R.drawable.ic_location_pin));
        mapView.getOverlays().add(mMarkerCurrentLocation);
        mapView.invalidate();

        //Attempt to add accuracy circle on map
        mAccuracyCircle = new Polygon(mapView);
        int fillColor = Color.parseColor("#663322DD");
        mAccuracyCircle.setFillColor(fillColor);
        mAccuracyCircle.setStrokeColor(Color.BLACK);
        mAccuracyCircle.setStrokeWidth(3);

        //Attempt to add scale bar
        final DisplayMetrics dm = this.getResources().getDisplayMetrics();
        ScaleBarOverlay sbo = new ScaleBarOverlay(mapView);
        sbo.setCentred(true);
        sbo.setScaleBarOffset(dm.widthPixels / 2, 20);
        mapView.getOverlays().add(sbo);

        //Adding Test GeoJSON layer on map
        //--------------------------------------------------------
        //offlineMapView.loadGeoJSON(DebugHelper.getGeoJsonISB());
        //mKmlBlockBoundary = mOfflineMapView.parseGeoJson(DebugHelper.getGeoJsonISB());
        //mOfflineMapView.addKmlDocumentOverlay(mKmlBlockBoundary, 0);

        //Setting locate button behaviour
        //---------------------------------------------------------
        btnLocate.setOnClickListener((view) -> {
            Location location = getLocationService().getLocation();
            if (!LocationUtils.isValidLocation(location))
                location = mCurrentLocation;
            else
                mCurrentLocation.set(location);

            mMapController.animateTo(new GeoPoint(location.getLatitude(), location.getLongitude()), mDefaultZoom, 1000L);
        });

        //Setting switching of auto and manual modes
        //-----------------------------------------------------------
//        btnLocate.setOnLongClickListener((view) -> {
//            mAutoLocation = !mAutoLocation;
//            mOfflineMapView.setEnabledAnimatedLocationPicker(!mAutoLocation, this, mMapUtils);
//            if (mAutoLocation) {
//                btnLocate.setImageDrawable(getDrawable(R.drawable.ic_current_location));
//                Location location = mLocationService.getLocation();
//                if (!LocationUtils.isValidLocation(location))
//                    location = mCurrentLocation;
//
//                mOnLocationChangedListener.onLocationChange(location);
//                mAccuracyCircle.setVisible(true);
//            } else {
//                btnLocate.setImageDrawable(getDrawable(R.drawable.ic_location_disabled));
//                mAccuracyCircle.setVisible(false);
//            }
//            mMarkerCurrentLocation.setVisible(mAutoLocation);
//            mMapUtils.getMap().invalidate();
//            return true;
//        });
    }

    private void initActivityElements() {
        btnLocate = findViewById(R.id.btnLocate);
//        btnAddUnit = findViewById(R.id.btnAddUnit);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnViewUnits = findViewById(R.id.btnViewUnits);
//        btnCompleteListing = findViewById(R.id.btnCompleteListing);
//        btnExit = findViewById(R.id.btnExit);

        tvEB = findViewById(R.id.tv_bc);
        tvAddress = findViewById(R.id.tv_address);
        tvLat = findViewById(R.id.tv_lat);
        tvLon = findViewById(R.id.tv_lon);
        tvAlt = findViewById(R.id.tv_alt);
        tvAccuracy = findViewById(R.id.tv_accuracy);
        tvSRC = findViewById(R.id.tv_src);
        tvLocationTime = findViewById(R.id.tv_location_time);

        updateDisplayData();
        btnAddNote.setOnClickListener(view -> {
            openNoteTakerDialogue();
        });
        btnViewUnits.setOnClickListener(view -> {
            changeBlockCode();
        });
//        btnExit.setOnClickListener(view -> {
//            mUXToolkit.showToast("Speed: " + mCurrentLocation.getSpeed());
//        });
//        btnCompleteListing.setOnClickListener(view -> {
//            mUXToolkit.showToast(mCurrentLocation.toString());
//        });
//        btnViewUnits.setOnClickListener(view -> {
//            mUXToolkit.showToast("Provider: " + mCurrentLocation.getProvider());
//        });
//        btnAddUnit.setOnClickListener(view -> {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                mUXToolkit.showToast("SpeedAccuracyMetersPerSecond: " + mCurrentLocation.getSpeedAccuracyMetersPerSecond());
//        });
    }

    private void changeBlockCode(){
        LinearLayout Layout = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_dialogue_change_block, null);
        AlertDialog mDialogue = getUXToolkit().getDialogBuilder()
                .setView(Layout)
                .setCancelable(false)
                .setPositiveButton(
                        "Set Block"
                        , (dialog, which) -> {
                            EditText kbi = (EditText) Layout.findViewById(R.id.kbi);
                            String noteText = kbi.getText().toString();
                            if (noteText.length() > 0) {
                                mBlockCode = noteText;
                                tvEB.setText(mBlockCode);

                                File file = mFileManager.getExternalPublicDirectory(geoJsonDirectory);
                                if (file.exists()) {
                                    File boundaryFile = new File(file, mBlockCode+".json");

                                    if (!boundaryFile.exists())
                                        boundaryFile = new File(file, mBlockCode);

                                    if (boundaryFile.exists()) {
                                        try {
                                            mKmlBlockBoundary = mOfflineMapView.parseGeoJson(mFileManager.readFile(boundaryFile));

                                            if (blockBoundaryOverlayIndex > 0) {
                                                //mUXToolkit.showToast("Valid GeoJSON found for selected block which is plotted on the map");
                                                mOfflineMapView.getMapUtils().getMap().getOverlayManager().remove(
                                                        mOfflineMapView.getMapUtils().getMap().getOverlayManager().size() - 1
                                                );
                                                mOfflineMapView.getMapUtils().getMap().invalidate();
                                            }

                                            blockBoundaryOverlayIndex = mOfflineMapView.getMapUtils().getMap().getOverlayManager().size();
                                            mOfflineMapView.addKmlDocumentOverlay(
                                                    mKmlBlockBoundary,
                                                    blockBoundaryOverlayIndex
                                            );
                                            updateDisplayData();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        mUXToolkit.showToast("GeoJSON for selected block is not found");
                                        mKmlBlockBoundary = null;
                                    }
                                }else
                                    file.mkdir();
                            }
                            ensureFullScreen();
                        }
                )
                .setNegativeButton(
                        "Cancel"
                        , (dialog, id) -> {
                            ensureFullScreen();
                        })
                .create();

        mDialogue.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        ;
        Layout.requestFocus();
        mDialogue.show();
    }

    private void openNoteTakerDialogue(){
        if (mAddNoteDialogue == null) {
            mAddNoteLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_dialogue_alert_input_et, null);
            mAddNoteDialogue = getUXToolkit().getDialogBuilder()
                    .setView(mAddNoteLayout)
                    .setCancelable(false)
                    .setPositiveButton(
                            "Save"
                            , (dialog, which) -> {
                                EditText kbi = (EditText) mAddNoteLayout.findViewById(R.id.kbi);
                                String noteText = kbi.getText().toString();
                                if (noteText.length() > 0) {

                                    Locations note = new Locations(
                                            mBlockCode,
                                            noteText,
                                            mCurrentLocation.getLongitude(),
                                            mCurrentLocation.getLatitude(),
                                            mCurrentLocation.getAltitude(),
                                            mCurrentLocation.getAccuracy(),
                                            mCurrentLocation.getProvider(),
                                            mCurrentLocation.getTime(),
                                            isPointInsideBlockBoundary(new GeoPoint(mCurrentLocation)),
                                            ((EditText) mAddNoteLayout.findViewById(R.id.extLat)).getText().toString(),
                                            ((EditText) mAddNoteLayout.findViewById(R.id.extLon)).getText().toString()
                                    );
                                    Long id = DatabaseUtils.getFutureValue(mRepository.insert(note));
                                    kbi.setText("");
                                    kbi.clearFocus();
                                    if (id != null && id > 0)
                                        mAddNoteLayout.postDelayed(
                                                () -> {
                                                    mUXToolkit.showToast("New location record added!");
                                                }, 300
                                        );
                                }
                                ensureFullScreen();
                            }
                    )
                    .setNegativeButton(
                            "Cancel"
                            , (dialog, id) -> {
                                EditText kbi = (EditText) mAddNoteLayout.findViewById(R.id.kbi);
                                kbi.setText("");
                                kbi.clearFocus();
                                ensureFullScreen();
                            })
                    .create();

            mAddNoteDialogue.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            ;
        }
        mAddNoteLayout.requestFocus();
        mAddNoteDialogue.show();
    }

    private void ensureFullScreen(){
        tvEB.post(this::hideSystemControls);
    }
    /**
     * This function is only use able if manual location picker is enabled
     * because it depends on accuracy circle polygon
     * @param p GeoPoint to be tested
     * @return true if given point is inside accuracy circle
     */
    private boolean isPointInsideAccuracyCircle(GeoPoint p){
        if (mAccuracyCircle != null) {
            return LocationUtils.isPointInPolygon(p, mAccuracyCircle.getPoints());
        }
        return true;
    }

    /**
     *
     * @param p geopoint to be tested
     * @return if block boundary found then checks if point inside, else always returns true
     */
    private Boolean isPointInsideBlockBoundary(GeoPoint p){
        if (mKmlBlockBoundary != null) {
            ArrayList<KmlFeature> bounds = mKmlBlockBoundary.mKmlRoot.mItems;
            for (int i = 0; i < bounds.size(); i++) {
                ArrayList<GeoPoint> polygons = ((KmlPlacemark) bounds.get(i)).mGeometry.mCoordinates;
                if (LocationUtils.isPointInPolygon(p, polygons)) {
                    Log.d(TAG, "Point found inside " + bounds.get(i).mExtendedData.get("desc"));
                    return true;
                }
            }
            return false;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (++backClickCount == 2) {
            mOfflineMapView.post(() -> {
                mOfflineMapView.getHandler()
                        .removeCallbacks(backButtonListener);
                finish();
            });
        } else {
            mOfflineMapView.getHandler()
                    .postDelayed(backButtonListener, 500);
        }

    }
}