package com.orangetalents.biometria.criarBiometria;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BiometriaRepository extends JpaRepository<Biometria, UUID> {
    Optional<Biometria> findByNumeroCartao(String s);
}
