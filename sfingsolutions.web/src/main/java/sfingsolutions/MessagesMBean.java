package sfingsolutions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Classe responsável pelas mensagens de erro e alerta.
 * @author Vinicius Braz.
 */
@ViewScoped
@ManagedBean(name = "messagesMBean")
public class MessagesMBean implements Serializable {

    /**
     * Retorna as mensagens de erros lançadas pela aplicação.
     * @return Lista de mensagens de erros.
     */
    public List<FacesMessage> getMessageList() {
        FacesContext context = FacesContext.getCurrentInstance();
        return new ArrayList<FacesMessage>(context.getMessageList());
    }

    /**
     * Retorna verdadeiro caso a severidade da mensagem seja WARN.
     * @param message Mensagem.
     * @return verdadeiro caso a severidade da mensagem seja WARN.
     */
    public boolean isWarn(FacesMessage message) {
        return isMensagemDoTipo(message, FacesMessage.SEVERITY_WARN);
    }

    /**
     * Retorna verdadeiro caso a severidade da mensagem seja ERROR.
     * @param message Mensagem.
     * @return verdadeiro caso a severidade da mensagem seja ERROR.
     */
    public boolean isError(FacesMessage message) {
        return isMensagemDoTipo(message, FacesMessage.SEVERITY_ERROR);
    }

    /**
     * Retorna verdadeiro caso a severidade da mensagem seja FATAL.
     * @param message Mensagem.
     * @return verdadeiro caso a severidade da mensagem seja FATAL.
     */
    public boolean isFatal(FacesMessage message) {
        return isMensagemDoTipo(message, FacesMessage.SEVERITY_FATAL);
    }

    /**
     * Retorna verdadeiro caso a severidade da mensagem seja INFO.
     * @param message Mensagem.
     * @return verdadeiro caso a severidade da mensagem seja INOF.
     */
    public boolean isInfo(FacesMessage message) {
        return isMensagemDoTipo(message, FacesMessage.SEVERITY_INFO);
    }

    private boolean isMensagemDoTipo(FacesMessage message, Severity tipo) {
        return tipo.equals(message.getSeverity());
    }

}
