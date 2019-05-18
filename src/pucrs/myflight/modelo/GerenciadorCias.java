package pucrs.myflight.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorCias {
    private Map<String, CiaAerea> empresas;
    private static GerenciadorCias gc = new GerenciadorCias();

    public GerenciadorCias() {
//        this.empresas = new HashMap<>();
//        this.empresas = new TreeMap<>();
        this.empresas = new LinkedHashMap<>();
    }

    public ArrayList<CiaAerea> listarTodas() {

        return new ArrayList<>(empresas.values());
    }


    public void carregaDadosCias() throws IOException {
        Path path = Paths.get("airlines.dat");
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
            String header = sc.nextLine(); // pula cabeçalho
            String cod, nome;
            while (sc.hasNext()) {
                cod = sc.next();
                nome = sc.next().replaceAll("(\r)", "");
                CiaAerea nova = new CiaAerea(cod, nome);
                adicionar(nova);
                //System.out.format("%s - %s (%s)%n", nome, data, cpf);
            }
        }
    }

    public void adicionar(CiaAerea cia1) {
        empresas.put(cia1.getCodigo(),
                cia1);
    }

    public CiaAerea buscarCodigo(String cod) {
        return empresas.get(cod);
//        for (CiaAerea cia : empresas)
//            if (cia.getCodigo().equals(cod))
//                return cia;
//        return null;
    }

    public static GerenciadorCias getInstance() {

        return gc;
    }

    public List<CiaAerea> ProcuraCia(GerenciadorCias ciaX){
        List<CiaAerea> lista = new LinkedList<>();
        for (CiaAerea a:ciaX.listarTodas()) {
            lista.add(a);
        }
        return lista;
    }

    public ObservableList getListCia(GerenciadorCias ciaX){

        return FXCollections.observableList(ProcuraCia(ciaX));
    }


    public CiaAerea buscarNome(String nome) {
        for(CiaAerea cia: empresas.values())
           if(cia.getNome().equals(nome))
               return cia;
        return null;
    }
}
