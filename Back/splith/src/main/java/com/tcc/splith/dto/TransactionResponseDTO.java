package com.tcc.splith.dto;

import com.tcc.splith.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDTO(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate date,
        String type,
        String category,
        String account,
        String groupName,
        UserResponseDTO user
) {
    public static TransactionResponseDTO fromEntity(Transaction transaction) {
        String groupName = transaction.getGroup() != null ? transaction.getGroup().getName() : "Pessoal";
        UserResponseDTO userDto = transaction.getUser() != null ? UserResponseDTO.fromEntity(transaction.getUser()) : null;

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getAccount(),
                groupName,
                userDto
        );
    }
}
