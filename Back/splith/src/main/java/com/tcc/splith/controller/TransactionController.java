package com.tcc.splith.controller;

import com.tcc.splith.config.JWTUserData;
import com.tcc.splith.dto.TransactionRequest;
import com.tcc.splith.entity.Group;
import com.tcc.splith.entity.Transaction;
import com.tcc.splith.entity.User;
import com.tcc.splith.repository.GroupRepository;
import com.tcc.splith.repository.TransactionRepository;
import com.tcc.splith.repository.UserRepository;
import com.tcc.splith.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.tcc.splith.dto.TransactionResponseDTO;
import java.util.stream.Collectors;
import java.util.List;

import com.tcc.splith.service.StatementImportService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final GroupRepository groupRepository;

    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository, TransactionService transactionService, GroupRepository groupRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/group/{groupName}")
    public ResponseEntity<List<TransactionResponseDTO>> getGroupTransactions(@PathVariable String groupName) {
        // 1. Busca o grupo pelo nome
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado"));

        // 2. Busca as transações usando o ID do grupo encontrado
        List<Transaction> transactions = transactionRepository.findByGroupId(group.getId());

        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequest request) {
        JWTUserData loggedUser = (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(loggedUser.userId()).orElseThrow();

        Transaction saved = transactionService.createAndSplitTransaction(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponseDTO.fromEntity(saved));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getUserTransactions() {
        // 1. Descobre quem é o usuário logado pelo Token
        JWTUserData loggedUser = (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(loggedUser.userId()).orElseThrow();

        // 2. Busca no banco só as transações dele
        List<Transaction> transactions = transactionRepository.findByUserOrderByIdDesc(user);

        // 3. Devolve a lista para o Angular
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest request) {
        JWTUserData loggedUser = (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(loggedUser.userId()).orElseThrow();

        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada"));

        if (!t.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        t.setDescription(request.description());
        t.setAmount(request.amount());
        t.setDate(request.date());
        t.setCategory(request.category());
        t.setAccount(request.account());
        t.setType(request.type());

        if (request.groupName() != null && !request.groupName().equalsIgnoreCase("Pessoal")) {
            Group group = groupRepository.findByName(request.groupName()).orElse(null);
            t.setGroup(group);
        } else {
            t.setGroup(null);
        }

        Transaction saved = transactionRepository.save(t);
        return ResponseEntity.ok(TransactionResponseDTO.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        JWTUserData loggedUser = (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(loggedUser.userId()).orElseThrow();

        Transaction t = transactionRepository.findById(id).orElseThrow();

        if (!t.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        transactionRepository.delete(t);
        return ResponseEntity.noContent().build();
    }

}

