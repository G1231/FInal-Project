package finalproject;

import java.util.Date;

public class LogEntry {
	//setting variables for LogEntry.
	private String user;
    private String exerciseType;
    private double duration;
    private String intensity;
    private Date date;
    private double caloriesBurnt;

    // Constructor for log entry
    public LogEntry(String user, String exerciseType, double duration, String intensity, Date date) {
    	this.user = user;
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.intensity = intensity;
        this.date = date;
    }

    public LogEntry(String user, String exerciseType, double duration, String intensity, Date date, double caloriesBurnt) {
    	this.user = user;
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.intensity = intensity;
        this.date = date;
        this.caloriesBurnt = caloriesBurnt;
    }

    // Get methods
    public String getUser() {
    	return user;
    }
    public String getExerciseType() {
        return exerciseType;
    }
    
    public double getDuration() {
        return duration;
    }
    
    public String getIntensity() {
        return intensity;
    }
    
    public Date getDate() {
        return date;
    }
    public double getCalories() {
    	return caloriesBurnt;
    }
    //set methods
    public void setUser(String user) {
    	this.user = user;
    }
    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }


    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }


    public void setDate(Date date) {
        this.date = date;
    }
    
    //method to calculate calories
    public double calculateCaloriesBurnt(User user) {
        double calPerMin = 0;
        double userMultiplier = 1.0;
        double avgMaleWeight = 140;
        double avgFemaleWeight = 120;
        
        // switch cases for types of exercise and calories burnt
        if ("male".equals(user.getGender())) {
            // Male-specific adjustments based on weight
            if (user.getWeight() > avgMaleWeight) {
                userMultiplier += ((user.getWeight() - avgMaleWeight) / 200);
            } else if (user.getWeight() < avgMaleWeight) {
                userMultiplier -= ((avgMaleWeight - user.getWeight()) / 200);
            }
        } else if ("female".equals(user.getGender())) {
            // Female-specific adjustments based on weight
            if (user.getWeight() > avgFemaleWeight) {
                userMultiplier += ((user.getWeight() - avgFemaleWeight) / 200);
            } else if (user.getWeight() < avgFemaleWeight) {
                userMultiplier -= ((avgFemaleWeight - user.getWeight()) / 200);
            }
        }

        
        
        switch (exerciseType.toLowerCase()) {
            case "running":
            	calPerMin = 12.5; 
                break;
            case "swimming":
                calPerMin = 8.0;
                break;
            case "cycling":
                calPerMin = 9.0;
                break;
            case "weight lifting":
            	calPerMin = 3.6;
            	break;
            case "rowing":
            	calPerMin = 8.4; 
                break;
            case "calisthenics":
                calPerMin = 5.4;
                break;
            case "walking":
                calPerMin = 5.8;
                break;
            case "skateboarding":
            	calPerMin = 6.0;
            	break;
            case "dancing":
                calPerMin = 6.6;
                break;
            case "hiking":
            	calPerMin = 7.2;
            	break;
            case "soccer":
            	calPerMin = 8.4; 
                break;
            case "basketball":
                calPerMin = 9.6;
                break;
            case "football":
                calPerMin = 10.8;
                break;
            default:
                calPerMin = 0.0; //calories burnt by living
                break;
        }

        // increase or reduce calories burnt based on intensity inputed.
        switch (intensity.toLowerCase()) {
            case "low":
                calPerMin *= 0.66; 
                break;
            case "moderate":
                break;
            case "high":
                calPerMin *= 1.33;
                break;
        }
        //change calories burned into the calculation and return it
        if ("male".equals(user.getGender())) {
            caloriesBurnt = calPerMin * duration * userMultiplier * 1.1; // Male-specific multiplier
        } else if ("female".equals(user.getGender())) {
            caloriesBurnt = calPerMin * duration * userMultiplier; // Female-specific multiplier
        }
        return caloriesBurnt;
    }
}
