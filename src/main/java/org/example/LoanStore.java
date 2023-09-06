package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class LoanStore {

    private static final Logger logger = Logger.getLogger(LoanStore.class.getName());
    private List<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        validateLoan(loan);
        loans.add(loan);
    }
    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }
    private void validateLoan(Loan loan) {
        // Validate the loan and throw an exception if it's invalid

        if (loan.getPaymentDate().isAfter(loan.getDueDate())) {
            // Log an alert when a loan crosses the due date
            logger.warning("Loan crossed the due date: Loan ID " + loan.getLoanId());
            throw new IllegalArgumentException("Payment date cannot be greater than the due date.");
        }
    }

    public double aggregateRemainingAmountByLender(String lenderId) {
        // Aggregate remaining amount by lender
        return loans.stream()
                .filter(loan -> loan.getLenderId().equals(lenderId))
                .mapToDouble(Loan::getRemainingAmount)
                .sum();
    }

    public double aggregateInterestByInterestRate(double interestRate) {
        // Aggregate interest by interest rate
        return loans.stream()
                .filter(loan -> loan.getInterestPerDay() == interestRate)
                .mapToDouble(Loan::calculateInterest)
                .sum();
    }

    public double aggregateRemainingAmountByCustomer(String customerId) {
        // Aggregate remaining amount by customer
        return loans.stream()
                .filter(loan -> loan.getCustomerId().equals(customerId))
                .mapToDouble(Loan::getRemainingAmount)
                .sum();
    }
}
