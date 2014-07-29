package sfingsolutions;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.NivelEntity;
import sfingsolutions.negocio.EventBC;
import sfingsolutions.negocio.exception.BCException;

/**
 * Manager Bean responsável pelo entidade cadastro de nivel.
 * 
 * @author Vinicius Braz.
 */
@ViewScoped
@ManagedBean(name = "cadastroNivelMBean")
public class CadastroNivelMBean extends AbstratoCrudMBean<NivelEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CadastroNivelMBean.class);

    @EJB
    private EventBC eventBC;

    /**
     * Construtor padrão da classe VisualMBean.
     */
    public CadastroNivelMBean() {
        super();
        LOGGER.debug("... CadastroNivelMBean()");
        setItem(new NivelEntity());

        setIsCadastro(Boolean.TRUE);

        DialogMensagemMBean dialogMensagemMBean = getReferenciaDialogMensagemMBean();

        dialogMensagemMBean.setAbstratoMBean(this);
        dialogMensagemMBean.setMensagemDialogExcluir("cadastroNivel.mensagemDialogExcluir");
        dialogMensagemMBean.setMensagemDialogSalvar("cadastroNivel.mensagemDialogSalvar");
    }

    /**
     * preEditar.
     * @param entity NivelEntity.
     */
    public void preEditar(NivelEntity entity) {
        try {
            setItem(eventBC.buscarNivelEntity(entity));

            setIsCadastro(Boolean.FALSE);

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o preEditar{}: ", e);
            lancarExcecao(e.getMessage());
        }
    }

    /**
     * preNovo.
     */
    public void preNovo() {
        setItem(new NivelEntity());
        setIsCadastro(Boolean.TRUE);
    }
    
    public void onChanceValueBigBlind() {
        if(getItem().getSmallBlind() != null) {
            getItem().setBigBlind(getItem().getSmallBlind() * 2);
        }
            
    }

    @Override
    public List<NivelEntity> carregarListagem(int first, int pageSize, String sortField, SortOrder sortOrder,
        Map<String, String> filters) {

        try {
            return eventBC.listarAllNivel(first, pageSize);
        } catch (BCException e) {
            LOGGER.error("Erro ao obter a lista da base de dados {}: ", e);
            return null;
        }
    }
    
    @Override
    public int getRowCountForDataModel() {
        try {
            return eventBC.listarAllNivel().size();
        } catch (BCException e) {
            LOGGER.error("Erro ao executar getRowCountForDataModel da class CadastroNivelMBean{}: ", e);
            return 0;
        }
    }
    
    @Override
    public Boolean validarCamposCadastro() {
        NivelEntity nivelEntity = getItem();

        setIsValid(Boolean.TRUE);
        
        isInValidInteger(nivelEntity.getNumberNivel(), "cadastroNivel.error.message.obrigatorio.number",
            ":formDialogNovoCadastro:inputNumber");

        isInValidDouble(nivelEntity.getSmallBlind(), "cadastroNivel.error.message.obrigatorio.small",
            ":formDialogNovoCadastro:inputSmall");
        
        isInValidDouble(nivelEntity.getBigBlind(), "cadastroNivel.error.message.obrigatorio.big",
            ":formDialogNovoCadastro:inputBig");

        confirmDialogSalvar(getIsValid());
        return getIsValid();
    }

    @Override
    public NivelEntity salvar() {
        try {
            eventBC.salvarNivelEntity(getItem());
        } catch (BCException e) {
            LOGGER.error("Erro ao salvar o nivel na base de dados {}: ", e);
            lancarExcecao(e.getMessage());
        }
        
        return getItem();
    }

    @Override
    public NivelEntity excluir() {
        // TODO Auto-generated method stub
        return null;
    }

}
