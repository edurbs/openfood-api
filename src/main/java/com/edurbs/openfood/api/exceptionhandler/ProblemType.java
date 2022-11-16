package com.edurbs.openfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso"),
    ERRO_GENERICO("/erro-negocio", "Violação da regra de negócio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreesível"), 
    PROPRIEDADE_INVALIDA("/propriedade-invalida", "Propriedade inválida"), 
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-sistema", "Erro de sistema");
    

    
    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://openfood.com.br"+path;
        this.title = title;
    }
}
