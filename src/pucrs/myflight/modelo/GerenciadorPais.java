package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorPais {
    private Map<String, Pais> paises;

    public GerenciadorPais(){

        paises = new HashMap<>();
    }

    public void adicionar(String valor, Pais pais) {
        paises.put(valor, pais);
    }

    public void adicionaAeroporto(String valor, Aeroporto aero) {
        Pais p = paises.get(valor);
        p.adicionarAeroporto(aero);
    }

    public static void ordenaPorNome(ArrayList<Pais> listaDePaises) {
        listaDePaises.sort((Pais p1, Pais p2) ->
                p1.getNome().compareTo(p2.getNome()));
        }

    public void carregaDadosPais() throws IOException {
        Path path = Paths.get("countries.dat");
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
            String header = sc.nextLine(); // pula cabeçalho
            String cod, nome;
            while (sc.hasNext()) {
                cod = sc.next();
                nome = sc.next().replaceAll("(\r)", "");
                Pais nova = new Pais(cod, nome);
                adicionar(cod,nova);
            }
        }
    }

    public Map<String, Pais> getPaises() {
        return paises;
    }

    public ArrayList<Pais> listarTodas() {

        return new ArrayList<>(paises.values());
    }

    public List<Pais> ProcuraPais(GerenciadorPais paisX){
        List<Pais> lista = new LinkedList<>();
        for (Pais a:paisX.listarTodas()) {
            lista.add(a);
        }
        return lista;
    }

    public Pais getPais(String valor) {
        return paises.get(valor);
    }

    public ArrayList<Pais> toArray() {
        ArrayList<Pais> paises1 = new ArrayList<Pais>();
        for(String valor : paises.keySet()) {
            paises1.add(paises.get(valor));
        }
        ordenaPorNome(paises1);
        return paises1;
    }

    public ArrayList<Pais> toArrayOrdenado() {
        ArrayList<Pais> tempais = new ArrayList<Pais>();
        for(String valor : paises.keySet()) {
            tempais.add(paises.get(valor));
        }
        ordenaPorNome(tempais);
        return tempais;
    }


    public void carregaAeroportos(GerenciadorAeroportos gerAero) {
        for(int i = 0; i < gerAero.toArray().size(); i++) {
            Aeroporto a = gerAero.toArray().get(i);
            String CodigoPais = a.getIdentificadorPais().replaceAll("(\r)", "");
            Pais p = paises.get(CodigoPais);
            p.adicionarAeroporto(a);
        }
    }
}