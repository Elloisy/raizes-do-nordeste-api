package com.raizes.api.repository;

import com.raizes.api.domain.EstoqueUnidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EstoqueUnidadeRepository extends JpaRepository<EstoqueUnidade, Long> {
    List<EstoqueUnidade> findByUnidadeId(Long unidadeId);
    Optional<EstoqueUnidade> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);
}
