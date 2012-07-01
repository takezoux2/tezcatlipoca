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
  def reflect(from : AnyRef,to : AnyRef) : Int

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

object Mirror{

  implicit def wrap( mMirror : ManifestMirror) = {
    new ManifestMirrorWrapper(mMirror)
  }

}

class ManifestMirrorWrapper(innerMirror : ManifestMirror) extends Mirror{
  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflect(from: AnyRef, to: AnyRef): Int = {
    innerMirror.reflect(from,to)(Manifest.classType(from.getClass),Manifest.classType(to.getClass))
  }

  def reflect(from: AnyRef): Map[String, String] = {
    innerMirror.reflect(from)(Manifest.classType(from.getClass))
  }

  def reflectOnly[T](from: AnyRef)(implicit m: Manifest[T]): Map[String, T] = {
    innerMirror.reflectOnly[AnyRef,T](from)(Manifest.classType(from.getClass),m)
  }

  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflectFromMap(from: Map[String, String], to: AnyRef): Int = {
    innerMirror.reflectFromMap(from,to)(Manifest.classType(to.getClass))
  }
}


