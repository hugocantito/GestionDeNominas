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

public class DatosNoCorrectosException extends Exception {

    private static final long serialVersionUID = 1L;
    
	public DatosNoCorrectosException() {
		super("Datos no correctos");
	}
}
