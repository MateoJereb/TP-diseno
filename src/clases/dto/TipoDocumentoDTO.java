package clases.dto;

public class TipoDocumentoDTO {

	private Integer id;
	private String tipo;
	
	public TipoDocumentoDTO() {
		super();
	}
	public TipoDocumentoDTO(Integer id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
