package sfingsolutions.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import sfingsolutions.dominio.defaultobject.DefaultObject;

@Entity
@Table(name = "estadousuario")
public class EstadoUsuarioEntity extends DefaultObject<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2907159958203204883L;
	
	public static final Integer ATIVO = 1;
	public static final Integer INATIVO = 2;

	@Id
	@Column(name = "codestado")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "descricao", nullable = false, length = 50)
	private String descricao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoUsuario")
	private List<UsuarioEntity> listaUsuario;
	
	public EstadoUsuarioEntity() {
	    super();
	}
	
	public EstadoUsuarioEntity(Integer id){
	    this.id = id;
	}

	/**
	 * @return the listaUsuario
	 */
	public List<UsuarioEntity> getListaUsuario() {
		return listaUsuario;
	}

	/**
	 * @param listaUsuario
	 *            the listaUsuario to set
	 */
	public void setListaUsuario(List<UsuarioEntity> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer Id) {
		this.id = id;
		
	}
}
