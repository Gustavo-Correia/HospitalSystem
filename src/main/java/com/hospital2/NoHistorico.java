package com.hospital2;
import java.io.Serializable;

public class NoHistorico implements Serializable{
    String historico;
    private NoHistorico proximo;



    public NoHistorico(String historico) {
        this.historico = historico;
        setProximo(null);
    }


    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getHistorico() {
        return historico;
    }

    public NoHistorico getProximo() {
        return proximo;
    }

    public void setProximo(NoHistorico proximo) {
        this.proximo = proximo;
    }



}
