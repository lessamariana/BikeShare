package br.ufal.ic.p2.bikeshare.modelo.estacao;

// Bicicleta sem motor

public class BicicletaConvencional extends Bicicleta
{

    private static final long serialVersionUID = 1L;

    public BicicletaConvencional(String id)
    {
        super(id);
    }

    @Override
    public TipoBicicleta getTipo()
    {
        return TipoBicicleta.CONVENCIONAL;
    }
}
