package com.amerikati.introduccioncontraintlayout;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amerikati.introduccioncontraintlayout.db.entity.NotaEntity;

import java.util.List;

public class NuevoNotaDialogViewModel extends AndroidViewModel {

    private LiveData<List<NotaEntity>> allNotas;
    private NotaRepository notaRepository;

    public NuevoNotaDialogViewModel(Application application){
        super(application);

        notaRepository = new NotaRepository(application);
        allNotas = notaRepository.getAll();
    }

    public LiveData<List<NotaEntity>> getAllNotas(){
        return this.allNotas;
    }

    public void insertarNota(NotaEntity nuevaNotaEntity){
        notaRepository.insert(nuevaNotaEntity);
    }
}
