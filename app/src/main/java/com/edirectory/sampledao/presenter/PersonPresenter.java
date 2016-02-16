package com.edirectory.sampledao.presenter;

import android.content.Context;

import com.edirectory.sampledao.model.Person;
import com.edirectory.sampledao.view.PersonView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

/**
 * Created by betorcs on 2/15/16.
 */
public class PersonPresenter extends MvpBasePresenter<PersonView> {

    private PeopleTask mTask;

    private void cancelObtainPeopleTaskIfRunning() {
        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    public void obtainPeople(Context context) {
        cancelObtainPeopleTaskIfRunning();

        mTask = new PeopleTask(context, new PeopleTask.ObtainPeopleListener() {
            @Override
            public void onObtainPeople(List<Person> people) {
                getView().showPeople(people);
            }

            @Override
            public void onError(Throwable t) {
                getView().showError(t);
            }
        });
        mTask.execute();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            cancelObtainPeopleTaskIfRunning();
        }
    }
}
