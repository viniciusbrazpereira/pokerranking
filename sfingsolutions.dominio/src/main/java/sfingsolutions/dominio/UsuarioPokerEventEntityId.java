package sfingsolutions.dominio;


public class UsuarioPokerEventEntityId implements java.io.Serializable {

	private static final long serialVersionUID = -3416780742986729201L;

	private UsuarioEntity usuarioEntity;
	private PokerEventEntity pokerEventEntity;

	public UsuarioPokerEventEntityId() {
		super();
	}
	
	public UsuarioPokerEventEntityId(UsuarioEntity usuarioEntity, PokerEventEntity pokerEventEntity) {
		this();
		this.usuarioEntity = usuarioEntity;
		this.pokerEventEntity = pokerEventEntity;
	}
	
	/**
	 * @return the usuarioEntity
	 */
	public UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}

	/**
	 * @param usuarioEntity
	 *            the usuarioEntity to set
	 */
	public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

	/**
	 * @return the pokerEventEntity
	 */
	public PokerEventEntity getPokerEventEntity() {
		return pokerEventEntity;
	}

	/**
	 * @param pokerEventEntity
	 *            the pokerEventEntity to set
	 */
	public void setPokerEventEntity(PokerEventEntity pokerEventEntity) {
		this.pokerEventEntity = pokerEventEntity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((pokerEventEntity == null) ? 0 : pokerEventEntity.hashCode());
		result = prime * result
				+ ((usuarioEntity == null) ? 0 : usuarioEntity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioPokerEventEntityId other = (UsuarioPokerEventEntityId) obj;
		if (pokerEventEntity == null) {
			if (other.pokerEventEntity != null)
				return false;
		} else if (!pokerEventEntity.equals(other.pokerEventEntity))
			return false;
		if (usuarioEntity == null) {
			if (other.usuarioEntity != null)
				return false;
		} else if (!usuarioEntity.equals(other.usuarioEntity))
			return false;
		return true;
	}

}
