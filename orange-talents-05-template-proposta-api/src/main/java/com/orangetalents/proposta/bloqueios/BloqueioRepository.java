package com.orangetalents.proposta.bloqueios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloqueioRepository extends JpaRepository<BloqueioCartao, UUID> {
}
