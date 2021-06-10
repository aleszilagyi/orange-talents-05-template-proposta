package com.orangetalents.proposta.servicosExternos.cartoes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${cartao.host}", name = "consultaCartao")
public interface ConsultaCartao {
    @RequestMapping(method = RequestMethod.GET, value = "/api/cartoes", consumes = "application/json")
    ResponseEntity<InformacoesCartaoResponse> consultaRestricaoSolicitante(@RequestParam(value = "idProposta") String idProposta);
}
