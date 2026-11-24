package br.ufal.ic.p2.bikeshare.excecao;

/* Raiz de todas as exceções de regra de negócio do BikeShare
 O sistema nunca vai lançar um erro genérios, sempre uma das subclasses por categoria (usuário, estação, bicicleta, aluguel)
 A mensagem recebida é exatamente a que os testes de aceitação esperam.
 */

public abstract class BikeShareException extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    protected BikeShareException(String mensagem)
    {
        super(mensagem);
    }

    protected BikeShareException(String mensagem, Throwable causa)
    {
        super(mensagem, causa);
    }
}
