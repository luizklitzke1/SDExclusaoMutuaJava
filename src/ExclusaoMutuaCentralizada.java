//Alunos: Arthur B Pinotti, Kaue Reblin, Luiz Gustavo Klitzke

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExclusaoMutuaCentralizada  extends Thread 
{
    private final List<Processo> processos;
    public static Coordenador coordenador;

    @Override
    public void run() 
    {
        System.out.println("Algoritmo de Exclusão Mútua - Centralizado");

        this.encerrarCoordenador();
        this.criarProcesso();
    }

    public ExclusaoMutuaCentralizada () 
    {
        this.processos = new ArrayList<>();
    }

    private void encerrarCoordenador() 
    {
        new Thread(() -> 
        {
            while (true) 
            {
                try 
                {
                    Thread.sleep(Utilidades.getTempoMatarCoordenador());

                    System.out.println("[COORDENADOR] Morto!");
                    coordenador = new Coordenador();
                    System.out.println("[COORDENADOR] Criado!");

                    for (Processo processo : this.processos)
                        processo.reiniciaProcesso();

                } 
                catch (InterruptedException e) 
                {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void criarProcesso() 
    {
        Random gerador = new Random();
        new Thread(() -> 
        {
            while (true) 
            {
                try 
                {
                    Thread.sleep(Utilidades.getTempoNovoProcesso());

                    Processo processo = new Processo();
                    
                    int novoId = 0;
                    do 
                    {
                        novoId = gerador.nextInt(Integer.MAX_VALUE);
                    } 
                    while (testaId(novoId));

                    processo.setId(novoId);
                    processo.requisitaRecurso();
                    this.processos.add(processo);

                    System.out.println("[PROCESSO] Criado: " + processo.getId());
                    imprimirProcessos();
                } 
                catch (InterruptedException e) 
                {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private boolean testaId(int id) 
    {
        return this.processos.stream().anyMatch(p -> p.getId() == id);
    }

    private void imprimirProcessos() 
    {
        String processos = "[PROCESSOS] ";

        for (Processo processo : this.processos)
        {
            processos += processo.getId() + ", ";
        }

        System.out.println(processos + "\n");
    }
}