package br.ufal.ic.p2.bikeshare.modelo.pagamento;

import br.ufal.ic.p2.bikeshare.excecao.PagamentoException;
import br.ufal.ic.p2.bikeshare.util.Texto;

// Pagamento com cartão: guarda número e validade (MM/AAAA)

public class Cartao extends MetodoPagamento
{

    private static final long serialVersionUID = 1L;

    // Mês 01 a 12, barra e ano com quatro dígitos

    private static final String FORMATO_VALIDADE = "(0[1-9]|1[0-2])/\\d{4}";

    private final String numero;
    private final String validade;

    public Cartao(String numero, String validade)
    {
        this.numero = Texto.exigir(numero, "Numero do cartao nao pode ser nulo.");
        this.validade = validarValidade(validade);
    }

    private static String validarValidade(String validade)
    {
        String texto = Texto.exigir(validade, "Validade do cartao nao pode ser nula.").trim();
        if(!texto.matches(FORMATO_VALIDADE))
        {
            throw new PagamentoException("Validade do cartao invalida.");
        }
        return texto;
    }

    @Override
    public String getNome()
    {
        return "cartao";
    }

    @Override
    public String getNumeroCartao()
    {
        return numero;
    }

    @Override
    public String getValidadeCartao()
    {
        return validade;
    }
}
