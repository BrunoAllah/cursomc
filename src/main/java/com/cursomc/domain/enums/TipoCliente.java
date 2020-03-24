package com.cursomc.domain.enums;

public enum TipoCliente {
	
	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Juridíca");
	
	private int cod;
	private String desc;
	
	private TipoCliente(int cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public int getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if (cod == null)
			return null;
		
		for (TipoCliente tipoCliente : TipoCliente.values()) {
			if (tipoCliente.equals(cod))
				return tipoCliente;
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
