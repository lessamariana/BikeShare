package br.ufal.ic.p2.bikeshare.excecao;

// Violação de regra envolvendo métodos de pagamento e cobrança

public class PagamentoException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public PagamentoException(String mensagem)
    {
        super(mensagem);
    }
}
