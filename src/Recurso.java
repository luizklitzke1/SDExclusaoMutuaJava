//Alunos: Arthur B Pinotti, Kaue Reblin, Luiz Gustavo Klitzke

public class Recurso 
{
    private int id;
    private boolean usando;

    public Recurso(int id) 
    {
        this.id = id;
    }

    public int getId() 
    {
        return id;
    }

    public boolean usando() 
    {
        return usando;
    }

    public void setUsando(boolean sendoAcessado)
    {
        this.usando = sendoAcessado;
    }

    @Override
    public int hashCode() 
    {
        return this.getId();
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
        {
            return true;
        }
        else if (obj == null)
        {
            return false;
        }
        else if (obj instanceof Recurso)
        {
            Recurso recurso = (Recurso) obj;
            return recurso.getId() == this.getId();
        }
        else
        {
            return false;
        }
    }
}