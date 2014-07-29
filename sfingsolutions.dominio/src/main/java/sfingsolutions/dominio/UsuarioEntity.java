package sfingsolutions.dominio;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sfingsolutions.dominio.defaultobject.DefaultObject;

/**
 * Entidade que representa um Usuário.
 * 
 * @author Vinicius Braz.
 */
@Entity
@Table(name = "usuario")
public class UsuarioEntity extends DefaultObject<Integer> {

	private static final long serialVersionUID = 7457936439709821819L;

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@Column(name = "login", nullable = false, length = 50)
	private String userLogin;

	@Column(name = "userpassword", nullable = false, length = 150)
	private String userPassword;

	@Column(name = "email", length = 80)
	private String email;
	
	@Column(name = "dataInclusao")
    private Date dataInclusao;
	
	@Column(name = "phone", length = 20)
    private String phone;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codestado")
	private EstadoUsuarioEntity estadoUsuario;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "usuarioperfil", joinColumns = { @JoinColumn(name = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "codperfil", nullable = false, updatable = false) })
	private List<PerfilEntity> perfis;

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin
	 *            the userLogin to set
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the estadoUsuario
	 */
	public EstadoUsuarioEntity getEstadoUsuario() {
		return estadoUsuario;
	}

	/**
	 * @param estadoUsuario
	 *            the estadoUsuario to set
	 */
	public void setEstadoUsuario(EstadoUsuarioEntity estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}

	/**
	 * @return the perfis
	 */
	public List<PerfilEntity> getPerfis() {
		return perfis;
	}

	/**
	 * @param perfis
	 *            the perfis to set
	 */
	public void setPerfis(List<PerfilEntity> perfis) {
		this.perfis = perfis;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * @return the dataInclusao
     */
    public Date getDataInclusao() {
        return dataInclusao;
    }

    /**
     * @param dataInclusao the dataInclusao to set
     */
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public boolean equals(Object object) {
        UsuarioEntity usuarioEntity = (UsuarioEntity) object;
        return this.getId().intValue() == usuarioEntity.getId().intValue();
    }
}
