package pos.fa7.cursoweb.util;

import org.apache.commons.lang3.StringUtils;

import pos.fa7.cursoweb.model.Cep;

/**
 * Classe para auxiliar nas validacoes de numeros (CPF).
 * 
 * @author Fabio Barros
 */
public class ValidacaoHelper {

	/**
	 * Tamanho do CPF.
	 */
	public static final int TAMANHO_CPF = 11;

	/**
	 * Contrutor privado.
	 */
	private ValidacaoHelper() {
		// NOP
	}

	/**
	 * Valida um CPF.
	 * 
	 * @param cpf
	 *            valor a ser validado
	 * @return true se for valido e false caso contrario
	 */
	public static boolean validaCpf(String cpf) {
		int d1;
		int d2;
		int digito1;
		int digito2;
		int resto;
		int digitoCPF;
		String nDigResult;

		d1 = 0;
		d2 = 0;
		digito1 = 0;
		digito2 = 0;
		resto = 0;

		for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}

		// Primeiro resto da divisao por 11.
		resto = d1 % 11;

		// Se o resultado for 0 ou 1 o digito e 0 caso contrario o digito e 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		// Segundo resto da divisao por 11.
		resto = d2 % 11;

		// Se o resultado for 0 ou 1 o digito e 0 caso contrario o digito e 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		// Digito verificador do CPF que esta sendo validado.
		String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);
	}

	public static boolean validaCep(Object object) {
		Cep cep = (Cep) object;
		if (cep == null || !StringUtils.isNumeric(cep.getRegiao()) ||
				!StringUtils.isNumeric(cep.getSufixo())) {
			return false;
		}
		return true;
	}

}
