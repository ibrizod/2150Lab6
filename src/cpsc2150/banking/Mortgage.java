package cpsc2150.banking;

import cpsc2150.banking.models.AbsMortgage;
import cpsc2150.banking.models.ICustomer;
import cpsc2150.banking.models.IMortgage;

public class Mortgage extends AbsMortgage implements IMortgage {

    // Private instance variables
    private double rate; // Interest rate per monthly period
    private double debtToIncomeRatio;
    private ICustomer customer;
    private double payment;
    private double principal; // Amount of the loan
    private int numberOfPayments; // Total number of payments the customer will make on the loan
    private double percentDown; // Percent of the house cost covered by the down payment

    // Constructor to initialize the Mortgage object
    public Mortgage(ICustomer customer, double principal, int years, double percentDown, double payment) {
        super(principal);
        this.debtToIncomeRatio = debtToIncomeRatio;
        this.principal = principal;
        this.percentDown = percentDown;
        this.numberOfPayments = years * MONTHS_IN_YEAR;
        this.rate = calculateRate();
    }
}

