package com.filipecrodrigues.backend_grupos.model;

public class LinhaComparacao {

    private String usuarioReferencia;
    private String grupoReferencia;
    private String usuarioComparado;
    private String grupoComparado;

    public String getUsuarioReferencia() {
        return usuarioReferencia;
    }

    public void setUsuarioReferencia(String usuarioReferencia) {
        this.usuarioReferencia = usuarioReferencia;
    }

    public String getGrupoReferencia() {
        return grupoReferencia;
    }

    public void setGrupoReferencia(String grupoReferencia) {
        this.grupoReferencia = grupoReferencia;
    }

    public String getUsuarioComparado() {
        return usuarioComparado;
    }

    public void setUsuarioComparado(String usuarioComparado) {
        this.usuarioComparado = usuarioComparado;
    }

    public String getGrupoComparado() {
        return grupoComparado;
    }

    public void setGrupoComparado(String grupoComparado) {
        this.grupoComparado = grupoComparado;
    }

    
}
