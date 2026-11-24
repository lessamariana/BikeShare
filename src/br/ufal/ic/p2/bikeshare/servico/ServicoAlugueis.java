package br.ufal.ic.p2.bikeshare.servico;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.modelo.aluguel.Aluguel;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Bicicleta;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Estacao;
import br.ufal.ic.p2.bikeshare.modelo.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.util.Datas;

import java.time.LocalDateTime;

// Casos de uso de aluguel: retirada (US8) e devolução (US9)

public class ServicoAlugueis
{

    private final RepositorioAlugueis alugueis;
    private final RepositorioUsuarios usuarios;
    private final RepositorioEstacoes estacoes;
    private final RepositorioBicicletas bicicletas;
    private final Configuracao configuracao;

    public ServicoAlugueis(RepositorioAlugueis alugueis, RepositorioUsuarios usuarios, RepositorioEstacoes estacoes, RepositorioBicicletas bicicletas, Configuracao configuracao)
    {
        this.alugueis = alugueis;
        this.usuarios = usuarios;
        this.estacoes = estacoes;
        this.bicicletas = bicicletas;
        this.configuracao = configuracao;
    }

    // Inicia um aluguel

    public String retirar(String idUsuario, String idEstacao, String idBicicleta, String data)
    {
        Usuario usuario = usuarios.buscar(idUsuario);
        Estacao origem = estacoes.buscar(idEstacao);
        Bicicleta bicicleta = bicicletas.buscar(idBicicleta);
        LocalDateTime inicio = Datas.converter(data);
        validarRetirada(usuario, origem, bicicleta);
        Aluguel aluguel = alugueis.cadastrar(id -> new Aluguel(id, usuario, bicicleta, origem, inicio));
        aluguel.iniciar();
        return aluguel.getId();
    }

    // Ordem dos testes: usuário, presença na estação, bicicleta, pagamento

    private void validarRetirada(Usuario usuario, Estacao origem, Bicicleta bicicleta)
    {
        usuario.validarNovaRetirada();
        origem.exigirPresenca(bicicleta);
        bicicleta.validarRetirada(configuracao);
        usuario.autorizarRetirada();
    }

    public void devolver(String idAluguel, String idEstacao, String data)
    {
        Aluguel aluguel = alugueis.buscar(idAluguel);
        Estacao destino = estacoes.buscar(idEstacao);
        LocalDateTime fim = Datas.converter(data);
        aluguel.devolver(destino, fim, configuracao);
    }

    public String getAtributo(String idAluguel, String atributo)
    {
        return alugueis.buscar(idAluguel).getAtributo(atributo);
    }
}
