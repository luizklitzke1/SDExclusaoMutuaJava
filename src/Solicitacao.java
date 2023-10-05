//Alunos: Arthur B Pinotti, Kaue Reblin, Luiz Gustavo Klitzke

public class Solicitacao 
{
    private final Processo processo;
    private final Recurso recurso;

    public Solicitacao(Processo processo, Recurso recurso)
    {
        this.processo = processo;
        this.recurso = recurso;
    }

    public Processo getProcesso() 
    {
        return this.processo;
    }

    public Recurso getRecurso() 
    {
        return this.recurso;
    }
}