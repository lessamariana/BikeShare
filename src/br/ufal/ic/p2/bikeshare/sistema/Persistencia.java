package br.ufal.ic.p2.bikeshare.sistema;

import br.ufal.ic.p2.bikeshare.excecao.PersistenciaException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Persistencia
{

    private final Path arquivo;

    public Persistencia(Path arquivo)
    {
        this.arquivo = arquivo;
    }

    //Lê o estado salvo, na primeira execução (sem arquivo) começa vazio
    public EstadoSistema carregar()
    {
        if(!Files.exists(arquivo))
        {
            return new EstadoSistema();
        }
        return protegido(() -> desserializar(Files.readAllBytes(arquivo)), "Nao foi possivel carregar o sistema.");
    }

    public void salvar(EstadoSistema estado)
    {
        protegido(() -> Files.write(arquivo, serializar(estado)), "Nao foi possivel salvar o sistema.");
    }

    public byte[] serializar(EstadoSistema estado)
    {
        return protegido(() -> {ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (ObjectOutputStream saida = new ObjectOutputStream(bytes))
            {
                saida.writeObject(estado);
            }
            return bytes.toByteArray();
        }, "Nao foi possivel copiar o estado do sistema.");
    }

    public EstadoSistema desserializar(byte[] dados)
    {
        return protegido(() -> {
            try (ObjectInputStream entrada = new ObjectInputStream(new ByteArrayInputStream(dados)))
            {
                return (EstadoSistema) entrada.readObject();
            }
        }, "Nao foi possivel restaurar o estado do sistema.");
    }

    @FunctionalInterface
    private interface OperacaoIO<T>
    {
        T executar() throws IOException, ClassNotFoundException;
    }

    private static <T> T protegido(OperacaoIO<T> operacao, String mensagem)
    {
        try
        {
            return operacao.executar();
        } catch (IOException | ClassNotFoundException erro)
        {
            throw new PersistenciaException(mensagem, erro);
        }
    }
}
