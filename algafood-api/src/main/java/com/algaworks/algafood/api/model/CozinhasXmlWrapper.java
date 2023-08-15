package com.algaworks.algafood.api.model;




import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@JacksonXmlRootElement(localName = "cozinhas do Caio")// ELEMENTO RAIZ, especifico para customização de SERIALIZAÇÃO no formato XML
// Por estarmos trabalhando com LISTAS é mais apropriado usamos @JacksonXmlRootElement() ao invés @JsonRootName
@Data
public class CozinhasXmlWrapper {// A função dessa Classe é empacotar uma lista de cozinhas
    @JsonProperty("cozinha")
    @JacksonXmlElementWrapper(useWrapping = false) // desabilita o embrulho
    @NonNull // Diz ao construtor que essa propriedade é obrigatória.
    private List<Cozinha> cozinhas;
    /* Com essa anotação gera-se um construtor que recebe um parâmetro com o
    mesmo tipo da propriedade obrigatória, ou seja, o construtor irá
    receber com parâmetro uma lista de cozinhas*/
}
