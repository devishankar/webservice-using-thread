package devishankar.github.com.devishankarlogitech.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.devishankar.devishankarlogitech.R;

import java.util.ArrayList;

import devishankar.github.com.devishankarlogitech.models.Device;


/**
 * @author Devishankar
 */
public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.ViewHolder> {

    private final ArrayList<Device> list;

    public DevicesListAdapter(ArrayList<Device> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Device place = this.list.get(position);
        holder.getTxtName().setText(place.getName());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
        }

        TextView getTxtName() {
            return txtName;
        }

    }
}
