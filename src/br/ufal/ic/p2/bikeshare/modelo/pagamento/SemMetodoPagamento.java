package br.ufal.ic.p2.bikeshare.modelo.pagamento;

import br.ufal.ic.p2.bikeshare.excecao.PagamentoException;

import java.math.BigDecimal;

// Situação de quem ainda não cadastrou forma de pagamento

public class SemMetodoPagamento extends MetodoPagamento
{

    private static final long serialVersionUID = 1L;

    @Override
    public String getNome()
    {
        return "nenhum";
    }

    @Override
    public void autorizarRetirada()
    {
        throw naoCadastrado();
    }

    @Override
    public void cobrar(BigDecimal valor)
    {
        throw naoCadastrado();
    }

    private static PagamentoException naoCadastrado()
    {
        return new PagamentoException("Metodo de pagamento nao cadastrado.");
    }
}
