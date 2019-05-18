package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorAeroportos {

    private Map<String, Aeroporto> aeroportos;

    public GerenciadorAeroportos() {
        aeroportos = new HashMap<String, Aeroporto>();
    }

    public void adicionar(Aeroporto aero) {
        aeroportos.put(aero.getCodigo(), aero);
    }

    public static ArrayList<Aeroporto> ordenarCodigo(ArrayList<Aeroporto> aeroportoss) {
        aeroportoss.sort( (Aeroporto a1, Aeroporto a2)
                -> a1.getCodigo().compareTo(a2.getCodigo()));
        return aeroportoss;
    };

    public ArrayList<Aeroporto> toArray() {
        ArrayList<Aeroporto> temaero = new ArrayList<Aeroporto>();
        for(String chave : aeroportos.keySet()) {
            temaero.add(aeroportos.get(chave));
        }
        return temaero;
    }

    public Aeroporto buscarCodigo(String codigo) {
        return aeroportos.get(codigo);
    }

    public static void print (ArrayList<Aeroporto> aeroportos) {
        if(aeroportos == null) return;
        for(Aeroporto a : aeroportos) {
            if(a != null) {
                System.out.println(a);
            }

        }
    }

    public void carregaDadosAeroporto() throws IOException {
        Path path = Paths.get("airports.dat");
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
            String header = sc.nextLine(); // pula cabeçalho
            String codigo, nome, pais;
            String latitude,longitude;
            while (sc.hasNext()) {
                codigo = sc.next();
                latitude = sc.next();
                longitude = sc.next();
                nome = sc.next();
                pais = sc.next().replaceAll("(\r)", "");
                Geo geo = new Geo(Double.parseDouble(latitude), Double.parseDouble(longitude));
                Aeroporto novo = new Aeroporto(codigo, nome, geo, pais);
                adicionar(novo);
            }
        }
        catch (IOException e) {
            System.err.format("Erro:%n", e);
        }
    }
}
