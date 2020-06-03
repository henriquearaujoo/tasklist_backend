package br.com.supero.taskapi.model;

public enum StatusEnum {
  CREATED("Created"),
  DONE("Done");

  private String descricao;

  public String getDescricao(){
    return descricao;
  }

  private StatusEnum(String descricao){
    this.descricao = descricao;
  }
}