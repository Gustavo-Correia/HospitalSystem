package com.hospital2;
import java.io.Serializable;

public class No implements Serializable{
	private Pessoa pessoa = new Pessoa();

	public No(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
