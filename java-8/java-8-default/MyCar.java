interface Car {
    default void printWheelCount() {
        System.out.println("4");
    }

    void printColor();
}

public class MyCar implements Car {

    public static void main(String args[]) {
        MyCar myCar = new MyCar();
        myCar.printColor();
        myCar.printWheelCount();
    }

    @Override
    public void printColor() {
        System.out.println("Red");
    }

    private BigDecimal calculateLifetimeEarnedAmount(CustomerAccountInfoEntity customerAccountInfo) {
    BigDecimal lifetimeEarnedAmount = null;

    boolean cdlxEarnedAmountTimestampValid = false;
    boolean figgEarnedAmountTimestampValid = false;

    long cdlxTimeDiff, cdlxTimeDiffMinutes, figgTimeDiff, figgTimeMinutes;

    //CDLX earned amount timestamp check
    if (customerAccountInfo.getCdlxRedeemedAmountUpdateTs() != null) {
        cdlxTimeDiff = Timestamp.from(Instant.now()).getTime() - customerAccountInfo.getCdlxRedeemedAmountUpdateTs().getTime();
        cdlxTimeDiffMinutes = TimeUnit.MILLISECONDS.toMinutes(cdlxTimeDiff);
        log.info("Timestamp Difference Earned Amount CDLX: {}", cdlxTimeDiffMinutes);
        if (cdlxTimeDiffMinutes <= cdlxLifetimeAmountTimestampDifference) {
            cdlxEarnedAmountTimestampValid = true;
        } else {
            return null; //if either timestamp is expired we return null
        }
    }

    //Figg earned amount timestamp check
    if (customerAccountInfo.getFiggRedeemedAmountUpdateTs() != null) {
        figgTimeDiff = Timestamp.from(Instant.now()).getTime() - customerAccountInfo.getFiggRedeemedAmountUpdateTs().getTime();
        figgTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(figgTimeDiff);
        log.info("Timestamp Difference Earned Amount FIGG: {}", figgTimeMinutes);
        if (figgTimeMinutes <= figgLifetimeAmountTimestampDifference) {
            figgEarnedAmountTimestampValid = true;
        } else {
            return null; //if either timestamp is expired we return null
        }
    }

    // If both aggregators timestamp are null, treat amount as 0 and return 0
    if (customerAccountInfo.getCdlxRedeemedAmountUpdateTs() == null && customerAccountInfo.getFiggRedeemedAmountUpdateTs() == null) {
        lifetimeEarnedAmount = BigDecimal.valueOf(0);

    }

    //One aggregator has valid cache but other aggregator has null timestamp, return amount of aggregator that has valid cache
    if (customerAccountInfo.getCdlxRedeemedAmountUpdateTs() != null && customerAccountInfo.getFiggRedeemedAmountUpdateTs() == null) {
        if (customerAccountInfo.getCdlxLifetimeReedemedAmount() == null) {
            lifetimeEarnedAmount = BigDecimal.valueOf(0);
        } else {
            lifetimeEarnedAmount = customerAccountInfo.getCdlxLifetimeReedemedAmount();
        }
    } else if (customerAccountInfo.getCdlxRedeemedAmountUpdateTs() == null && customerAccountInfo.getFiggRedeemedAmountUpdateTs() != null) {
        if (customerAccountInfo.getFiggLifetimeReedemedAmount() == null) {
            lifetimeEarnedAmount = BigDecimal.valueOf(0);
        } else {
            lifetimeEarnedAmount = customerAccountInfo.getFiggLifetimeReedemedAmount();
        }
    }

    //Both aggregators caches are valid, return aggregated amount for both aggregators
    if (cdlxEarnedAmountTimestampValid == true && figgEarnedAmountTimestampValid == true) {
        if (customerAccountInfo.getCdlxLifetimeReedemedAmount() != null && customerAccountInfo.getFiggLifetimeReedemedAmount() == null) {
            lifetimeEarnedAmount = customerAccountInfo.getCdlxLifetimeReedemedAmount();
        } else if (customerAccountInfo.getCdlxLifetimeReedemedAmount() == null && customerAccountInfo.getFiggLifetimeReedemedAmount() != null) {
            lifetimeEarnedAmount = customerAccountInfo.getFiggLifetimeReedemedAmount();
        } else if (customerAccountInfo.getCdlxLifetimeReedemedAmount() == null && customerAccountInfo.getFiggLifetimeReedemedAmount() == null) {
            lifetimeEarnedAmount = BigDecimal.valueOf(0);
        } else {
            lifetimeEarnedAmount = customerAccountInfo.getCdlxLifetimeReedemedAmount().add(customerAccountInfo.getFiggLifetimeReedemedAmount());
        }
    }
    return lifetimeEarnedAmount;
}


}
