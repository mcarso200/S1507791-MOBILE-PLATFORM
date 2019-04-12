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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class Map extends Fragment implements OnMapReadyCallback {

    ArrayList<EarthQuake> equakes;
    SupportMapFragment mapfrag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.map, container, false);
        mapfrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapfrag == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapfrag = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapfrag).commit();
        }
        Bundle bundle = getArguments();

        equakes = bundle.getParcelableArrayList("equake");
        mapfrag.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float col = 0;
        if (equakes != null) {
            for (EarthQuake o : equakes) {

                double mag = o.getMagnitude();
                if (mag >= 2.0) {
                    col = BitmapDescriptorFactory.HUE_ORANGE;
                } else if (mag >= 1.0 && mag < 2.0) {
                    col = BitmapDescriptorFactory.HUE_GREEN;
                } else if (mag >= 0.0 && mag < 1.0) {
                    col = BitmapDescriptorFactory.HUE_VIOLET;
                } else {
                    col = BitmapDescriptorFactory.HUE_YELLOW;
                }

                String[] parsed = o.getDateTime().split(",");
                String snip = parsed[1] + " M : " + o.getMagnitude() + " D : " + o.getDepth() + "Km";

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(o.getGeolat(), o.getGelong()))
                        .title(o.getLocation())
                        .snippet(o.getDateTime())
                        .snippet(snip)
                        .icon(BitmapDescriptorFactory.defaultMarker(col)));
            }
            // Scientifically defined centre of the uk
            LatLng LL = new LatLng(54.00366, -2.547855);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LL,15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(4), 2000, null);
        }
    }
}