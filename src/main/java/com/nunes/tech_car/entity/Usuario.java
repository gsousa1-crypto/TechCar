package com.nunes.tech_car.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String nome;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_papeis", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "papel")
    private List<String> papeis = new ArrayList<>();

    // Construtor padrão
    public Usuario() {}

    // Construtor útil
    public Usuario(String email, String senha, String nome, String... papeis) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.papeis = List.of(papeis);
    }
}