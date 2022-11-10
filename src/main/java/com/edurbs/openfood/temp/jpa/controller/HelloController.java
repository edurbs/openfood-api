package com.edurbs.openfood.temp.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edurbs.openfood.temp.jpa.Cliente;
import com.edurbs.openfood.temp.jpa.service.AtivacaoClienteService;


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
		ativacaoClienteService.ativar(c1); 
		return "Cliente ativado";
        
        
    }
    
}
