/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hugocantito.gestiondenominas.Laboral;

/**
 * @author hugocantito
 *
 */


public class Persona {
	public String nombre;
	public String dni;
	public char sexo;
	
	/**
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * @throws DatosNoCorrectosException 
	 */
	public Persona(String nombre, String dni, char sexo) throws DatosNoCorrectosException {
		super();
		this.nombre = nombre;
		ValidadorDNI validadordni = new ValidadorDNI(dni);
                
		if (validadordni.validar()) {
			this.dni = dni;
		}else {
			throw new DatosNoCorrectosException();
		}
		if (sexo != 'F' && sexo != 'M') {
			throw new DatosNoCorrectosException();
		}else {
			this.sexo = sexo;
		}
	}
	
	/**
	 * @param nombre
	 * @param sexo
	 * @throws DatosNoCorrectosException 
	 */
	public Persona(String nombre, char sexo) throws DatosNoCorrectosException {
		super();
		this.nombre = nombre;
                
		if (sexo != 'F' && sexo != 'M') {
			throw new DatosNoCorrectosException();
		}else {
			this.sexo = sexo;
		}
	}

	/**
	 * @param dni numero del dni
	 * @throws DatosNoCorrectosException 
	 */
	public void setDni(String dni) throws DatosNoCorrectosException {
		ValidadorDNI validadordni = new ValidadorDNI(dni);
                
		if (validadordni.validar()) {
			this.dni = dni;
		}else {
			throw new DatosNoCorrectosException();
		}
	}

	/**
	 * @return nombre y dni de la persona
	 */
	public String imprime() {
		return "nombre=" + nombre + ", dni=" + dni;
	}
	
	/*
	 * @author hugocantito
	 */

	class ValidadorDNI
	{
		private String dni;

		public ValidadorDNI(String dni) {
			this.dni = dni;
		}

		
		public boolean validar() {

			
			String letraMayuscula = "";
				
			
			if(dni.length() != 9 || Character.isLetter(this.dni.charAt(8)) == false ) {
				return false;
			}

			
			letraMayuscula = (this.dni.substring(8)).toUpperCase();

			if(soloNumeros() == true && letraDNI().equals(letraMayuscula)) {
				return true;
			}
			else {
				return false;
			}
		}

			private boolean soloNumeros() {

				int i, j = 0;
				String numero = ""; 
				String miDNI = ""; 
				String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

				for(i = 0; i < this.dni.length() - 1; i++) {
					numero = this.dni.substring(i, i+1);

					for(j = 0; j < unoNueve.length; j++) {
						if(numero.equals(unoNueve[j])) {
							miDNI += unoNueve[j];
						}
					}
				}

				if(miDNI.length() != 8) {
					return false;
				}
				else {
					return true;
				}
			}

			private String letraDNI() {
			
			
			int miDNI = Integer.parseInt(this.dni.substring(0,8));
			int resto = 0;
			String miLetra = "";
			String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

			resto = miDNI % 23;

			miLetra = asignacionLetra[resto];

			return miLetra;
		}
	}
	
	
	
	

}
