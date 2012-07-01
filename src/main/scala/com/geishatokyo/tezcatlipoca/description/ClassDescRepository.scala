package com.geishatokyo.tezcatlipoca.description

import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConversions._

/**
 *
 * User: takeshita
 * Create: 12/07/01 19:12
 */

class ClassDescRepository(
                           builders : Seq[ClassDescBuilder],
                           defaultBuilder : ClassDescBuilder) {

  def this(builders : Seq[ClassDescBuilder]) = {
    this(builders,builders.last)
  }

  protected val repo : scala.collection.mutable.ConcurrentMap[Manifest[_],ClassDesc[_]] =
    new ConcurrentHashMap[Manifest[_],ClassDesc[_]]()

  def apply( m : Manifest[_]) : ClassDesc[_] = {

    if (repo.contains(m)){
      return repo(m)
    }
    m.erasure.synchronized{
      if(!repo.contains(m)){
        val builder = findBuilder(m)
        val desc = builder.getClassDesc(m)
        repo +=( m -> desc)

        desc
      }else{
        repo(m)
      }
    }
  }

  protected def findBuilder(m : Manifest[_]) = {
    builders.find(cb => cb.loadableClass_?(m)).getOrElse(defaultBuilder)
  }

  def register(m : Manifest[_], classDesc : ClassDesc[_]) : Unit = {
    repo +=(m -> classDesc)
  }

  def modify[T](m : Manifest[T] , modifyMethod : ClassDesc[T] => ClassDesc[T]) : Unit = {
    m.erasure.synchronized{
      val desc = apply(m)
      val modified = modifyMethod(desc.asInstanceOf[ClassDesc[T]])
      repo +=( m -> modified)
    }
  }


  override def toString() = {
    """Builders : %s
DefaultBuilder : %s
Registered : %s""".format(
      builders.mkString("(",",",")"),
      defaultBuilder,
      repo.keys.mkString("(",",",")")
    )
  }

}
