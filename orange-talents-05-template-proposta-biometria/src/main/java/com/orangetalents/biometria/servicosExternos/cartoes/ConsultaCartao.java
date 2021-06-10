package com.orangetalents.biometria.servicosExternos.cartoes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${cartao.host}", name = "consultaCartao")
public interface ConsultaCartao {
    @RequestMapping(method = RequestMethod.GET, value = "/api/cartoes/{id}", consumes = "application/json")
    ResponseEntity<InformacoesCartaoResponse> consultaCartao(@PathVariable(value = "idCartao") String idCartao);
}
