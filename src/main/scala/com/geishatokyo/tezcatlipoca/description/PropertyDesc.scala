package com.geishatokyo.tezcatlipoca.description

import java.lang.reflect.{TypeVariable, Field, Method}

/**
 *
 * User: takeshita
 * Create: 12/07/01 18:05
 */

case class PropertyDesc(name: String, nestObj: Option[Method],
                        getter: Method, setter: Method , field : Option[Field]) {

  def propertyType = getter.getGenericReturnType

  var readOnly = true

  var generic_? = propertyType.isInstanceOf[TypeVariable[_]]

  var synonym : Seq[String] = Seq()


}
