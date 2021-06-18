package com.orangetalents.proposta.carteiras;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarteiraRepository extends JpaRepository<Carteira, UUID> {
    Optional<Carteira> findByNomeCarteiraAndNumCartao(CarteiraEnum nomeCarteira, String numCartao);
}
