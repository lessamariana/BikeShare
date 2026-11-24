package br.ufal.ic.p2.bikeshare.modelo.plano;

import br.ufal.ic.p2.bikeshare.util.Dinheiro;

import java.math.BigDecimal;

//Plano com mensalidade e franquia de minutos gratuitos por corrida

public class PlanoAssinatura extends Plano
{

    private static final long serialVersionUID = 1L;

    private final BigDecimal mensalidade;

    public PlanoAssinatura(String nome, int franquiaMinutos, BigDecimal valorMinutoExcedente, BigDecimal mensalidade)
    {
        super(nome, franquiaMinutos, valorMinutoExcedente);
        this.mensalidade = mensalidade;
    }

    @Override
    public BigDecimal getMensalidade()
    {
        return mensalidade;
    }

    // Copia franquia e tarifa do modelo, trocando só a mensalidade negociada

    @Override
    public Plano contratar(String mensalidadeTexto)
    {
        BigDecimal valor = Dinheiro.converter(mensalidadeTexto, "Mensalidade nao pode ser nula.", "Mensalidade deve ser numerica.");
        Dinheiro.exigirNaoNegativo(valor, "Mensalidade deve ser nao-negativa.");
        return new PlanoAssinatura(getNome(), getFranquiaMinutos(), getValorMinutoExcedente(), valor);
    }
}
