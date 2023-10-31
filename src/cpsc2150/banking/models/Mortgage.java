package cpsc2150.banking.models;

import cpsc2150.banking.models.AbsMortgage;
import cpsc2150.banking.models.ICustomer;
import cpsc2150.banking.models.IMortgage;

public class Mortgage extends AbsMortgage implements IMortgage {

    // Member variables
    private double Payment;
    private double Rate;
    private ICustomer Customer;
    private double DebtToIncomeRatio;
    private double Principal;
    private int NumberOfPayments;
    private double PercentDown;

    /**
     * Constructor for Mortgage class that initializes the mortgage details.
     *
     * @param costOfHome The total cost of the home.
     * @param downPayment The amount of money paid upfront for the home.
     * @param years The number of years for which the mortgage is taken.
     * @param customer The customer associated with the mortgage.
     *
     * @pre costOfHome > 0 AND downPayment > 0 AND downPayment < costOfHome AND
     *      years >= MIN_YEARS AND years <= MAX_YEARS AND customer != null
     *
     * @post Principal = costOfHome - downPayment AND
     *       PercentDown = downPayment / costOfHome AND
     *       NumberOfPayments = years * MONTHS_IN_YEAR AND
     *       Rate, Payment, and DebtToIncomeRatio are computed based on provided parameters and class methods.
     *       Customer data is set to the provided customer.
     */
    public Mortgage(double costOfHome, double downPayment, int years, ICustomer customer) {
        // Compute concepts as per the given instructions

        this.Principal = costOfHome - downPayment;
        this.PercentDown = downPayment / costOfHome;
        this.NumberOfPayments = years * MONTHS_IN_YEAR;
        this.Rate = computeAPR(costOfHome, downPayment, years, customer);
        this.Payment = computePayment();
        this.Customer = customer;
        this.DebtToIncomeRatio = (customer.getMonthlyDebtPayments() + this.Payment) / customer.getIncome();
    }

    private double computeAPR(double costOfHome, double downPayment, int years, ICustomer customer) {
        double APR = BASERATE;

        APR += (years < MAX_YEARS) ? GOODRATEADD : NORMALRATEADD;

        if (PercentDown < PREFERRED_PERCENT_DOWN) {
            APR += GOODRATEADD;
        }

        int creditScore = customer.getCreditScore();

        if (creditScore < BADCREDIT) {
            APR += VERYBADRATEADD;
        } else if (creditScore < FAIRCREDIT) {
            APR += BADRATEADD;
        } else if (creditScore < GOODCREDIT) {
            APR += NORMALRATEADD;
        } else if (creditScore < GREATCREDIT) {
            APR += GOODRATEADD;
        }

        return APR / MONTHS_IN_YEAR;
    }

    private double computePayment() {
        return (Rate * Principal) / (1 - Math.pow((1 + Rate), -NumberOfPayments));
    }

    @Override
    public boolean loanApproved() {
        return (Rate * 12 < RATETOOHIGH) && (PercentDown >= MIN_PERCENT_DOWN) && (DebtToIncomeRatio <= DTOITOOHIGH);
    }

    @Override
    public double getPayment() {
        return this.Payment;
    }

    @Override
    public double getRate() {
        return this.Rate * 12;
    }

    @Override
    public double getPrincipal() {
        return this.Principal;
    }

    @Override
    public int getYears() {
        return NumberOfPayments / MONTHS_IN_YEAR;
    }
}
