import org.example.Loan;
import org.example.LoanStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class LoanStoreTest {
    private LoanStore loanStore;
    private static final Logger logger = Logger.getLogger(LoanStore.class.getName());


    @BeforeEach
    void setUp() {
        // Initialize a LoanStore object before each test
        loanStore = new LoanStore();
    }

    @Test
    void testAddLoan() {
        // Test adding a valid loan to the store
        Loan loan = new Loan("L1", "C1", "LEN1", 10000, 10000, LocalDate.of(2023, 5, 6), 0.01, LocalDate.of(2023, 5, 7), 0.01);
        loanStore.addLoan(loan);

        // Check if the loan was added to the store
        List<Loan> loans = loanStore.getLoans(); // Replace with your actual method to retrieve loans
        assertEquals(1, loans.size());
        assertEquals(loan, loans.get(0));
    }

    @Test
    void testAddInvalidLoan() {
        // Test adding an invalid loan (payment date after due date)
        Loan invalidLoan = new Loan("L2", "C2", "LEN2", 20000, 20000, LocalDate.of(2023, 5, 10), 0.01, LocalDate.of(2023, 5, 7), 0.01);

        // Check if an exception is thrown when adding the invalid loan
        assertThrows(IllegalArgumentException.class, () -> loanStore.addLoan(invalidLoan));
    }

    @Test
    void testAggregateRemainingAmountByLender() {
        // Test aggregating remaining amount by lender
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, LocalDate.of(2023, 5, 6), 0.01, LocalDate.of(2023, 5, 7), 0.01);
        Loan loan2 = new Loan("L2", "C2", "LEN1", 20000, 20000, LocalDate.of(2023, 5, 6), 0.02, LocalDate.of(2023, 5, 8), 0.01);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        // Aggregate remaining amount for lender LEN1
        double aggregateAmount = loanStore.aggregateRemainingAmountByLender("LEN1");
        assertEquals(30000, aggregateAmount);
    }

    @Test
    void testAggregateInterestByInterestRate() {
        // Test aggregating interest by interest rate
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, LocalDate.of(2023, 5, 6), 0.01, LocalDate.of(2023, 5, 7), 0.01);
        Loan loan2 = new Loan("L2", "C2", "LEN1", 20000, 20000, LocalDate.of(2023, 5, 6), 0.02, LocalDate.of(2023, 5, 8), 0.01);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        // Aggregate interest for loans with an interest rate of 0.01
        double aggregateInterest = loanStore.aggregateInterestByInterestRate(0.01);
        assertEquals(10000, aggregateInterest);
    }

    @Test
    void testAggregateRemainingAmountByCustomer() {
        // Test aggregating remaining amount by customer
        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, LocalDate.of(2023, 5, 6), 0.01, LocalDate.of(2023, 5, 7), 0.01);
        Loan loan2 = new Loan("L2", "C1", "LEN2", 20000, 20000, LocalDate.of(2023, 5, 6), 0.02, LocalDate.of(2023, 5, 8), 0.01);

        loanStore.addLoan(loan1);
        loanStore.addLoan(loan2);

        // Aggregate remaining amount for customer C1
        double aggregateAmount = loanStore.aggregateRemainingAmountByCustomer("C1");
        assertEquals(20000, aggregateAmount);
    }
}
