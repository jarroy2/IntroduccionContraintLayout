package com.amerikati.introduccioncontraintlayout;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.amerikati.introduccioncontraintlayout.db.entity.NotaEntity;

public class NuevoNotaDialogFragment extends DialogFragment {


    public static NuevoNotaDialogFragment newInstance() {
        return new NuevoNotaDialogFragment();
    }
    private View view;
    private EditText etTitulo, etContenido;
    private RadioGroup rgColor;
    private Switch swNotaFavorita;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nuevo_nota_dialog_fragment, container, false);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Nueva nota")
                .setMessage("Introduzca los datos de la nueva nota")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String titulo = etTitulo.getText().toString();
                        String contenido = etContenido.getText().toString();
                        String color = "azul";
                        switch (rgColor.getCheckedRadioButtonId()){
                            case R.id.radioButtonColorRojo:
                                color = "rojo";
                                break;
                            case R.id.radioButtonColorAzul:
                                color = "azul";
                                break;
                        }

                        boolean esFavorita = swNotaFavorita.isChecked();

                        NuevoNotaDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NuevoNotaDialogViewModel.class);
                        mViewModel.insertarNota(new NotaEntity(titulo, contenido, esFavorita, color));

                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.nuevo_nota_dialog_fragment, null);

        etTitulo = view.findViewById(R.id.editTextTitulo);
        etContenido = view.findViewById(R.id.editTextContenido);
        rgColor = view.findViewById(R.id.radioGroupColor);
        swNotaFavorita = view.findViewById(R.id.switchNotaFavorita);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
