package e.matthew.earthquakeanalytics;
// Name                MATTHEW CARSON
// Student ID           S1507791
// Programme of Study   Computing
//
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class Statistics extends Fragment  implements Statistics_Adapter.OnEarthQuakeListener{
    ArrayList<EarthQuake> equakes;
    ArrayList<EarthQuake> stats;
    Statistics_Adapter statadpat;
    final Bundle bundl = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.statistics, container, false);
        Bundle bundle = getArguments();

        equakes = bundle.getParcelableArrayList("equake");
        stats = new ArrayList<EarthQuake>();
        largestMagnitude(equakes);
        getdeepest(equakes);
        getshallowest(equakes);
        mostWesternly(equakes);
        getmosteasternly(equakes);
        smallestMagnitude(equakes);
        getmostnorthern(equakes);
        getmostsouthern(equakes);

        RecyclerView recyclerView = inf.findViewById(R.id.rvEQ);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        statadpat = new Statistics_Adapter(getContext(),stats, this);
        recyclerView.setAdapter(statadpat);

        return inf;
    }

    public void largestMagnitude (ArrayList<EarthQuake> equake) {
        EarthQuake largest = null;
        double max = 0.0;
        if (equake != null) {
            for (EarthQuake o : equake) {
                if (o.getMagnitude() > max) {
                    max = o.getMagnitude();
                   largest = o;
                }
            }
        }
         largest.setStat("Largest");
        stats.add(largest);
    }

    public void smallestMagnitude (ArrayList<EarthQuake> equake) {
        EarthQuake smallest = null;
        double max = 0.0;
        if (equake != null) {
            for (EarthQuake o : equake) {
                if (o.getMagnitude() < max) {
                    max = o.getMagnitude();
                    smallest = o;
                }
            }
        }
        smallest.setStat("Smallest");
        stats.add(smallest);
    }

   public void mostWesternly(ArrayList<EarthQuake> equake) {
       EarthQuake west = null;
       double max = 0.0;
       if (equake != null) {
           for (EarthQuake o : equake) {
               if (o.getGelong() < max) {
                   max = o.getGelong();
                   west = o;
               }
           }
       }
       west.setStat("Most Westernly");
       stats.add(west);
   }

   public void getmosteasternly(ArrayList<EarthQuake> equakes) {
       EarthQuake east = null;
       double max = 0.0;
       if (equakes != null) {
           for (EarthQuake o : equakes) {
               if (o.getGelong() > max) {

                   max = o.getGelong();
                   east = o;
               }
           }
       }
       east.setStat("Most Easterly");
       stats.add(east);
   }

    public void getmostnorthern(ArrayList<EarthQuake> equakes) {
        EarthQuake north = null;
        double max = 0.0;
        if (equakes != null) {
            for (EarthQuake o : equakes) {
                if (o.getGeolat() > max) {

                    max = o.getGeolat();
                    north = o;
                }
            }
        }
        north.setStat("Most Northern");
        stats.add(north);
    }

    public void getmostsouthern(ArrayList<EarthQuake> equakes) {
        EarthQuake south = null;
        double max = 100.0;
        if (equakes != null) {
            for (EarthQuake o : equakes) {
                if (o.getGeolat() < max) {

                    max = o.getGeolat();
                    south = o;
                }
            }
        }
        south.setStat("Most Southern");
        stats.add(south);
    }

    public void getdeepest(ArrayList<EarthQuake> equake) {
        EarthQuake deepest = null;
        int max = 0;
        if (equake != null) {
            for (EarthQuake o : equake) {
                if (o.getDepth() > max) {
                    max = o.getDepth();
                    deepest = o;
                }
            }
        }
        deepest.setStat("Deepest");
        stats.add(deepest);
}

    public void getshallowest(ArrayList<EarthQuake> equake) {

        EarthQuake shallow= null;
        int max = 100;
        if (equake != null) {
            for (EarthQuake o : equake) {
                if (o.getDepth() < max) {
                    max = o.getDepth();
                    shallow = o;
                }
            }
        }
        shallow.setStat("Shallowest");
        stats.add(shallow);
    }

    @Override
    public void onEarthQuakeClick(int Position) {
        EarthQuake earthquake = stats.get(Position);
        Fragment adv = new EarthquakeView();
        bundl.putParcelable("quake", earthquake);
        adv.setArguments(bundl);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, adv, null)
                .addToBackStack(null)
                .commit();
    }
}