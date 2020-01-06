package com.example.rodofroll.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.R;

public class RecyclerViewFragment extends Fragment {

    Adapter adapter= null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ficha, container, false);
      /*  RecyclerView recyclerView = view.findViewById(R.id.recycler);

        try {
            adapter = new Adapter(getContext());



        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));*/

        return view;
    }



}
