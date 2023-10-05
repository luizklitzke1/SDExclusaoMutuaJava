//Alunos: Arthur B Pinotti, Kaue Reblin, Luiz Gustavo Klitzke

public class Processo 
{
    private  int id;
    private Thread thread;

    public Processo() 
    {
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void Processa(Recurso recurso) {
        try 
        {
            int duracaoExecucao = Utilidades.getTempoProcessamento();
            System.out.println("[INICIO] Processo: " + this.getId() + " | Recurso: " + recurso.getId());
            
            recurso.setUsando(true);
            Thread.sleep(duracaoExecucao);

            System.out.println("[FIM] Processo: " + this.getId() + " | Recurso: " + recurso.getId() + "| Tempo: " + (duracaoExecucao / 1000) + "s");

            ExclusaoMutuaCentralizada.coordenador.liberarRecurso(new Solicitacao(this, recurso));
        } 
        catch (InterruptedException e) 
        {
            recurso.setUsando(false);
            System.out.println("[INTERRUPÇÃO] Processamento do Processo: " + this.getId() + " | Recurso: " + recurso.getId());
        }
    }

    public void requisitaRecurso() 
    {
        this.thread = new Thread(() -> 
        {
            while (true) 
            {
                try 
                {
                    int tempoEntreExecucoes = Utilidades.getTempoProcessamento();
                    Thread.sleep(tempoEntreExecucoes);

                    Recurso recurso = ExclusaoMutuaCentralizada.coordenador.obterRecursoAleatorio();
                    System.out.println("[ENVIADO] Processo: " + this.getId() + "| Após: " + tempoEntreExecucoes / 1000 + "s" + " | Recurso: " + recurso.getId());

                    ExclusaoMutuaCentralizada.coordenador.solicitarAcessoRecurso(new Solicitacao(this, recurso));
                } 
                catch (InterruptedException e) 
                {
                    System.out.println("[INTERRUPÇÃO] Loop de Requisição do Processo: " + this.getId());
                }
            }
        });
        this.thread.start();
    }

    public void reiniciaProcesso() 
    {
        this.thread.interrupt();
        requisitaRecurso();
    }
}