package com.company1.gpasaver.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// For randomuser.me API: https://randomuser.me/documentation#howto
@Entity(tableName = "users")
public class User implements Serializable {
    // Fields marked public for testing.
    //@SerializedName("id") public  String id;
    @PrimaryKey
    @ColumnInfo(name = "userid")
    private int id;

    @ColumnInfo(name = "firstname")
    @SerializedName("firstName")
    public String firstName;

    @Ignore
    @SerializedName("lastName")
    public String lastName;

    @Ignore
    @SerializedName("userName")
    public String userName;

    @Ignore
    @SerializedName("password")
    public String password;

    @Ignore
    @SerializedName("isTutor")
    public int isTutor;

    @Ignore
    @SerializedName("phone")
    public String phoneNumber;

    @Ignore
    @SerializedName("image")
    public int image;

    @Ignore
    @SerializedName("gender")
    public String gender;

    @Ignore
    @SerializedName("name")
    public Name name;

    // @SerializedName("location") public Location location;
    @Ignore
    @SerializedName("email")
    public String email;

    @Ignore
    @SerializedName("login")
    public Login login;

    @Ignore
    @SerializedName("course_id")
    public int course_id;

    @Ignore
    @SerializedName("picture")
    public Picture picture;

    @Ignore
    @SerializedName("tutor_id")
    public int tutor_id;

    public String picture_string;

    public String GPA;
    public String rate;
    public double balance;

    @Ignore public String fullName;
    @Ignore public List<String> subjects_tutored = new ArrayList<>();
    @Ignore public List<String> subjects_will_tutor = new ArrayList<>();
    @Ignore public List<String> subjects_leanred = new ArrayList<>();
    @Ignore public List<String> subjects_will_learn = new ArrayList<>();

    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }
    @Ignore
    public User() {

    }

    public User(int id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    //public String getId() {
    //  return id;
    //}
    //
    //public User setId(String id) {
    //  this.id = id;
    //  return this;
    //}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGPA() {
        return GPA;
    }

    public User setGPA(String GPA) {
        this.GPA = GPA;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public User setRate(String rate) {
        this.rate = rate;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setPicture(String picture) {
        this.picture_string = picture;
        return this;
    }

    public String getPicture() {
        return picture_string;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User setImage(int image) {
        this.image = image;
        return this;
    }

    public int getImage() {
        return image;
    }

    public User setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public User addSubjectWillTutor(String subject) {
        subjects_will_tutor.add(subject);
        return this;
    }

    public String getSubjectWillTutor() {
        StringBuilder sb = new StringBuilder();
        for (String s : subjects_will_tutor) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public User addSubjectTutored(String subject) {
        subjects_tutored.add(subject);
        return this;
    }

    public String getSubjectTutored() {
        StringBuilder sb = new StringBuilder();
        for (String s : subjects_tutored) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public User addSubjectLearned(String subject) {
        subjects_leanred.add(subject);
        return this;
    }

    public String getSubjectLearned() {
        StringBuilder sb = new StringBuilder();
        for (String s : subjects_leanred) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public User addSubjectWillLearn(String subject) {
        subjects_will_learn.add(subject);
        return this;
    }

    public String getSubjectWillLearn() {
        StringBuilder sb = new StringBuilder();
        for (String s : subjects_will_learn) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public User setCourse(int course_id) {
        this.course_id = course_id;
        return this;
    }

    public User setTutorid(int id) {
        this.tutor_id = id;
        return this;
    }

    public int getTutorid() {
        return this.tutor_id;
    }

    public int getIsTutor() {
        return isTutor;
    }

    public void setIsTutor(int isTutor) {
        this.isTutor = isTutor;
    }
}

