package com.geishatokyo.tezcatlipoca.description

/**
 *
 * User: takeshita
 * Create: 12/07/01 18:18
 */

trait ClassDescBuilder {

  def loadableClass_?(m : Manifest[_]) : Boolean

  def getClassDesc( m : Manifest[_]): ClassDesc[_]

}
