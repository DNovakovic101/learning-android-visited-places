package pmf.novak101.visitedplacesmapspractice.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pmf.novak101.visitedplacesmapspractice.R;

public class AdapterPlaces extends BaseAdapter {

    private Context context;
    private List<ListItemPlace> places;

    public AdapterPlaces(Context context, List<ListItemPlace> places) {
        this.context = context;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (View) inflater.inflate(
                    R.layout.list_item_place, null);
        }
        TextView address = convertView.findViewById(R.id.textViewAddress);
        address.setText(places.get(position).getAddress());
        return convertView;
    }
}
