package com.hospital2;

public class NoEncadeado extends No {
	
	private NoEncadeado proximo;

	public NoEncadeado(Pessoa pessoa) {
		super(pessoa);
		setProximo(null);
	}


	public NoEncadeado getProximo() {
		return proximo;
	}

	public void setProximo(NoEncadeado proximo) {
		this.proximo = proximo;
	}

}


