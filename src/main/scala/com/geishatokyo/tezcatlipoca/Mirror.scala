package com.geishatokyo.tezcatlipoca

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 1:39
 * To change this template use File | Settings | File Templates.
 */

trait Mirror {

  def reflect( from : AnyRef, to : AnyRef) : Int

  def reflect(from : AnyRef) : Map[String,String]

  def reflectFromMap(from : Map[String,String] , to : AnyRef) : Int

}
