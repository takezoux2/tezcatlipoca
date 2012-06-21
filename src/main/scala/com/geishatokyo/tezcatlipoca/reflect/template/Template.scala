package com.geishatokyo.tezcatlipoca.template

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 2:07
 * To change this template use File | Settings | File Templates.
 */

trait Template[T] {

  def toString( obj : T) : String

  def fromString( s : String) : T
}
