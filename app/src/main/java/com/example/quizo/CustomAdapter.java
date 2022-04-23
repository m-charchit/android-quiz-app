package com.example.quizo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<String[]> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView pscoreView;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            nameView = view.findViewById(R.id.nameView);
            pscoreView = view.findViewById(R.id.pscoreView);
        }

        public TextView getNameView() {
            return nameView;
        }
        public TextView getpscoreView() {
            return pscoreView;
        }
    }

    // Initialize the dataset of the Adapter.
    public CustomAdapter(ArrayList<String[]> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_adapter, viewGroup, false);

        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getNameView().setText(localDataSet.get(position)[0]);
        viewHolder.getpscoreView().setText(localDataSet.get(position)[1]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

