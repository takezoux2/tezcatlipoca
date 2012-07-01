package com.geishatokyo.tezcatlipoca.reflection.description

import com.geishatokyo.tezcatlipoca.description.{PropertyDesc, ClassDesc, ClassDescBuilder}
import com.geishatokyo.tezcatlipoca.util.PropFinder

/**
 *
 * User: takeshita
 * Create: 12/07/01 23:52
 */

class ScalaPlainObjectDescBuilder extends ClassDescBuilder {


  def loadableClass_?(m: Manifest[_]): Boolean = {
    classOf[ScalaObject].isAssignableFrom(m.erasure)
  }



  def getClassDesc(m : Manifest[_]): ClassDesc[_] = {
    val clazz = m.erasure
    val props = PropFinder.listUpProps(clazz)
    new ClassDesc(m, props.map( p => {
      //TODO check annotation
      val name = p.getter.getName
      PropertyDesc(name,None,p.getter,p.setter,p.field)
    }))

  }
}
