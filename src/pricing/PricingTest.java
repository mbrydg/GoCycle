package pricing;

public class PricingTest {

    private static void check(String name, double actual, double expected) {
        if (Math.abs(actual - expected) > 0.00001) {
            throw new AssertionError(name + " failed. Expected: "
                    + expected + ", Got: " + actual);
        }
        System.out.println("PASS: " + name);
    }

    public static void main(String[] args) {

        TripCostCalculator calculator =
                new TripCostCalculator(1.00, 5.00);

        PricingStrategy casual = new CasualPricing(0.30);
        PricingStrategy member = new MemberPricing(0.20);
        PricingStrategy student = new StudentPricing(0.10);

        check("Casual rider", calculator.calculateCost(30, casual), 10.00);
        check("Member rider", calculator.calculateCost(30, member), 7.00);
        check("Student rider", calculator.calculateCost(30, student), 4.00);

        check("Exactly 120 minutes",
                calculator.calculateCost(120, casual), 37.00);

        check("Over 120 minutes",
                calculator.calculateCost(121, casual), 42.30);

        System.out.println("All pricing tests passed!");
    }
}
