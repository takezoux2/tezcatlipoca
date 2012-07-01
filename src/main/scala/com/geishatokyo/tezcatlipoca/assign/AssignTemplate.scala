package com.geishatokyo.tezcatlipoca.assign

/**
 *
 * User: takeshita
 * Create: 12/07/01 18:21
 */

trait AssignTemplate[A, B] {

  def assign(from: A, to: B): Int
}
