package pucrs.myflight.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import pucrs.myflight.modelo.*;
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorPais gerPais;
	private GerenciadorCias gerCias;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorMapa gerenciador;

	private EventosMouse mouse;

	private BorderPane pane;
	private TextArea outputTextArea;

	private double defaultToolboxItemWidth = 240.0;
	private double defaultToolboxItemHeight = 20.0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		setup();

		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		createSwingContent(mapkit);

		setupInterface(); // interface gráfica
		gerenciador.setMaxZoomText(15);

		Scene scene = new Scene(pane, 1000, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MyFlight");
		primaryStage.show();
	}

		// Inicializando os dados aqui
	    // LEITURA DOS ARQUIVOS .DAT (REORGANIZADO POIS ESTAVA DANDO CONFLITO ENTRE AS CONSULTAS

		private void setup () {
			gerPais = new GerenciadorPais();
			try {
				gerPais.carregaDadosPais();
			} catch (IOException e) {
				System.err.println("Nao foi possivel ler o arquivo countries.dat!");
			}

			gerCias = new GerenciadorCias();
			try {
				gerCias.carregaDadosCias();
			} catch (IOException e) {
				System.err.println("Nao foi possivel ler o arquivo airlines.dat!");
			}

			gerAero = new GerenciadorAeroportos();
			try {
				gerAero.carregaDadosAeroporto();
			} catch (IOException e) {
				System.err.println("Nao foi possivel ler o arquivo airports.dat!");
			}

			gerPais.carregaAeroportos(gerAero);

			gerAvioes = new GerenciadorAeronaves();
			try {
				gerAvioes.carregaDadosAvioes();
			} catch (IOException e) {
				System.err.println("Nao foi possivel ler o arquivo equipment.dat!");
			}

			gerRotas = new GerenciadorRotas();
			try {
				gerRotas.carregaDadosRotas(gerCias, gerAero, gerAvioes);
			} catch (IOException e) {
				System.err.println("Nao foi possivel ler o arquivo routes.dat!");
			}
		}


	private void setupInterface () {

			pane = new BorderPane();
			pane.setPadding(new Insets(3, 3, 3, 3));

            //LABELS DE CONSULTA
			Label labelConsulta1 = new Label();
			labelConsulta1.setText("Rotas de uma Cia Aérea");
			labelConsulta1.setMinHeight(defaultToolboxItemHeight);
			labelConsulta1.setAlignment(Pos.TOP_LEFT);
			labelConsulta1.setMinWidth(defaultToolboxItemWidth);
			labelConsulta1.setPrefWidth(defaultToolboxItemWidth);
			labelConsulta1.setMaxWidth(defaultToolboxItemWidth);

			Label labelConsulta2 = new Label();
			labelConsulta2.setMinHeight(defaultToolboxItemHeight);
			labelConsulta2.setAlignment(Pos.TOP_LEFT);
			labelConsulta2.setMinWidth(defaultToolboxItemWidth);
			labelConsulta2.setPrefWidth(defaultToolboxItemWidth);
			labelConsulta2.setMaxWidth(defaultToolboxItemWidth);

		Label labelConsulta3 = new Label();
		labelConsulta3.setMinHeight(defaultToolboxItemHeight);
		labelConsulta3.setAlignment(Pos.TOP_LEFT);
		labelConsulta3.setMinWidth(defaultToolboxItemWidth);
		labelConsulta3.setPrefWidth(defaultToolboxItemWidth);
		labelConsulta3.setMaxWidth(defaultToolboxItemWidth);


			Label labelConsulta4 = new Label();
			labelConsulta4.setMinHeight(defaultToolboxItemHeight);
			labelConsulta4.setAlignment(Pos.TOP_LEFT);
			labelConsulta4.setMinWidth(defaultToolboxItemWidth);
			labelConsulta4.setPrefWidth(defaultToolboxItemWidth);
			labelConsulta4.setMaxWidth(defaultToolboxItemWidth);

			VBox toolbox = new VBox();
			toolbox.setSpacing(5);
			toolbox.setPadding(new Insets(5, 5, 5, 0));

            // BUTTONS DE CONSULTA
			Button btnConsulta1 = new Button("Consulta 1");
			Button btnConsulta2 = new Button("Consulta 2");
			Button btnConsulta3 = new Button("Consulta 3");
			Button btnConsulta4 = new Button("Consulta 4");


			// DEFINININDO TAMANHO DOS BOTÕES
			btnConsulta1.setMinWidth(defaultToolboxItemWidth*1/2);
			btnConsulta1.setPrefWidth(defaultToolboxItemWidth*1/2);
			btnConsulta1.setMaxWidth(defaultToolboxItemWidth*1/2);
			btnConsulta2.setMinWidth(defaultToolboxItemWidth*1/2);
			btnConsulta2.setPrefWidth(defaultToolboxItemWidth*1/2);
			btnConsulta2.setMaxWidth(defaultToolboxItemWidth*1/2);
			btnConsulta3.setMinWidth(defaultToolboxItemWidth);
			btnConsulta3.setPrefWidth(defaultToolboxItemWidth);
			btnConsulta3.setMaxWidth(defaultToolboxItemWidth);
			btnConsulta4.setMinWidth(defaultToolboxItemWidth);
			btnConsulta4.setPrefWidth(defaultToolboxItemWidth);
			btnConsulta4.setMaxWidth(defaultToolboxItemWidth);

			//Criação das ComboBox
			ComboBox<CiaAerea> comboCia = new ComboBox<CiaAerea>();
			ComboBox<Pais> comboTrafego = new ComboBox<Pais>();
			comboTrafego.setMinWidth(defaultToolboxItemWidth * 2 / 3);
			comboTrafego.setPrefWidth(defaultToolboxItemWidth * 2 / 3);
			comboTrafego.setMaxWidth(defaultToolboxItemWidth * 2 / 3);


			//*LISTA DA CONSULTA 1
			HBox hb1 = new HBox();
			hb1.getChildren().add(comboCia);
			hb1.getChildren().add(btnConsulta1);
			toolbox.getChildren().add(hb1);

			toolbox.getChildren().add(new Separator());

			toolbox.getChildren().add(labelConsulta1);
			toolbox.getChildren().add(btnConsulta1);
			toolbox.getChildren().add(comboCia);

			//Consulta 2
			//Adicionando uma lista para escolha de algum país específico ou os maiores à nível mundial para a consulta 2
			labelConsulta2.setText("Volume de Tráfego");
			toolbox.getChildren().add(labelConsulta2);
			HBox hb2 = new HBox();
			hb2.getChildren().add(comboTrafego);
			hb2.getChildren().add(btnConsulta2);
			toolbox.getChildren().add(hb2);

			toolbox.getChildren().add(new Separator());

			// CRIAÇÃO DE UMA CAIXA DE TEXTO, CONTENDO A SAÍDA DAS CONSULTAS REALIZADAS
			outputTextArea = new TextArea();
			outputTextArea.setText("Nenhuma consulta feita");
			outputTextArea.setMinWidth(defaultToolboxItemWidth);
			outputTextArea.setPrefWidth(defaultToolboxItemWidth);
			outputTextArea.setMaxWidth(defaultToolboxItemWidth);
			outputTextArea.setMinHeight(defaultToolboxItemHeight * 3);
			outputTextArea.setPrefHeight(defaultToolboxItemHeight * 15);
			outputTextArea.setFont(Font.font("Verdana", 9));
			toolbox.getChildren().add(outputTextArea);


			//*ADICIONANDO UMA LISTA PARA ESCOLHA DA CIA NA CONSULTA 1
			comboCia.setItems(gerCias.getListCia(gerCias));
			comboCia.setMinWidth(defaultToolboxItemWidth * 2 / 3);
			comboCia.setPrefWidth(defaultToolboxItemWidth * 2 / 3);
			comboCia.setMaxWidth(defaultToolboxItemWidth * 2 / 3);
			comboCia.setOnAction((event) -> {
				CiaAerea escolido1 = comboCia.getSelectionModel().getSelectedItem();
				btnConsulta1.setOnAction(e -> {
					consulta1(escolido1);
				});
			});

			// ADICIONANDO A OPÇÃO PARA OS AEROPORTOS DO MUNDO E POPULANDO O COMBOBOX COM OS OUTROS PAÍSES
			Pais todos = new Pais("Mundo", "Mundialmente");
			for (Aeroporto a : gerAero.toArray()) {
				todos.getAeroportos().add(a);
			}

			comboTrafego.setItems(FXCollections.observableArrayList(gerPais.toArrayOrdenado()));
			comboTrafego.getItems().add(0, todos);
			comboTrafego.getSelectionModel().selectFirst();

			btnConsulta2.setOnAction(e -> {
				consulta2(comboTrafego.getValue());
			});

		//*CONSULTA 3


			TextField textoConsulta3A = new TextField();
			textoConsulta3A.setMinWidth(defaultToolboxItemWidth/4);
			textoConsulta3A.setPrefWidth(defaultToolboxItemWidth/4);
			textoConsulta3A.setMaxWidth(defaultToolboxItemWidth/4);

		    TextField textoConsulta3B = new TextField();
	    	textoConsulta3B.setMinWidth(defaultToolboxItemWidth/4);
	    	textoConsulta3B.setPrefWidth(defaultToolboxItemWidth/4);
		    textoConsulta3B.setMaxWidth(defaultToolboxItemWidth/4);

	    	ComboBox<String> comboSelecionaRota = new ComboBox<String>();
	    	comboSelecionaRota.setMinWidth(defaultToolboxItemWidth/4);
	    	comboSelecionaRota.setPrefWidth(defaultToolboxItemWidth/4);
	    	comboSelecionaRota.setMaxWidth(defaultToolboxItemWidth/4);

	    	labelConsulta3.setText("Procura rota entre até dois aeroportos\npelos seus codigos:");
	    	toolbox.getChildren().add(labelConsulta3);
	    	HBox hb3labels = new HBox();
	    	Label labelOrigem = new Label();
	    	labelOrigem.setAlignment(Pos.TOP_LEFT);
	    	labelOrigem.setMinWidth(defaultToolboxItemWidth/4);
	    	labelOrigem.setPrefWidth(defaultToolboxItemWidth/4);
	    	labelOrigem.setMaxWidth(defaultToolboxItemWidth/4);
	    	Label labelDestino = new Label();
	    	labelDestino.setAlignment(Pos.TOP_LEFT);
	    	labelDestino.setMinWidth(defaultToolboxItemWidth/4);
	    	labelDestino.setPrefWidth(defaultToolboxItemWidth/4);
		    labelDestino.setMaxWidth(defaultToolboxItemWidth/4);
	    	Label labelConsutaCod = new Label();
	    	labelConsutaCod.setAlignment(Pos.TOP_LEFT);
	    	labelConsutaCod.setMinWidth(defaultToolboxItemWidth/4);
	    	labelConsutaCod.setPrefWidth(defaultToolboxItemWidth/4);
	    	labelConsutaCod.setMaxWidth(defaultToolboxItemWidth/4);
		    labelOrigem.setText("Origem"); labelDestino.setText("Destino");
		    labelConsutaCod.setText("Rotas possiveis");
		    labelOrigem.setFont(Font.font ("Verdana", 9));
	    	labelDestino.setFont(Font.font ("Verdana", 9));
	    	labelConsutaCod.setFont(Font.font ("Verdana", 9));
	    	hb3labels.getChildren().addAll(labelOrigem, labelDestino, labelConsutaCod);
	    	toolbox.getChildren().add(hb3labels);
	    	HBox hb3 = new HBox();
	    	hb3.getChildren().add(textoConsulta3A);
	    	hb3.getChildren().add(textoConsulta3B);
	    	hb3.getChildren().add(comboSelecionaRota);
	    	toolbox.getChildren().add(hb3);
	    	toolbox.getChildren().add(btnConsulta3);

		    toolbox.getChildren().add(new Separator());

		//*ADICIONANDO AS LISTAS PARA ESCOLHA DOS AEROPORTOS NA CONSULTA 3
		textoConsulta3A.setText("POA");
		textoConsulta3B.setText("LHR");


		btnConsulta3.setOnAction(e -> {
			Aeroporto origem = gerAero.buscarCodigo(textoConsulta3A.getText().toUpperCase());
			Aeroporto destino = gerAero.buscarCodigo(textoConsulta3B.getText().toUpperCase());
			consulta3(origem, destino,comboSelecionaRota);
		});

		//*CONSULTA 4

		labelConsulta4.setText("Rotas em um período de 12h");
		toolbox.getChildren().add(labelConsulta4);
		HBox hb4 = new HBox();
		hb4.getChildren().add(btnConsulta4);
		toolbox.getChildren().add(hb4);


		pane.setCenter(mapkit);
			pane.setLeft(toolbox);

	}

		private void consulta1 (CiaAerea escolido1){

		outputTextArea.clear();
			// Lista para armazenar o resultado da consulta
			List<MyWaypoint> lstPoints = new ArrayList<>();

			// *Desenhar todos os aeroportos onde uma determinada companhia aérea opera. Mostre também as rotas envolvidas.
			ArrayList<Aeroporto> desenharAero = new ArrayList<>();
			//CiaAerea cia = new CiaAerea("3J","Jubba Airways Ltd");

			ArrayList<Tracado> linhasDeRota = new ArrayList<>();

			for (Rota r : gerRotas.listarTodas()) {
				//if(r.getCia().getCodigo().equals("3J")){
				if (r.getCia().getCodigo().equals(escolido1.getCodigo())) {
					desenharAero.add(r.getOrigem());
					desenharAero.add(r.getDestino());

					Tracado tr = new Tracado();
					//tr.setLabel("Teste");
					tr.setWidth(5);
					tr.setCor(new Color(0, 0, 0, 60));
					tr.addPonto(r.getOrigem().getLocal());
					tr.addPonto(r.getDestino().getLocal());
					linhasDeRota.add(tr);
					double rotaF = r.getOrigem().getLocal().distancia(r.getDestino().getLocal());
					DecimalFormat df = new DecimalFormat("0.##");
					String dx = df.format(rotaF);
					// Quando uma rota é exibida, deve-se mostrar também a distância entre os pontos e a aeronave sendo utilizada.
					outputTextArea.appendText("Rota: " + r.getOrigem().getCodigo() + " -> " + r.getDestino().getCodigo()
							+ "\nDistancia da Rota: " +dx+ " quilômetros"
							+ "\nAeronave: " + r.getAeronave().getCodigo() + " - " + r.getAeronave().getDescricao() + "\n\n");
				}
			}


			gerenciador.clear();
			// Adiciona os locais de cada aeroporto (sem repetir) na lista de
			// waypoints

			//*DESENHO DA CONSULTA 1
			for (Aeroporto a : desenharAero) {
				lstPoints.add(new MyWaypoint(Color.BLUE, a.getCodigo(), a.getLocal(), 5));
			}
			for (Tracado t : linhasDeRota) {
				gerenciador.addTracado(t);
			}

			gerenciador.setPontos(lstPoints);

			// Quando for o caso de limpar os traçados...
			// gerenciador.clear();

			gerenciador.getMapKit().repaint();
		}

		private void consulta2(Pais qualquer) {
			outputTextArea.setText("Aeroportos com maior volume de tráfego \n\n");
			Map<Aeroporto, Integer> trafego = new HashMap<Aeroporto, Integer>();
			for (Aeroporto a : qualquer.getAeroportos()) {
				trafego.put(a, 0);
			}
			// Verifica lista de rotas origem e destino
			for (Rota r : gerRotas.toArray()) {
				Aeroporto origem = r.getOrigem();
				Aeroporto destino = r.getDestino();


				//Os if abaixo são responsáveis por popular as origens e possíveis destino
				if (trafego.containsKey(origem)) {  //Se o trafego conter a origem que está sendo passada por parâmetro
					Integer valor = trafego.get(origem);  // A variável valor vai receber a origem
					if (valor == null) {
						valor = 0;
					}
					valor++; // é incrementado
					trafego.put(origem, valor);  //Dentro do HashMap de trafego é adicionado o valor e a string origem
				}

				if (trafego.containsKey(destino)) {
					Integer valor2 = trafego.get(destino);
					if (valor2 == null) {
						valor2 = 0;
					}
					valor2++; // é incrementado
					trafego.put(destino, valor2);  //Dentro do HashMap de trafego é adicionado o valor e a string origem
				}
			}


			List<Entry<Aeroporto, Integer>> resultado = trafego.entrySet().stream()
					.sorted(Entry.comparingByValue(Comparator.reverseOrder())) // Comparando os valores com um Comparator e tendo que usar um reverseOrder, para aparecer em ordem dos maiores dos menores
					.limit(15) // Quantidade de dados a serem mostrados na saída
					.collect(Collectors.toList());


			List<MyWaypoint> PontosT = new ArrayList<>();
			gerenciador.clear();
			int trafegoTotal = 0;
			for (Entry<Aeroporto, Integer> entry : resultado) {
				trafegoTotal += entry.getValue(); //variável vai pegar o valor de entrada de resultado
			}

			for (Entry<Aeroporto, Integer> entry : resultado) {
				Aeroporto a = entry.getKey();
				int count = entry.getValue();
				int tamanho; // Variavel pro tamanho do círculo do aeroporto
				Color waypointColor = Color.YELLOW;
				if (trafegoTotal != 0 && count != 0) { // Caso o Aeroporto receba um número consideravel de vôos, ele vai ter um
					tamanho = (count * 70) / (2 * 160);// tamanho específico.
				} else {
					tamanho = 3;                       //Caso o tráfego total seja igual a 0, o aeroporto vai ter um tamanho padrão
					waypointColor = Color.RED;
				}
				PontosT.add(new MyWaypoint(waypointColor, a.getNome() + " (" + count + ")", a.getLocal(), tamanho));
				outputTextArea.setText(outputTextArea.getText() + count + " vôos realizados  (" + entry.getKey().getCodigo() + ") - " + entry.getKey().getNome() + "\n");
			}

			gerenciador.setPontos(PontosT);
			gerenciador.getMapKit().repaint();
		}
	private void consulta3(Aeroporto origem,Aeroporto destino,ComboBox comboSelecionaRota) {
		// Lista para armazenar o resultado da consulta
		List<MyWaypoint> lstPoints = new ArrayList<>();
		ArrayList<Tracado> linhasDeRota = new ArrayList<>();

		outputTextArea.clear();
		//Selecionar um aeroporto de origem e um de destino e mostrar todas as rotas possíveis entre os dois,
		// considerando que haverá no máximo 2 conexões entre eles. Por exemplo,
		// se houver as rotas POA->GRU->LHR e POA->GIG->LHR, esta consulta deve desenhar todas elas.


		ArrayList<String> textoConsulta3 = new ArrayList<>();
		ArrayList<String> lol = new ArrayList<>();
		ArrayList<String> lol2 = new ArrayList<>();
		int solo = 0;


		for (Rota r :gerRotas.listarTodas()) {
			if (origem.getCodigo().equals(r.getOrigem().getCodigo())) {
				if (destino.getCodigo().equals(r.getDestino().getCodigo())){
					textoConsulta3.add(r.getOrigem().getCodigo()+" -> "+r.getDestino().getCodigo()+"\n");
					solo++;
					lol2.add(r.getAeronave().getCodigo()+" - "+r.getAeronave().getDescricao());
				}else for (Rota r2:gerRotas.listarTodas()) {
					if (r.getDestino().getCodigo().equals(r2.getOrigem().getCodigo())&&
							r2.getDestino().getCodigo().equals(destino.getCodigo())) {
						textoConsulta3.add(origem.getCodigo() + " -> "
								+ r.getDestino().getCodigo() + " -> " + destino.getCodigo()+"\n");
						lol.add(r2.getOrigem().getCodigo());
					}
				}
			}
		}

		TreeSet<String> semDuplicidade = new TreeSet<>(textoConsulta3); // sua lista
		TreeSet<String> semDuplicidade2 = new TreeSet<>(lol); // sua lista

		gerenciador.clear();

		//*DESENHO DA CONSULTA 3
		lstPoints.add(new MyWaypoint(Color.RED, origem.getCodigo(), origem.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.BLUE, destino.getCodigo(), destino.getLocal(), 5));
		outputTextArea.setText(semDuplicidade+"\n");
		if (solo>0){
			Tracado tr = new Tracado();
			tr.setWidth(5);
			tr.setCor(new Color(0,0,0,0));
			tr.addPonto(origem.getLocal());
			tr.addPonto(destino.getLocal());
			linhasDeRota.add(tr);
			gerenciador.addTracado(tr);
			double distR = origem.getLocal().distancia(destino.getLocal());
			DecimalFormat df = new DecimalFormat("0.##");
			String dx = df.format(distR);
			outputTextArea.appendText("Rota: " + origem.getCodigo() + " -> " + destino.getCodigo()
					+ "\nDistancia da Rota: " +dx+ " quilômetros");
			for (Aeronave a: gerRotas.buscarAero(origem.getCodigo(),destino.getCodigo()).listarTodas()) {
				outputTextArea.appendText("\nAeronave: " + a.getCodigo() + " - " + a.getDescricao());
			}
			outputTextArea.appendText("\n\n");
		}
		for (String t:semDuplicidade2) {
			Tracado tr = new Tracado();
			tr.setWidth(5);
			tr.setCor(Color.ORANGE);
			tr.addPonto(origem.getLocal());
			tr.addPonto(gerAero.buscarCodigo(t).getLocal());
			linhasDeRota.add(tr);
			gerenciador.addTracado(tr);
			double distR = origem.getLocal().distancia(gerAero.buscarCodigo(t).getLocal());
			DecimalFormat df = new DecimalFormat("0.##");
			String dx = df.format(distR);
			outputTextArea.appendText("Rota: " + origem.getCodigo() + " -> " + gerAero.buscarCodigo(t)
					+ "\nDistancia da Rota: " +dx+ " quilômetros");
			for (Aeronave a: gerRotas.buscarAero(origem.getCodigo(),gerAero.buscarCodigo(t).getCodigo()).listarTodas()) {
				outputTextArea.appendText("\nAeronave: " + a.getCodigo() + " - " + a.getDescricao());
			}
			outputTextArea.appendText("\n\n");
			Tracado tr2 = new Tracado();
			tr2.setWidth(5);
			tr2.setCor(new Color(0, 0, 0, 60));
			tr2.addPonto(gerAero.buscarCodigo(t).getLocal());
			tr2.addPonto(destino.getLocal());
			linhasDeRota.add(tr2);
			gerenciador.addTracado(tr2);
			double distR2 = gerAero.buscarCodigo(t).getLocal().distancia(destino.getLocal());
			DecimalFormat df2 = new DecimalFormat("0.##");
			String dx2 = df.format(distR2);
			outputTextArea.appendText("Rota: " + gerAero.buscarCodigo(t).getCodigo() + " -> " + destino.getCodigo()
					+ "\nDistancia da Rota: " +dx2+ " quilômetros");
			for (Aeronave a: gerRotas.buscarAero(gerAero.buscarCodigo(t).getCodigo(),destino.getCodigo()).listarTodas()) {
				outputTextArea.appendText("\nAeronave: " + a.getCodigo() + " - " + a.getDescricao());
			}
			outputTextArea.appendText("\n\n");
			lstPoints.add(new MyWaypoint(Color.YELLOW, gerAero.buscarCodigo(t).getCodigo(), gerAero.buscarCodigo(t).getLocal(), 5));

		}

		gerenciador.setPontos(lstPoints);
		gerenciador.getMapKit().repaint();

		List<String> teste = new LinkedList<>();
		teste.addAll(semDuplicidade);
		comboSelecionaRota.setItems(FXCollections.observableList(teste));

		//String escolido = comboSelecionaRota.getTypeSelector();
		comboSelecionaRota.setOnAction(e -> {
			String escolido = comboSelecionaRota.getSelectionModel().getSelectedItem().toString();
                for (String s2:semDuplicidade2) {
                    if (escolido.equalsIgnoreCase(origem+" -> "+s2+" -> "+destino)
                            ||escolido.equalsIgnoreCase(origem+" -> "+destino)){
                        Tracado tr2 = new Tracado();
                        tr2.setWidth(5);
                        tr2.setCor(Color.RED);
                        tr2.addPonto(origem.getLocal());
                        tr2.addPonto(gerAero.buscarCodigo(s2).getLocal());
                        linhasDeRota.add(tr2);
                        gerenciador.addTracado(tr2);

                        Tracado tr = new Tracado();
                        tr.setWidth(5);
                        tr.setCor(Color.RED);
                        tr.addPonto(gerAero.buscarCodigo(s2).getLocal());
                        tr.addPonto(destino.getLocal());
                        linhasDeRota.add(tr);
                        gerenciador.addTracado(tr);


                }
            }
		});

	}


	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				gerenciador.getMapKit().repaint();
			}
		}
	}


		private void createSwingContent ( final SwingNode swingNode){
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					swingNode.setContent(gerenciador.getMapKit());
				}
			});
		}

		public static void main (String[]args){
			launch(args);
		}
	}
