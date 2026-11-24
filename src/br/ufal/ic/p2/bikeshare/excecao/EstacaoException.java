package br.ufal.ic.p2.bikeshare.excecao;

// Violação de regra envolvendo estações (inexistente, sem vagas, capacidade inválida)

public class EstacaoException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public EstacaoException(String mensagem)
    {
        super(mensagem);
    }
}
