package com.hospital2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Arquivo implements Serializable {

     // Método para adicionar dados ao arquivo CSV
    public void escreverResultado(NoEncadeado candidatos) {
        try {
            // Abre o arquivo para escrita
            BufferedWriter writer = new BufferedWriter(new FileWriter("historico.txt", true));
            // Escreve o tempo de execução no arquivo
            writer.write( "\nFicha #" + candidatos.getPessoa().getFicha());
            writer.write( "\nNome: " + candidatos.getPessoa().getNome());
            writer.write( "\nCPF:  " + candidatos.getPessoa().getCpf());
            writer.write( "\nEstado:" + candidatos.getPessoa().getEstado());
            writer.write("\n\n");
            writer.flush();
            writer.close(); // Fecha o arquivo
        } catch (IOException e) {
            e.printStackTrace(); // Trata exceções de IO
        }
    }

    public BufferedReader lerHistorico() {
        try {
            // Abre o arquivo para leitura
            BufferedReader reader = new BufferedReader(new FileReader("historico.txt"));
            
            return reader; // Retorna o BufferedReader aberto, sem fechar automaticamente
        } catch (IOException e) {
            e.printStackTrace(); // Trata exceções de IO
            return null;
        }
    }

  public void salvarPacientes(ListaEncadeada listaPacientes, String nomeArquivo) {
        try (FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
             
            out.writeObject(listaPacientes);
            System.out.println("Lista de pacientes salva com sucesso no arquivo: " + nomeArquivo);
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar os pacientes: " + e.getMessage());
        }
    }

    
    public ListaEncadeada carregarPacientes(String nomeArquivo) {
        ListaEncadeada listaPacientes = null;
        try (FileInputStream fileIn = new FileInputStream(nomeArquivo);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
             
            listaPacientes = (ListaEncadeada) in.readObject();
            System.out.println("Lista de pacientes carregada com sucesso do arquivo: " + nomeArquivo);
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os pacientes: " + e.getMessage());
        }
        return listaPacientes;
    }
    
}

