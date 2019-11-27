package com.amerikati.introduccioncontraintlayout.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amerikati.introduccioncontraintlayout.NuevoNotaDialogViewModel;
import com.amerikati.introduccioncontraintlayout.R;
import com.amerikati.introduccioncontraintlayout.db.entity.NotaEntity;

import java.util.ArrayList;
import java.util.List;

public class NotaFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;

    private List<NotaEntity> notasList;
    private MyNotaRecyclerViewAdapter adapterNota;

    private NuevoNotaDialogViewModel notaViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orie  ntation changes).
     */
    public NotaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NotaFragment newInstance(int columnCount) {
        NotaFragment fragment = new NotaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels/ displayMetrics.density;
                int numeroColumnas = (int) (dpWidth/180);

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numeroColumnas, StaggeredGridLayoutManager.VERTICAL));
            }

            notasList = new ArrayList<>();
            /*notasList.add(new NotaEntity("Lista de compra", "comprar pan tostado", true, android.R.color.holo_blue_light));
            notasList.add(new NotaEntity("Recordar", "He parqueado el carro en la calle Murillo con la carrera 43, no olvidarme de pagar el parquimetro", false, android.R.color.holo_green_light));
            notasList.add(new NotaEntity("Cumpleaños (fiesta)", "La Secretaría de Tránsito y Seguridad Vial explicó que se deben a labores de mantenimiento de la Triple A y una caminata por en conmemoración del Día Mundial de la Prevención de la Diabetes.", true, android.R.color.holo_orange_light));

            notasList.add(new NotaEntity("Lista de compra", "comprar pan tostado", true, android.R.color.holo_blue_light));
            notasList.add(new NotaEntity("Recordar", "He parqueado el carro en la calle Murillo con la carrera 43, no olvidarme de pagar el parquimetro", false, android.R.color.holo_green_light));
            notasList.add(new NotaEntity("Cumpleaños (fiesta)", "La Secretaría de Tránsito y Seguridad Vial explicó que se deben a labores de mantenimiento de la Triple A y una caminata por en conmemoración del Día Mundial de la Prevención de la Diabetes.", true, android.R.color.holo_orange_light));
*/
            adapterNota  = new MyNotaRecyclerViewAdapter(notasList, getActivity());
            recyclerView.setAdapter(adapterNota);

            lanzarViewModel();

        }
        return view;
    }

    private void lanzarViewModel(){
        notaViewModel = ViewModelProviders.of(getActivity()).get(NuevoNotaDialogViewModel.class);
        notaViewModel.getAllNotas().observe(getActivity(), new Observer<List<NotaEntity>>() {
            @Override
            public void onChanged(List<NotaEntity> notaEntities) {
                adapterNota.setNuevasNotas(notaEntities);
            }
        });
    }


}
