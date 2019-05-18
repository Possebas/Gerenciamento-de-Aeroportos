package pucrs.myflight.modelo;

public class Aeroporto implements Comparable<Aeroporto> {
	private String codigo;
	private String nome;
	private Geo loc;
	private String identificadorPais;
	
	public Aeroporto(String codigo, String nome, Geo loc,String identificadorPais) {
		this.codigo = codigo;
		this.nome = nome;
		this.loc = loc;
		this.identificadorPais = identificadorPais;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Geo getLocal() {
		return loc;
	}

	public String getIdentificadorPais() {
		return identificadorPais;
	}

	@Override
    public String toString() {
        return codigo + " - " + nome + " [" + loc + "] "+identificadorPais;
    }

	@Override
	public int compareTo(Aeroporto outro) {
		return this.nome.compareTo(outro.nome);
	}
}
