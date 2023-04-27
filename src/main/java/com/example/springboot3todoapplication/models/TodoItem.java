package com.example.springboot3todoapplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.Month;

@Getter
@Setter
@Entity
@Table(name = "todo_items")
public class TodoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Field cannot be empty")
    @Digits(integer = 10, fraction = 2, message = "Expense must be a number with up to 2 decimal places")
    @Min(value = 0, message = "Amount must be greater than Zero")
    private BigDecimal description;

    //@NotBlank(message = "Approved Amount is required")
    @Digits(integer = 10, fraction = 2, message = "Expense must be a number with up to 2 decimal places")
    private String approvedAmount;


    private Boolean isComplete;

    @DateTimeFormat(pattern = "dd/MM/yy")
    private Instant createdAt;

    private Instant updatedAt;

    @NotBlank(message = "Claim Type is required")
    private String claimType;

    //private Instant dateOfExpense;

    @NotNull(message = "field cannot be empty")
    @Min(value = 1, message = "Claim month must be between 1 and 12")
    @Max(value = 12, message = "Claim month must be between 1 and 12")
    private Integer claimMonth;

    @Digits(integer = 4, fraction = 0, message = "Claim year must be a valid year")
    @Min(value = 1900, message = "Claim year must be after 1900")
    private Integer claimYear;

    public Month getClaimMonthAsObject() {
        return Month.of(claimMonth);
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", approvedAmount='" + approvedAmount + '\'' +
                ", isComplete=" + isComplete +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", claimType='" + claimType + '\'' +
                ", claimMonth=" + claimMonth +
                ", claimYear=" + claimYear +
                '}';
    }
}
