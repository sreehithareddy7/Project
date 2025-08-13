import java.util.Scanner;

// --- Base Class ---
class Workout {
    protected String type;
    protected int caloriesBurned;

    public Workout(String type, int caloriesBurned) {
        this.type = type;
        this.caloriesBurned = caloriesBurned;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public String getType() {
        return type;
    }

    // Method that can be overridden
    public String workoutInfo() {
        return type + " workout burned " + caloriesBurned + " calories.";
    }
}

// --- Subclass with @Override ---
class CardioWorkout extends Workout {
    public CardioWorkout(String type, int caloriesBurned) {
        super(type, caloriesBurned);
    }

    @Override
    public String workoutInfo() {
        return "You completed a cardio session: " + type + " and burned " + caloriesBurned + " calories.";
    }
}

// --- Fitness Tracker Class ---
class FitnessTracker {
    private int calorieGoal;
    private int waterGoal;
    private int totalCalories;
    private int totalWater;
    private int streakCount;

    public FitnessTracker(int calorieGoal, int waterGoal) {
        this.calorieGoal = calorieGoal;
        this.waterGoal = waterGoal;
        this.totalCalories = 0;
        this.totalWater = 0;
        this.streakCount = 0;
    }

    public void logWorkout(Workout workout, int waterIntake) {
        totalCalories += workout.getCaloriesBurned();
        totalWater += waterIntake;

        System.out.println(workout.workoutInfo());
        System.out.println(waterIntake + " ml water intake logged.");

        checkGoals();
    }

    public void viewSummary() {
        System.out.println("\n--- Daily Summary ---");
        double calPercent = (totalCalories * 100.0) / calorieGoal;
        double waterPercent = (totalWater * 100.0) / waterGoal;

        System.out.printf("Calories burned: %d / %d (%.1f%%)\n", totalCalories, calorieGoal, calPercent);
        System.out.printf("Water intake: %d / %d ml (%.1f%%)\n", totalWater, waterGoal, waterPercent);

        if (totalCalories >= calorieGoal && totalWater >= waterGoal) {
            streakCount++;
            System.out.println("Congrats! You've met your goals today!");
            System.out.println("You've met your goals " + streakCount + " days in a row! Keep the streak going!");
        } else {
            streakCount = 0;
            int calLeft = calorieGoal - totalCalories;
            int waterLeft = waterGoal - totalWater;
            System.out.println("Keep going! You're making progress.");
            if (calLeft > 0)
                System.out.println("Only " + calLeft + " calories left to burn.");
            if (waterLeft > 0)
                System.out.println("Only " + waterLeft + " ml water left to drink.");
        }

        System.out.println("Keep pushing! Every day is a new opportunity to get stronger.");
    }

    private void checkGoals() {
        if (totalCalories >= calorieGoal && totalWater >= waterGoal) {
            System.out.println("Wow! You reached both your calorie and water goals today!");
        }
    }
}

// --- Main Class ---
public class FitnessTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome! Set your daily fitness goals.");

        System.out.print("Enter calorie burn goal: ");
        int calorieGoal = scanner.nextInt();

        System.out.print("Enter water intake goal (ml): ");
        int waterGoal = scanner.nextInt();

        FitnessTracker tracker = new FitnessTracker(calorieGoal, waterGoal);

        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Log cardio workout and water intake");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter cardio workout type: ");
                    String type = scanner.nextLine();

                    System.out.print("Enter calories burned: ");
                    int calories = scanner.nextInt();

                    System.out.print("Enter water intake after workout (ml): ");
                    int water = scanner.nextInt();

                    // Create a CardioWorkout instead of Workout
                    Workout workout = new CardioWorkout(type, calories);
                    tracker.logWorkout(workout, water);
                    tracker.viewSummary();
                }
                case 0 -> {
                    running = false;
                    System.out.println("Goodbye! Stay motivated!");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
