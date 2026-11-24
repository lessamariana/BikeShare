package br.ufal.ic.p2.bikeshare.modelo.aluguel;

import br.ufal.ic.p2.bikeshare.excecao.AluguelException;

// Condição de uma corrida
// Só "em andamento" se comporta diferente: pode ser devolvido, mas ainda não tem fim, destino nem valor

public enum EstadoAluguel
{

    EM_ANDAMENTO("emAndamento"){
        @Override
        public void exigirEncerrado()
        {
            throw new AluguelException("Aluguel ainda esta em andamento.");
        }

        @Override
        public void exigirEmAndamento()
        {
            // em andamento: pode ser devolvido
        }
    }, FINALIZADO("finalizado"), CANCELADO("cancelado"), EXPIRADO("expirado");

    private final String nome;

    EstadoAluguel(String nome)
    {
        this.nome = nome;
    }

    public String getNome()
    {
        return nome;
    }

    // Dados de encerramento (fim, destino, valor) só existem depois da devolução

    public void exigirEncerrado()
    {
        // encerrado: os dados de encerramento existem
    }

    // Só aluguel em andamento pode ser devolvido

    public void exigirEmAndamento()
    {
        throw new AluguelException("Aluguel nao esta em andamento.");
    }
}
