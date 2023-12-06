package edu.yu.introtoalgs;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

public class Tx extends TxBase {
    private Account sender;
    private Account receiver;
    private int amount;
    private LocalDateTime time;
    private static final AtomicLong counter = new AtomicLong(1); // make sure this is allowed and correct

    /**
     * Constructor.
     *
     * @param sender   non-null initiator of the transaction
     * @param receiver non-null recipient
     * @param amount   positive-integer-valued amount transfered in the
     *                 transaction.
     */
    public Tx(Account sender, Account receiver, int amount) {
        super(sender, receiver, amount);
        // need to figure out how to check if account can be checked to not be null --> currently throwing IAE exception fo rbeing null
//        if (sender == null || receiver == null || amount < 1) {
//            throw new IllegalArgumentException();
//        }
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        LocalDateTime time = LocalDateTime.now();
        this.time = time;
    }

    @Override
    public Account receiver() {
        return this.receiver;
    }

    @Override
    public Account sender() {
        return this.sender;
    }

    @Override
    public int amount() {
        return this.amount;
    }

    /**
     * Returns a unique non-negative identifier.
     */
    @Override
    public long id() {
        return counter.getAndIncrement();
    }

    /**
     * Returns the time that the Tx was created or null.
     */
    @Override
    public LocalDateTime time() {
        return this.time;
    }

    @Override
    public void setTimeToNull() {
        this.time = null;
    }

    @Override
    public String toString() {
        return "Tx{" +
                "sender=" + sender() +
                ", receiver=" + receiver() +
                ", amount=" + amount() +
                ", id=" + id() +
                ", time=" + time() +
                '}';
    }
 // double check if this works and if parameter can be TxBase
    // might need to use the comparator import
    @Override
    public int compareTo(TxBase other) {
        if (this.time == null && other.time() == null) {
            return 0; // Both are considered equal
        }

        if (this.time == null) {
            return -1; // This instance is considered "worse" than a non-null other
        }

        if (other.time() == null) {
            return 1; // This instance is considered "better" than a null other
        }

        // Compare the LocalDateTime instances
        return this.time.compareTo(other.time());
    }
}

