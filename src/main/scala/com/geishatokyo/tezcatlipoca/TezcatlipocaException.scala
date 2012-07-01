package com.geishatokyo.tezcatlipoca

/**
 *
 * User: takeshita
 * Create: 12/07/01 18:15
 */

class TezcatlipocaException(m : String, e : Throwable) extends Exception(m,e) {

  def this(m : String) = this(m,null)
}
