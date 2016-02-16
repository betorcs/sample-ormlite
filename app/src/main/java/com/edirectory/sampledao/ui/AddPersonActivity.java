package com.edirectory.sampledao.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.edirectory.sampledao.R;
import com.edirectory.sampledao.dao.DatabaseHelper;
import com.edirectory.sampledao.databinding.ActivityAddPersonBinding;
import com.edirectory.sampledao.model.Person;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by betorcs on 2/16/16.
 */
public class AddPersonActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "person";
    private ActivityAddPersonBinding binding;

    private Person mPerson;
    private DatabaseHelper mDatabaseHelper;

    private EditText mNameView;
    private EditText mAgeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_person);

        mNameView = (EditText) findViewById(R.id.person_name);
        mAgeView = (EditText) findViewById(R.id.person_age);

        mDatabaseHelper = new DatabaseHelper(this);

        mPerson = getIntent().getParcelableExtra(EXTRA_PERSON);
        if (mPerson == null) {
            mPerson = new Person();
        }
        binding.setPerson(mPerson);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_person, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                savePerson();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePerson() {
        boolean success = false;
        try {

            mPerson.setName(mNameView.getText().toString());
            mPerson.setAge(Integer.valueOf(mAgeView.getText().toString()));

            Dao.CreateOrUpdateStatus status = mDatabaseHelper.getPersonDao()
                    .createOrUpdate(mPerson);
            if (status.getNumLinesChanged() > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (success) {
            setResult(RESULT_OK);
            finish();
        } else {
            Snackbar.make(binding.getRoot(), "Not saved!", Snackbar.LENGTH_SHORT).show();
        }
    }
}
