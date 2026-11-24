package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.excecao.ValidacaoException;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Conversão e formatação de valores monetários


public final class Dinheiro
{
    private static final String FORMATO_NUMERICO = "-?\\d+([.,]\\d+)?";

    private Dinheiro() {
    }

    // Converte um texto para valor monetário, validando preenchimento e formato

    public static BigDecimal converter(String texto, String mensagemVazio, String mensagemInvalido)
    {
        String valor = Texto.exigir(texto, mensagemVazio).trim();
        if(!valor.matches(FORMATO_NUMERICO))
        {
            throw new ValidacaoException(mensagemInvalido);
        }
        return new BigDecimal(valor.replace(',', '.'));
    }

    // Lança a mensagem informada se o valor for negativo
    public static BigDecimal exigirNaoNegativo(BigDecimal valor, String mensagem)
    {
        if(valor.signum() < 0)
        {
            throw new ValidacaoException(mensagem);
        }
        return valor;
    }

    public static BigDecimal valorPositivo(String texto)
    {
        BigDecimal valor = converter(texto, "Valor nao pode ser nulo.", "Valor deve ser numerico.");
        if(valor.signum() <= 0)
        {
            throw new ValidacaoException("Valor deve ser positivo.");
        }
        return valor;
    }

    // Formata com duas casas e vírgula: 50 vira "50,00"
    public static String formatar(BigDecimal valor)
    {
        return valor.setScale(2, RoundingMode.HALF_UP).toPlainString().replace('.', ',');
    }
}
