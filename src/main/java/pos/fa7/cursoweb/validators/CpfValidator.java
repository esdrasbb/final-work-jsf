package pos.fa7.cursoweb.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import pos.fa7.cursoweb.util.ValidacaoHelper;

@FacesValidator("validators.CpfValidator")
public class CpfValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			String valor = value.toString();
			if (!ValidacaoHelper.validaCpf(valor)) {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Erro de Valida��o");
				message.setDetail("CPF Inv�lido");
				throw new ValidatorException(message);
			}
		}
	}

}
