package com.hospital2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class funcoes implements Serializable {
    public static Arquivo arquivo = new Arquivo();
    // Carrega a lista de pacientes do arquivo
    ListaEncadeada listaencadeada = new ListaEncadeada();
    ListaEncadeada listaatendidos = new ListaEncadeada();
    Scanner sc = new Scanner(System.in);
    int ficha = 1;
    int cont=1;
    public funcoes() {
        ListaEncadeada listaPacientes = arquivo.carregarPacientes("pacientes.txt");

        if (listaPacientes == null) {
            listaPacientes = new ListaEncadeada();
        } else {
            listaencadeada = listaPacientes;
        }
    }

    private void adicionarpaciente(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Adicionar Paciente");
        // Coletando dados do Paciente

        ArrayList<String> historicoDoencas = new ArrayList<>();
        // Criação dos campos de entrada
        TextField historicoField = new TextField();
        TextField nomeField = new TextField();
        TextField cpfField = new TextField();
        TextField idadeField = new TextField();
        TextField enderecoField = new TextField();
        TextField celularField = new TextField();
        // Criação de botões de rádio para o gênero
        ToggleGroup generoGroup = new ToggleGroup();
        RadioButton masculinoButton = new RadioButton("Masculino");
        masculinoButton.setToggleGroup(generoGroup);
        RadioButton femininoButton = new RadioButton("Feminino");
        femininoButton.setToggleGroup(generoGroup);

        // Plano de saúde
        ComboBox<String> planoDeSaudeCombo = new ComboBox<>();
        planoDeSaudeCombo.getItems().addAll("Possui plano de Saúde", "Não Possui plano de Saúde");

        // Grupo sanguíneo
        ComboBox<String> grupoSanguineoCombo = new ComboBox<>();
        grupoSanguineoCombo.getItems().addAll("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");

        // Estado de saúde
        ComboBox<String> estadoCombo = new ComboBox<>();
        estadoCombo.getItems().addAll("ESTÁVEL", "EM OBSERVAÇÃO", "CRÍTICO");

        // Criação do botão de adicionar
        Button adicionarButton = new Button("Adicionar Paciente");
        // Criação do botão de adicionar
        Button VoltarButton = new Button("Voltar");
        // Layout GridPane para organizar os elementos
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Adicionando os componentes ao layout
        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(new Label("CPF:"), 0, 1);
        grid.add(cpfField, 1, 1);
        grid.add(new Label("Idade:"), 0, 2);
        grid.add(idadeField, 1, 2);
        grid.add(new Label("Endereço:"), 0, 3);
        grid.add(enderecoField, 1, 3);
        grid.add(new Label("Celular:"), 0, 4);
        grid.add(celularField, 1, 4);
        grid.add(new Label("Gênero:"), 0, 5);
        grid.add(masculinoButton, 1, 5);
        grid.add(femininoButton, 2, 5);
        grid.add(new Label("Plano de Saúde:"), 0, 6);
        grid.add(planoDeSaudeCombo, 1, 6);
        grid.add(new Label("Grupo Sanguíneo:"), 0, 7);
        grid.add(grupoSanguineoCombo, 1, 7);
        grid.add(new Label("Estado de Saúde:"), 0, 8);
        grid.add(estadoCombo, 1, 8);
        grid.add(new Label("Historico de doença:"), 0, 9);
        grid.add(historicoField, 1, 9);
        grid.add(new Label("Aperte Enter No historico de doenças, para enviar, e só depois clique em adicionar"), 2, 9);
        grid.add(adicionarButton, 1, 10);
        grid.add(VoltarButton, 1, 10, 1, 10);
        Pessoa pessoa = new Pessoa();

        VoltarButton.setOnAction(e -> {
            try {
                menu(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Evento de pressionar "Enter" para adicionar doenças ao histórico
        historicoField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String novaDoenca = historicoField.getText().trim(); // Captura o texto inserido

                if (!novaDoenca.isEmpty()) {
                    historicoDoencas.add(novaDoenca);
                    System.out.println("Doença adicionada: " + novaDoenca); // Apenas para verificação no console

                    // Adiciona a nova doença ao histórico
                    NoHistorico novoHistorico = new NoHistorico(novaDoenca);
                    pessoa.getHistorico().inserirH(novoHistorico);

                }
                historicoField.clear(); // Limpa o campo para permitir nova entrada
                event.consume(); // Evita a inserção de uma nova linha
            }
        });
        adicionarButton.setOnAction(e -> {
            boolean nomevalido = false;
            ficha++;
            try {
                // Validação do nome
                String nometest = nomeField.getText();
                if (nometest.isEmpty()) {
                    throw new Exception("O campo nome não pode estar vazio.");
                }
                nomevalido = true;
                for (int x = 0; x < nometest.length(); x++) {
                    char c = nometest.charAt(x);
                    if (Character.isDigit(c)) {
                        nomevalido = false;
                        throw new Exception("O nome não pode conter números.");
                    }
                }

                if (nomevalido) {
                    // Validação do CPF
                    String cpf = cpfField.getText();
                    if (cpf.isEmpty() || cpf.length() != 11 || !cpf.matches("\\d+")) {
                        throw new Exception("CPF inválido. Deve conter 11 dígitos numéricos.");
                    }

                    // Validação da idade
                    String idadeTexto = idadeField.getText();
                    int idade;
                    try {
                        idade = Integer.parseInt(idadeTexto);
                        if (idade < 0) {
                            throw new Exception("Idade deve ser um número positivo.");
                        }
                    } catch (NumberFormatException ex) {
                        throw new Exception("Idade inválida. Deve ser um número.");
                    }

                    // Validação do endereço
                    String endereco = enderecoField.getText();
                    if (endereco.isEmpty()) {
                        throw new Exception("Endereço não pode estar vazio.");
                    }

                    // Validação do celular
                    String celular = celularField.getText();
                    if (celular.isEmpty() || !celular.matches("\\d+")) {
                        throw new Exception("Celular inválido. Deve conter apenas números.");
                    }

                    // Validação do gênero
                    if (generoGroup.getSelectedToggle() == null) {
                        throw new Exception("Por favor, selecione um gênero.");
                    }
                    String genero2 = ((RadioButton) generoGroup.getSelectedToggle()).getText();
                    char genero = genero2.charAt(0);

                    // Validação do plano de saúde
                    String planoDeSaude = planoDeSaudeCombo.getValue();
                    if (planoDeSaude == null) {
                        throw new Exception("Por favor, selecione uma opção de plano de saúde.");
                    }

                    // Validação do grupo sanguíneo
                    String grupoSanguineo = grupoSanguineoCombo.getValue();
                    if (grupoSanguineo == null) {
                        throw new Exception("Por favor, selecione um grupo sanguíneo.");
                    }

                    // Validação do estado de saúde
                    String estado2 = estadoCombo.getValue();
                    if (estado2 == null) {
                        throw new Exception("Por favor, selecione um estado de saúde.");
                    }

                    EstadoPaciente estadopaciente;
                    switch (estado2) {
                        case "ESTÁVEL":
                            estadopaciente = EstadoPaciente.ESTAVEL;
                            break;
                        case "EM OBSERVAÇÃO":
                            estadopaciente = EstadoPaciente.EM_OBSERVACAO;
                            break;
                        case "CRÍTICO":
                            estadopaciente = EstadoPaciente.CRITICO;
                            break;
                        default:
                            throw new Exception("Estado de saúde inválido.");
                    }

                    // Exibir os dados do paciente no console
                    System.out.println("Paciente adicionado:");
                    System.out.println("Nome: " + nometest);
                    System.out.println("CPF: " + cpf);
                    System.out.println("Idade: " + idade);
                    System.out.println("Endereço: " + endereco);
                    System.out.println("Celular: " + celular);
                    System.out.println("Gênero: " + genero);
                    System.out.println("Plano de Saúde: " + planoDeSaude);
                    System.out.println("Grupo Sanguíneo: " + grupoSanguineo);
                    System.out.println("Estado: " + estado2);

                    // Preenche os dados do paciente
                    pessoa.setNome(nometest);
                    pessoa.setCpf(cpf);
                    pessoa.setIdade(idade);
                    pessoa.setEndereco(endereco);
                    pessoa.setCelular(celular);
                    pessoa.setGenero(genero);
                    pessoa.setPlanodesaude(planoDeSaude);
                    pessoa.setGrupo_Sanguíneo(grupoSanguineo);
                    pessoa.setEstado(estadopaciente);
                    pessoa.setFicha(ficha);
                    // Limpar o histórico de doenças ao iniciar a adição
                    historicoDoencas.clear();

                    // Adiciona o paciente à lista
                    NoEncadeado novo = new NoEncadeado(pessoa);
                    listaencadeada.inserir(novo);
                    listaencadeada.ordenarPacientesPorEstado(listaencadeada);

                    
                    

                    // Limpar todos os campos após a adição
                    nomeField.clear();
                    cpfField.clear();
                    idadeField.clear();
                    enderecoField.clear();
                    celularField.clear();
                    generoGroup.selectToggle(null); // Desmarcar seleção do gênero
                    planoDeSaudeCombo.setValue(null); // Limpar seleção do plano de saúde
                    grupoSanguineoCombo.setValue(null); // Limpar seleção do grupo sanguíneo
                    estadoCombo.setValue(null); // Limpar seleção do estado de saúde
                    historicoField.clear(); // Limpar o campo de histórico

                    try {
                        menu(primaryStage);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    throw new Exception("O nome é inválido.");
                }

            } catch (Exception ex) {
                // Exibir mensagem de erro no console
                System.out.println("Erro: " + ex.getMessage());
                // Adicionar mensagem de erro à interface
                // Adicionar Label de erro com a cor vermelha
                Label erroLabel = new Label("Erro: " + ex.getMessage());
                erroLabel.setStyle("-fx-text-fill: red;"); // Definir a cor vermelha via CSS
                grid.add(erroLabel, 1, 11);

                // Usar PauseTransition para remover a mensagem de erro após 3 segundos
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> grid.getChildren().remove(erroLabel));
                pause.play();
            }
        });
        
        Scene scene = new Scene(grid, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Função para remover Paciente
    private void removerpaciente(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Remover paciente");
        // Criação do botão de voltar
        Button VoltarButton = new Button("Voltar");
        TextField cpfField = new TextField();
        // Criação do botão de remover
        Button removerButton = new Button("remover Paciente");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(removerButton, 1, 5);
        grid.add(VoltarButton, 2, 5);
        // Adicionando os componentes ao layout
        grid.add(new Label("Digite o cpf do Paciente que gostaria de remover: "), 0, 1);
        grid.add(cpfField, 1, 1);

        VoltarButton.setOnAction(e -> {
            try {
                menu(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        removerButton.setOnAction(e -> {
            String cpfaremover = cpfField.getText();
            try {
                if (cpfaremover.isEmpty() || cpfaremover.length() != 11) {
                    Label erroLabel = new Label("CPF ERRADO");
                    erroLabel.setStyle("-fx-text-fill: red;"); // Definir a cor vermelha via CSS
                    grid.add(erroLabel, 1, 2);

                    // Usar PauseTransition para remover a mensagem de erro após 3 segundos
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));
                    pause.setOnFinished(event -> grid.getChildren().remove(erroLabel));
                    pause.play();
                } else {
                    listaencadeada.removerNo(cpfaremover);
                    grid.add(new Label("CPF REMOVIDO COM SUCESSO"), 1, 2);
                }
            } finally {
            }

        });

        // Configuração da cena e tamanho da janela

        Scene scene = new Scene(grid, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Função para listar Paciente
    private void listarPacientes(Stage primaryStage) {

        primaryStage.setTitle("Listar Pacientes");

        // Criação do botão de voltar
        Button VoltarButton = new Button("Voltar");

        // Criando o GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20); // Espaçamento horizontal
        grid.setVgap(20); // Espaçamento vertical
        grid.add(VoltarButton, 0, 0); // Adiciona o botão de voltar na posição 0,0

        // Adiciona ação de voltar ao menu principal
        VoltarButton.setOnAction(e -> {
            try {
                menu(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Variáveis de controle de layout
        int row = 1; // Inicializa na linha 1
        int column = 0; // Inicializa na coluna 0
        int columnLimit = 4; // Limite de colunas (por exemplo, 3 por linha)

        NoEncadeado noTemp = listaencadeada.getCabeca();
        if (listaencadeada.estaVazia()) {
            grid.add(new Label("A lista não contém nenhum paciente!"), 0, 1);
        } else {
            Label label = new Label("Dados dos pacientes");
            label.setStyle("-fx-text-fill: orange;");
            grid.add(label, 2, 0);

            // Exibindo os dados coletados
            while (noTemp != null) {

                grid.add(new Label("Paciente #" + noTemp.getPessoa().getFicha()), column, row);
                grid.add(new Label("Nome: " + noTemp.getPessoa().getNome()), column, row + 1);
                grid.add(new Label("CPF: " + noTemp.getPessoa().getCpf()), column, row + 2);
                grid.add(new Label("Idade: " + noTemp.getPessoa().getIdade()), column, row + 3);
                grid.add(new Label("Endereço: " + noTemp.getPessoa().getEndereco()), column, row + 4);
                grid.add(new Label("Celular: " + noTemp.getPessoa().getCelular()), column, row + 5);
                grid.add(new Label("Estado do Paciente: " + noTemp.getPessoa().getEstado()), column, row + 6);
                grid.add(new Label("Plano de Saude: " + noTemp.getPessoa().getPlanodesaude()), column, row + 7);
                grid.add(new Label("Grupo Sanguíneo: " + noTemp.getPessoa().getGrupo_Sanguíneo()), column, row + 8);
                grid.add(new Label("Historico Médico: "), column, row + 9);

                // Listar histórico médico
                NoHistorico noTempPercorrer = noTemp.getPessoa().getHistorico().getCabecaHistorico();
                int historicoRow = row + 10;
                while (noTempPercorrer != null) {
                    grid.add(new Label("- " + noTempPercorrer.getHistorico()), column, historicoRow++);
                    noTempPercorrer = noTempPercorrer.getProximo(); // Atualizar para o próximo histórico
                }
                column++;
                // Se atingiu o limite de colunas, move para a próxima linha e reseta as colunas
                if (column == columnLimit) {
                    column = 0; // Reinicia a coluna para a próxima linha
                    row = historicoRow + 2; // Move para a próxima linha, considerando o espaço do histórico
                }

                // Próximo paciente
                noTemp = noTemp.getProximo(); // Avançar para o próximo paciente
            }
        }
        // Adiciona a barra de rolagem
        ScrollPane scrollPane = new ScrollPane(grid); // Envolva o GridPane com um ScrollPane
        scrollPane.setFitToWidth(true); // Faz a barra se ajustar à largura da janela

        // Configuração da cena
        Scene scene = new Scene(scrollPane, 1366, 768); // Adiciona o scrollPane à cena
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Função para buscar paciente por cpf e posicao
    private void buscarpaciente(Stage primaryStage) {
        primaryStage.setTitle("Buscar Paciente");

        TextField cpfField = new TextField();
        TextField posicaoField = new TextField();

        // Criação dos botões
        Button voltarButton = new Button("Voltar");
        Button posicaoButton = new Button("Buscar por Posição");
        Button cpfButton = new Button("Buscar por CPF");

        // Criando o GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        // Adiciona o título
        Label titulo = new Label("Buscar paciente por CPF ou posição na fila");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        grid.add(titulo, 1, 0, 2, 1); // Span 2 columns

        // Adiciona os botões e campos de entrada
        grid.add(voltarButton, 0, 1);
        grid.add(new Label("CPF:"), 0, 2);
        grid.add(cpfField, 1, 2);
        grid.add(cpfButton, 2, 2);
        grid.add(new Label("Posição na fila:"), 0, 3);
        grid.add(posicaoField, 1, 3);
        grid.add(posicaoButton, 2, 3);

        // Área para exibir os detalhes do paciente
        VBox pacienteBox = new VBox(10); // Espaçamento de 10 entre elementos
        pacienteBox.setAlignment(Pos.CENTER);
        grid.add(pacienteBox, 1, 4, 2, 1); 
        voltarButton.setOnAction(e -> {
            try {
                menu(primaryStage);
            } catch (Exception ef) {
                ef.getMessage();
            }
        });

        cpfButton.setOnAction(e -> {
            pacienteBox.getChildren().clear(); // Limpa a área de exibição
            try {
                String cpfbuscar = cpfField.getText();
                if (cpfbuscar.isEmpty() || cpfbuscar.length() != 11 || !cpfbuscar.matches("\\d+")) {
                    throw new Exception("CPF inválido. Deve conter 11 dígitos numéricos.");
                }

                NoEncadeado nobuscado = listaencadeada.buscarNo(cpfbuscar);
                if (nobuscado == null) {
                    pacienteBox.getChildren().add(new Label("Paciente não encontrado."));
                } else {
                    exibirDetalhesPaciente(nobuscado, pacienteBox);
                }
            } catch (Exception ex) {
                pacienteBox.getChildren().add(new Label("Erro: " + ex.getMessage()));
            }
        });

        posicaoButton.setOnAction(e -> {
            pacienteBox.getChildren().clear(); // Limpa a área de exibição
            try {
                String posicao = posicaoField.getText();
                int posicaoint = Integer.parseInt(posicao);

                NoEncadeado nobuscado = listaencadeada.buscarNoposicao(posicaoint);
                if (nobuscado == null) {
                    pacienteBox.getChildren().add(new Label("Paciente não encontrado."));
                } else {
                    exibirDetalhesPaciente(nobuscado, pacienteBox);
                }
            } catch (Exception ex) {
                pacienteBox.getChildren().add(new Label("Erro: " + ex.getMessage()));
            }
        });
        // Configuração da cena
        Scene scene = new Scene(grid, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Função auxiliar para exibir os detalhes do paciente
    private void exibirDetalhesPaciente(NoEncadeado paciente, VBox pacienteBox) {
        pacienteBox.getChildren().add(new Label("Paciente #" + paciente.getPessoa().getFicha()));
        pacienteBox.getChildren().add(new Label("Nome: " + paciente.getPessoa().getNome()));
        pacienteBox.getChildren().add(new Label("CPF: " + paciente.getPessoa().getCpf()));
        pacienteBox.getChildren().add(new Label("Idade: " + paciente.getPessoa().getIdade()));
        pacienteBox.getChildren().add(new Label("Endereço: " + paciente.getPessoa().getEndereco()));
        pacienteBox.getChildren().add(new Label("Celular: " + paciente.getPessoa().getCelular()));
        pacienteBox.getChildren().add(new Label("Estado do Paciente: " + paciente.getPessoa().getEstado()));
        pacienteBox.getChildren().add(new Label("Plano de Saude: " + paciente.getPessoa().getPlanodesaude()));
        pacienteBox.getChildren().add(new Label("Grupo Sanguíneo: " + paciente.getPessoa().getGrupo_Sanguíneo()));

        Label historicoLabel = new Label("Histórico de doenças:");
        pacienteBox.getChildren().add(historicoLabel);
        // Listar histórico médico
        NoHistorico noTempPercorrer = paciente.getPessoa().getHistorico().getCabecaHistorico();
        while (noTempPercorrer != null) {
            pacienteBox.getChildren().add(new Label("- " + noTempPercorrer.getHistorico())); //pegar historico
            noTempPercorrer = noTempPercorrer.getProximo();//serve para passar pro proximo
        }
    }

    // Função para atualizar o estado do paciente
    private void atualizarEstado(Stage primaryStage) {
        primaryStage.setTitle("Atualizar Estado de Saúde");

        // Criar campos de entrada
        TextField cpfField = new TextField();
        cpfField.setPromptText("Digite o CPF do paciente");

        ComboBox<String> estadoComboBox = new ComboBox<>();
        estadoComboBox.getItems().addAll(
                "RECEBEU ALTA",
                "ESTAVEL",
                "EM OBSERVACAO",
                "CRITICO");

        Button atualizarButton = new Button("Atualizar Estado");
        Button voltarButton = new Button("Voltar");

        // Configurar o layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                new Label("Atualizar Estado de Saúde"),
                cpfField,
                new Label("Selecione o novo estado de saúde:"),
                estadoComboBox,
                atualizarButton,
                voltarButton);

        // Evento para o botão "Atualizar Estado"
        atualizarButton.setOnAction(e -> {
            String cpfbuscar = cpfField.getText();
            NoEncadeado nobuscado = listaencadeada.buscarNo(cpfbuscar);

            if (nobuscado != null) {
                String estadoSelecionado = estadoComboBox.getValue();
                if (estadoSelecionado != null) {
                    switch (estadoSelecionado) {
                        case "RECEBEU ALTA":
                            nobuscado.getPessoa().setEstado(EstadoPaciente.RECEBEU_ALTA);

                            arquivo.escreverResultado(nobuscado);
                            listaencadeada.removerNo(cpfbuscar);
                            break;
                        case "ESTAVEL":
                            nobuscado.getPessoa().setEstado(EstadoPaciente.ESTAVEL);
                            break;
                        case "EM OBSERVACAO":
                            nobuscado.getPessoa().setEstado(EstadoPaciente.EM_OBSERVACAO);
                            break;
                        case "CRITICO":
                            nobuscado.getPessoa().setEstado(EstadoPaciente.CRITICO);
                            break;
                    }
                    System.out.println("Estado de saúde atualizado com sucesso.");
                } else {
                    System.out.println("Selecione um estado válido.");
                }
            } else {
                System.out.println("Paciente não encontrado.");
            }
        });

        // Evento para o botão "Voltar"
        voltarButton.setOnAction(e -> {
            // Voltar para o menu principal
            try {
                menu(primaryStage);
            } catch (IOException e1) {
                e1.printStackTrace();
            } // Exemplo de como voltar para o menu
        });

        Scene scene = new Scene(layout, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Função para atualizar informações do Paciente
    private void atualizarInformacoes(Stage primaryStage) {
        primaryStage.setTitle("Atualizar Informações do Paciente");
        Button voltarButton = new Button("Voltar");
        TextField cpfField = new TextField();
        Button buscarButton = new Button("Buscar Paciente");
        VBox formBox = new VBox(10); // Espaçamento entre elementos
        formBox.setAlignment(Pos.CENTER);

        // Layout inicial para busca do paciente
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.add(voltarButton, 2, 5);
        grid.add(new Label("CPF do Paciente:"), 0, 0);
        grid.add(cpfField, 1, 0);
        grid.add(buscarButton, 1, 1);

        VBox rootBox = new VBox(20);
        rootBox.setAlignment(Pos.CENTER);
        rootBox.getChildren().add(grid);

        // Cena inicial
        Scene scene = new Scene(rootBox, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();

        voltarButton.setOnAction(e -> {
            try {
                menu(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        buscarButton.setOnAction(e -> {
            formBox.getChildren().clear();
            try {
                String cpfbuscar = cpfField.getText();

                if (cpfbuscar.isEmpty() || cpfbuscar.length() != 11 || !cpfbuscar.matches("\\d+")) {
                    throw new Exception("CPF inválido. Deve conter 11 dígitos numéricos.");
                }

                NoEncadeado paciente = listaencadeada.buscarNo(cpfbuscar);
                if (paciente == null) {
                    formBox.getChildren().add(new Label("Paciente não encontrado."));
                } else {
                    exibirFormularioAtualizacao(paciente, formBox);
                }
            } catch (Exception ex) {
                formBox.getChildren().add(new Label("Erro: " + ex.getMessage()));
            }

            rootBox.getChildren().add(formBox);
        });
    }

    // Função auxiliar para exibir o formulário de atualização
    private void exibirFormularioAtualizacao(NoEncadeado paciente, VBox formBox) {
        formBox.getChildren().clear();

        // Criar campos de entrada para cada informação
        TextField nomeField = new TextField(paciente.getPessoa().getNome());
        TextField cpfField = new TextField(paciente.getPessoa().getCpf());
        TextField idadeField = new TextField(String.valueOf(paciente.getPessoa().getIdade()));
        TextField generoField = new TextField(String.valueOf(paciente.getPessoa().getGenero()));
        TextField enderecoField = new TextField(paciente.getPessoa().getEndereco());
        TextField celularField = new TextField(paciente.getPessoa().getCelular());
        TextField planoSaudeField = new TextField(paciente.getPessoa().getPlanodesaude());
        TextField grupoSanguineoField = new TextField(paciente.getPessoa().getGrupo_Sanguíneo());

       
        StringBuilder historicoText = new StringBuilder();

        // Itera sobre a ListaEncadeada de histórico do paciente
        NoHistorico noAtual = paciente.getPessoa().getHistorico().getCabecaHistorico();
        while (noAtual != null) {
            historicoText.append(noAtual.getHistorico()); // Assumindo que getHistorico() retorna o dado do nó
            historicoText.append("\n"); // Adiciona quebra de linha entre os registros do histórico
            noAtual = noAtual.getProximo(); // Avança para o próximo nó
        }

        // Exibir o histórico em um campo TextArea
        TextArea historicoField = new TextArea(historicoText.toString());
        historicoField.setPrefRowCount(5); // Define o número de linhas visíveis para o TextArea

        // Layout para os campos de atualização
        GridPane updateGrid = new GridPane();
        updateGrid.setHgap(10);
        updateGrid.setVgap(10);
        updateGrid.setAlignment(Pos.CENTER);

        // Adicionar os campos ao GridPane
        updateGrid.add(new Label("Nome:"), 0, 0);
        updateGrid.add(nomeField, 1, 0);
        updateGrid.add(new Label("CPF:"), 0, 1);
        updateGrid.add(cpfField, 1, 1);
        updateGrid.add(new Label("Idade:"), 0, 2);
        updateGrid.add(idadeField, 1, 2);
        updateGrid.add(new Label("Gênero:"), 0, 3);
        updateGrid.add(generoField, 1, 3);
        updateGrid.add(new Label("Endereço:"), 0, 4);
        updateGrid.add(enderecoField, 1, 4);
        updateGrid.add(new Label("Celular:"), 0, 5);
        updateGrid.add(celularField, 1, 5);
        updateGrid.add(new Label("Plano de Saúde:"), 0, 6);
        updateGrid.add(planoSaudeField, 1, 6);
        updateGrid.add(new Label("Grupo Sanguíneo:"), 0, 7);
        updateGrid.add(grupoSanguineoField, 1, 7);
        updateGrid.add(new Label("Histórico Médico:"), 0, 8);
        updateGrid.add(historicoField, 1, 8);

        // Adiciona o GridPane ao formBox (ou outro contêiner)
        formBox.getChildren().add(updateGrid);
        // Botão para salvar as atualizações
        Button salvarButton = new Button("Salvar Atualizações");
        formBox.getChildren().addAll(updateGrid, salvarButton);

        salvarButton.setOnAction(e -> {
            // Atualiza os dados do paciente
            paciente.getPessoa().setNome(nomeField.getText());
            paciente.getPessoa().setCpf(cpfField.getText());
            paciente.getPessoa().setIdade(Integer.parseInt(idadeField.getText()));
            paciente.getPessoa().setGenero(generoField.getText().charAt(0));
            paciente.getPessoa().setEndereco(enderecoField.getText());
            paciente.getPessoa().setCelular(celularField.getText());
            paciente.getPessoa().setPlanodesaude(planoSaudeField.getText());
            paciente.getPessoa().setGrupo_Sanguíneo(grupoSanguineoField.getText());

            // Criar uma nova ListaEncadeada para o histórico
            ListaEncadeada historico2 = new ListaEncadeada();

            // Dividir o texto do campo historicoField e adicionar na lista encadeada
            String[] entradasDeHistorico = historicoField.getText().split("\\n");
            for (String historicoItem : entradasDeHistorico) {
                NoHistorico no = new NoHistorico(historicoItem); // Cria um novo nó para cada entrada
                historico2.inserirH(no); // Adiciona o nó à lista encadeada
            }

            paciente.getPessoa().setHistorico(historico2);
            formBox.getChildren().add(new Label("Informações atualizadas com sucesso!"));
        });
    }

    

    // fazer interface
    // Função para listar o histórico de atendimentos
    public void listarHistorico(@SuppressWarnings("exports") Stage primaryStage) throws IOException {
        primaryStage.setTitle("Listar Pacientes");

        BufferedReader reader = arquivo.lerHistorico();
        String linha;

        if (reader == null) {
            System.out.println("Não tem nenhum funcionario que recebeu alta na lista");
        }
        {
            // Criação do botão de voltar
            Button VoltarButton = new Button("Voltar");

            // Criando o GridPane
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.TOP_CENTER);
            grid.setHgap(20); // Espaçamento horizontal
            grid.setVgap(20); // Espaçamento vertical
            grid.add(VoltarButton, 0, 0); // Adiciona o botão de voltar na posição 0,0

            // Variáveis de controle de layout
            int row = 1; // Inicializa na linha 1
            int column = 0; // Inicializa na coluna 0
            int columnLimit = 4; // Limite de colunas (por exemplo, 3 por linha)

            Label label = new Label("Dados dos pacientes");
            label.setStyle("-fx-text-fill: orange;");
            grid.add(label, 2, 0);
            int historicoRow = row + 10;

            while ((linha = reader.readLine()) != null) {

                grid.add(new Label(linha), column, row);
                row++; // Incrementa a linha para que os rótulos não se sobreponham
            }
            column++;
            // Se atingiu o limite de colunas, move para a próxima linha e reseta as colunas
            if (column == columnLimit) {
                column = 0; // Reinicia a coluna para a próxima linha
                row = historicoRow + 2; // Move para a próxima linha, considerando o espaço do histórico
            }

            // Adiciona ação de voltar ao menu principal
            VoltarButton.setOnAction(e -> {
                try {
                    menu(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            Scene scene = new Scene(grid, 1366, 768);
            primaryStage.setScene(scene);
            primaryStage.show();
            reader.close();
        }

    }

    public void menu(@SuppressWarnings("exports") Stage primaryStage) throws IOException {

        primaryStage.setTitle("Sistema de Atendimento Hospitalar");
    
        // Carregar a imagem principal
        Image image = new Image(getClass().getResource("/com/hospital2/logo.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
    
        // Carregar os GIFs (ou imagens) para a esquerda e a direita
        Image gif1 = new Image(getClass().getResource("/com/hospital2/fofo.gif").toExternalForm());
        Image gif2 = new Image(getClass().getResource("/com/hospital2/jojo.gif").toExternalForm());
        ImageView gifView1 = new ImageView(gif1);
        ImageView gifView2 = new ImageView(gif2);
    
        gifView1.setFitHeight(350);  // Definir o tamanho do primeiro gif
        gifView1.setFitWidth(350);
        gifView2.setFitHeight(350);  // Definir o tamanho do segundo gif
        gifView2.setFitWidth(350);
    
        // Criar uma VBox para as imagens à esquerda e à direita
        VBox leftImageBox = new VBox(gifView1);
        leftImageBox.setAlignment(Pos.CENTER_LEFT);  // Alinhar a imagem na esquerda
    
        VBox rightImageBox = new VBox(gifView2);
        rightImageBox.setAlignment(Pos.CENTER_RIGHT);  // Alinhar a imagem na direita
    
        // Criar os botões
        Button btnAdicionarPaciente = new Button("Adicionar Paciente");
        Button btnRemoverPaciente = new Button("Remover Paciente");
        Button btnListarPacientes = new Button("Listar Pacientes");
        Button btnBuscarPaciente = new Button("Buscar Paciente");
        Button btnAtualizarEstado = new Button("Atualizar Estado do Paciente");
        Button btnAtualizarInformacoes = new Button("Atualizar Informações do Paciente");
        Button btnListarHistorico = new Button("Listar Histórico de Atendimentos");
        
        Button btnSair = new Button("Sair e Salvar");
    
        double buttonWidth = 300;
        double buttonHeight = 40;
    
        btnAdicionarPaciente.setPrefSize(buttonWidth, buttonHeight);
        btnRemoverPaciente.setPrefSize(buttonWidth, buttonHeight);
        btnListarPacientes.setPrefSize(buttonWidth, buttonHeight);
        btnBuscarPaciente.setPrefSize(buttonWidth, buttonHeight);
        btnAtualizarEstado.setPrefSize(buttonWidth, buttonHeight);
        btnAtualizarInformacoes.setPrefSize(buttonWidth, buttonHeight);
        btnListarHistorico.setPrefSize(buttonWidth, buttonHeight);
        
        btnSair.setPrefSize(buttonWidth, buttonHeight);
    
        // Layout principal para os botões e a imagem principal
        VBox layout = new VBox(20, imageView);
        layout.setAlignment(Pos.CENTER); 
        layout.getChildren().addAll(btnAdicionarPaciente, btnRemoverPaciente, btnListarPacientes, btnBuscarPaciente,
                btnAtualizarEstado, btnAtualizarInformacoes, btnListarHistorico, btnSair);
    
        // Criar um BorderPane para organizar os elementos
        BorderPane borderPane = new BorderPane();
    
        // Adicionar as imagens na esquerda e na direita
        borderPane.setLeft(leftImageBox);  // Imagem à esquerda
        borderPane.setRight(rightImageBox);  // Imagem à direita
    
        // Centralizar os botões
        borderPane.setCenter(layout);
    
        // Ações dos botões
        btnAdicionarPaciente.setOnAction(e -> {
            try {
                adicionarpaciente(primaryStage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    
        btnRemoverPaciente.setOnAction(e -> {
            try {
                removerpaciente(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    
        btnListarPacientes.setOnAction(e -> listarPacientes(primaryStage));
    
        btnBuscarPaciente.setOnAction(e -> buscarpaciente(primaryStage));
    
        btnAtualizarEstado.setOnAction(e -> atualizarEstado(primaryStage));
    
        btnAtualizarInformacoes.setOnAction(e -> atualizarInformacoes(primaryStage));
    
        btnListarHistorico.setOnAction(e -> {
            try {
                listarHistorico(primaryStage);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    
        
    
        btnSair.setOnAction(e -> {
            System.out.println("Saindo do sistema e salvando...");
            arquivo.salvarPacientes(listaencadeada, "pacientes.txt");
            primaryStage.close();
        });
    
        // Criando a cena e definindo no palco
        Scene scene = new Scene(borderPane, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
