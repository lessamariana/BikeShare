package br.ufal.ic.p2.bikeshare.modelo.plano;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.excecao.PlanoException;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

// Planos que o sistema oferece que começa com avulso e assinante

public class CatalogoPlanos implements Serializable
{

    private static final long serialVersionUID = 1L;

    private static final String AVULSO = "avulso";
    private static final String ASSINANTE = "assinante";

    private final Map<String, Plano> modelos = new LinkedHashMap<>();

    public CatalogoPlanos(Configuracao configuracao)
    {
        registrar(new PlanoAvulso(AVULSO, configuracao.getTarifaMinutoAvulso()));
        registrar(new PlanoAssinatura(ASSINANTE, configuracao.getFranquiaMinutosAssinante(), configuracao.getTarifaMinutoExcedenteAssinante(), configuracao.getMensalidadePadraoAssinante()));
    }

    private void registrar(Plano modelo)
    {
        modelos.put(modelo.getNome(), modelo);
    }

    // Plano de quem se cadastra sem informar plano

    public Plano padrao()
    {
        return modelos.get(AVULSO);
    }

    public Plano buscar(String nome)
    {
        Plano modelo = nome == null ? null : modelos.get(nome);
        if(modelo == null)
        {
            throw new PlanoException("Plano invalido.");
        }
        return modelo;
    }
}
