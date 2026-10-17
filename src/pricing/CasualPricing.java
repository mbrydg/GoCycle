 package pricing;

/**
 * Pricing strategy for casual riders.
 * Casual riders pay the standard per-minute rate.
 */
public class CasualPricing implements PricingStrategy {

    private final double ratePerMinute;

    public CasualPricing(double ratePerMinute) {
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
