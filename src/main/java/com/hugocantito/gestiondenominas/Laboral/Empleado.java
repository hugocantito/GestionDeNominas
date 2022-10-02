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
public class Empleado extends Persona {

	private int categoria=1;
	public int anyos=0;
	
	/**
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * @param categoria
	 * @param anyos
	 */
	public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) throws DatosNoCorrectosException{
		super(nombre, dni, sexo);
		this.setCategoria(categoria);
		if (anyos>=0)
			this.anyos = anyos;
		else throw new DatosNoCorrectosException();
	}

	/**
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * @throws DatosNoCorrectosException 
	 */
	public Empleado(String nombre, String dni, char sexo) throws DatosNoCorrectosException {
		super(nombre, dni, sexo);
	}

	/**
	 * @return catagoría
	 */
	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) throws DatosNoCorrectosException {
		if (categoria<1 || categoria>10) {
			throw new DatosNoCorrectosException();
		}else {
			this.categoria = categoria;
		}
	}

	public void incrAnyo() {
		this.anyos++;
	}

    /**
     *
     * @return toda la información
     */
    @Override
	public String imprime() {
		return "Empleado ["+ super.imprime() +", categoria=" + categoria + ", anyos=" + anyos + "]";
	}	
	

}