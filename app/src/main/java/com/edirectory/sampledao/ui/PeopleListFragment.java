package com.edirectory.sampledao.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edirectory.sampledao.R;
import com.edirectory.sampledao.databinding.PersonItemViewBinding;
import com.edirectory.sampledao.model.Person;
import com.edirectory.sampledao.presenter.PersonPresenter;
import com.edirectory.sampledao.view.PersonView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by betorcs on 2/15/16.
 */
public class PeopleListFragment extends MvpFragment<PersonView, PersonPresenter> implements
        PersonView, PersonAdapter.OnPersonClickListener {

    private static final int REQUEST_PERSON_CHANGES = 101;
    private final PersonAdapter mAdapter = new PersonAdapter(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_people_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPersonClicked(null);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERSON_CHANGES) {
            if (resultCode == Activity.RESULT_OK) {
                loadPeople();
            }
        }
    }

    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPeople();
    }

    private void loadPeople() {
        getPresenter().obtainPeople(getContext());
    }

    @Override
    public void showPeople(List<Person> people) {
        mAdapter.update(people);
    }

    @Override
    public void showError(Throwable e) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.app_name)
                .setMessage("Error: " + e.getMessage())
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void onPersonClicked(Person person) {
        Intent intent = new Intent(getContext(), AddPersonActivity.class);
        intent.putExtra(AddPersonActivity.EXTRA_PERSON, person);
        startActivityForResult(intent, REQUEST_PERSON_CHANGES);
    }

}

class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    private final List<Person> mPeople = new ArrayList<>();
    private final OnPersonClickListener mListener;

    public PersonAdapter(OnPersonClickListener listener) {
        this.mListener = listener;
    }

    public interface OnPersonClickListener {
        void onPersonClicked(Person person);
    }

    public void update(Collection<Person> people) {
        mPeople.clear();
        if (people != null) {
            mPeople.addAll(people);
        }
        notifyDataSetChanged();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PersonViewHolder(PersonItemViewBinding.inflate(inflater));
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        final Person person = mPeople.get(position);
        holder.binding.setPersoon(person);
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onPersonClicked(person);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }
}

class PersonViewHolder extends RecyclerView.ViewHolder {
    final PersonItemViewBinding binding;
    public PersonViewHolder(PersonItemViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}