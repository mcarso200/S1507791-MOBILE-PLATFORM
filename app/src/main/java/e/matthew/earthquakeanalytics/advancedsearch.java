package e.matthew.earthquakeanalytics;
// Name                MATTHEW CARSON
// Student ID           S1507791
// Programme of Study   Computing
//
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class advancedsearch  extends Fragment implements RecyclerViewAdapter.OnEarthQuakeListener  {
    RecyclerViewAdapter adapter;
    Button btn;
    Button btn2;
    Button btn3;
    View inf;
    TextView error;
    ArrayList<EarthQuake> eq;
    static Date startdate;
    static  Date endDate;
    static Boolean selected;
    final Bundle bundl = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                inf = inflater.inflate(R.layout.advanced_search, container, false);
                Bundle bundle = getArguments();
                final ArrayList<EarthQuake> earthquakes = bundle.getParcelableArrayList("equake");
                selected = false;
                error = (TextView) inf.findViewById(R.id.error);
                btn = (Button) inf.findViewById(R.id.start);
                btn2 = (Button) inf.findViewById(R.id.enddate);
                btn3 = (Button) inf.findViewById(R.id.resultys);
                btn.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatepickerFragment();
                datePicker.show(getFragmentManager(), "date picker");
            }
     });

                btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatepickerFragment2();
                datePicker.show(getFragmentManager(), "date picker2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                Date date = null;
                eq = new ArrayList<EarthQuake>();
                for (EarthQuake e : earthquakes) {
                    SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy ");
                    try {
                        date = formatter.parse(e.getDateTime());
                    } catch (ParseException e1) {
                        Log.e("ERROR", "Parse Exception");
                    }

                    if (selected == true) {
                        if (startdate != null && endDate != null) {
                            if (startdate.after(endDate)) {
                                error.setText("endDate occurs before start date!");
                            }
                            if (date.after(startdate) && date.before(endDate)) {
                                eq.add(e);
                            }
                        }
                            if (endDate == null) {
                                if(date.after(startdate)) {
                                    eq.add(e);
                                }
                            }
                            if (startdate == null) {
                               if(date.before(endDate)) {
                                   eq.add(e);
                               }
                            }
                    } else {
                        error.setText("No dates Selected");
                    }
            }
                if (eq != null) {
                getRec();
                }
                if (eq == null) {
                    error.setText("NO Earthqaukes avaible for this range");
                }
            }
        });
        return inf;
    }

  public void getRec() {


      RecyclerView recyclerView = inf.findViewById(R.id.recre);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      adapter = new RecyclerViewAdapter(getContext(), eq,this );
      recyclerView.setAdapter(adapter);
  }

    static void setstart(Date start) {
        startdate = start;
    }

    static void setend(Date end) {
        endDate = end;
    }

    static void setselected(Boolean bool) {
        selected = bool;
    }
@Override
    public void onEarthQuakeClick(int position) {
        EarthQuake earthquake = eq.get(position);
        Fragment adv = new EarthquakeView();
        bundl.putParcelable("quake", earthquake);
        adv.setArguments(bundl);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, adv, null)
                .addToBackStack(null)
                .commit();
    }
}
