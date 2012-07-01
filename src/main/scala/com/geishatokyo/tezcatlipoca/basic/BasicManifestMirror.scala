package com.geishatokyo.tezcatlipoca.basic

import com.geishatokyo.tezcatlipoca.{ManifestMirror, Mirror}
import com.geishatokyo.tezcatlipoca.description.ClassDescRepository
import com.geishatokyo.tezcatlipoca.assign.{AssignTemplate, AssignTemplateRepository}

/**
 *
 * User: takeshita
 * Create: 12/07/01 19:08
 */

class BasicManifestMirror(classDescRepository : ClassDescRepository,
                          assignTemplateRepository : AssignTemplateRepository,
                          val forceBelieveManifest : Boolean) extends ManifestMirror {


  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflect[A, B](from: A, to: B)(implicit m1: Manifest[A], m2: Manifest[B]): Int = {

    val fromDesc = classDescRepository(shouldDetectFromInstanceItsSelf(from,m1))
    val toDesc = classDescRepository(shouldDetectFromInstanceItsSelf(to,m2))

    val assignTemplate = assignTemplateRepository(fromDesc,toDesc).asInstanceOf[AssignTemplate[A,B]]

    assignTemplate.assign(from,to)
  }

  def reflect[A](from: A)(implicit m1: Manifest[A]): Map[String, String] = {
    Map.empty
  }


  /**
   *
   * @param from
   * @param to
   * @return number of updated fields
   */
  def reflectFromMap[A](from: Map[String, String], to: A)(implicit m: Manifest[A]): Int = 0

  def reflectOnly[A, T](from: A)(implicit a: Manifest[T], m: Manifest[T]): Map[String, T] = {
    Map.empty
  }

  protected def shouldDetectFromInstanceItsSelf[T](obj : T, m : Manifest[T]) = {
    if(forceBelieveManifest) m
    else{
      val clazz = obj.getClass
      if(clazz.getTypeParameters().size > 0){
        // if these class is passed, type guessing may be wrong
        // try to use type erased clazz
        if( (clazz == classOf[AnyRef]) || (clazz == classOf[Serializable]) ) {
          Manifest.classType(clazz)
        }else{
          m
        }
      }else{
        Manifest.classType(clazz)
      }
    }



  }

}
