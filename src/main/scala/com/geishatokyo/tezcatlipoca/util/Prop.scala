package com.geishatokyo.tezcatlipoca.util

import java.lang.reflect.{Type, Method,Field}

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 1:43
 * To change this template use File | Settings | File Templates.
 */

case class Prop(getter : Method,setter : Method, field : Option[Field]){

  val propType : Type = getter.getGenericReturnType


  def getAnnotation[A <: java.lang.annotation.Annotation](implicit m : Manifest[A]) : Option[A] = {
    val annoClass = m.erasure.asInstanceOf[Class[A]]
    field.flatMap(f => {
      Option(f.getAnnotation(annoClass))
    }).orElse({
      Option(getter.getAnnotation(annoClass))
    }).orElse({
      Option(setter.getAnnotation(annoClass))
    })

  }


}
