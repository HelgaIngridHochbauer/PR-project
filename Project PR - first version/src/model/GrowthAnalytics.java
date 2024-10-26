package model;
import utils.OutputDevice;
import utils.InputDevice;

public class GrowthAnalytics implements Analyticable {

    @Override
    public void analyze() {
        // Implementation of analysis logic
        System.out.println("Performing growth analysis...");
    }

    @Override
    public double calculateEngagementGrowth(int currentLikes, int previousLikes) {
        if(previousLikes == 0) {
            return 100.0;
        }
        return ((double)(currentLikes - previousLikes) / previousLikes) * 100;
    }
    @Override
    public double analyzeFollowerGrowth(int currentFollowers, int previousFollowers) {
        if(previousFollowers == 0) {
            return 100.0;
        }
        return ((double) (currentFollowers - previousFollowers) / previousFollowers) * 100;
    }
}