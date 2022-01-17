package app.instap
package rdp

final case class Point(key: Double, value: Double) {
  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  def perpendicularDistance(
      lineStart: Point,
      lineEnd: Point,
    ): Double = {

    var dx: Double = lineEnd.key - lineStart.key
    var dy: Double = lineEnd.value - lineStart.value

    // Normalize
    val mag = Math.hypot(dx, dy)
    if (mag > 0.0) {
      dx /= mag
      dy /= mag
    }

    val pvx = key - lineStart.key
    val pvy = value - lineStart.value

    // Get dot product (project pv onto normalized direction)
    val pvdot = dx * pvx + dy * pvy

    // Scale line direction vector and subtract it from pv
    val ax = pvx - pvdot * dx
    val ay = pvy - pvdot * dy

    Math.hypot(ax, ay)
  }
}
