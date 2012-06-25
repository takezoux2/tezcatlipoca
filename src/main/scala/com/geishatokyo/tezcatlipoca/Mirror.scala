package com.geishatokyo.tezcatlipoca

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 1:39
 * To change this template use File | Settings | File Templates.
 */

trait Mirror {

  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflect( from : AnyRef, to : AnyRef) : Int

  def reflect(from : AnyRef) : Map[String,String]

  def reflectOnly[T](from : AnyRef)(implicit m : Manifest[T]) : Map[String,T]

  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflectFromMap(from : Map[String,String] , to : AnyRef) : Int

}


