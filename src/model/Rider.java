package model;

import pricing.PricingStrategy;

public class Rider {

    private final String id;
    private final String name;
    private PricingStrategy pricingStrategy;

    public Rider(
            String id,
            String name,
            PricingStrategy pricingStrategy
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Rider ID is required");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Rider name is required");
        }

        this.id = id;
        this.name = name;
        setPricingStrategy(pricingStrategy);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        if (pricingStrategy == null) {
            throw new IllegalArgumentException("Pricing strategy is required");
        }

        this.pricingStrategy = pricingStrategy;
    }
}
