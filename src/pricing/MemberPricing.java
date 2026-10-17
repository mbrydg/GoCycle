package pricing;

/**
 * Pricing strategy for monthly members.
 * Members receive a discounted per-minute rate.
 */
public class MemberPricing implements PricingStrategy {

    private final double ratePerMinute;

    public MemberPricing(double ratePerMinute) {
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
