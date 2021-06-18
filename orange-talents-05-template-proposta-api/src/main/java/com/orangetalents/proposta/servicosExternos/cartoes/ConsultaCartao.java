package com.orangetalents.proposta.servicosExternos.cartoes;

import com.orangetalents.proposta.servicosExternos.cartoes.bloquear.BloqueioResponse;
import com.orangetalents.proposta.servicosExternos.cartoes.bloquear.SistemaResponsavelRequest;
import com.orangetalents.proposta.servicosExternos.cartoes.carteira.CarteiraRequest;
import com.orangetalents.proposta.servicosExternos.cartoes.carteira.CarteiraResponse;
import com.orangetalents.proposta.servicosExternos.cartoes.detalhes.InformacoesCartaoResponse;
import com.orangetalents.proposta.servicosExternos.cartoes.viagem.AvisarViagemRequest;
import com.orangetalents.proposta.servicosExternos.cartoes.viagem.AvisoViagemResponse;
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
    ResponseEntity<BloqueioResponse> bloquearCartao(@PathVariable(value = "id") String idCartao, @RequestBody SistemaResponsavelRequest sistemaResponsavelRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/api/cartoes/{id}/avisos", consumes = "application/json")
    ResponseEntity<AvisoViagemResponse> avisarViagem(@PathVariable(value = "id") String idCartao, @RequestBody AvisarViagemRequest avisarViagemRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/api/cartoes/{id}/carteiras", consumes = "application/json")
    ResponseEntity<CarteiraResponse> cadastrarCarteira(@PathVariable(value = "id") String idCartao, @RequestBody CarteiraRequest carteiraRequest);
}
