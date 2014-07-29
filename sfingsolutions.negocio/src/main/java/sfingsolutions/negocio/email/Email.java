package sfingsolutions.negocio.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfingsolutions.utils.DatetimeUtils;

/**
 * Classe abstrata responsável pelo envio de e-mails.
 * @author Vinicius Braz Pereira.
 */
public abstract class Email {

    private static final Logger log = LoggerFactory.getLogger(Email.class);

    private String remetente;
    private String nomeRemetente;
    private String destinatario;
    private String corpo;
    private String titulo;
    private Date dataEnviado;

    /**
     * Construtor default da classe Email (privado).
     */
    private Email() {
        super();
        this.dataEnviado = DatetimeUtils.getAgora();
    }

    /**
     * Define as propriedades para envio de e-mail.
     * @return Propriedades.
     */
    private Properties obterParametros() {
        Properties retorno = new Properties();
        retorno.put("mail.transport.protocol", "smtp");
        retorno.put("mail.smtp.sendpartial", "true");
        retorno.put("mail.store.protocol", "pop3");
        retorno.put("mail.debug", "false");
        // retorno.put("mail.smtp.host",
        // getParametroSistema().getHostServidorEmail());
        // retorno.put("mail.smtp.port",
        // getParametroSistema().getPortaServidorEmail());
        return retorno;
    }

    /**
     * Envia o e-mail.
     * @throws Exception exceção lançada em caso de erro.
     */
    public void enviar() throws Exception {
        try {
            Session session = Session.getInstance(obterParametros(), null);
            session.setDebug(false);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(remetente, nomeRemetente));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario, true));

            msg.setSubject(titulo);
            msg.setSentDate(dataEnviado);

            MimeBodyPart mainBody = new MimeBodyPart();
            mainBody.setContent(corpo, "text/html");

            Multipart parts = new MimeMultipart();
            parts.addBodyPart(mainBody);
            msg.setContent(parts);

            Transport.send(msg);

            log.warn("# -----------------------------------------------");
            log.warn("# E-mail enviado com sucesso");
            log.warn("# De: " + remetente);
            log.warn("# Para: " + destinatario);
            log.warn("# Título: " + titulo);
            log.warn("# -----------------------------------------------");

        } catch (Exception e) {
            log.error("# -----------------------------------------------");
            log.error("# Erro ao enviar e-mail");
            log.error("# De: " + remetente);
            log.error("# Para: " + destinatario);
            log.error("# Título: " + titulo);
            log.error("# Erro: " + e.getCause());
            log.error("# Motivo: " + e.getMessage());
            log.error("# -----------------------------------------------");
            throw e;
        }

    }

    /**
     * @param remetente the remetente to set
     */
    protected void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    /**
     * @param nomeRemetente the nomeRemetente to set
     */
    protected void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    /**
     * @param destinatario the destinatario to set
     */
    protected void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * @param corpo the corpo to set
     */
    protected void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    /**
     * @param titulo the titulo to set
     */
    protected void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @param dataEnviado the dataEnviado to set
     */
    protected void setDataEnviado(Date dataEnviado) {
        this.dataEnviado = dataEnviado;
    }

}
