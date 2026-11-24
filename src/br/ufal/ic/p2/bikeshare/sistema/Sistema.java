package br.ufal.ic.p2.bikeshare.sistema;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;

// Coordenador do BikeShare: guarda o estado atual e define como uma operação é executada

public class Sistema
{

    private static final Path ARQUIVO_DADOS = Path.of("bikeshare.dat");

    private final Persistencia persistencia = new Persistencia(ARQUIVO_DADOS);
    private EstadoSistema estado;
    private Servicos servicos;

    // Retoma o estado salvo na execução anterior (se houver)
    public Sistema()
    {
        usar(persistencia.carregar());
    }

    public <T> T consultar(Function<Servicos, T> consulta)
    {
        return consulta.apply(servicos);
    }

    public <T> T executarComRetorno(Function<Servicos, T> operacao)
    {
        byte[] instantaneo = persistencia.serializar(estado);
        try
        {
            return operacao.apply(servicos);
        }
        catch (RuntimeException erro)
        {
            usar(persistencia.desserializar(instantaneo));
            throw erro;
        }
    }

    public void executar(Consumer<Servicos> operacao)
    {
        executarComRetorno(servico -> {operacao.accept(servico);
            return null;
        });
    }

    // Volta ao estado inicial
    public void zerar()
    {
        usar(new EstadoSistema());
    }

    // Grava o estado para a próxima execução
    public void encerrar()
    {
        persistencia.salvar(estado);
    }

    private void usar(EstadoSistema novoEstado)
    {
        estado = novoEstado;
        servicos = new Servicos(novoEstado);
    }
}
