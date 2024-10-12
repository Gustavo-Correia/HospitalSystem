package com.hospital2;
import java.io.Serializable;

public class ListaEncadeada implements Serializable{
	

	private NoEncadeado cabeca;
	private NoHistorico cabecaHistorico;



	public NoEncadeado getCabeca() {
		return cabeca;
	}
	public NoHistorico getCabecaHistorico() {
		return cabecaHistorico;
	}

	public void setCabecaHistorico(NoHistorico cabecaHistorico) {
		this.cabecaHistorico = cabecaHistorico;
	}

	public void setCabeca(NoEncadeado cabeca) {
		this.cabeca = cabeca;
	}

	public ListaEncadeada() {
		setCabeca(null);
	}

	public boolean estaVazia() {
		return this.getCabeca() == null;
	}
	public boolean estaVaziaH() {
		return this.getCabecaHistorico() == null;
	}

	public void inserir(NoEncadeado no) {
		if (estaVazia()) {
			this.cabeca = no;
		}else {
			if (this.cabeca.getProximo() == null) {
				this.cabeca.setProximo(no);
			}else {
				NoEncadeado noTemp = this.cabeca;
				while (noTemp.getProximo() != null) {
					noTemp = noTemp.getProximo();
				}
				noTemp.setProximo(no);
			}
		}
	}
	public void inserirH(NoHistorico no) {
		if (estaVaziaH()) {
			this.cabecaHistorico = no;
		}else {
			if (this.cabecaHistorico.getProximo() == null) {
				this.cabecaHistorico.setProximo(no);
			}else {
				NoHistorico noTemp = this.cabecaHistorico;
				while (noTemp.getProximo() != null) {
					noTemp = noTemp.getProximo();
				}
				noTemp.setProximo(no);
			}
		}
	}

	public void inserirNaCabeca(NoEncadeado no) {
		if (estaVazia()) {
			this.cabeca = no;
		}else {
			NoEncadeado noTemp = this.cabeca;
			this.cabeca = no;
			no.setProximo(noTemp);
			this.cabeca.setProximo(noTemp);
		}
	}

	public void inserirNoFinal(NoEncadeado no) {
		// Se a lista estiver vazia, o novo nó será a cabeça
		if (estaVazia()) {
			this.cabeca = no;
		} else {
			// Percorre a lista até encontrar o último nó
			NoEncadeado atual = this.cabeca;
			while (atual.getProximo() != null) {
				atual = atual.getProximo();
			}
			// Insere o novo nó no final
			atual.setProximo(no);
		}
	}
	
	public void removerNo(String cpf) {
		if (!this.estaVazia()) {
			if (this.cabeca.getPessoa().getCpf().equals(cpf)) {
				NoEncadeado noTemp = this.cabeca;
				this.cabeca = noTemp.getProximo();
				noTemp = null;
			}else {
				NoEncadeado noAnterior = null;
				NoEncadeado noTemp = this.cabeca;
				while (noTemp != null && noTemp.getPessoa().getCpf().equals(cpf) == false) {
					noAnterior = noTemp;
					noTemp = noTemp.getProximo();
				}
				if (noTemp != null) {
					noAnterior.setProximo(noTemp.getProximo());
					noAnterior = null;
					noTemp = null;
				}
			}
		}
	}

	public void removerPrimeiro() {
		if (!this.estaVazia()) {
			NoEncadeado noTemp = this.cabeca;
			this.cabeca = this.cabeca.getProximo();
			noTemp.setProximo(null);
		}
	}

	public void apagarLista() {
		while (this.cabeca != null) {
			NoEncadeado noTemp = this.cabeca.getProximo();
			this.cabeca = noTemp;
			noTemp = null;

		}
	}

	

	public void clear() {
		cabeca = null; // Define o início da lista como nulo, removendo todos os elementos
	}

	public NoEncadeado buscarNo(String cpf) {
		NoEncadeado noBuscado = null;
		for (NoEncadeado no = this.getCabeca(); no != null; no = no.getProximo()) {
			if (no.getPessoa().getCpf().equals(cpf)) {
				noBuscado = no;
			}
		}
		return noBuscado;
	}

