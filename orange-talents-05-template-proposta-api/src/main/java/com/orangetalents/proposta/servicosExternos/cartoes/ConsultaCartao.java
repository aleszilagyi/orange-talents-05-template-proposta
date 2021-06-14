package com.orangetalents.proposta.servicosExternos.cartoes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "${cartao.host}", name = "consultaCartao")
public interface ConsultaCartao {
    @RequestMapping(method = RequestMethod.GET, value = "/api/cartoes", consumes = "application/json")
    ResponseEntity<InformacoesCartaoResponse> consultaRestricaoSolicitante(@RequestParam(value = "idProposta") String idProposta);

    @RequestMapping(method = RequestMethod.GET, value = "/api/cartoes/{id}", consumes = "application/json")
    ResponseEntity<InformacoesCartaoResponse> consultaCartao(@PathVariable(value = "id") String idCartao);

    @RequestMapping(method = RequestMethod.POST, value = "/api/cartoes/{id}/bloqueios", consumes = "application/json")
    ResponseEntity<BloqueioResponse> bloquearCartao(@PathVariable(value = "id") String idCartao, @RequestBody SistemaResponsavel sistemaResponsavel);
}
