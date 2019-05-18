package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorAeronaves {

	private Map<String, Aeronave> avioes;

	public GerenciadorAeronaves() {
		this.avioes = new LinkedHashMap<>();
	}

	public void adicionar(Aeronave aviao) {
		avioes.put(aviao.getCodigo(),aviao);
	}

	public ArrayList<Aeronave> listarTodas() {
		return new ArrayList<>(avioes.values());
	}

	public Aeronave buscarCodigo(String codigo) {
		return avioes.get(codigo);
	}

    public static void ordenaCodigo(ArrayList<Aeronave> Aaeronaves) {
        Collections.sort(Aaeronaves);
        Aaeronaves.sort( (Aeronave a1, Aeronave a2)
                -> a1.getDescricao().compareTo(a2.getCodigo()));
        Aaeronaves.sort(Comparator.comparing(a -> a.getCodigo()));
        Aaeronaves.sort(Comparator.comparing(Aeronave::getCodigo));
    }

    public ArrayList<Aeronave> toArray() {
        ArrayList<Aeronave> temp_avioes = new ArrayList<Aeronave>();
        for(String cod : avioes.keySet()) {
            temp_avioes.add(avioes.get(cod));
        }
        return temp_avioes;
    }

    public ArrayList<Aeronave> ArrayOrdenado() {
        ArrayList<Aeronave> temavioes = new ArrayList<Aeronave>();
        for(String cod : avioes.keySet()) {
            temavioes.add(avioes.get(cod));
        }
        ordenaCodigo(temavioes);
        return temavioes;
    }

    public void carregaDadosAvioes() throws IOException {
                Path path = Paths.get("equipment.dat");
                try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
                        sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
                        String header = sc.nextLine(); // pula cabeçalho
                        String codigo = "";
                        String descricao = "";
                        int capacidade;
                        while (sc.hasNext()) {
                                codigo = sc.next();
                                descricao = sc.next();
                                capacidade = Integer.parseInt(sc.next().replaceAll("(\r)", ""));
                                Aeronave nova = new Aeronave(codigo, descricao,capacidade);
                                adicionar(nova);
                        }
                }
    }
}
