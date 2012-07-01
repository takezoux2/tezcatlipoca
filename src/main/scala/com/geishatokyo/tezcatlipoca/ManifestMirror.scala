package com.geishatokyo.tezcatlipoca

/**
 *
 * User: takeshita
 * Create: 12/07/01 23:26
 */

trait ManifestMirror {

  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflect[A,B]( from : A, to : B)(implicit m1 : Manifest[A],m2 : Manifest[B]) : Int

  def reflect[A](from : A)(implicit m1 : Manifest[A]) : Map[String,String]

  def reflectOnly[A,T](from : A)(implicit a : Manifest[T], m : Manifest[T]) : Map[String,T]

  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflectFromMap[A](from : Map[String,String] , to : A)(implicit m : Manifest[A]) : Int
}
