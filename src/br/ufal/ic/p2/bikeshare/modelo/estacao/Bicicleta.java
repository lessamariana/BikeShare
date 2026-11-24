package br.ufal.ic.p2.bikeshare.modelo.estacao;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.excecao.BicicletaException;
import br.ufal.ic.p2.bikeshare.util.Atributos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Map.entry;

/*Veículo compartilhado, identificado unicamente, que fica em uma estação enquanto não está em uso
 * O que é comum (id, estado, estação) fica aqui. O que muda entre
 * convencional e elétrica (bateria) tem um comportamento padrão e é sobrescrito apenas pela BicicletaEletrica}.
 */

public abstract class Bicicleta implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final String id;
    private EstadoBicicleta estado = EstadoBicicleta.DISPONIVEL;
    private Estacao estacao;

    protected Bicicleta(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public EstadoBicicleta getEstado()
    {
        return estado;
    }

    public abstract TipoBicicleta getTipo();

    public String getAtributo(String atributo)
    {
        return Atributos.consultar(leitoresDeAtributos(), atributo);
    }

    private Map<String, Supplier<String>> leitoresDeAtributos()
    {
        return Map.ofEntries(entry("tipo", () -> getTipo().getNome()), entry("estado", estado::getNome), entry("estacao", () -> estacao == null ? "nenhuma" : estacao.getId()), entry("nivelBateria", this::getNivelBateria));
    }

    //  Padrão: bicicleta sem bateria. A elétrica sobrescreve

    protected String getNivelBateria()
    {
        throw semBateria();
    }

    //  Pode ser retirada agora? Precisa estar disponível e, se tiver bateria, com carga mínima

    public boolean prontaParaRetirada(Configuracao configuracao)
    {
        return estaDisponivel() && temCargaSuficiente(configuracao);
    }

    public void validarRetirada(Configuracao configuracao)
    {
        if(!estaDisponivel())
        {
            throw new BicicletaException("Bicicleta nao esta disponivel.");
        }
        if(!temCargaSuficiente(configuracao))
        {
            throw new BicicletaException("Nivel de bateria insuficiente.");
        }
    }

    public void iniciarUso()
    {
        estado = EstadoBicicleta.EM_USO;
    }

    public void disponibilizar()
    {
        estado = EstadoBicicleta.DISPONIVEL;
    }

    public void enviarParaManutencao()
    {
        estado = EstadoBicicleta.EM_MANUTENCAO;
    }

    // Valor extra pela bicicleta, somado à tarifa do plano

    public BigDecimal calcularAdicional(long minutos, Configuracao configuracao)
    {
        return BigDecimal.ZERO;
    }

    private boolean estaDisponivel()
    {
        return estado == EstadoBicicleta.DISPONIVEL;
    }

    // Padrão: sem bateria, não há restrição de carga
    protected boolean temCargaSuficiente(Configuracao configuracao)
    {
        return true;
    }

    public boolean estaEm(Estacao local)
    {
        return estacao == local;
    }

    void estacionarEm(Estacao local)
    {
        this.estacao = local;
    }

    void sairDaEstacao()
    {
        this.estacao = null;
    }

    static BicicletaException semBateria()
    {
        return new BicicletaException("Bicicleta nao possui bateria.");
    }
}
