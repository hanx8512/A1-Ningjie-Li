/** A dietitian who works in a named area of nutrition. */
public class Dietitian extends HealthProfessional {
    private final String nutritionFocus;

    /** Creates a dietitian with chosen working days. */
    public Dietitian(String id, String name, String[] days, String nutritionFocus) {
        super(id, name, days);
        this.nutritionFocus = requireText(nutritionFocus, "Nutrition focus");
    }

    /** Returns the role name. */
    @Override
    public String getRole() {
        return "Dietitian";
    }

    /** Describes a task specific to dietitians. */
    public String createMealPlan() {
        return getName() + " created a meal plan.";
    }

    /** Includes both shared and dietitian-specific information. */
    @Override
    public String toString() {
        return super.toString() + " | Nutrition focus: " + nutritionFocus;
    }
}
