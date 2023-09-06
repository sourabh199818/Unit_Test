import org.example.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class LoanTest {

    private Loan loan;

    @BeforeEach
    void setUp() {

        loan = new Loan("L1", "C1", "LEN1", 10000, 10000, LocalDate.of(2023, 5, 6), 0.01, LocalDate.of(2023, 5, 7), 0.01);
        loan = new Loan("L2", "C1", "LEN1", 20000, 5000, LocalDate.of(2023, 1, 6), 0.01, LocalDate.of(2023, 5, 8), 0.01);
        loan = new Loan("L3", "C2", "LEN2", 50000, 30000, LocalDate.of(2023, 5, 6), 0.02, LocalDate.of(2023, 4, 5), 0.02);
        loan = new Loan("L4", "C3", "LEN2", 50000, 30000, LocalDate.of(2023, 5, 6), 0.02, LocalDate.of(2023, 4, 5), 0.02);



    }

    @Test
    void testLoanInitialization() {
        // Test constructor initialization
        assertEquals("L1", loan.getLoanId());
        assertEquals("C1", loan.getCustomerId());
        assertEquals("LEN1", loan.getLenderId());
        assertEquals(10000, loan.getAmount());
        assertEquals(10000, loan.getRemainingAmount());
        assertEquals(LocalDate.of(2023, 5, 6), loan.getPaymentDate());
        assertEquals(0.01, loan.getInterestPerDay(), 0.0001); // Allow for a small delta in double comparison
        assertEquals(LocalDate.of(2023, 5, 7), loan.getDueDate());
        assertEquals(0.01, loan.getPenaltyPerDay(), 0.0001);
    }

    @Test
    void testSettersAndGetters() {
        // Test setters and getters
        loan.setAmount(20000);
        assertEquals(20000, loan.getAmount());

        loan.setRemainingAmount(15000);
        assertEquals(15000, loan.getRemainingAmount());
    }

    @Test
    void testCalculateInterest() {
        // Test calculateInterest method
        assertEquals(0.0, loan.calculateInterest()); // Since remaining amount is the same as the amount

        // Update remaining amount and calculate interest
        loan.setRemainingAmount(5000);
        assertEquals(5000.0 * 0.01, loan.calculateInterest(), 0.0001);
    }

    @Test
    void testCalculatePenalty() {
        // Test calculatePenalty method when payment is on time (no penalty)
        assertEquals(0.0, loan.calculatePenalty());

        // Set payment date to a date after the due date and calculate penalty
        loan.setPaymentDate(LocalDate.of(2023, 5, 10));
        assertEquals(3 * 0.01 * 10000, loan.calculatePenalty(), 0.0001);
    }

    @Test
    void testIsPaymentDateValid() {
        // Test isPaymentDateValid method for valid payment date
        assertTrue(loan.isPaymentDateValid());

        // Set payment date to a date after the due date
        loan.setPaymentDate(LocalDate.of(2023, 5, 10));
        assertFalse(loan.isPaymentDateValid());
    }
}
