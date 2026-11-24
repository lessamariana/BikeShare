package br.ufal.ic.p2.bikeshare.modelo.estacao;


public enum EstadoBicicleta
{

    DISPONIVEL("disponivel"), EM_USO("emUso"), EM_MANUTENCAO("emManutencao");

    private final String nome;

    EstadoBicicleta(String nome)
    {
        this.nome = nome;
    }

    public String getNome()
    {
        return nome;
    }
}
