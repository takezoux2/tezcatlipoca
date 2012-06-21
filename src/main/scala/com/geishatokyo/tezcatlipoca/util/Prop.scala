package com.geishatokyo.tezcatlipoca.util

import java.lang.reflect.{Type, Method}

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 1:43
 * To change this template use File | Settings | File Templates.
 */

case class Prop(getter : Method,setter : Method){

  val name = getter.getName
  val propType : Type = getter.getGenericReturnType



}
