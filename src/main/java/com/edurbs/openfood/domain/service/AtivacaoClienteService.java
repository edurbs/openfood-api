package com.edurbs.openfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.model.Cliente;
import com.edurbs.openfood.domain.service.notificacao.NivelUrgencia;
import com.edurbs.openfood.domain.service.notificacao.Notificador;
import com.edurbs.openfood.domain.service.notificacao.TipoNotificador;


@Component
public class AtivacaoClienteService implements InitializingBean, DisposableBean{
   
    @Autowired
    // @Qualifer("email") 
    @TipoNotificador(NivelUrgencia.NORMAL)
    private Notificador notificador;  
    //private List<Notificador> listaNotificadores;


    // @Autowired
    // public AtivacaoClienteService(Notificador notificador) {       
    //     this.notificador = notificador;
    // }

    // public AtivacaoClienteService() {       
    //     
    // }

    
    // @Autowired
    // public void setNotificador(Notificador notificador) {
    //     this.notificador = notificador;
    // }
    
    

    public String ativar(Cliente cliente){
        cliente.setAtivo(true);
        return notificador.enviar(cliente, "Cliente ativado");
        // StringBuilder retorno = new StringBuilder();
        // for (Notificador notificador : listaNotificadores) {
        //     retorno.append(notificador.enviar(cliente, "Cliente ativado"));
        // }
        // return retorno.toString();

    }


    @Override
    public void destroy() throws Exception {
        System.out.println("isso aqui acontece antes de destruir a implementação");
        
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Estou iniciando");
        
    }



}
