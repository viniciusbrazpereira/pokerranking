package sfingsolutions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.dominio.EstadoUsuarioEntity;
import sfingsolutions.dominio.PokerEventEntity;
import sfingsolutions.dominio.UsuarioEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntity;
import sfingsolutions.dominio.UsuarioPokerEventEntityId;
import sfingsolutions.helper.CalculoRankingHelper;
import sfingsolutions.negocio.EventBC;
import sfingsolutions.negocio.UsuarioBC;
import sfingsolutions.negocio.exception.BCException;
import sfingsolutions.utils.ApoioMatematico;

/**
 * Manager Bean responsável pelo entidade cadastro de evento.
 * 
 * @author Vinicius Braz.
 */
@ViewScoped
@ManagedBean(name = "cadastroEventMBean")
public class CadastroEventMBean extends AbstratoCrudMBean<PokerEventEntity> {

    /**
     * 
     */
    private static final long serialVersionUID = -6116967344575885143L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CadastroEventMBean.class);

    private Integer idApostador;
    private List<SelectItem> selectApostador;
    private List<UsuarioEntity> apostadores = new ArrayList<UsuarioEntity>();
    private List<UsuarioEntity> selectItemApostador;

    private List<UsuarioPokerEventEntity> pokerEventEntities;

    private static final String DIALOG_OPEN_EVENT = "dlgOpenEvent.show()";
    private static final String DIALOG_START_EVENT = "dlgStartEvent.show()";

    private static final Integer HORA_ADICIONAL = 3;

    private Boolean startedEvent;
    private Boolean stopedEvent;
    private Boolean excluirPunter;
    private Boolean addPunter;

    @EJB
    private UsuarioBC usuarioBC;

    @EJB
    private EventBC eventBC;

    /**
     * Construtor padrão da classe.
     */
    public CadastroEventMBean() {
        super();
        LOGGER.debug("... CadastroEventMBean()");

        setIsCadastro(Boolean.TRUE);
        setItem(new PokerEventEntity());

        startButtons();

        DialogMensagemMBean dialogMensagemMBean = getReferenciaDialogMensagemMBean();

        dialogMensagemMBean.setAbstratoMBean(this);
        dialogMensagemMBean.setMensagemDialogExcluir("cadastroEvent.mensagemDialogExcluir");
        dialogMensagemMBean.setMensagemDialogSalvar("cadastroEvent.mensagemDialogSalvar");
    }

    private void startButtons() {
        stopedEvent = Boolean.TRUE;
        startedEvent = Boolean.FALSE;
        excluirPunter = Boolean.FALSE;
        addPunter = Boolean.FALSE;
    }

    private List<UsuarioEntity> getListaApostador() {
        try {
            return this.usuarioBC.listarUsuario();
        } catch (BCException e) {
            LOGGER.error("Erro no metodo getListaApostador da class CadastroEventMBean {}: ", e);
            return null;
        }
    }

    /**
     * preNovo.
     */
    public void preNovo() {
        setItem(new PokerEventEntity());
        setIsCadastro(Boolean.TRUE);
    }

    /**
     * preEditar.
     * @param entity PokerEventEntity.
     */
    public void preEditar(PokerEventEntity pokerEventEntity) {
        try {
            setItem(eventBC.buscarPokerEventEntity(pokerEventEntity));
            setIsCadastro(Boolean.FALSE);

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o preEditar{}: ", e);
            lancarExcecao("Erro ao carregar o preEditar{}: " + e.getMessage());
        }
    }

    /**
     * isEditarPokerEvent
     * @param pokerEventEntity
     */
    public Boolean isEditarPokerEvent(PokerEventEntity pokerEventEntity) {
        return pokerEventEntity.getDataInicio() != null;
    }

    /**
     * iniciarEvento.
     */
    public void iniciarEvento() {
        try {
            UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();
            usuarioPokerEventEntityId.setPokerEventEntity(getItem());

            UsuarioPokerEventEntity usuarioPokerEventEntity = new UsuarioPokerEventEntity();
            usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

            List<UsuarioPokerEventEntity> usuarioEvento = eventBC
                .selectAllUsuarioPokerEventEntity(usuarioPokerEventEntity);

            if (usuarioEvento.isEmpty()) {
                lancarExcecao("cadastroEvent.mensagemAviso.incluirPunter");
            } else {
                PokerEventEntity pokerEventEntity = getItem();
                pokerEventEntity.setDataInicio(new Date());

                eventBC.salvarPokerEventEntity(pokerEventEntity);

                startedEvent = Boolean.TRUE;
                stopedEvent = Boolean.FALSE;
            }

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o iniciarEvento{}: ", e);
            lancarExcecao("Erro ao carregar o iniciarEvento{}: " + e.getMessage());
        }
    }

    /**
     * finalizarEvento.
     */
    public void finalizarEvento() {
        try {
            PokerEventEntity pokerEventEntity = getItem();
            pokerEventEntity.setDataFinal(new Date());

            eventBC.salvarPokerEventEntity(pokerEventEntity);

            stopedEvent = Boolean.TRUE;
            addPunter = Boolean.TRUE;

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o finalizarEvento{}: ", e);
            lancarExcecao("Erro ao carregar o finalizarEvento{}: " + e.getMessage());
        }
    }

    /**
     * preAddPunter.
     * @param entity PokerEventEntity.
     */
    public void preAddPunter(PokerEventEntity pokerEventEntity) {
        try {
            Calendar calendar = Calendar.getInstance(); // creates calendar
            calendar.setTime(pokerEventEntity.getDataEvent()); // sets calendar
                                                               // time/date
            calendar.add(Calendar.HOUR_OF_DAY, HORA_ADICIONAL); // adds one hour
                                                                // ... horario
                                                                // adicional
                                                                // para iniciar
                                                                // o evento.

            if (calendar.getTime().compareTo(new Date()) < 0 && pokerEventEntity.getDataInicio() == null) {
                lancarAviso("cadastroEvent.mensagemAviso.data.excedida");

            } else {
                setItem(eventBC.buscarPokerEventEntity(pokerEventEntity));
                setIsCadastro(Boolean.FALSE);

                UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();
                usuarioPokerEventEntityId.setPokerEventEntity(getItem());

                UsuarioPokerEventEntity usuarioPokerEventEntity = new UsuarioPokerEventEntity();
                usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

                List<UsuarioPokerEventEntity> usuarioEvento = eventBC
                    .selectAllUsuarioPokerEventEntity(usuarioPokerEventEntity);

                apostadores = new ArrayList<UsuarioEntity>();

                for (UsuarioPokerEventEntity evento : usuarioEvento) {
                    apostadores.add(evento.getUsuarioPokerEventEntityId().getUsuarioEntity());
                }

                startButtons();

                if (getItem().getDataInicio() == null && getItem().getDataFinal() == null) {
                    excluirPunter = Boolean.FALSE;
                    addPunter = Boolean.FALSE;
                }

                if (getItem().getDataInicio() != null && getItem().getDataFinal() == null) {
                    excluirPunter = Boolean.TRUE;
                    addPunter = Boolean.FALSE;
                }

                if (getItem().getDataInicio() != null && getItem().getDataFinal() != null) {
                    excluirPunter = Boolean.TRUE;
                    addPunter = Boolean.TRUE;
                }

                getRequestContext().execute(DIALOG_OPEN_EVENT);
            }

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o preAddPunter{}: ", e);
            lancarExcecao("Erro ao carregar o preAddPunter{}: " + e.getMessage());
        }
    }

    /**
     * preIniciarEvento.
     * @param entity PokerEventEntity.
     */
    public void preIniciarEvento(PokerEventEntity pokerEventEntity) {
        try {
            Calendar calendar = Calendar.getInstance(); // creates calendar
            calendar.setTime(pokerEventEntity.getDataEvent()); // sets calendar
                                                               // time/date
            calendar.add(Calendar.HOUR_OF_DAY, HORA_ADICIONAL); // adds one hour
                                                                // ... horario
                                                                // adicional
                                                                // para iniciar
                                                                // o evento.

            if (calendar.getTime().compareTo(new Date()) < 0 && pokerEventEntity.getDataInicio() == null) {
                lancarAviso("cadastroEvent.mensagemAviso.data.excedida");

            } else {
                setItem(eventBC.buscarPokerEventEntity(pokerEventEntity));
                setIsCadastro(Boolean.FALSE);

                UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();
                usuarioPokerEventEntityId.setPokerEventEntity(getItem());

                UsuarioPokerEventEntity usuarioPokerEventEntity = new UsuarioPokerEventEntity();
                usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

                pokerEventEntities = eventBC.selectAllUsuarioPokerEventEntity(usuarioPokerEventEntity);
                CalculoRankingHelper.orderUsuarioPokerEventByPosition(pokerEventEntities);

                apostadores = new ArrayList<UsuarioEntity>();

                for (UsuarioPokerEventEntity evento : pokerEventEntities) {
                    apostadores.add(evento.getUsuarioPokerEventEntityId().getUsuarioEntity());
                }

                if (pokerEventEntities != null) {
                    for (UsuarioPokerEventEntity pokerEventRanking : pokerEventEntities) {
                        pokerEventRanking.setRankingPoint(CalculoRankingHelper.calcRanking(pokerEventEntities,
                            pokerEventRanking, getItem()));
                    }
                }

                startButtons();

                if (getItem().getDataInicio() != null && getItem().getDataFinal() == null) {
                    startedEvent = Boolean.TRUE;
                    stopedEvent = Boolean.FALSE;
                }

                if (getItem().getDataFinal() != null && getItem().getDataInicio() != null) {
                    excluirPunter = Boolean.TRUE;
                    startedEvent = Boolean.TRUE;

                    stopedEvent = Boolean.TRUE;
                    addPunter = Boolean.TRUE;
                }

                getRequestContext().execute(DIALOG_START_EVENT);
            }

        } catch (BCException e) {
            LOGGER.error("Erro ao carregar o preIniciarEvento{}: ", e);
            lancarExcecao("Erro ao carregar o preIniciarEvento{}: " + e.getMessage());
        }
    }

    private double calcPrizeWinner(List<UsuarioPokerEventEntity> entities, UsuarioPokerEventEntity pokerEventRanking) {
        double buyin = getItem().getBuy() * pokerEventRanking.getCountBuy();
        double rebuyin = getItem().getRebuy() * pokerEventRanking.getCountRebuy();
        double addon = getItem().getAddon() * pokerEventRanking.getCountAddon();

        return buyin + rebuyin + addon;
    }

    private double calcRebuyinPercent(UsuarioPokerEventEntity pokerEventRanking) {
        double rebuyin = getItem().getRebuy() * pokerEventRanking.getCountRebuy();
        Double percentPrize = ApoioMatematico.calcPrizeHome(rebuyin, getItem().getPercentPrizeHome());

        if (getItem().getIsPercentRebuyin()) {
            return percentPrize;
        }

        return 0;
    }

    private double calcAddonPercent(UsuarioPokerEventEntity pokerEventRanking) {
        double addon = getItem().getAddon() * pokerEventRanking.getCountAddon();
        Double percentPrize = ApoioMatematico.calcPrizeHome(addon, getItem().getPercentPrizeHome());

        if (getItem().getIsPercentAddon()) {
            return percentPrize;
        }

        return 0;
    }

    @Override
    public List<PokerEventEntity> carregarListagem(int first, int pageSize, String sortField,
        org.primefaces.model.SortOrder sortOrder, Map<String, String> filters) {

        try {
            return eventBC.listarAllPokerEvent(first, pageSize);
        } catch (BCException e) {
            LOGGER.error("Erro nome metodo carregarListagem da class CadastroEventMBean {}: ", e);
            return null;
        }
    }

    @Override
    public int getRowCountForDataModel() {
        try {
            return eventBC.rowCountAllPokerEvent().intValue();
        } catch (BCException e) {
            LOGGER.error("Erro nome metodo carregarListagem da class CadastroEventMBean {}: ", e);
            return 0;
        }
    }

    private void createSelectUsuario() {
        selectApostador = new ArrayList<SelectItem>();
        selectItemApostador = getListaApostador();

        for (UsuarioEntity entity : apostadores) {
            for (int i = 0; i < selectItemApostador.size(); i++) {
                if (entity.equals(selectItemApostador.get(i))) {
                    selectItemApostador.remove(i);
                }
            }
        }

        for (UsuarioEntity entity : selectItemApostador) {
            if (entity.getEstadoUsuario().getId() == EstadoUsuarioEntity.ATIVO) {
                createSelectItem(selectApostador, entity.getNome(), entity.getId());
            }
        }
    }

    protected PokerEventEntity populaBean() {
        PokerEventEntity pokerEventEntity = getItem();

        return pokerEventEntity;
    }

    @Override
    public PokerEventEntity salvar() {
        PokerEventEntity pokerEventEntity = populaBean();

        if (getIsCadastro()) {
            pokerEventEntity.setDataInclusao(new Date());
            pokerEventEntity.setPrizeWinner(new Double(0));
            pokerEventEntity.setPrizeHome(new Double(0));
        }

        try {
            eventBC.salvarPokerEventEntity(pokerEventEntity);
        } catch (BCException e) {
            LOGGER.error("Erro ao salvar o evento na base de dados {}: ", e);
            lancarExcecao("Erro ao salvar o evento na base de dados {}: " + e.getMessage());
        }

        return pokerEventEntity;
    }

    @Override
    public PokerEventEntity excluir() {
        PokerEventEntity pokerEventEntity = populaBean();

        try {
            eventBC.deletarPokerEventEntity(pokerEventEntity);
        } catch (BCException e) {
            LOGGER.error("Erro ao excluir o evento na base de dados {}: ", e);
            lancarExcecao("Erro ao excluir o evento na base de dados {}: " + e.getMessage());
        }

        return pokerEventEntity;
    }

    public void addApostador() {
        try {
            if (idApostador == 0) {
                lancarAviso("cadastroEvent.mensagemAviso.selectedPunter");

            } else {
                UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();

                PokerEventEntity pokerEventEntity = getItem();
                Double percentPrize = ApoioMatematico.calcPrizeHome(pokerEventEntity.getBuy(), getItem()
                    .getPercentPrizeHome());

                Double prizeHome = pokerEventEntity.getPrizeHome() + percentPrize;
                Double prizeWinner = pokerEventEntity.getPrizeWinner() + (pokerEventEntity.getBuy() - percentPrize);

                pokerEventEntity.setPrizeHome(prizeHome);
                pokerEventEntity.setPrizeWinner(prizeWinner);
                pokerEventEntity.setPrizeTotal(prizeHome + prizeWinner);

                eventBC.salvarPokerEventEntity(pokerEventEntity);

                UsuarioEntity usuarioEntity = obterUsuarioId(idApostador);

                usuarioPokerEventEntityId.setUsuarioEntity(usuarioEntity);
                usuarioPokerEventEntityId.setPokerEventEntity(pokerEventEntity);

                UsuarioPokerEventEntity usuarioPokerEventEntity = new UsuarioPokerEventEntity();
                usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

                apostadores.add(usuarioEntity);
                selectApostador = null;

                usuarioPokerEventEntity.setCountBuy(1);

                eventBC.salvarUsuarioPokerEventEntity(usuarioPokerEventEntity);
            }
        } catch (BCException e) {
            LOGGER.error("Erro ao salvar usuarioPokerEventEntity na base de dados {}: ", e);
            lancarExcecao("Erro ao salvar usuarioPokerEventEntity na base de dados {}: " + e.getMessage());
        }
    }

    public void deleteApostador(UsuarioEntity usuarioEntity) {
        try {
            PokerEventEntity pokerEventEntity = getItem();
            Double percentPrize = ApoioMatematico.calcPrizeHome(pokerEventEntity.getBuy(), getItem()
                .getPercentPrizeHome());

            Double prizeHome = pokerEventEntity.getPrizeHome() - percentPrize;
            Double prizeWinner = pokerEventEntity.getPrizeWinner() - (pokerEventEntity.getBuy() - percentPrize);

            pokerEventEntity.setPrizeHome(prizeHome);
            pokerEventEntity.setPrizeWinner(prizeWinner);
            pokerEventEntity.setPrizeTotal(prizeHome + prizeWinner);

            eventBC.salvarPokerEventEntity(pokerEventEntity);

            UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();

            usuarioPokerEventEntityId.setUsuarioEntity(usuarioEntity);
            usuarioPokerEventEntityId.setPokerEventEntity(getItem());

            UsuarioPokerEventEntity usuarioPokerEventEntity = new UsuarioPokerEventEntity();
            usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

            apostadores.remove(usuarioEntity);
            selectApostador = null;

            eventBC.deleteUsuarioPokerEventEntity(usuarioPokerEventEntity);
        } catch (BCException e) {
            LOGGER.error("Erro ao salvar usuarioPokerEventEntity na base de dados {}: ", e);
            lancarExcecao("Erro ao salvar usuarioPokerEventEntity na base de dados {}: " + e.getMessage());
        }
    }

    public void editarPunterEvent(UsuarioPokerEventEntity usuarioPokerEventEntity) {
        try {
            eventBC.salvarUsuarioPokerEventEntity(usuarioPokerEventEntity);

            PokerEventEntity pokerEventEntity = getItem();

            UsuarioPokerEventEntityId usuarioPokerEventEntityId = new UsuarioPokerEventEntityId();
            usuarioPokerEventEntityId.setPokerEventEntity(pokerEventEntity);

            usuarioPokerEventEntity.setUsuarioPokerEventEntityId(usuarioPokerEventEntityId);

            pokerEventEntities = eventBC.selectAllUsuarioPokerEventEntity(usuarioPokerEventEntity);

            if (pokerEventEntities != null) {
                CalculoRankingHelper.orderUsuarioPokerEventByPosition(pokerEventEntities);

                double totalPrize = 0;
                double totalBuyinPercent = 0;
                double totalRebuyinPercent = 0;
                double totalAddonPercent = 0;

                for (UsuarioPokerEventEntity pokerEventRanking : pokerEventEntities) {
                    // Calculo total menos valor da porcentagem total para o
                    // ranking
                    totalPrize = totalPrize + calcPrizeWinner(pokerEventEntities, pokerEventRanking);

                    totalBuyinPercent = totalBuyinPercent
                        + ApoioMatematico.calcPrizeHome(getItem().getBuy(), getItem().getPercentPrizeHome());

                    totalRebuyinPercent = totalRebuyinPercent + calcRebuyinPercent(pokerEventRanking);
                    totalAddonPercent = totalAddonPercent + calcAddonPercent(pokerEventRanking);

                    // Calculo da pontuacao
                    pokerEventRanking.setRankingPoint(CalculoRankingHelper.calcRanking(pokerEventEntities,
                        pokerEventRanking, getItem()));
                }

                Double totalHome = totalBuyinPercent + totalRebuyinPercent + totalAddonPercent;

                for (UsuarioPokerEventEntity pokerEventRanking : pokerEventEntities) {
                    // Calculo do valor ganho por posicao
                    Double prizeGain = ApoioMatematico.calcPrize(pokerEventEntities.size(),
                        pokerEventRanking.getPosition(), (totalPrize - totalHome));

                    pokerEventRanking.setPrizeGain(new Double(Math.round(prizeGain)));
                    eventBC.salvarUsuarioPokerEventEntity(pokerEventRanking);

                    // Calculo da pontuacao
                    pokerEventRanking.setRankingPoint(CalculoRankingHelper.calcRanking(pokerEventEntities,
                        pokerEventRanking, pokerEventEntity));
                }

                pokerEventEntity.setPrizeWinner(totalPrize - totalHome);
                pokerEventEntity.setPrizeHome(totalHome);
                pokerEventEntity.setPrizeTotal(totalPrize);

                eventBC.salvarPokerEventEntity(pokerEventEntity);
            }

        } catch (BCException e) {
            LOGGER.error("Erro no metodo editarPunterEvent da class CadastroEventMBean {}: ", e);
            lancarExcecao("Erro no metodo editarPunterEvent da class CadastroEventMBean {}: " + e.getMessage());
        }
    }

    private UsuarioEntity obterUsuarioId(Integer id) {
        for (UsuarioEntity entity : selectItemApostador) {
            if (entity.getId().intValue() == id.intValue()) {
                return entity;
            }
        }
        return null;
    }

    public Double getPrimeiroPremio() {
        try {
            return new Double(Math.round(ApoioMatematico.calcPrimeiroPremio(pokerEventEntities.size(), getItem()
                .getPrizeWinner())));
        } catch (Exception e) {
            return 0.d;
        }
    }

    public Double getSegundoPremio() {
        try {
            return new Double(Math.round(ApoioMatematico.calcSegundoPremio(pokerEventEntities.size(), getItem()
                .getPrizeWinner())));
        } catch (Exception e) {
            return 0.d;
        }
    }

    public Double getTerceiroPremio() {
        try {
            return new Double(Math.round(ApoioMatematico.calcTerceiroPremio(pokerEventEntities.size(), getItem()
                .getPrizeWinner())));
        } catch (Exception e) {
            return 0.d;
        }
    }

    /**
     * @return the idApostador
     */
    public Integer getIdApostador() {
        return idApostador;
    }

    /**
     * @param idApostador the idApostador to set
     */
    public void setIdApostador(Integer idApostador) {
        this.idApostador = idApostador;
    }

    /**
     * @return the selectApostador
     */
    public List<SelectItem> getSelectApostador() {
        createSelectUsuario();
        return selectApostador;
    }

    /**
     * @param selectApostador the selectApostador to set
     */
    public void setSelectApostador(List<SelectItem> selectApostador) {
        this.selectApostador = selectApostador;
    }

    /**
     * @return the apostadores
     */
    public List<UsuarioEntity> getApostadores() {
        return apostadores;
    }

    /**
     * @param apostadores the apostadores to set
     */
    public void setApostadores(List<UsuarioEntity> apostadores) {
        this.apostadores = apostadores;
    }

    /**
     * @return the startedEvent
     */
    public Boolean getStartedEvent() {
        return startedEvent;
    }

    /**
     * @param startedEvent the startedEvent to set
     */
    public void setStartedEvent(Boolean startedEvent) {
        this.startedEvent = startedEvent;
    }

    /**
     * @return the excluirPunter
     */
    public Boolean getExcluirPunter() {
        return excluirPunter;
    }

    /**
     * @param excluirPunter the excluirPunter to set
     */
    public void setExcluirPunter(Boolean excluirPunter) {
        this.excluirPunter = excluirPunter;
    }

    /**
     * @return the stopedEvent
     */
    public Boolean getStopedEvent() {
        return stopedEvent;
    }

    /**
     * @param stopedEvent the stopedEvent to set
     */
    public void setStopedEvent(Boolean stopedEvent) {
        this.stopedEvent = stopedEvent;
    }

    /**
     * @return the addPunter
     */
    public Boolean getAddPunter() {
        return addPunter;
    }

    /**
     * @param addPunter the addPunter to set
     */
    public void setAddPunter(Boolean addPunter) {
        this.addPunter = addPunter;
    }

    /**
     * @return the pokerEventEntities
     */
    public List<UsuarioPokerEventEntity> getPokerEventEntities() {
        return pokerEventEntities;
    }

    /**
     * @param pokerEventEntities the pokerEventEntities to set
     */
    public void setPokerEventEntities(List<UsuarioPokerEventEntity> pokerEventEntities) {
        this.pokerEventEntities = pokerEventEntities;
    }
}
