package com.geishatokyo.tezcatlipoca.exception

/**
 *
 * User: takeshita
 * Create: 12/06/25 12:29
 */

class ConversionException(propName : String,e : Exception) extends Exception(propName , e) {

}
