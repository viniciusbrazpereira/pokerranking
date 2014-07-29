package sfingsolutions;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Manager Bean responsável por controlar as telas de mensagem de
 * salvar/excluir.
 * 
 * @author Vinicius Braz.
 */
@ViewScoped
@ManagedBean(name = "dialogMensagemMBean")
public class DialogMensagemMBean implements Serializable{

    private AbstratoCrudMBean<?> abstratoCrudMBean;

    private String mensagemDialogExcluir;
    private String mensagemDialogSalvar;

    /**
     * Construtor
     */
    public DialogMensagemMBean() {
    }

    /**
     * @return <T>
     */
    public <T> T salvar() {
        return (T) abstratoCrudMBean.salvar();
    }

    /**
     * @return <T>
     */
    public <T> T excluir() {
        return (T) abstratoCrudMBean.excluir();
    }

    /**
     * @return the mensagemDialogExcluir
     */
    public String getMensagemDialogExcluir() {
        return mensagemDialogExcluir;
    }

    /**
     * @param mensagemDialogExcluir the mensagemDialogExcluir to set
     */
    public void setMensagemDialogExcluir(String mensagemDialogExcluir) {
        this.mensagemDialogExcluir = abstratoCrudMBean.getMensagemDoRotulo(mensagemDialogExcluir);
        if (this.mensagemDialogExcluir == null) {
            this.mensagemDialogExcluir = mensagemDialogExcluir;
        }
    }

    /**
     * @return the mensagemDialogSalvar
     */
    public String getMensagemDialogSalvar() {
        return mensagemDialogSalvar;
    }

    /**
     * @param mensagemDialogSalvar the mensagemDialogSalvar to set
     */
    public void setMensagemDialogSalvar(String mensagemDialogSalvar) {
        this.mensagemDialogSalvar = abstratoCrudMBean.getMensagemDoRotulo(mensagemDialogSalvar);
        if (this.mensagemDialogSalvar == null) {
            this.mensagemDialogSalvar = mensagemDialogSalvar;
        }
    }

    /**
     * @return the abstratoMBean
     */
    public AbstratoCrudMBean<?> getAbstratoMBean() {
        return abstratoCrudMBean;
    }

    /**
     * @param abstratoMBean the abstratoMBean to set
     */
    public void setAbstratoMBean(AbstratoCrudMBean<?> abstratoMBean) {
        this.abstratoCrudMBean = abstratoMBean;
    }
}
