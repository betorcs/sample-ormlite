package com.edirectory.sampledao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edirectory.sampledao.model.Person;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by betorcs on 2/15/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "sampleDAO.db";
    private static final int DB_VERSION = 1;

    private Dao<Person, Long> mPersonDao;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Person.class);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate: Can't create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    public Dao<Person, Long> getPersonDao() throws SQLException {
        if (mPersonDao == null) {
            mPersonDao = getDao(Person.class);
        }
        return mPersonDao;
    }

    @Override
    public void close() {
        super.close();
        mPersonDao = null;
    }
}
