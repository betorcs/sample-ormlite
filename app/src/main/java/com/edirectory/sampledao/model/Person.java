package com.edirectory.sampledao.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by betorcs on 2/15/16.
 */

@DatabaseTable(tableName = "person")
public class Person implements Parcelable {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField
    private int age;

    public Person() {}

    protected Person(Parcel in) {
        id = in.readLong();
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeInt(age);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
