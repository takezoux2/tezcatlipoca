package com.geishatokyo.tezcatlipoca.description

import com.geishatokyo.tezcatlipoca.TezcatlipocaException

/**
 *
 * User: takeshita
 * Create: 12/07/01 18:10
 */

case class ClassDesc[T](manifest : Manifest[T], properties: Map[String,PropertyDesc]) extends Map[String, PropertyDesc] {

  def this(manifest : Manifest[T], propSeq: Seq[PropertyDesc]) = {
    this(manifest,{
      propSeq.flatMap( p => {
        (p.name -> p) :: p.synonym.map( s => {
          s -> p
        }).toList
      }).toMap
    })
  }


  def get(key: String): Option[PropertyDesc] = {
    properties.get(key)
  }

  def iterator: Iterator[(String, PropertyDesc)] = properties.iterator

  def -(key: String): Map[String, PropertyDesc] = {
    ClassDesc( manifest, properties - key)
  }


  def +[B1 >: PropertyDesc](kv: (String, B1)): Map[String, B1] = {
    ClassDesc( manifest, properties + kv.asInstanceOf[Tuple2[String,PropertyDesc]])
  }

  def merge(desc: ClassDesc[T]) = {
    if (desc.manifest == manifest) {
      ClassDesc(manifest, properties ++ desc.properties)
    } else {
      throw new TezcatlipocaException("Can't merge defferent class description.Base:%s Passed:%s".format(manifest, desc.manifest))
    }
  }

}
