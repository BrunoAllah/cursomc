package com.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENT(2, "ROLE_CLIENT");
	
	private int cod;
	private String desc;
	
	private Perfil(int cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public int getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
	
	public static Perfil toEnum(Integer cod) {
		if (cod == null)
			return null;
		
		for (Perfil estadoPagamento : Perfil.values()) {
			if (cod.equals(estadoPagamento.getCod()))
				return estadoPagamento;
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
