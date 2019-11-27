package com.amerikati.introduccioncontraintlayout;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.amerikati.introduccioncontraintlayout.db.NotaRoomDatabase;
import com.amerikati.introduccioncontraintlayout.db.dao.NotaDao;
import com.amerikati.introduccioncontraintlayout.db.entity.NotaEntity;

import java.util.List;

public class NotaRepository {

    private NotaDao notaDao;
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> allNotasFavoritas;

    public NotaRepository(Application application){
        NotaRoomDatabase db = NotaRoomDatabase.getDataBase(application);
        notaDao = db.notaDao();
        this.allNotas = notaDao.getAll();
        this.allNotasFavoritas = notaDao.getAllFavorita();
    }

    public LiveData<List<NotaEntity>> getAll() {
       return allNotas;
    }

    public LiveData<List<NotaEntity>> getAllFavoritas() {
        return allNotasFavoritas;
    }

    public void insert(NotaEntity nota){
        new insertAsyncTask(notaDao).execute(nota);
    }

    private static class insertAsyncTask extends AsyncTask<NotaEntity, Void, Void>{
        private NotaDao notaDaoAsyncTask;

        insertAsyncTask(NotaDao dao){
            notaDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsyncTask.insert(notaEntities[0]);
            return null;
        }
    }
}
