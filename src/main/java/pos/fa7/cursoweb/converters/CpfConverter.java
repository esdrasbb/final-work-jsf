package pos.fa7.cursoweb.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import pos.fa7.cursoweb.util.MessageHelper;

@FacesConverter("converters.CpfConverter")
public class CpfConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (StringUtils.isNotBlank(value)) {
			String cpf = value.replaceAll("\\.", "").replaceAll("\\-", "");
			try {
				// Testa se somente existem numeros.
				Long.valueOf(cpf);
				return cpf;
			} catch (NumberFormatException e) {
				throw new ConverterException(
						MessageHelper.createMessage(FacesMessage.SEVERITY_ERROR, "validator.CpfConverter"));
			}
		}
		return value;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String cpf = (value == null ? null : value.toString());
		if (StringUtils.isNotBlank(cpf)) {
			cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
		}
		return cpf;
	}
}
