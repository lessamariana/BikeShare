package br.ufal.ic.p2.bikeshare.modelo.estacao;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.excecao.BicicletaException;
import br.ufal.ic.p2.bikeshare.excecao.EstacaoException;
import br.ufal.ic.p2.bikeshare.util.Numeros;
import br.ufal.ic.p2.bikeshare.util.Texto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Ponto físico com um número fixo de vagas, onde bicicletas ficam disponíveis para retirada e podem ser devolvidas
// É a única classe que mexe na relação estação-bicicleta: ao estacionar ou tirar uma bicicleta, atualiza a lista e a localização da bicicleta

public class Estacao implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String nome;
    private final String endereco;
    private final int capacidade;
    private final List<Bicicleta> bicicletas = new ArrayList<>();

    public Estacao(String id, String nome, String endereco, String capacidade)
    {
        this.id = id;
        this.nome = Texto.exigir(nome, "Nome nao pode ser nulo.");
        this.endereco = Texto.exigir(endereco, "Endereco nao pode ser nulo.");
        this.capacidade = validarCapacidade(capacidade);
    }

    private static int validarCapacidade(String texto)
    {
        int valor = Numeros.inteiro(texto, "Capacidade nao pode ser nula.", "Capacidade deve ser numerica.");
        if(valor <= 0)
        {
            throw new EstacaoException("Capacidade deve ser positiva.");
        }
        return valor;
    }

    public String getId()
    {
        return id;
    }

    public String getNome()
    {
        return nome;
    }

    public String getEndereco()
    {
        return endereco;
    }

    // Vagas livres = capacidade - bicicletas presentes

    public int getVagasDisponiveis()
    {
        return capacidade - bicicletas.size();
    }

    // Bicicletas prontas para retirada (elétrica com pouca carga ocupa vaga, mas não conta)

    public long getBicicletasDisponiveis(Configuracao configuracao)
    {
        return bicicletas.stream().filter(bicicleta -> bicicleta.prontaParaRetirada(configuracao)).count();
    }

    // Cadastro de uma bicicleta nova pelo operador (US6)

    public void receberNova(Bicicleta bicicleta)
    {
        if(getVagasDisponiveis() == 0)
        {
            throw new EstacaoException("Estacao sem vagas disponiveis.");
        }
        estacionar(bicicleta);
    }

    // Devolução só é aceita com vaga livre caso contrário o aluguel continua em andamento

    public void exigirVagaParaDevolucao()
    {
        if(getVagasDisponiveis() == 0)
        {
            throw new EstacaoException("Estacao cheia.");
        }
    }

    public void receberDevolucao(Bicicleta bicicleta)
    {
        exigirVagaParaDevolucao();
        estacionar(bicicleta);
    }

    // Bicicleta em uso não está em estação nenhuma

    public void exigirPresenca(Bicicleta bicicleta)
    {
        if(!bicicleta.estaEm(this))
        {
            throw new BicicletaException("Bicicleta nao esta nesta estacao.");
        }
    }

    // Tira a bicicleta da estação (remoção pelo operador ou retirada pelo usuário)

    public void liberar(Bicicleta bicicleta)
    {
        exigirPresenca(bicicleta);
        bicicletas.remove(bicicleta);
        bicicleta.sairDaEstacao();
    }

    private void estacionar(Bicicleta bicicleta)
    {
        bicicletas.add(bicicleta);
        bicicleta.estacionarEm(this);
    }
}
