package br.ufal.ic.p2.bikeshare.sistema;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.modelo.plano.CatalogoPlanos;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioUsuarios;

import java.io.Serializable;

public class EstadoSistema implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final Configuracao configuracao = new Configuracao();
    private final CatalogoPlanos planos = new CatalogoPlanos(configuracao);
    private final RepositorioUsuarios usuarios = new RepositorioUsuarios();
    private final RepositorioEstacoes estacoes = new RepositorioEstacoes();
    private final RepositorioBicicletas bicicletas = new RepositorioBicicletas();
    private final RepositorioAlugueis alugueis = new RepositorioAlugueis();

    public Configuracao getConfiguracao()
    {
        return configuracao;
    }

    public CatalogoPlanos getPlanos()
    {
        return planos;
    }

    public RepositorioUsuarios getUsuarios()
    {
        return usuarios;
    }

    public RepositorioEstacoes getEstacoes()
    {
        return estacoes;
    }

    public RepositorioBicicletas getBicicletas()
    {
        return bicicletas;
    }

    public RepositorioAlugueis getAlugueis()
    {
        return alugueis;
    }
}
