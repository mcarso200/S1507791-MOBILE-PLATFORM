package e.matthew.earthquakeanalytics;
// Name                MATTHEW CARSON
// Student ID           S1507791
// Programme of Study   Computing
//
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class Home_Fragment extends Fragment implements RecyclerViewAdapter.OnEarthQuakeListener  {

    RecyclerViewAdapter adapter;
    ArrayList<EarthQuake> earthquakes;
    final Bundle bundl = new Bundle();
    @Nullable
    @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View inf=  inflater.inflate(R.layout.home, container, false);
            Bundle bundle = getArguments();
            earthquakes = bundle.getParcelableArrayList("equake");

            if (earthquakes != null) {
                RecyclerView recyclerView = inf.findViewById(R.id.rvEQ);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new RecyclerViewAdapter(getContext(), earthquakes, this);
                recyclerView.setAdapter(adapter);

                for (EarthQuake o : earthquakes) {
                    Log.e("MyTag", Double.toString(o.getGelong()));
                }
            }
                return inf;
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) searchItem.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

@Override
public void onEarthQuakeClick(int position) {
             EarthQuake earthquake = earthquakes.get(position);
             Fragment adv = new EarthquakeView();
             bundl.putParcelable("quake", earthquake);
             adv.setArguments(bundl);
             getActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, adv, null)
            .addToBackStack(null)
            .commit();
        }
}



