package utils;

import java.io.Serializable;

/**
 * Created by c-denipost on 24-Nov-17.
 **/
public class Student implements Serializable{
    private String Name;
    private int age;

    public Student(String name, int age) {
        Name = name;
        this.age = age;
    }

    public String getName() {
        return Name;
    }

    public int getAge() {
        return age;
    }
}
