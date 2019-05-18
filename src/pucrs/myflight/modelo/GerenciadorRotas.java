package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorRotas {

    private ArrayList<Rota> rotas;

    public GerenciadorRotas() {

        this.rotas = new ArrayList<>();
    }

    public void ordenarCias() {
        rotas.sort((Rota r1, Rota r2) -> r1.getCia().getNome().compareTo(r2.getCia().getNome()));
    }

    public void ordenarNomesCias() {
        rotas.sort((Rota r1, Rota r2) ->
                r1.getCia().getNome().compareTo(
                        r2.getCia().getNome()));
    }

    public void ordenarNomesAeroportos() {
        rotas.sort((Rota r1, Rota r2) ->
                r1.getOrigem().getNome().compareTo(
                        r2.getOrigem().getNome()));
    }

    public void ordenarNomesAeroportosCias() {
        rotas.sort((Rota r1, Rota r2) -> {
            int result = r1.getOrigem().getNome().compareTo(
                    r2.getOrigem().getNome());
            if (result != 0)
                return result;
            return r1.getCia().getNome().compareTo(
                    r2.getCia().getNome());
        });
    }

    public void adicionar(Rota r) {

        rotas.add(r);
    }

    public ArrayList<Rota> listarTodas() {

        return new ArrayList<>(rotas);
    }

    public ArrayList<Rota> buscarOrigem(String codigo) {
        ArrayList<Rota> result = new ArrayList<>();
        for (Rota r : rotas)
            if (r.getOrigem().getCodigo().equals(codigo))
                result.add(r);
        return result;
    }

    public GerenciadorAeronaves buscarAero(String codigo,String codigo2) {
        GerenciadorAeronaves result = new GerenciadorAeronaves();
        for (Rota r : rotas)
            if (r.getOrigem().getCodigo().equalsIgnoreCase(codigo)&&r.getDestino().getCodigo().equalsIgnoreCase(codigo2))
                result.adicionar(r.getAeronave());
        return result;
    }

    public GerenciadorRotas buscarOrigemG(String codigo) {
        GerenciadorRotas result = new GerenciadorRotas();
        for (Rota r : rotas)
            if (r.getOrigem().getCodigo().equals(codigo))
                result.adicionar(r);
        return result;
    }

    public GerenciadorRotas buscarDestinoG(String codigo) {
        GerenciadorRotas result = new GerenciadorRotas();
        for (Rota r : rotas)
            if (r.getDestino().getCodigo().equals(codigo))
                result.adicionar(r);
        return result;
    }


    public void carregaDadosRotas(GerenciadorCias gerCias, GerenciadorAeroportos gerAero, GerenciadorAeronaves gerAvioes) throws IOException {
        Path path = Paths.get("routes.dat");
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n ]"); // separadores: ; e nova linha
            String header = sc.nextLine(); // pula cabeçalho
            String ciaO, oriO, destO, equipO;
            int linha = 1;
            while (sc.hasNext()) {
                ciaO = sc.next().replaceAll("(\r)", "");
                oriO = sc.next().replaceAll("(\r)", "");
                destO = sc.next().replaceAll("(\r)", "");
                sc.next();
                sc.next();
                equipO = sc.next().replaceAll("(\r)", "");
                sc.nextLine();
                linha++;
                CiaAerea cia = gerCias.buscarCodigo(ciaO);
                Aeroporto origem = gerAero.buscarCodigo(oriO);
                Aeroporto destino = gerAero.buscarCodigo(destO);
                Aeronave aeronave = gerAvioes.buscarCodigo(equipO);
                Rota r = new Rota(cia, origem, destino, aeronave);
                adicionar(r);
            }
        } catch (IOException e) {
            System.err.format("Erro", e);
        }
    }

    public ArrayList<Rota> toArray() {
        return rotas;
    }
}


