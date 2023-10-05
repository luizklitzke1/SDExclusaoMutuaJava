import java.util.Random;

public class Utilidades 
{

    public static int geraValorEntre(int min, int max)
     {
        return new Random().nextInt(max - min) + min;
    }

    public static int getTempoProcessamento()
    {
        return geraValorEntre(5000, 15000);
    }

    public static int getTempoRequisicao()
    {
        return geraValorEntre(10000, 25000);
    }

    public static int getTempoMatarCoordenador()
    {
        return 60000;
    }

    public static int getTempoNovoProcesso()
    {
        return 40000;
    }
}
