package finalproject;

public class User {
	//initialize variables
    private String name;
    private int age;
    private double weight;
    private String gender;

    // Constructor for user
    public User(String name, int age, double weight, String gender) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.gender = gender;

    }

    // get methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public String getGender() {
        return gender;
    }
    
    
    //set methods
    public void setName(String name) {
        this.name = name;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }
    
}