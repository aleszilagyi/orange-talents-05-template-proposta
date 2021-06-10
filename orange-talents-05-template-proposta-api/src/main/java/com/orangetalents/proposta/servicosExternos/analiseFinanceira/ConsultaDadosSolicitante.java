package com.orangetalents.proposta.servicosExternos.analiseFinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${restricao.host}", name = "consultaRestricao")
public interface ConsultaDadosSolicitante {
    @RequestMapping(method = RequestMethod.POST, value = "/api/solicitacao", consumes = "application/json")
    ResponseEntity<FormAnaliseFinanceira> consultaRestricaoSolicitante(@RequestBody FormAnaliseFinanceira formAnaliseFinanceira);
}
