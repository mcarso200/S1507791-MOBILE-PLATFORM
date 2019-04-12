package e.matthew.earthquakeanalytics;
// Name                MATTHEW CARSON
// Student ID           S1507791
// Programme of Study   Computing
//
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EarthquakeView  extends Fragment implements OnMapReadyCallback {

    View inf;
    TextView eTitle;
    TextView edepth;
    TextView emag;
    TextView edate;
    TextView elat;
    TextView elon;
    LinearLayout maglayout;
    SupportMapFragment mapfrag;
    EarthQuake equake;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inf = inflater.inflate(R.layout.earthquakeview, container, false);
        mapfrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapfrag == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapfrag = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapfrag).commit();
        }

        Bundle bundle = getArguments();
        equake = bundle.getParcelable("quake");
        eTitle = inf.findViewById(R.id.title);
        edepth = inf.findViewById(R.id.depth);
        emag = inf.findViewById(R.id.mag);
        edate = inf.findViewById(R.id.date);
        elat = inf.findViewById(R.id.Lat);
        elon = inf.findViewById(R.id.Lon);
        maglayout = inf.findViewById(R.id.maglayout);

        double mag = equake.getMagnitude();
        if (mag >= 2.0) {
          maglayout.setBackgroundResource(R.drawable.orange);
        } else if (mag >= 1.0 && mag < 2.0) {
            maglayout.setBackgroundResource(R.drawable.rounded_corner);
        } else if (mag >= 0.0 && mag < 1.0) {
           maglayout.setBackgroundResource(R.drawable.purple);
        } else {
            maglayout.setBackgroundResource(R.drawable.gold);
        }

        String depth = "Depth : " + Integer.toString(equake.getDepth()) + "km";
        String magnitude = "Magnitude : " + Double.toString(equake.getMagnitude());
        String Lat = "Latitude : " + Double.toString(equake.getGeolat());
        String Lon = "Longitude: " + Double.toString(equake.getGelong());
        eTitle.setText(equake.getLocation());
        edepth.setText(depth);
        emag.setText(magnitude);
        edate.setText(equake.getDateTime());
        elat.setText(Lat);
        elon.setText(Lon);
        mapfrag.getMapAsync(this);

        return inf;
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float col = 0;

        double mag = equake.getMagnitude();
        if (mag >= 2.0) {
            col = BitmapDescriptorFactory.HUE_ORANGE;
        } else if (mag >= 1.0 && mag < 2.0) {
            col = BitmapDescriptorFactory.HUE_GREEN;
        } else if (mag >= 0.0 && mag < 1.0) {
            col = BitmapDescriptorFactory.HUE_VIOLET;
        } else {
            col = BitmapDescriptorFactory.HUE_YELLOW;
        }

        String[] parsed = equake.getDateTime().split(",");

        String snip = parsed[1] + " M : " + equake.getMagnitude() + " D : " + equake.getDepth() + "Km";

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(equake.getGeolat(), equake.getGelong()))
                .title(equake.getLocation())
                .snippet(equake.getDateTime())
                .snippet(snip)
                .icon(BitmapDescriptorFactory.defaultMarker(col)));

        LatLng LL = new LatLng(equake.getGeolat(),equake.getGelong());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LL,15));

        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null);

        }
}


