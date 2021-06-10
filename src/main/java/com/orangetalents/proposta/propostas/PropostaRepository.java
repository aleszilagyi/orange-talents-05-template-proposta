package com.orangetalents.proposta.propostas;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface PropostaRepository extends JpaRepository<Proposta, UUID> {
    @Transactional
    List<Proposta> findTop10ByStatusAnaliseAndNumeroCartaoIsNull(StatusAnalise statusAnalise);
}
