package com.orangetalents.proposta.viagens;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViagemRepository extends JpaRepository<ViagemCartao, UUID> {
}
