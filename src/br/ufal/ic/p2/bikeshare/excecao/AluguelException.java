package br.ufal.ic.p2.bikeshare.excecao;

// Violação de regra envolvendo aluguéis (inexistente, fora de andamento, datas incoerentes)

public class AluguelException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public AluguelException(String mensagem)
    {
        super(mensagem);
    }
}
