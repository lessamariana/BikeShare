package br.ufal.ic.p2.bikeshare.config;

import java.io.Serializable;
import java.math.BigDecimal;

public class Configuracao implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final BigDecimal tarifaMinutoAvulso = new BigDecimal("0.50");
    private final int franquiaMinutosAssinante = 60;
    private final BigDecimal tarifaMinutoExcedenteAssinante = new BigDecimal("0.30");
    private final BigDecimal mensalidadePadraoAssinante = new BigDecimal("30.00");
    private final int nivelMinimoBateria = 15;
    private final BigDecimal tarifaMinutoAdicionalEletrica = new BigDecimal("0.20");
    private final int carenciaMinutos = 2;
    private final int tempoMaximoMinutos = 1440;
    private final BigDecimal valorPenalidadeAtraso = new BigDecimal("10.00");

    public BigDecimal getTarifaMinutoAvulso()
    {
        return tarifaMinutoAvulso;
    }

    public int getFranquiaMinutosAssinante()
    {
        return franquiaMinutosAssinante;
    }

    public BigDecimal getTarifaMinutoExcedenteAssinante()
    {
        return tarifaMinutoExcedenteAssinante;
    }

    public BigDecimal getMensalidadePadraoAssinante()
    {
        return mensalidadePadraoAssinante;
    }

    // Carga mínima para uma bicicleta elétrica ser retirada

    public int getNivelMinimoBateria()
    {
        return nivelMinimoBateria;
    }

    // Valor extra por minuto cobrado de bicicletas elétricas (mesmo dentro da franquia)

    public BigDecimal getTarifaMinutoAdicionalEletrica()
    {
        return tarifaMinutoAdicionalEletrica;
    }

    // Devolução na mesma estação até este tempo cancela o aluguel sem cobrança

    public int getCarenciaMinutos()
    {
        return carenciaMinutos;
    }

    // Acima deste tempo o aluguel expira
    public int getTempoMaximoMinutos()
    {
        return tempoMaximoMinutos;
    }

    public BigDecimal getValorPenalidadeAtraso()
    {
        return valorPenalidadeAtraso;
    }
}
