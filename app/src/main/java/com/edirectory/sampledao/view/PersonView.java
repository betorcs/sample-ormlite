package com.edirectory.sampledao.view;

import com.edirectory.sampledao.model.Person;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by betorcs on 2/15/16.
 */
public interface PersonView extends MvpView {

    void showPeople(List<Person> people);
    void showError(Throwable e);

}
