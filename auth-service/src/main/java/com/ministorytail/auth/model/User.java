package com.ministorytail.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// Correção importante de adequação aos padrões de DDD
// Model precisa ser rico em sua construção 
// Trazer essa referência dentro do trabalho, é bem sólida
@Entity
// Insight para o trabalho: "entendendo que user é uma palavra reservada no postgresql,
// renomeei a tabela para app_users" - Refletir essa decisão no trabalho
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected User() {
    }

    private User(String name, String email, String phone, String password) {
        validarEmail(email);
        validarSenha(password);
        validarNome(name);

        this.name = name;
        this.email = email.toLowerCase().trim();
        this.phone = phone;
        this.password = password;
    }

    public static User registrar(String name, String email, String phone, String password) {
        return new User(name, email, phone, password);
    }

    public boolean validarCredenciais(String senhaFornecida) {
        if (senhaFornecida == null || senhaFornecida.isBlank()) {
            return false;
        }
        return this.password.equals(senhaFornecida);
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    private void validarSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        if (senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}