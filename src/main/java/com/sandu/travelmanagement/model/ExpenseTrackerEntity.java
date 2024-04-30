package com.sandu.travelmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Expense")
public class ExpenseTrackerEntity {
    @Id
    @GeneratedValue
    long id;

    long limit;
    long currentSpend;
    List<Spending> spendLog;
    @Transient
    boolean isLimit;

    void makePayment(Spending payment) {
        currentSpend += payment.amount;
        spendLog.add(payment);
    }

    boolean checkLimit() {
        return limit >= currentSpend;
    }
}
