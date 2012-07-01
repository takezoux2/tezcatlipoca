package com.geishatokyo.tezcatlipoca.reflection.assign

import com.geishatokyo.tezcatlipoca.assign.{AssignTemplate, AssignTemplateBuilder}
import com.geishatokyo.tezcatlipoca.description.ClassDesc

/**
 *
 * User: takeshita
 * Create: 12/07/02 0:52
 */

class ReflectionAssignTemplateBuilder extends AssignTemplateBuilder {

  def buildAssignTemplate(from: ClassDesc[_], to: ClassDesc[_]): AssignTemplate[_, _] = {

    val pairs = from.collect({
      case (name,fromProp) if to.contains(name)=> {
        val toProp = to(name)
        (fromProp.name, fromProp.getter, toProp.setter)
      }
    }).toList.distinct


    new ReflectionAssignTemplate(pairs,true)
  }
}
