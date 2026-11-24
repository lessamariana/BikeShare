package br.ufal.ic.p2.bikeshare.modelo.pagamento;

import br.ufal.ic.p2.bikeshare.excecao.PagamentoException;

import java.io.Serializable;
import java.math.BigDecimal;

// Forma de pagamento escolhida pelo usuário

public abstract class MetodoPagamento implements Serializable
{

    private static final long serialVersionUID = 1L;

    // Nome exibido no atributo

    public abstract String getNome();

    public String getNumeroCartao()
    {
        throw naoUsaCartao();
    }

    public String getValidadeCartao()
    {
        throw naoUsaCartao();
    }

    public String getChavePix()
    {
        throw new PagamentoException("Usuario nao usa pix.");
    }

    public BigDecimal getSaldo()
    {
        throw naoUsaSaldo();
    }

    public void recarregar(BigDecimal valor)
    {
        throw naoUsaSaldo();
    }

    // Cobra o valor de uma corrida. Cartão e Pix sempre aprovam, os outros sobrescrevem

    public void cobrar(BigDecimal valor) {
        // por padrão, aprovado
    }

    // Pode iniciar uma corrida? Cartão e Pix sempre podem, os outros sobrescrevem

    public void autorizarRetirada() {
        // por padrão, autorizado
    }

    private static PagamentoException naoUsaSaldo()
    {
        return new PagamentoException("Usuario nao usa saldo pre-carregado.");
    }

    private static PagamentoException naoUsaCartao()
    {
        return new PagamentoException("Usuario nao usa cartao.");
    }
}
