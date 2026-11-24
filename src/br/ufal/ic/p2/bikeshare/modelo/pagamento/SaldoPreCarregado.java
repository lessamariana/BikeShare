package br.ufal.ic.p2.bikeshare.modelo.pagamento;

import br.ufal.ic.p2.bikeshare.excecao.PagamentoException;

import java.math.BigDecimal;

// Créditos carregados antecipadamente pelo usuário
// O usuário guarda sempre o mesmo objeto de saldo, por isso trocar para Pix/cartão e depois voltar para saldo não perde o valor carregado

public class SaldoPreCarregado extends MetodoPagamento
{

    private static final long serialVersionUID = 1L;

    private BigDecimal saldo = BigDecimal.ZERO;

    @Override
    public String getNome()
    {
        return "saldoPreCarregado";
    }

    @Override
    public BigDecimal getSaldo()
    {
        return saldo;
    }

    // O valor já chega validado como positivo

    @Override
    public void recarregar(BigDecimal valor)
    {
        saldo = saldo.add(valor);
    }

    // Debita o valor, se não houver saldo suficiente, recusa sem alterar nada

    @Override
    public void cobrar(BigDecimal valor)
    {
        if(valor.compareTo(saldo) > 0)
        {
            throw saldoInsuficiente();
        }
        saldo = saldo.subtract(valor);
    }

    //Sem crédito nenhum não há retirada

    @Override
    public void autorizarRetirada()
    {
        if(saldo.signum() <= 0)
        {
            throw saldoInsuficiente();
        }
    }

    private static PagamentoException saldoInsuficiente()
    {
        return new PagamentoException("Saldo insuficiente.");
    }
}
