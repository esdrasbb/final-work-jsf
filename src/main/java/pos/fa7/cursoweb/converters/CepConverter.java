package pos.fa7.cursoweb.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import pos.fa7.cursoweb.model.Cep;
import pos.fa7.cursoweb.util.MessageHelper;

@FacesConverter("converters.CepConverter")
public class CepConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (StringUtils.isNotBlank(value)) {
			String cep = value.replaceAll("\\.", "").replaceAll("\\-", "");
			try {
				// Testa se somente existem numeros.
				Long.valueOf(cep);
				return new Cep(cep.substring(0, 5), cep.substring(5, 8));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						MessageHelper.createMessage(FacesMessage.SEVERITY_ERROR, "validator.CepConverter"));
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		Cep cep = (Cep) (value == null ? null : value);
		String cepText = StringUtils.EMPTY;
		if (cep != null) {
			cepText = cep.getRegiao() + "-" + cep.getSufixo();
		}
		return cepText;
	}
}
