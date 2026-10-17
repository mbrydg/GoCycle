package pricing;

/**
 * Pricing strategy for student riders.
 * Students receive a reduced per-minute rate.
 */
public class StudentPricing implements PricingStrategy {

    private final double ratePerMinute;

    public StudentPricing(double ratePerMinute) {
        if (ratePerMinute < 0 || !Double.isFinite(ratePerMinute)) {
            throw new IllegalArgumentException("Invalid rate");
        }
        this.ratePerMinute = ratePerMinute;
    }

    @Override
    public double getRatePerMinute() {
        return ratePerMinute;
    }
}
