package com.edirectory.sampledao.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.edirectory.sampledao.dao.DatabaseHelper;
import com.edirectory.sampledao.model.Person;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by betorcs on 2/15/16.
 */
public class PeopleTask extends AsyncTask<Void,Void, Object> {

    private Dao<Person, Long> mPersonDao;
    private final DatabaseHelper mHelper;
    private final ObtainPeopleListener mListener;

    public PeopleTask(Context context, ObtainPeopleListener listener) {
        mListener = listener;
        mHelper = new DatabaseHelper(context);
        try {
            this.mPersonDao = mHelper.getPersonDao();
        } catch (SQLException e) {
            mListener.onError(e);
        }
    }


    public interface ObtainPeopleListener {
        void onObtainPeople(List<Person> people);
        void onError(Throwable t);
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return mPersonDao.queryForAll();
        } catch (SQLException e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o instanceof Throwable) {
            mListener.onError((Throwable) o);
        } else {
            mListener.onObtainPeople((List<Person>) o);
        }
    }
}
