package com.example.cadastro_usuarios.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chave artificial

    @Column(unique = true, nullable = false)
    private String cpf; // Chave de negócio, CPF

    @Column(nullable = false)
    private String nome; // Nome do usuário

    @Column(nullable = false)
    private LocalDateTime dataNascimento; // Data de nascimento

    // Endereço
    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRegistro status; // Status do registro (Ativo ou Removido)

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao; // Data e hora de criação

    @Column(nullable = false)
    private String usuarioCriacao; // Usuário que criou o registro

    private LocalDateTime dataAtualizacao; // Data e hora de atualização
    private String usuarioAtualizacao; // Usuário que atualizou o registro

    private LocalDateTime dataRemocao; // Data e hora de remoção
    private String usuarioRemocao; // Usuário que removeu o registro

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusRegistro.ATIVO; // Status padrão ao criar
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Método para marcar como removido
    public void remover(String usuario) {
        this.status = StatusRegistro.REMOVIDO;
        this.dataRemocao = LocalDateTime.now();
        this.usuarioRemocao = usuario;
    }

}
