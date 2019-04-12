package e.matthew.earthquakeanalytics;
// Name                MATTHEW CARSON
// Student ID           S1507791
// Programme of Study   Computing
//
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<EarthQuake> equakes;
    private ArrayList<EarthQuake> eqaukeFull;
    private LayoutInflater lInfaltor;
    private OnEarthQuakeListener earth;

    RecyclerViewAdapter(Context context, ArrayList<EarthQuake> data, OnEarthQuakeListener onearthquake) {
        this.lInfaltor = LayoutInflater.from(context);
        this.equakes = data;
        eqaukeFull = new ArrayList<EarthQuake>(data);
        earth = onearthquake;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = lInfaltor.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view, earth);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EarthQuake equake = equakes.get(position);
        double mag = equake.getMagnitude();
        if (mag >= 2.0) {
            holder.maglayout.setBackgroundResource(R.drawable.orange);

        } else if (mag >= 1.0 && mag < 2.0) {

            holder.maglayout.setBackgroundResource(R.drawable.rounded_corner);

        } else if (mag >= 0.0 && mag < 1.0) {

            holder.maglayout.setBackgroundResource(R.drawable.purple);
        } else {

            holder.maglayout.setBackgroundResource(R.drawable.gold);

        }

        String depth = "Depth : " + Integer.toString(equake.getDepth()) + "km";
        String magnitude = "Magnitude : " + Double.toString(equake.getMagnitude());
        holder.eTitle.setText(equake.getLocation());
        holder.edepth.setText(depth);
        holder.emag.setText(magnitude);
        holder.edate.setText(equake.getDateTime());
    }

    @Override
    public int getItemCount() {
        return equakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView eTitle;
        TextView edepth;
        TextView emag;
        TextView edate;
        LinearLayout maglayout;

        OnEarthQuakeListener onearthlisen;
        ViewHolder(View itemView, OnEarthQuakeListener onearth) {
            super(itemView);
            eTitle = itemView.findViewById(R.id.title);
            edepth = itemView.findViewById(R.id.depth);
            emag = itemView.findViewById(R.id.mag);
            edate = itemView.findViewById(R.id.date);
            maglayout = itemView.findViewById(R.id.maglayout);
            itemView.setOnClickListener(this);
            onearthlisen = onearth;
        }

        @Override
        public void onClick(View v) {
            onearthlisen.onEarthQuakeClick(getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter() {
        return equakeFilter;
    }

    private Filter equakeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence query) {
            ArrayList<EarthQuake> filteredList = new ArrayList<EarthQuake>();
            if (query == null || query.length() == 0) {
                filteredList.addAll(eqaukeFull);
            } else {
                String filterquery = query.toString().toLowerCase().trim();
                for (EarthQuake equake : eqaukeFull) {
                    if (equake.getLocation().toLowerCase().contains(filterquery)) {
                        filteredList.add(equake);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            equakes.clear();
            equakes.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public interface OnEarthQuakeListener {
        void onEarthQuakeClick(int Position);
    }
}