	public NoEncadeado buscarNoposicao(int posicao) {
		NoEncadeado noBuscado = null;
		for (NoEncadeado no = this.getCabeca(); no != null; no = no.getProximo()) {
			System.out.println(no.getPessoa().getFicha());
			if (no.getPessoa().getFicha() == posicao) {
				noBuscado = no;
			}
		}
		return noBuscado;
	}
	
	// Insere um nó no início da lista
public void inserirNoInicio(NoEncadeado novoNo) {
    novoNo.setProximo(this.cabeca); // O novo nó aponta para a antiga cabeça
    this.cabeca = novoNo; // A cabeça da lista agora é o novo nó
}

// Insere um nó após todos os pacientes críticos
public void inserirDepoisDosCriticos(NoEncadeado novoNo) {
    if (this.cabeca == null || this.cabeca.getPessoa().getEstado() != EstadoPaciente.CRITICO) {
        // Se a lista estiver vazia ou não houver pacientes críticos, insere no início
        inserirNoInicio(novoNo);
    } else {
        // Percorre a lista até o último paciente crítico
        NoEncadeado atual = this.cabeca;
        while (atual.getProximo() != null && atual.getProximo().getPessoa().getEstado() == EstadoPaciente.CRITICO) {
            atual = atual.getProximo();
        }
        // Insere o novo nó após o último paciente crítico
        novoNo.setProximo(atual.getProximo());
        atual.setProximo(novoNo);
    }
}

// Insere um nó após todos os pacientes estáveis
public void inserirDepoisDosEstaveis(NoEncadeado novoNo) {
    if (this.cabeca == null || 
        (this.cabeca.getPessoa().getEstado() != EstadoPaciente.CRITICO && this.cabeca.getPessoa().getEstado() != EstadoPaciente.ESTAVEL)) {
        // Se a lista estiver vazia ou não houver pacientes críticos/estáveis, insere no início
        inserirNoInicio(novoNo);
    } else {
        // Percorre a lista até o último paciente estável
        NoEncadeado atual = this.cabeca;
        while (atual.getProximo() != null && 
               (atual.getProximo().getPessoa().getEstado() == EstadoPaciente.CRITICO || atual.getProximo().getPessoa().getEstado() == EstadoPaciente.ESTAVEL)) {
            atual = atual.getProximo();
        }
        // Insere o novo nó após o último paciente estável
        novoNo.setProximo(atual.getProximo());
        atual.setProximo(novoNo);
    }
}

public void ordenarPacientesPorEstado(ListaEncadeada listaEncadeada) {
    // Nova lista para armazenar os pacientes em ordem de prioridade
    ListaEncadeada listaOrdenada = new ListaEncadeada();
    
    // Percorre a lista original
    NoEncadeado atual = listaEncadeada.getCabeca();
    while (atual != null) {
        EstadoPaciente estado = atual.getPessoa().getEstado();
        NoEncadeado proximo = atual.getProximo(); // Salva a referência para o próximo nó
        atual.setProximo(null); // Desconecta o nó atual da lista original

        // Insere o paciente diretamente na lista ordenada de acordo com o estado
        switch (estado) {
            case CRITICO:
                listaOrdenada.inserirNoInicio(atual); // Paciente crítico vai para o início
                break;
            case ESTAVEL:
                listaOrdenada.inserirDepoisDosCriticos(atual); // Após os pacientes críticos
                break;
            case EM_OBSERVACAO:
                listaOrdenada.inserirDepoisDosEstaveis(atual); // Após os pacientes estáveis
                break; //ja que na funcao adicionar paciente n tem a necessidade de colocar os pacientes que receberam alta
						//n tem necessidade de colocar aqui
			default:   //adicionamos essa parte de default, só para sair um aviso chato
				break;
        }

        atual = proximo; // Move para o próximo nó
    }

    // Substitui a lista original pela lista ordenada
    listaEncadeada.setCabeca(listaOrdenada.getCabeca());

    // Atualiza a ficha dos pacientes com base na nova ordem
    int cont = 1;
    for (NoEncadeado no = listaEncadeada.getCabeca(); no != null; no = no.getProximo()) {
        no.getPessoa().setFicha(cont);
        cont++;
    }

    System.out.println("Pacientes ordenados com sucesso por estado de saúde.");
}

}