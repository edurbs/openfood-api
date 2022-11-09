package com.edurbs.openfood.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edurbs.openfood.domain.model.Cliente;
import com.edurbs.openfood.domain.service.AtivacaoClienteService;


@Controller
public class HelloController {

    private AtivacaoClienteService ativacaoClienteService;    

    public HelloController(AtivacaoClienteService ativacaoClienteService) {
        this.ativacaoClienteService = ativacaoClienteService;
    }

    @GetMapping(value="/hello")
    @ResponseBody
    public String getHello() {

        Cliente c1 = new Cliente("Eduardo", "edurbs@gmail.com", "1234-5678");
		        
		return ativacaoClienteService.ativar(c1);
        
        
    }
    
}
