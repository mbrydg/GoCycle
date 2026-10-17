package pricing;

/**
 * Calculates trip costs using a rider's pricing strategy.
 */
public class TripCostCalculator {

    private final double unlockFee;
    private final double longTripSurcharge;

    public TripCostCalculator(double unlockFee, double longTripSurcharge) {
        if (!Double.isFinite(unlockFee) || unlockFee < 0
                || !Double.isFinite(longTripSurcharge) || longTripSurcharge < 0) {
            throw new IllegalArgumentException("Invalid fee");
        }

        this.unlockFee = unlockFee;
        this.longTripSurcharge = longTripSurcharge;
    }

    public double calculateCost(int minutes, PricingStrategy strategy) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Invalid trip duration");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("Pricing strategy is required");
        }

        double total = unlockFee + minutes * strategy.getRatePerMinute();

        if (minutes > 120) {
            total += longTripSurcharge;
        }

        return Math.round(total * 100.0) / 100.0;
    }
}