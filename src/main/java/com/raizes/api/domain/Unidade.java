package com.raizes.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unidades")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Unidade {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false)
    private Boolean ativo = true;
}
