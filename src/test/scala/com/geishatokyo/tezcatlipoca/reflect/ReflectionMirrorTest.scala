package com.geishatokyo.tezcatlipoca.reflect

import org.specs2.mutable.SpecificationWithJUnit
import template.TemplateRegistry

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 3:00
 * To change this template use File | Settings | File Templates.
 */

class ReflectionMirrorTest extends SpecificationWithJUnit {

  val mirror = new ReflectionMirror(TemplateRegistry())
  "ReflectionMirror" should{
    "reflect object" in{

      val a1 = new A1()
      a1.name = "a1"

      val a2 = new A2()
      mirror.reflect(a1,a2) must_== 2

      a2.age must_== a1.age
      a2.name must_== a1.name
      a2.sex must_!= a1.sex
    }

    "to Map[String,String]" in{
      val m = mirror.reflect(new A2)
      println(m)
      m.size must_== 4
    }

    "set from Map" in{
      val a2 = new A2
      mirror.reflectFromMap(Map("name" -> "hoge","age" -> "22"),a2) must_== 2

      a2.name must_== "hoge"
      a2.age must_== 22
    }
  }

}

class A1{
  var name : String = ""
  var age : Int = 232
  var sex : Int = 2333
}

class A2{
  var name : String = ""
  var nickname : String = ""
  var age : Int = 5323
  var sex : Byte = 1

}