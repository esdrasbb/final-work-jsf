package pos.fa7.cursoweb.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Description: Classe auxiliar para manipula��o de mensagens no contexto do
 * Faces.
 * 
 * @author F�bio Barros
 */
public final class MessageHelper {

	/**
	 * Construtor privado para impedir a instanciacao de um objeto. Forca
	 * utilizacao dos metodos na notacao estatica.
	 */
	private MessageHelper() {
	}

	/**
	 * Retorna o resource bundle da aplica��o.
	 * 
	 * @return ResourceBundle de acordo com o Locale da aplica��o.
	 */
	private static ResourceBundle getBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, "msg");
	}

	/**
	 * Cria uma mensagem a partir do arquivo de resources e dos parametros
	 * passados.
	 * 
	 * @param messageKey
	 *            chave da mensagem
	 * @param parametros
	 *            parametros
	 * @return mensagem formatada
	 */
	public static String getMessage(String messageKey, Object... parametros) {
		return MessageFormat.format(getBundle().getString(messageKey), parametros);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos.
	 * 
	 * @param severity
	 *            Severidade do erro descrita em {@link FacesMessage}
	 * @param summaryKey
	 *            Chave da mensagem de erro
	 * @param parametros
	 *            parametros da mensagem
	 * @return mensagem criada
	 */
	public static FacesMessage createMessage(Severity severity, String summaryKey, Object... parametros) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(severity);
		if (summaryKey != null && !summaryKey.equals("")) {
			message.setSummary(getMessage(summaryKey, parametros));
		}

		return message;
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos e adiciona
	 * ao escopo da requisi��o atual.
	 * 
	 * @param severity
	 *            Severidade do erro descrita em {@link FacesMessage}
	 * @param componetId
	 *            ID do componente ao qual a mensagem ser� associada
	 * @param summaryKey
	 *            Chave da mensagem de erro
	 * @param parametros
	 *            parametros para a mensagem
	 */
	public static void addMessage(Severity severity, String componetId, String summaryKey, Object... parametros) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = createMessage(severity, summaryKey, parametros);
		context.addMessage(componetId, message);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de erro predefinida.
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem ser� associada
	 * @param summaryKey
	 *            Chave do detale da mensagem de erro
	 * @param parametros
	 *            parametros para mensagem de erro
	 */
	public static void addErrorMessage(String componetId, String summaryKey, Object... parametros) {
		addMessage(FacesMessage.SEVERITY_ERROR, componetId, summaryKey, "", parametros);
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de informa��o predefinida.
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem ser� associada
	 * @param summaryKey
	 *            Chave do detale da mensagem de erro
	 */
	public static void addInfoMessage(String componetId, String summaryKey) {
		addMessage(FacesMessage.SEVERITY_INFO, componetId, summaryKey, "");
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de aviso predefinida.
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem ser� associada
	 * @param summaryKey
	 *            Chave do detale da mensagem de erro
	 */
	public static void addWarnMessage(String componetId, String summaryKey) {
		addMessage(FacesMessage.SEVERITY_WARN, componetId, summaryKey, "");
	}

	/**
	 * Cria uma mensagem a partir de uma chave do arquivo de recursos com a
	 * severidade de erro fatal predefinida.
	 * 
	 * @param componetId
	 *            ID do componente ao qual a mensagem ser� associada
	 * @param summaryKey
	 *            Chave do detale da mensagem de erro
	 */
	public static void addFatalMessage(String componetId, String summaryKey) {
		addMessage(FacesMessage.SEVERITY_FATAL, componetId, summaryKey, "");
	}
}
