package pricing;

/**
 * Defines the pricing strategy for different rider types.
 * Each rider type can have its own per-minute rate.
 */
public interface PricingStrategy {

    double getRatePerMinute();

}
