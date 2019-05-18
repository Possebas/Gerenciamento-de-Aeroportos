package pucrs.myflight.modelo;

import java.util.ArrayList;

public class Pais {
    private String cod;
    private String nome;
    private ArrayList<Aeroporto> aeroportos;

    public Pais(String cod, String nome){
        this.cod = cod;
        this.nome = nome;
        this.aeroportos = new ArrayList<Aeroporto>();
    }

    public String getCod() {

        return cod;
    }

    public String getNome() {

        return nome;
    }

    public boolean adicionarAeroporto(Aeroporto aerop)
    {
        return aeroportos.add(aerop);
    }

    public ArrayList<Aeroporto> getAeroportos() {

        return aeroportos;
    }


    @Override
    public String toString() {
        return getNome();
    }
}
