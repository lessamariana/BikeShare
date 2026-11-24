package br.ufal.ic.p2.bikeshare.modelo.estacao;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.excecao.BicicletaException;
import br.ufal.ic.p2.bikeshare.util.Numeros;

import java.math.BigDecimal;

// Bicicleta com bateria: só pode ser retirada com carga mínima

public class BicicletaEletrica extends Bicicleta
{

    private static final long serialVersionUID = 1L;

    private static final int CARGA_MAXIMA = 100;

    private final int nivelBateria;

    public BicicletaEletrica(String id, String nivelBateria)
    {
        super(id);
        this.nivelBateria = validarNivel(nivelBateria);
    }

    private static int validarNivel(String texto)
    {
        int nivel = Numeros.inteiro(texto, "Nivel de bateria nao pode ser nulo.", "Nivel de bateria deve ser numerico.");
        if(nivel < 0 || nivel > CARGA_MAXIMA)
        {
            throw new BicicletaException("Nivel de bateria deve estar entre 0 e 100.");
        }
        return nivel;
    }

    @Override
    public TipoBicicleta getTipo()
    {
        return TipoBicicleta.ELETRICA;
    }

    @Override
    protected String getNivelBateria()
    {
        return String.valueOf(nivelBateria);
    }

    // A elétrica paga um adicional por minuto, independente do plano

    @Override
    public BigDecimal calcularAdicional(long minutos, Configuracao configuracao)
    {
        return configuracao.getTarifaMinutoAdicionalEletrica().multiply(BigDecimal.valueOf(minutos));
    }

    @Override
    protected boolean temCargaSuficiente(Configuracao configuracao)
    {
        return nivelBateria >= configuracao.getNivelMinimoBateria();
    }
}
