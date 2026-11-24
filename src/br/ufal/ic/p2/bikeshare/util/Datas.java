package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.excecao.ValidacaoException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

// Conversão de datas no formato dos testes: "dia/mês/ano hora:minuto"

public final class Datas
{

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("d/M/uuuu H:mm").withResolverStyle(ResolverStyle.STRICT);

    private Datas() {
    }

    public static LocalDateTime converter(String texto)
    {
        String valor = Texto.exigir(texto, "Data nao pode ser nula.").trim();
        try
        {
            return LocalDateTime.parse(valor, FORMATO);
        }
        catch (DateTimeParseException erro)
        {
            throw new ValidacaoException("Data invalida.");
        }
    }

    public static String formatar(LocalDateTime data)
    {
        return FORMATO.format(data);
    }
}
